import time
import serial
import requests


time.sleep(30)

URL = "https://api.spot-hinta.fi/JustNow"
location = "palvelin"
PARAMS = {'address':location}

r = requests.get(url=URL, params=PARAMS)

data = r.json()
 
hinta = data['PriceWithTax']
pvm = data['DateTime']
txt = "Aika: {aika} \nHinta: {arvo:.2f} snt"
print(txt.format(aika= pvm, arvo= hinta))

digit = str(hinta).split('.')

i1 = digit[1][0]
i2 = digit[1][1]


arduino = serial.Serial('/dev/ttyUSB0', 9600, timeout=.1)
time.sleep(1)

while True:
   
    arduino.write((i2 + i1).encode())

    try:
        data = arduino.readline().decode('UTF-8')
    except:
        print()
        
    if data:
        data = str(data)
        print(data)
        time.sleep(1)
        
    if data == 'GG':
        
        r = requests.get(url=URL, params=PARAMS)

        data = r.json()
         
        hinta = data['PriceWithTax']
        pvm = data['DateTime']
        txt = "Aika: {aika} \nHinta: {arvo:.2f} snt"
        print(txt.format(aika= pvm, arvo= hinta))

        digit = str(hinta).split('.')

        i1 = digit[1][0]
        i2 = digit[1][1]
        
        time.sleep(30)
