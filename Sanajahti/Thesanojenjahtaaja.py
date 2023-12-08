def etsiSanaRekursiivisesti(patka, indeksit, kirjaimikko, rivit):

    indeksi_x = 9
    indeksi_y = 9
    rivi = 9
    kirjain = patka[0]
    rivit2 = [rivit[0].copy(), rivit[1].copy(), rivit[2].copy(), rivit[3].copy()]
    # etsitään ensimmäisen kirjaimmen indeksi:
    iteraatiot = kirjaimikko.count(kirjain)

    for i in range(0, iteraatiot):
        indeksit1 = indeksit.copy()
        indeksit2 = indeksit.copy()
        indeksit3 = indeksit.copy()
        indeksit4 = indeksit.copy()

        if kirjain in rivit2[0]:
            indeksi_y, indeksi_x = 0, rivit2[0].index(kirjain)
            if ([indeksi_y, indeksi_x] not in indeksit):

                if ((abs(indeksi_y - indeksit1[-1][0]) <= 1) and  (abs(rivit2[indeksi_y].index(kirjain) - indeksit1[-1][1]) <= 1)):
                    if len(patka) == 1:
                        return True
                    else:
                        indeksit1.append([indeksi_y, indeksi_x])
                        if etsiSanaRekursiivisesti(patka[1:], indeksit1,
                                                   kirjaimikko, rivit):
                            return True

        if kirjain in rivit2[1]:
            indeksi_y, indeksi_x = 1, rivit2[1].index(kirjain)
            if ([indeksi_y, indeksi_x] not in indeksit):
                if ((abs(indeksi_y - indeksit2[-1][0]) <= 1) and  (abs(rivit2[indeksi_y].index(kirjain) - indeksit2[-1][1]) <= 1)):
                    if len(patka) == 1:
                        return True
                    else:
                        indeksit2.append([indeksi_y, indeksi_x])
                        if etsiSanaRekursiivisesti(patka[1:], indeksit2,
                                                   kirjaimikko, rivit):
                            return True

        if kirjain in rivit2[2]:
            indeksi_y, indeksi_x = 2, rivit2[2].index(kirjain)
            if ([indeksi_y, indeksi_x] not in indeksit):
                if ((abs(indeksi_y - indeksit3[-1][0]) <= 1) and  (abs(rivit2[indeksi_y].index(kirjain) - indeksit3[-1][1]) <= 1)):
                    if len(patka) == 1:
                        return True
                    else:
                        indeksit3.append([indeksi_y, indeksi_x])
                        if etsiSanaRekursiivisesti(patka[1:], indeksit3,
                                                   kirjaimikko, rivit):
                            return True

        if kirjain in rivit2[3]:
            indeksi_y, indeksi_x = 3, rivit2[3].index(kirjain)
            if ([indeksi_y, indeksi_x] not in indeksit):
                if ((abs(indeksi_y - indeksit4[-1][0]) <= 1) and  (abs(rivit2[indeksi_y].index(kirjain) - indeksit4[-1][1]) <= 1)):
                    if len(patka) == 1:
                        return True
                    else:
                        indeksit4.append([indeksi_y, indeksi_x])
                        if etsiSanaRekursiivisesti(patka[1:], indeksit4,
                                                       kirjaimikko, rivit):
                            return True

        rivit2[indeksi_y][indeksi_x] = "#"

    return False

def main():
    print("Syötä kirjaimikko:")
    kirjaimikko = input("> ")

    rivi1 = list(kirjaimikko[0:4])
    rivi2 = list(kirjaimikko[4:8])
    rivi3 = list(kirjaimikko[8:12])
    rivi4 = list(kirjaimikko[12:16])
    rivit = [rivi1, rivi2, rivi3, rivi4]

    SANALISTA = "sanalista.txt"
    tiedosto = open(SANALISTA, mode="r")
    sanat = []

    for rivi in tiedosto:
        sana = rivi.rstrip()

        indeksi_x2 = 0
        indeksi_y2 = 0
        kirjain = sana[0]
        rivit2 = [rivi1.copy(), rivi2.copy(), rivi3.copy(), rivi4.copy()]
        # etsitään ensimmäisen kirjaimmen indeksi:
        iteraatiot = kirjaimikko.count(kirjain)

        for i in range(0, iteraatiot):

            indeksit = []
            if kirjain in rivit2[0]:
                indeksi_y, indeksi_x = 0, rivit2[0].index(kirjain)

            elif kirjain in rivit2[1]:
                indeksi_y, indeksi_x = 1, rivit2[1].index(kirjain)

            elif kirjain in rivit2[2]:
                indeksi_y, indeksi_x = 2, rivit2[2].index(kirjain)

            elif kirjain in rivit2[3]:
                indeksi_y, indeksi_x = 3, rivit2[3].index(kirjain)

            rivit2[indeksi_y][indeksi_x] = "#"

            indeksit.append([indeksi_y, indeksi_x])
            if etsiSanaRekursiivisesti(sana[1:], indeksit, kirjaimikko, rivit):
                sanat.append(sana)
                break

    sanat.sort(key=len, reverse=True)
    for rivi in sanat:
        if len(rivi) > 2:
            print(rivi)
        else:
            break


if __name__ == '__main__':
    main()

