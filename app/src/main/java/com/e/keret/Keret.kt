package com.e.keret

import com.e.jatekter.JatekTer
import com.e.megjelenites.GrafikusMegjelenito
import com.e.megjelenites.IMegjelenitheto
import com.e.szabalyok.*
import java.lang.Exception
import java.util.*
import kotlin.random.Random

class Keret(val ter: JatekTer, val KINCSEK_SZAMA: Int): ObservableKotlin() {

    private var jatekVege: Boolean = false
    private val PALYA_MERET_X: Int
    private val PALYA_MERET_Y: Int
    var eletero = 10

    init {
        PALYA_MERET_X = ter.meretX
        PALYA_MERET_Y = ter.meretY
        PalyaGeneralas()
    }

    fun PalyaGeneralas(){
        for (x in 0 until PALYA_MERET_X){
            var fal = Fal(x, 0, ter)
        }
        for (x in 0 until PALYA_MERET_X) {
            var fal = Fal(x, PALYA_MERET_Y - 1, ter)
        }
        for (y in 0 until PALYA_MERET_Y){
            var fal = Fal(0, y, ter)
        }
        for (y in 0 until PALYA_MERET_Y){
            var fal = Fal(PALYA_MERET_X - 1, y, ter)
        }
        for (i in 0 until KINCSEK_SZAMA) {
            var kincsX = Random.nextInt(0, PALYA_MERET_X)
            var kincsY = Random.nextInt(0, PALYA_MERET_Y)
            var megadottHelyenLevo: Double = 0.0

            for (elem in ter.MegadottHelyenLevok(kincsX, kincsY)){
                megadottHelyenLevo += elem.meret
            }
            if (!(kincsX == 1 && kincsY == 1) && megadottHelyenLevo < 1) {
                var kincs = Kincs(kincsX, kincsY, ter)
            }
        }
    }

    fun Kattint(x: Int?, y: Int?){
        if (x == null || y == null){
            //throw Exception("invalid type, fun Kattint in Keret class")
            return
        }

        var args = ArrayList<Int>(2)

        args.add(x)
        args.add(y)

        eletero--

        notifyObservers(args)
    }

    fun Futtatas(){
        var megjelenito = GrafikusMegjelenito(ter, 0, 0)
        var jatekos = Jatekos(1, 1, ter)
        var kati = GepiJatekos(5,5, ter)
        var laci = GonoszGepiJatekos(3,3, ter)


        do {
            laci.mozgas()
            kati.mozgas()
            Thread.sleep(3000)

            megjelenito.Megjelenites()

        }while (!jatekVege)
    }
}