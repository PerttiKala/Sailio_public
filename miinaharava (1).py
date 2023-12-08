"""
Koodi kirjoitettu joulukuussa 2020
COMP.CS.100 Ohjelmointi 1,
Tekijät: Paulus Ylimäki, paulus.ylimaki@tuni.fi
         Henri Hosionaho, henri.hosionaho@tuni.fi

projekti: Miinaharava

Peli alkaa aloitus näytöllä, jossa kysytään ruudukon kokoa ja miinojen määrää.
Kun kelvolliset arvot on syötetty ja painetaan "Play" nappia ohjelma generoi
halutun kokoisen miinakentän. Itse miinat generoidaan vasta kun ensimmäinen
ruutu avataan, sillä ensimmäisen arvauksen kohdalle ei luoda miinoja.
"""
import random
from tkinter import *
import time


class StartUi:

    def __init__(self):
        """
        Luodaan aloitusnäyttö, jossa valitaan ruudukon koko ja miinojen määrä
        kahden entryn avulla. Pelin voi aloittaa aloitusnäyttö olevalla "Play"
        painikkeella, ja näytön voi sulkea "Quit" painikkeella.
        Aloitusnäyttö on jätetty tila myös virheilmoituksille.
        """

        self.__mainwindow = Tk()
        self.__otsikko_label = Label(self.__mainwindow, text="MIINAHARAVA",
                                     relief=SUNKEN, borderwidth=4,
                                     background="black", foreground="cyan",
                                     font=("Helvetica", 9, 'bold'))
        self.__ruudukko_entry = Entry(self.__mainwindow)
        self.__ruudukko_label = Label(self.__mainwindow, text="Ruudukon koko:")
        self.__miinat_entry = Entry(self.__mainwindow)
        self.__miinat_label = Label(self.__mainwindow, text="Miinojen määrä:")
        self.__error_label = Label(self.__mainwindow, text="")
        self.__aloitus_nappi = Button(self.__mainwindow, text="Play",
                                      borderwidth=4, relief=RAISED,
                                      background="green",
                                      command=self.startgame)
        self.__lopetus_nappi = Button(self.__mainwindow, text="Quit",
                                      borderwidth=4, relief=RAISED,
                                      background="red", command=self.stop
                                      )
        self.__otsikko_label.grid(row=0, column=0, columnspan=4, stick=W+E)
        self.__ruudukko_label.grid(row=2, column=0)
        self.__ruudukko_entry.grid(row=2, column=1)
        self.__miinat_label.grid(row=2, column=2)
        self.__miinat_entry.grid(row=2, column=3)
        self.__error_label.grid(row=3, column=0, columnspan=4, stick=W+E)
        self.__aloitus_nappi.grid(row=4, column=0, stick=W+E, columnspan=2)
        self.__lopetus_nappi.grid(row=4, column=2, stick=W+E, columnspan=2)

    def start(self):
        """
        Aloittaa mainloopin, eli avaa aloitusnäytön
        """
        self.__mainwindow.mainloop()

    def stop(self):
        """
        Lopettaa mainloopin, eli sulkee aloitusnäytön
        """
        self.__mainwindow.destroy()

    def startgame(self):
        """
        Luo peli-ikkunan käyttäen entryistä saatuja arvoja. Huolehtii lisäksi
        virhetilanteet ja virheilmoituksien näyttämisen.
        """
        # tarkastaa että ruudukon koko on annettu oikeassa muodossa
        # ja oikealta väliltä
        try:
            ruudukko = int(self.__ruudukko_entry.get())
            if not 4 < ruudukko < 21:
                self.__error_label["text"] = "error: Ruudun koon" \
                                             " tulee olla välillä 5-20"
                return
        except ValueError:
            self.__error_label["text"] = "error: Ruudun koon" \
                                         " tulee olla kokonaisluku"
            return

        # tarkastaa että miinojen määrä on annettu oikeassa muodossa
        # ja että miinoja ei ole liikkaa
        try:
            miinat = int(self.__miinat_entry.get())
            if not miinat <= ruudukko * ruudukko - 9:
                self.__error_label["text"] = "error: Liikaa miinoja"
                return

            elif 0 >= miinat:
                self.__error_label["text"] = "error: Miinojen määrän " \
                                            "tulee olla positiivinen kokonaisluku"
                return
        except ValueError:
            self.__error_label["text"] = "error: Miinojen määrän" \
                                         " tulee olla positiivinen kokonaisluku"
            return

        # jos ongelmia ei ollut tyhjennetään virheilmoitus
        # ja aloitetaan peli
        self.__error_label["text"] = ""
        GameUi(ruudukko, miinat).start()


