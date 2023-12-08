import csv
import matplotlib.pyplot as plt
from collections import Counter

csv_nimi = "m_tiedosto.csv"

data = {}

komento = ""

while komento != "q":
    komento = str(input("Komento (tiedosto.csv / show  / q) : "))
    if komento == "show":

        print(str(len(data)) + " eri ehdokasta")

        k = Counter(data)
        top20 = k.most_common(20)

        for i in top20:
            print(i[0], " :", i[1], " ")

        help = data.copy()

        for key, item in help.items():
            if item < top20[19][1]:
                del data[key]

        names = list(data.keys())
        values = list(data.values())
        plt.bar(range(len(data)), values, tick_label=names)
        plt.show()

        continue

    elif komento == "q":
        break

    else:
        csv_nimi = komento

    try:

        with open(csv_nimi, newline='',) as csvfile:
            data = {}
            reader = csv.reader(csvfile, delimiter=',')
            lista = []

            for item in reader:
                lista.append(item)

            for rivi in lista:

                amount = str(rivi[6])
                msg = str(rivi[11].replace(" ", "").lower())
                headers = ["s", "v", "l"]

                if amount != "Amount":

                    if msg != "" and msg[0] in headers and len(msg) < 6:

                        if msg[0:2] == "sl":
                            apu = str(msg[2:])
                            msg = "s" + apu

                        elif msg[0] == "l":
                            apu = str(msg[1:])
                            msg = "s" + apu

                        if msg not in data:
                            data[msg] = float(amount.replace(",", "."))

                        else:
                            data[msg] += float(amount.replace(",", "."))


        csvfile.close()

    except:
        print("Väärä tiedosto!")
