from microbit import *
import radio
import random

# Constantes
CARD_ID = "C1" #Cette carte
CARD_AUTH = "C2" #Cartes autorisées
CHANNEL = 5 #Canal pour RF
alphabet = 'ABCDEFGHIJKLNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789(),:."'
key = 'm9lDLfJcv3rTGH:Q4oI(UbWkiEdga6OAZ"RqjYs.F0,B)8ezPtypVN7Kw1XxSu2hC5n' #Clef de chiffrement par substitution

# Config Radio
radio.on() #Lancement du module radio
radio.config(channel=CHANNEL, length=251, queue=1) #Configuration radio

# Config Serial
uart.init(baudrate=115200) #Configuration serial

# fonctions
def send(m): #Fonction envoie série
    b = []
    for c in m:
        b.append(ord(c)) #Ajout du caractère UNICODE
    d = bytes(b) #Transformation en octet
    uart.write(d)

def checksum(msg): #Fonction de checksum
  v = 21
  for c in msg:
    v ^= ord(c)
  return v

def decrypt(cipher, key, alphabet): #Fonction de dechiffrement par substitution
    keyMap = dict(zip(key, alphabet))
    return ''.join(keyMap.get(c, c) for c in cipher)

while True:

    incomingMSG = radio.receive() #Reception RF

    if incomingMSG != None:
        tab_receive = incomingMSG.split('/') #Decoupage de la trame reçu
        msg_checksum = checksum(tab_receive[2]) #Checksum de la partie DATA pour comparaison
        incomingCHEKSUM = tab_receive[3] #Checksum reçu

        if tab_receive[0]=='C2' and str(msg_checksum) == str(incomingCHEKSUM): #Check ID carte et checksum
            
            msg_ack = CARD_ID+"/"+str(tab_receive[1])+"/"+"1" #String ACK
            msg_decode = decrypt(tab_receive[2],key,alphabet) #Déchiffrement

            if tab_receive[1]=='1': #Message de reset de la table
                msg_start = 'STARTDATA'
                send(msg_start) #Envoie UART
                
            msg_decode = msg_decode.replace("(", "{") #Formatage pour le JSON du process python
            msg_decode = msg_decode.replace(")", "}")
            send(msg_decode) #Envoie UART
            radio.send(str(msg_ack))  #Envoie message ACK
            sleep(200)
            radio.send(str(msg_ack))
            sleep(200)
            display.show(tab_receive[1]) #Visuel de vérification
            incomingMSG = None #Mise à zero
          