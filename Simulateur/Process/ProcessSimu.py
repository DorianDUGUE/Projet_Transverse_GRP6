from tkinter import *
import MySQLdb, serial

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
                                               db='simulation')
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
        

""" Fonction pour écrire simplement le message en UART """
def sendUARTMessage(msg):
    ser.write(msg.encode())

""" Fonction qui va faire la requête et écrire chaque résultat sur l'UART """
def read_scales():
    global DB_curs
    if loop:
        try:
                req = 'SELECT * FROM capteur where valeur > 0;'
                DB_curs.execute(req)
        except Exception as e:
                print("Requête SQL incorrecte :\n{}".format(req))
                print("[ERREUR] ", e)
                return 0
        else:
                print ("Résultat requête :")
                liste = DB_curs.fetchall()
                print (liste)
                for ligne in liste:
                    num = ligne[0]
                    column = ligne[1]
                    row = ligne[2]
                    value = ligne[3]
                    if (value > 0) :
                        string = "{Source : Serv1, Capteur : " + str(num) + ", PosX : " + str(row) + ", PosY : " + str(column) + ", Intensite : " + str(value) + "}"
                        print ("Envoie UART :")
                        print(string)
                        sendUARTMessage(string)
        master.after(10000 , read_scales)
        


""" Fonction pour stoper le programme """
def Stop_Click():
        global loop
        loop = False
        b['state'] = 'normal'
        stop['state'] = 'disabled'

""" Fonction pour lancer le programme """
def Start_Click():
        global loop
        loop = True
        stop['state'] = 'normal'
        b['state'] = 'disabled'
        master.after(0 , read_scales)

        

""" Pour l'instant envoie en UART lors de l'appuie de Start """
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
