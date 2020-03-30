package com.e.keret

import android.content.Context
import com.e.jatekter.JatekTer
import com.e.megjelenites.GrafikusMegjelenito
import com.e.szabalyok.*
import java.util.*
import kotlin.random.Random


class Keret(val ter: JatekTer, val KINCSEK_SZAMA: Int, val commandProcessor: CommandProcessor, val context: Context)
    : ObservableKotlin() {

    private var megtalaltKincsek: Int = 0
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
                var kincs = Kincs(kincsX, kincsY, ter, commandProcessor)
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
            var jatekos = Jatekos(1, 1, ter, commandProcessor)
            var kati = GepiJatekos(5,5, ter, 1, commandProcessor)
            var laci = GonoszGepiJatekos(3,3, ter, 1, commandProcessor)

            do {
                try {
                    laci.mozgas()
                    kati.mozgas()
                    Thread.sleep(3000)

                    megjelenito.Megjelenites()
                }
                catch (e: MozgasHelyHianyMiattNemSikerultKivetel){
                    var args = ArrayList<Any>(2)
                    commandProcessor.Execute(CommandId.PlayBeep, args)
                }
            }while (!jatekVege)
    }

     fun KincsFelvetelTortent(kincs: Kincs, jatekos: Jatekos){
        megtalaltKincsek++

        if (megtalaltKincsek == KINCSEK_SZAMA)
        {
            jatekVege = true
        }
    }

    fun JatekosValtozasTortent(jatekos: Jatekos, ujPontszam: Int, ujEletero: Int){
        // emberjátékos volt?
        if (eletero == 0 && (jatekos !is GepiJatekos)){
            jatekVege = true
        }
    }
}