from tkinter import *
import MySQLdb
import serial
import json

""" initialisation de base """
master = Tk()
SERIALPORT = "COM5"
BAUDRATE = 115200
ser = serial.Serial()
DB_curs = None
DB_co = None

""" fonction initialisant l'ensemble des connections (UART + BDD) """
def init():
        global DB_curs
        global DB_co
        stop['state'] = 'normal'
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

""" Fonction pour mettre le message recu sous le bon format afin de le traiter """
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

def Stop_Click():
        stop['state'] = 'disabled'

def check():
        if stop['state'] == 'normal':
                return True
        else:
                b['state'] = 'normal'
                return False
        
""" Fonction qui va faire la requête et écrire chaque résultat sur l'UART """
def write_scales():
    global DB_curs
    global DB_co
    b['state'] = 'disabled'
    stop['state'] = 'normal'
    while check():
        msg = ReceiveUARTMessage()
        print ("msg :" , msg)
        if "STARTDATA" in msg:
            ReintialiseBD()
        elif len(msg) > 20:
            msg = ParseFormat(msg)
            InsertBD(msg)
        else:
            print("Pas pris en compte")
            
                

""" Pour l'instant lecture en UART et écriture bdd lors de l'appuie de Start """
b = Button(master, text="Start", highlightcolor="blue", command = write_scales, state="disabled")
Btn=Button(master, text="Initialisation", highlightcolor="blue", command = init)
stop = Button(master, text="STOP", highlightcolor="blue",command = Stop_Click, state="normal")

b.grid(row=6,column=7,columnspan = 3)
Btn.grid(row=6, column=0, columnspan = 3)
stop.grid(row=6, column=14,columnspan = 3)

mainloop()
