from tkinter import *
import MySQLdb
import serial

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
    b['state'] = 'disabled'
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
            print(ligne)
            num = ligne[0]
            print(num)
            column = ligne[1]
            print(column)
            row = ligne[2]
            print(row)
            value = ligne[3]
            if (value > 0) :
                string = "{Source : Serv1, Capteur : " + str(num) + ", PosX : " + str(row) + ", PosY : " + str(column) + ", Intensite : " + str(value) + "}"
                print ("Envoie UART :")
                print(string)
                sendUARTMessage(string)
        b['state'] = 'normal'

""" Pour l'instant envoie en UART lors de l'appuie de Start """
b = Button(master, text="Start", highlightcolor="blue", command = read_scales, state="disabled")
Btn=Button(master, text="Initialisation", highlightcolor="blue", command = init)

b.grid(row=6,column=7,columnspan = 3)
Btn.grid(row=6, column=0, columnspan = 3)

mainloop()
