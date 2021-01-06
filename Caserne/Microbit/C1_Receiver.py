from microbit import *
import radio

# Constantes
CARD_ID = "C1"
CARD_AUTH = "C2"
CHANNEL = 5


# config radio
radio.on()
radio.config(channel=CHANNEL, length=251)

# functions
def send(m): #Fonction envoie série
    b = []
    for c in m:
        b.append(ord(c))
    d = bytes(b)
    uart.write(d)

#Variables
incomingJSON = ""

while True:
    incomingJSON = radio.receive()
    display.scroll(str(incomingJSON))

"""
    incomingJSON = radio.receive()
    if incomingJSON != None:
        # string part data recu
        if checkPassword(parsePassword(incomingJSON)):
            order = parseData(incomingJSON)[0]
            display.scroll(order)
    #add_text(0,2,getJSONToSend()[73:80]) # string part data envoyé
    radio.send(getJSONToSend())


    # Réception des valeurs [Json]
    incomingJSON = radio.receive()
    if incomingJSON != None:
        if checkPassWord(parsePassword(incomingJSON)):
            # Partie acknowledge
            send(incomingJSON)
      
"""