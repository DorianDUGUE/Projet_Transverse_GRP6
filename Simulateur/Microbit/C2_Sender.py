from microbit import *
import radio
import random

# Constantes
CARD_ID = "C2" #Cette carte
CARD_AUTH = "C1" #Cartes autorisées
CHANNEL = 5 #Canal pour RF
alphabet = 'ABCDEFGHIJKLNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789(),:."'
key = 'm9lDLfJcv3rTGH:Q4oI(UbWkiEdga6OAZ"RqjYs.F0,B)8ezPtypVN7Kw1XxSu2hC5n' #Clef de chiffrement par substitution

# Config Radio
radio.on() #Lancement du module radio
radio.config(channel=CHANNEL, length=251, queue=1) #Configuration radio

# Config Serial
uart.init(baudrate=115200) #Configuration serial

#Fonctions
def uart_receive(): #Fonction recéption série
    ACK = 1         #Création du compteur d'ACK
    var = ""		#Création de la variable contenant le string serie

    if uart.any():	#Vérification données en entrée
        lecture_uart = uart.read() #Lecture des données

        for char in lecture_uart:	#Boucle pour chaque caractères
            var = var + chr(char) #Ajout dans le string

            if chr(char) == ")": #Decouapge d'une trame
                encryptstring = encrypt(var,key,alphabet) #Chiffrement
                checksum_string = checksum(encryptstring) #Checksum
                stringToSend = CARD_ID+"/"+str(ACK)+"/"+encryptstring+"/"+str(checksum_string) #Création de la string à envoyer
                
                radio.send(str(stringToSend)) #Envoie RF

                string_receive = radio.receive() #Reçu ACK RF

                while string_receive == None: #Boucle de recherche
                    string_receive = radio.receive()

                tab_receive = string_receive.split('/') #Decoupage des valeurs reçu

                if tab_receive[0]!="C1" and tab_receive[1]!=ACK and tab_receive[2]!="1":
                    radio.send(stringToSend)
                    sleep(3000)
                    string_receive = radio.receive()
                    tab_receive = string_receive.split("/")

                display.show(ACK) #Visuel de vérification
                var = ""                #Mise à zero
                string_receive = None   #Mise à zero
                sleep(200)
                ACK+=1      #Augmentation du compteur
                if ACK == 9:
                    ACK = 2
                sleep(1000)

def checksum(msg): #Fonction de checksum
  v = 21
  for c in msg:
    v ^= ord(c)
  return v

def encrypt(plaintext, key, alphabet):#Fonction de chiffrement par substitution
    keyMap = dict(zip(alphabet, key)) #Création d'un tuple puis d'un dictionnaire
    return ''.join(keyMap.get(c, c) for c in plaintext) #Mélange selon la clé

while True:    # Réception du message série
    uart_receive() #Appel fonction