class GameUi:

    def __init__(self, ruudukon_koko, miina_maara):
        """
        Luo peli-ikkunan ja siinä olevat napit. Määrittää peli-ikkunalle myös
        muut arvot joita pelissä tarvitaan.
        :param ruudukon_koko: kertoo koinka iso ruudukko luodaan
        :param miina_maara: kertoo kuinka monta miinaa generoidaan
        """

        self.__mainwindow = Tk()
        self.__miina_maara = miina_maara
        self.__ei_genoroitu = True
        self.__tappio = False
        self.__koko = ruudukon_koko
        self.__miinat = []  # Tähän tallennetaan nappeja vastaavat miinat ja numerot
        self.__ruudut = []  # Tähän tallennetaan luodut napit
        self.__avatut = []  # Lista avatuista ruuduista
        self.__lopputulos = Label(self.__mainwindow, text="   ")
        self.__ruutuja_jaljella = Label(self.__mainwindow, text="  ")
        self.__aika1 = 0
        self.__aika2 = 0
        self.__kesto = 0
        self.__aika_label = Label(self.__mainwindow, text="   ")

        # Luodaan napeille ja miinoille tyhjät matriisit
        for rivi in range(0, self.__koko):
            self.__ruudut.append([])
            self.__miinat.append([])
            for sarake in range(0, self.__koko):
                self.__ruudut[rivi].append(0)
                self.__miinat[rivi].append(0)

        # Luodaan napit ja asetetaan ne paikoilleen. Määritetään napit kutsumaan funktiota 'avaa_ruutu'
        # parametrina oma koordinaatti.
        for y in range(0, self.__koko):
            for x in range(0, self.__koko):
                self.__ruudut[y][x] = Button(self.__mainwindow, text=" ", width=2, borderwidth=3,
                                             relief=RAISED, background="grey",
                                             command=lambda x=x, y=y: self.avaa_ruutu(x, y))
                self.__ruudut[y][x].grid(row=y, column=x)
        # Asetetaan tekstikentät paikoilleen
        self.__lopputulos.grid(row=self.__koko+4, column=0, columnspan=3)
        self.__ruutuja_jaljella.grid(row=self.__koko+4, column=3, columnspan=3)
        self.__aika_label.grid(row=self.__koko+2, column =0, columnspan=7)

    def start(self):
        """
        Aloittaa mainloopin.
        """
        self.__mainwindow.mainloop()

    def avaa_ruutu(self, x, y):
        """
        Tämä metodi määrittelee mitä ruudukon napin
        painaminen tekee. Lisäksi se generoi miinat
        ensimmäisen painalluksen jälkeen.
        Ensimmäisenä painetun napin ympärille ei
        luoda miinoja.
        Metodin parametreinä on painetun
        napin x:n ja y:n arvo.
        :param x: int
        :param y:int
        """

        # Ensimmäisen painalluksen kohdalla generoidaan miinat
        # metodin 'generoi_miinat' avulla. Määritetään muuttujan 'ei_generoitu' arvoksi
        # False jolloin miinoja ei generoida uudestaan
        if self.__ei_genoroitu:
            self.generoi_miinat(x, y)
            self.__ei_genoroitu = False
            self.__aika1 = time.time()


        # Jos painetaan miinasta, käyttöliittymään ilmestyy teksti 'Game over!' ja
        # kohta 'jäljellä' näyttää arvoksi ':('. Lisäksi kutsutaan metodia 'game_over'
        # joka paljastaa mitä nappien takana todellisuudessa oli.
        if self.__miinat[y][x] == "x":
            self.__tappio = True
            self.__lopputulos.configure(text="Game over!")
            self.__ruutuja_jaljella.configure(text=f"Jäljellä: :(")
            self.game_over(False)

        # Jos painetaan tyhjästä kohdasta, poistetaan nappi käytöstä ja muutetaan
        # sen ulkonäköä. Lisätään koordinaatti listaan 'avatut' sekä kutsutaan
        # metodia 'katso_ruudut' joka avaa ympärillä olevat avaamattomat ruudut
        # tämän metodin avulla. Lisäksi päivitetään käyttöliittymän kohta
        # 'ruutuja_jäljellä', joka ilmoittaa monta ruutua on vielä avaamatta
        # miinoja lukuunottamatta.
        elif self.__miinat[y][x] == 0:
            self.__ruudut[y][x]["state"] = "disabled"
            self.__ruudut[y][x].configure(relief=SUNKEN, background="white")
            self.__avatut.append([x, y])
            self.katso_ruudut([x, y])
            self.__ruutuja_jaljella.configure(
                text=f"Jäljellä: {(self.__koko * self.__koko - self.__miina_maara) - len(self.__avatut)}")

        # Jos painetaan tyhjästä kohdasta, poistetaan nappi käytöstä ja muutetaan
        # se näyttämään numeroarvoaan. Lisäksi lisätään koordinaatti listaan 'avatut' ja päivitetään
        # käyttöliittymän kohta 'jäljellä'
        elif self.__miinat[y][x] != "x" and self.__miinat[y][x] != 0:
            self.__avatut.append([x, y])
            self.__ruudut[y][x]["state"] = "disabled"
            if self.__miinat[y][x] == 1:
                self.__ruudut[y][x].configure(relief=SUNKEN,
                                              background="white", text="1",
                                              font=("Helvetica", 9, 'bold'),
                                              disabledforeground="blue")
            elif self.__miinat[y][x] == 2:
                self.__ruudut[y][x].configure(relief=SUNKEN,
                                              background="white", text="2",
                                              disabledforeground="green",
                                              font=("Helvetica", 9, 'bold'))
            elif self.__miinat[y][x] == 3:
                self.__ruudut[y][x].configure(relief=SUNKEN,
                                              background="white", text="3",
                                              disabledforeground="orange",
                                              font=("Helvetica", 9, 'bold'))
            elif self.__miinat[y][x] == 4:
                self.__ruudut[y][x].configure(relief=SUNKEN,
                                              background="white", text="4",
                                              disabledforeground="red",
                                              font=("Helvetica", 9, 'bold'))
            elif self.__miinat[y][x] == 5:
                self.__ruudut[y][x].configure(relief=SUNKEN,
                                              background="white", text="5",
                                              disabledforeground="pink",
                                              font=("Helvetica", 9, 'bold'))
            elif self.__miinat[y][x] == 6:
                self.__ruudut[y][x].configure(relief=SUNKEN,
                                              background="white", text="6",
                                              disabledforeground="blue",
                                              font=("Helvetica", 9, 'bold'))
            elif self.__miinat[y][x] == 7:
                self.__ruudut[y][x].configure(relief=SUNKEN,
                                              background="white", text="7",
                                              disabledforeground="red",
                                              font=("Helvetica", 9, 'bold'))
            elif self.__miinat[y][x] == 8:
                self.__ruudut[y][x].configure(relief=SUNKEN,
                                              background="white", text="8",
                                              disabledforeground="violet",
                                              font=("Helvetica", 9, 'bold'))

            self.__ruutuja_jaljella.configure(
                text=f"Jäljellä: {(self.__koko * self.__koko - self.__miina_maara) - len(self.__avatut)}")

        if len(self.__avatut) + self.__miina_maara == self.__koko * self.__koko and self.__tappio is False:
            self.__lopputulos.configure(text="Voitit! :)")
            self.game_over(True)
            self.__aika2 = time.time()
            self.__kesto = self.__aika2 - self.__aika1

            try:

                data = open(f"{self.__koko}{self.__miina_maara}.txt", mode="r")
                lista = []
                for rivi in data:
                    if rivi != "":
                        lista.append(float(rivi.rstrip()))
                data.close()

                vanha_enkka = min(lista)
                if self.__kesto < vanha_enkka:
                    data2 = open(f"{self.__koko}{self.__miina_maara}.txt", mode="a")
                    data2.write(f"{self.__kesto}\n")
                    self.__aika_label.configure(text = f"Aika ({self.__koko}.{self.__miina_maara}): {self.__kesto:.2f} Enkka: {self.__kesto:.2f}")
                    data2.close()
                else:
                    self.__aika_label.configure(text = f"Aika ({self.__koko}.{self.__miina_maara}): {self.__kesto:.2f} Enkka: {vanha_enkka:.2f}")

            except OSError:

                data = open(f"{self.__koko}{self.__miina_maara}.txt", mode="w")
                data.write(f"{str(self.__kesto)}\n")
                vanha_enkka = self.__kesto
                self.__aika_label.configure(text = f"Aika ({self.__koko}.{self.__miina_maara}): {self.__kesto:.2f} Enkka: {vanha_enkka:.2f}")
                data.close()







    def game_over(self, voitto):
        """
        Tämä metodi avaa kaikki avaamattomat
        ruudut pelin päätyttyä.
        Jos voitto == True, muutetaan
        miinat vihreiksi.
        Jos voitto == False, muutetaan
        miinat punaisikis
        :param voitto: bool
        """

        # Käydään läpi kaikki mahdolliset koordinaatit. Jos koordinaattia
        # vastaavaa nappia ei ole avattu, paljastetaan mitä sen alta löytyy
        # muokkaamalla sen ulkonäkö nappia vastaavaksi. Lisäksi poistetaan
        # nämä napit pois käytöstä.

        for x in range(0, self.__koko):
            for y in range(0, self.__koko):
                koord = [x, y]
                if koord not in self.__avatut:
                    if self.__miinat[y][x] == "x" and voitto == 0:
                        self.__ruudut[y][x]["state"] = "disabled"
                        self.__ruudut[y][x].configure(relief=SUNKEN,
                                                      background="red",
                                                      text="#", font=("Helvetica", 9, 'bold'),
                                                      disabledforeground="black")

                    elif self.__miinat[y][x] == "x" and voitto == 1:
                        self.__ruudut[y][x]["state"] = "disabled"
                        self.__ruudut[y][x].configure(relief=SUNKEN,
                                                      background="green",
                                                      text=":)",
                                                      font=("Helvetica", 9, 'bold'),
                                                      disabledforeground="white")

                    elif self.__miinat[y][x] == 0:
                        self.__ruudut[y][x]["state"] = "disabled"
                        self.__ruudut[y][x].configure(relief=SUNKEN,
                                                      background="white")
                        self.__avatut.append([x, y])

                    elif self.__miinat[y][x] != "x" and self.__miinat[y][x] != 0:
                        self.__avatut.append([x, y])
                        self.__ruudut[y][x]["state"] = "disabled"
                        if self.__miinat[y][x] == 1:
                            self.__ruudut[y][x].configure(relief=SUNKEN,
                                                          background="white",
                                                          text="1",
                                                          font=("Helvetica", 9,
                                                                'bold'),
                                                          disabledforeground="blue")
                        elif self.__miinat[y][x] == 2:
                            self.__ruudut[y][x].configure(relief=SUNKEN,
                                                          background="white",
                                                          text="2",
                                                          disabledforeground="green",
                                                          font=("Helvetica", 9,
                                                                'bold'))
                        elif self.__miinat[y][x] == 3:
                            self.__ruudut[y][x].configure(relief=SUNKEN,
                                                          background="white",
                                                          text="3",
                                                          disabledforeground="orange",
                                                          font=("Helvetica", 9,
                                                                'bold'))
                        elif self.__miinat[y][x] == 4:
                            self.__ruudut[y][x].configure(relief=SUNKEN,
                                                          background="white",
                                                          text="4",
                                                          disabledforeground="red",
                                                          font=("Helvetica", 9,
                                                                'bold'))
                        elif self.__miinat[y][x] == 5:
                            self.__ruudut[y][x].configure(relief=SUNKEN,
                                                          background="white",
                                                          text="5",
                                                          disabledforeground="pink",
                                                          font=("Helvetica", 9,
                                                                'bold'))
                        elif self.__miinat[y][x] == 6:
                            self.__ruudut[y][x].configure(relief=SUNKEN,
                                                          background="white",
                                                          text="6",
                                                          disabledforeground="blue",
                                                          font=("Helvetica", 9,
                                                                'bold'))
                        elif self.__miinat[y][x] == 7:
                            self.__ruudut[y][x].configure(relief=SUNKEN,
                                                          background="white",
                                                          text="7",
                                                          disabledforeground="red",
                                                          font=("Helvetica", 9,
                                                                'bold'))
                        elif self.__miinat[y][x] == 8:
                            self.__ruudut[y][x].configure(relief=SUNKEN,
                                                          background="white",
                                                          text="8",
                                                          disabledforeground="violet",
                                                          font=("Helvetica", 9,
                                                                'bold'))

    def katso_ruudut(self, koord):
        """
        Tämä metodi on tehty tilanteeseen, jossa avattu
        ruutu on tyhjä. Tällöin avataan ympärillä
        olevat ruudut jos niitä ei ole jo avattu.
        Tämä metodi palauttaa tyhjän ruudun (param. koord)
        ympärillä olevien (3x3 alueella) avaamattomien ruutujen
        koordinaatit metodille 'avaa_ruutu' joka avaa nämä ruudut.
        :param koord: [x,y]
        """

        ri = koord[1]  # y:n arvo, eli rivin numero
        sa = koord[0]  # x:n arvo, eli sarakkeen numero

        # Käydään läpi ympärillä olevat ruudut
        for koord2 in [[sa - 1, ri - 1], [sa, ri - 1], [sa + 1, ri - 1],
                       [sa - 1, ri],
                       [sa + 1, ri], [sa - 1, ri + 1], [sa, ri + 1],
                       [sa + 1, ri + 1]]:

            # Ei huomioida ruudukon ulkopuolelle meneviä koordinaatteja
            if koord2[0] == -1 or koord2[0] == self.__koko:
                continue
            elif koord2[1] == -1 or koord2[1] == self.__koko:
                continue

            # Jos ruutua ei ole avattu, avataan se metodilla 'avaa_ruutu'
            if koord2 not in self.__avatut:
                self.avaa_ruutu(koord2[0], koord2[1])

    def generoi_miinat(self, x, y):
        """
        Funktio muuttaa matriisia "self.__miinat" siten että, miinojen kohdalla
        matriisissa on "x" ja miinojen ympärillä on numeroita siten että numero
        kertoo monta miinaa ruudun ympärillä on.
        :param x: ensimmäisenä avatun ruudun x koordinaatti, int
        :param y: ensimmäisenä avatun ruudun y koordinaatti, int
        """
        # Haetaan lista miinojen koordinaateista kutsumalla funktiota 'miinojen_paikat'
        paikat = miinojen_paikat(self.__koko, self.__miina_maara, [x, y])
        # Listätään miinat matriisiin "self.__miinat"
        for koord in paikat:
            ri = koord[1]  # y:n arvo, eli rivi
            sa = koord[0]  # x:n arvo, eli sarake
            self.__miinat[ri][sa] = "x"

            # Nostetaan ympäröivien ruutujen arvoja yhdellä,
            # jos ne eivät ole miinoja ja jos tarksteltava ruutu ei ole ruudukon ulkopuolella
            for koord2 in [[sa-1, ri-1], [sa, ri-1], [sa+1, ri-1], [sa-1, ri],
                           [sa+1, ri], [sa-1, ri+1], [sa, ri+1], [sa+1, ri+1]]:

                if koord2[0] == -1 or koord2[0] == self.__koko:
                    continue
                elif koord2[1] == -1 or koord2[1] == self.__koko:
                    continue
                else:
                    if self.__miinat[koord2[1]][koord2[0]] != "x":
                        self.__miinat[koord2[1]][koord2[0]] += 1


