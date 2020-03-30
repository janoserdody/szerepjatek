package com.e.megjelenites

import com.e.automatizmus.IAutomatikusanMukodo

//forras: innen jönnek majd a megjelenítendő adatok.
//pozX: ennyivel kell majd eltolni a kirajzolandó adatok x koordinátáját
//pozY: ennyivel kell majd eltolni a kirajzolandó adatok y koordinátáját.
// NEM KELL POZX és POZY mert ez nem konzolos megjelenítés
class GrafikusMegjelenito(var forras: IMegjelenitheto, var pozX: Int, var pozY: Int): IAutomatikusanMukodo {

    // minden órajelciklusban frissíteni kell a képernyőt
    override val mukodesIntervallum: Int
        get() = 1

    // Megjelenites – kiolvassa a forras-ból a kirajzolandó elemeket, majd
    // ezeket kirajzolja a pozX és pozY által eltolt helyre:
    // A forras objektum MegjelenitendoElemek metódusával kiolvassa a kirajzolandó elemek
    // tömbjét.
fun Megjelenites(){
    var elemek = forras.MegjelenitendoElemek()
        // Lekérdezi azt is, hogy mekkora a megjelenítendő terület mérete (szintén a forrás adja
        // meg egy két elemű tömbben a szélességet és a magasságot).
        var megjelenitendoMeret = forras.megjelenitendoMeret
        // Ezt követően két egymásba ágyazott ciklussal végigszaladunk a méret által megadott
        // területen, ha
        // a forrás által visszaadott objektumok egyike sincs a megadott ponton, akkor ki-
        // írunk oda egy szóközt a SzalbiztosKonzol segítségével,
        for(x in 0 until megjelenitendoMeret[0]){

            for(y in 0 until megjelenitendoMeret[1]){
               for (elem in elemek){
                   if(elem.x == x && elem.y == y){
                       Kirajzol(elem.alak)
                   }
               }

            }
        }
}

    // Új publikus metódus: Mukodik – hívja meg a már létező Megjelenites metódust.
    override fun mukodik(){
        Megjelenites()
    }

    /*
∗ a megadott ponton van valami, akkor pedig annak az Abra tulajdonsága által
visszaadott karaktert írjuk ki a megadott helyre.
– A megjelenítés során a kirajzolandó elem x és y koordinátáit mindig toljuk el a meg-
jelenítő pozX és pozY mezőjének értékével.
     */
    private fun Kirajzol(imageId: Int) {
        TODO("Not yet implemented")
    }
}
