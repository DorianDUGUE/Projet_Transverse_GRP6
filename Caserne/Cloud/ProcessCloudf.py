import influxdb_client
"""
 import InfluxDBClient, Point, WriteOptions
 """
from influxdb_client.client.write_api import SYNCHRONOUS
import paho.mqtt.client as mqtt

""" Initialisation MQTT """
client_mqtt = mqtt.Client("InfluxDb_MQTT_Client2")

""" Constante Influxdb """
user = 'admin'
password = 'adminadmin'
database = 'DBinfluxdb'
orga = 'my-org'
token = 'WAlNMsqWBrC6Rox_ijzNC4M5QNIwASLOWZUxosbdjmoBRGbZNLC1SHGh2-Rpt4PmTU6O7_ls8JN6qF_n4Nu7bg=='
url = "http://127.0.0.1:8086"

""" Initialisation InfluxDB """
client_DB = influxdb_client.InfluxDBClient(
    url=url,
    token=token,
    org=orga
    )
write_api = client_DB.write_api(write_options=SYNCHRONOUS)

def on_connect(client_mqtt, userdata, flags, rc):
    print("Connected with result code "+str(rc))
    client_mqtt.subscribe([("Capteur",0),("Incendie",0),("Camion",0),("Intervention",0)])

def on_message(client_mqtt, userdata, message):
    msg = str(message.payload.decode("utf-8"))
    print("[Topic] ", message.topic, "[MSG] ", msg)
    if msg != "":
        msg = msg.split("|")
        if message.topic ==  'Capteur':
            p = [Point("Capteur").field("id", int(msg[0])),
                Point("Capteur").field("PosX", float(msg[1])),
                Point("Capteur").field("PosY", float(msg[2])),
                Point("Capteur").field("Valeur", float(msg[3]))
                ]
            write_api.write(bucket=database, org=orga, record=p)
            print("finis message")
        elif message.topic == 'Camion':
            p = [Point("Camion").field("id", int(msg[0])),
                Point("Camion").field("PosX", float(msg[1])),
                Point("Camion").field("PosY", float(msg[2])),
                Point("Camion").field("Capacite", float(msg[3]))
                ]
            write_api.write(bucket=database, org=orga, record=p)
            print("finis message")
        elif message.topic == 'Incendie':
            p = [Point("Incendie").field("id", int(msg[0])),
                Point("Incendie").field("Etat", float(msg[1])),
                Point("Incendie").field("idIntervention", float(msg[2])),
                Point("Incendie").field("NumCapteur", float(msg[3]))
                ]
            write_api.write(bucket=database, org=orga, record=p)
            print("finis message")
        elif message.topic == 'Intervention':
            p = [Point("Capteur").field("id", int(msg[0])),
                Point("Capteur").field("HeureDebut", float(msg[1])),
                Point("Capteur").field("numCamion", float(msg[2]))
                ]
            write_api.write(bucket=database, org=orga, record=p)
            print("finis message")
        else:
            print ("[ERREUR] Topic inconnu")
    


if __name__ == "__main__" :
    print("Début du programme")
    client_mqtt.on_message = on_message
    client_mqtt.on_connect = on_connect
    client_mqtt.connect('127.0.0.1')
    client_mqtt.loop_forever()