def miinojen_paikat(sivun_pituus, miinojen_maara, koord):
    """
    Generoi miinojen koordinaatit annettujen
    parametrien mukaan. Sivun pituus tarkoittaa
    ruudukon sivun pituutta. Koordinaatti tarkoittaa
    käyttäjän ensimmäisen arvauksen koordinaattia.
    Tähän kohtaan eikä sen lähelle luoda miinoja.
    Palauttaa miinojen koordinaatit
    muodossa [[x1,y1],[x2,y2]...].
    :param sivun_pituus:  int
    :param miinojen_maara: int
    :param koord: [x,y]
    :return: listoja listan sisällä
    """

    koordinaatit = []

    # Toistetaan silmukkaa kunnes listassa on on haluttu määrä miinoja
    while len(koordinaatit) < miinojen_maara:

        # Määritetään x:n ja y:n arvot satunnaisesti
        y = random.randint(0, sivun_pituus - 1)
        x = random.randint(0, sivun_pituus - 1)
        # Muodostetaan arvoista koordinaatti
        koordinaatti = [x, y]

        # Jos koordinaatti ei vielä ole listassa ja sen etäisyys ensimmäisen arvauksen koordinaatista
        # on enemmän kuin 1, lisätään saatu koordinaatti listaan
        if koordinaatti not in koordinaatit and (abs(koord[0] - x) > 1 or abs(koord[1] - y) > 1):
            koordinaatit.append(koordinaatti)

    return koordinaatit


def main():

    StartUi().start()


if __name__ == "__main__":
    main()
