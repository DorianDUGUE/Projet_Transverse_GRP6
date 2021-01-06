from microbit import *
import radio

# Constantes
CARD_ID = "C2"
CARD_AUTH = "C1"
CHANNEL = 5
#ASCII = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!"#$%&\'()*+,-./:;<=>?@[\\]^_`{|}~ \t\n\r\x0b\x0c'

# Config Radio
radio.on()
radio.config(channel=CHANNEL, length=251)


# Config Serial
uart.init(baudrate=115200)

def send(m): #Fonction envoie série
    b = []
    for c in m:
        b.append(ord(c))
    d = bytes(b)
    uart.write(d)

def uart_receive(): #Fonction recéption série
    s = ""
    if uart.any():
        m = uart.read()
        for b in m:
            s = s + chr(b)
    return s

#Initialiser le tableau puis le remplir dès que le caractère ; arrive
"""
Code de la partie UART :

def uart_receive(): #Fonction recéption série
    tab = []
    var = ""		#Création du string
    if uart.any():	#Vérification données en entrée
        lecture_uart = uart.read() #Lecture des données
        for char in lecture_uart:	#Boucle pour chaque caractères
            var = var + chr(char) #Ajout dans le string
            if var == ";":
                tab.append(var)
                var = ""		#Mise à zero
    return tab	
   }

"""

#Variables
tab_data = []
boucle = False
ACK = 0

while True:

    # Réception du message série
    DATA = uart_receive()
    sleep(20)

    #Construction de la trame
    stringToSend = CARD_ID + "," + str(ACK) + "," + DATA

    if string != "":
        # Envoie du de la trame
        radio.send(string)


