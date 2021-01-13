import paho.mqtt.client as mqtt
import MySQLdb, time, serial, json
from tkinter import *
from threading import Thread

""" initialisation de base """
master = Tk()
master.title("Process Caserne")
SERIALPORT = "COM6"
BAUDRATE = 115200
ser = serial.Serial()
client = mqtt.Client("SP_EmergencyManager")
client.connect("127.0.0.1")
DB_curs = None
DB_co = None
loop = False
threads = []

""" fonction initialisant l'ensemble des connections (UART + BDD) """
def init():
    global DB_curs
    global DB_co
    if Btn['text'] == "Initialisation":
            ser.port=SERIALPORT
            ser.baudrate=BAUDRATE
            ser.bytesize = serial.EIGHTBITS
            ser.parity = serial.PARITY_NONE
            ser.stopbits = serial.STOPBITS_ONE
            ser.timeout = 1
            ser.xonxoff = False 
            ser.rtscts = False
            ser.dsrdtr = False
            print ("Starting Up Serial Monitor")
            try:
                    ser.open()
            except serial.SerialException:
                    print("Serial {} port not available".format(SERIALPORT))
                    exit()
            print ("Connection to database")
            try:
                DB_co = MySQLdb.connect(host='127.0.0.1',
                                           user='simu',
                                           passwd='simu',
                                           db='DBCaserne')
            except Exception as e:
                print("Une erreur est survenue la connection à échoué")
                print("[ERREUR] ", e)
            else:
                DB_curs = DB_co.cursor()
                print("Succesfully Connected to database")
            Btn['text'] = "Réinitialiser"
            b['state'] = 'normal'
    else:
            ser.close()
            if DB_co :
                DB_curs.close()
                DB_co.close()
            Btn['text'] = "Initialisation"
            b['state'] = 'disabled'

""" Fonction pour lire simplement les messages en UART """
def ReceiveUARTMessage():
    s = ""
    m = ser.readline()
    for b in m:
        s = s + chr(b)
    return s

""" Fonction pour mettre le message recu UART sous le bon format afin de le traiter pour insertion BDD """
def ParseFormat(msg):
    try:    
        temp = json.loads(msg)
        liste = ( temp["Capteur"], temp["PosX"], temp["PosY"], temp["Intensite"] )
        print ("Reception UART : ", liste)
        return liste
    except Exception as e:
        print("Parse Json en erreur")
        print("[ERREUR] ", e)
        return 0

""" Fonction pour exécuter la commande de réinitialisation de la base de données """
def ReintialiseBD():
    global DB_curs
    global DB_co
    print ("Nettoyage de la table")
    try:
        req = 'TRUNCATE TABLE `Capteur`;'
        DB_curs.execute(req)
        DB_co.commit()
    except Exception as e:
        print("Requête SQL incorrecte :\n{}".format(req))
        print("[ERREUR] ", e)
    else:
        print("Truncate réussi")

""" Fonction pour insérer les valeurs de capteurs """
def InsertBD(data):
    global DB_curs
    global DB_co
    print("requete : ")
    print('INSERT INTO capteur(id,x,y,valeur) VALUES(', data[0], ',', data[1], ',', data[2], ',', data[3], ');')
    try:
        ''' (id, x, y, val) 'INSERT INTO capteur VALUES(?,?,?,?);' '''
        req = 'INSERT INTO Capteur(id,x,y,valeur) VALUES('+ str(data[0])+ ','+ str(data[1])+ ','+ str(data[2])+ ','+ str(data[3])+ ');'
        DB_curs.execute(req)
        DB_co.commit()
    except Exception as e:
        print("Requête SQL incorrecte :\n{}".format(req))
        print("[ERREUR] ", e)
    else:
        print ("Insertion BDD réussi")

""" Fonction pour envoyer en mqtt un message """
def sendMQTT(dest, msg):
    if not "|" in msg:
        print("[ERREUR] format incorrect", msg)
    else:
        print("[DEST]",dest,"[MSG]",msg)
        res = client.publish(dest, msg)

""" Fonction pour lire les données de la table passé en argument et envoyé en mqtt """
def GetDB(Table):
    global DB_curs
    global DB_co
    try:
        req = 'SELECT * FROM '+ Table + ';'
        print (req)
        DB_curs.execute(req)
    except Exception as e:
        print("Requête SQL incorrecte :\n{}".format(req))
        print("[ERREUR] ", e)
    else:
        print ("Résultat requête :")
        liste = DB_curs.fetchall()
        print (liste)
        for ligne in liste:
            msg = ""
            for res in ligne:
                msg = msg + str(res) + "|"
            msg = msg[:-1]
            sendMQTT(Table, msg)

""" Process pour la partie MQTT """
def ProcessMQTT():
    global loop
    if loop:
        GetDB("Capteur")
        GetDB("Incendie")
        GetDB("Camion")
        GetDB("Intervention")
        time.sleep(15)
        ProcessMQTT()

""" Fonction qui va faire la requête et écrire chaque résultat sur l'UART """
def write_scales():
    global DB_curs
    global DB_co
    global loop
    if loop:
        msg = ReceiveUARTMessage()
        if msg != "":
            print ("msg :" , msg)
            if "STARTDATA" in msg:
                """ print ("TRUNCATE TABLE 'Capteur'") """
                ReintialiseBD() 
                msg = msg.replace('STARTDATA', '')
            if len(msg) > 20:
                liste = msg.split("}")
                for i in range(len(liste) - 1):
                    print(liste[i])
                    msg = ParseFormat(liste[i] + "}")
                    if msg != 0:
                        InsertBD(msg)
                    """ print('INSERT INTO Capteur(id,x,y,valeur) VALUES('+ str(msg[0])+ ','+ str(msg[1])+ ','+ str(msg[2])+ ','+ str(msg[3])+ ');') """
            elif msg != "":
                print("Pas pris en compte")
                print(msg)
        write_scales()

""" Fonction pour stoper le programme """
def Stop_Click():
        global loop
        loop = False
        b['state'] = 'normal'
        stop['state'] = 'disabled'
        for p in threads:
            p.join()

""" Fonction pour lancer le programme """
def Start_Click():
        global loop
        loop = True
        stop['state'] = 'normal'
        b['state'] = 'disabled'
        p = Thread(target=write_scales)
        p.start()
        threads.append(p)
        p = Thread(target=ProcessMQTT)
        p.start()
        threads.append(p)

Btn  = Button(master, text="Initialisation", highlightcolor="blue", command = init)
b    = Button(master, text="Start", highlightcolor="blue", command = Start_Click, state="disabled")
stop = Button(master, text="STOP", highlightcolor="blue",command = Stop_Click, state="disabled")

Btn.grid(row=0, column=0, columnspan = 3)
b.grid(row=0,column=7,columnspan = 3)
stop.grid(row=0, column=14,columnspan = 3)

if __name__ == "__main__" :
    print("Début du programme")
    master.mainloop()
    print("Fin du programme")
        
