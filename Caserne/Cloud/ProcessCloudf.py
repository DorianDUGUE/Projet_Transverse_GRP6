import influxdb_client
from influxdb_client.client.write_api import SYNCHRONOUS
import paho.mqtt.client as mqtt

""" Initialisation MQTT """
client = mqtt.Client("InfluxDb_MQTT_Client2")

""" Constante Influxdb """
user = 'admin'
password = 'adminadmin'
database = 'DBinfluxdb'
orga = 'my-org'
token = 'WAlNMsqWBrC6Rox_ijzNC4M5QNIwASLOWZUxosbdjmoBRGbZNLC1SHGh2-Rpt4PmTU6O7_ls8JN6qF_n4Nu7bg=='
url = "127.0.0.1:8086"

""" Initialisation InfluxDB """
client = influxdb_client.InfluxDBClient(
    url=url,
    token=token,
    org=orga
    )
write_api = client.write_api(write_options=SYNCHRONOUS)

def WriteInflu(Table, Champ, Val):
    global write_api
    p = influxdb_client.Point(Table).field(Champ, Val)
    print("[", Table , "] : ", Champ, "( ", Val, " )")
    write_api.write(bucket=database, org=orga, record=p)

def on_connect(client, userdata, flags, rc):
    print("Connected with result code "+str(rc))
    client.subscribe([("Capteur",0),("Incendie",0),("Camion",0),("Intervention",0)])

def on_message(client, userdata, message):
    msg = str(message.payload.decode("utf-8"))
    print("[Topic] ", message.topic, "[MSG] ", msg)
    msg = msg.split("|")
    switch (message.topic){
        case 'Capteur':
            WriteInflu('Capteur','id',msg[0])
            WriteInflu('Capteur','PosX',msg[1])
            WriteInflu('Capteur','PosY',msg[2])
            WriteInflu('Capteur','Valeur',msg[3])
            break;
        case 'Camion':
            WriteInflu('Camion','id',msg[0])
            WriteInflu('Camion','PosX',msg[1])
            WriteInflu('Camion','PosY',msg[2])
            WriteInflu('Camion','Capacite',msg[3])
            break;
        case 'Incendie':
            WriteInflu('Incendie','id',msg[0])
            WriteInflu('Incendie','Etat',msg[1])
            WriteInflu('Incendie','idIntervention',msg[2])
            WriteInflu('Incendie','NumCapteur',msg[3])
            break;
        case 'Intervention':
            WriteInflu('Intervention','id',msg[0])
            WriteInflu('Intervention','HeureDebut',msg[1])
            WriteInflu('Intervention','numCamion',msg[2])
            break;
        default:
            print ("[ERREUR] Topic inconnu")
        }


if __name__ == "__main__" :
    print("Début du programme")
    client.on_message = on_message
    client.on_connect = on_connect
    client.connect("127.0.0.1")
    client.loop_forever()
