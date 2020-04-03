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
    private val MAXFAL = 10
    var eletero = 10


    init {
        PALYA_MERET_X = ter.meretX
        PALYA_MERET_Y = ter.meretY
        PalyaGeneralas()
    }

    fun PalyaGeneralas(){
        var falak = ArrayList<Fal>()

        falak.add(Fal(2,2, ter))

        for (x in 0 until PALYA_MERET_X){
            var fal = Fal(x, 0, ter)
        }
        for (x in 0 until PALYA_MERET_X) {
            var fal = Fal(x, PALYA_MERET_Y - 1, ter)
        }
        for (y in 1 until PALYA_MERET_Y - 1){
            var fal = Fal(0, y, ter)
        }
        for (y in 1 until PALYA_MERET_Y - 1){
            var fal = Fal(PALYA_MERET_X - 1, y, ter)
        }

        var iranyok = IntArray(4, {i -> i})
        var falIndex = 0
        var elmozdulas : Array<Int>
        var nemZsakutca = 1

        var fal = Fal(Random.nextInt(1, PALYA_MERET_X / 2 - 2) * 2, Random.nextInt(1, PALYA_MERET_Y / 2 - 2) * 2, ter)
        falak.add(fal)
        var iranyFal = fal

        while (falIndex < falak.count()){

            var x = fal.x
            var y = fal.y

            do{
                iranyok = GetRandomIranyok(iranyok)

                nemZsakutca = 0

                for (i in 0 until 4){
                    elmozdulas = GetIranyKoordinata(iranyok[i])

                    if (x + elmozdulas[0] < 0 || x + elmozdulas[0] >= PALYA_MERET_X
                        || y + elmozdulas[1] < 0 || y + elmozdulas[1] >= PALYA_MERET_Y){
                        continue
                    }

                    var megadottHelyen =
                        ter.MegadottHelyenLevok(x + elmozdulas[0], y + elmozdulas[1], 1)

                    if (megadottHelyen.count() == 0){
                        var ujFal = Fal(x + elmozdulas[0], y + elmozdulas[1], ter)
                        falak.add(ujFal)
                        var ujFal2 = Fal(x + elmozdulas[0]/2, y + elmozdulas[1]/2, ter)
                        if (nemZsakutca == 0){
                            iranyFal = ujFal
                        }
                        nemZsakutca ++
                    }
                }
                fal = iranyFal
                var x = fal.x
                var y = fal.y
                // ha a nemZsakutca = 0, akkor nem talált új irányt
            } while(nemZsakutca > 0)

            fal = falak[falIndex++]
        }

        for (i in 0 until KINCSEK_SZAMA) {
            var kincsX = Random.nextInt(1, PALYA_MERET_X - 2)
            var kincsY = Random.nextInt(1, PALYA_MERET_Y - 2)
            var megadottHelyenLevo: Double = 0.0

            for (elem in ter.MegadottHelyenLevok(kincsX, kincsY)){
                megadottHelyenLevo += elem.meret
            }
            if (!(kincsX == 1 && kincsY == 1) && megadottHelyenLevo < 1) {
                var kincs = Kincs(kincsX, kincsY, ter, commandProcessor)
            }
        }
    }

    private fun GetIranyKoordinata(e: Int): Array<Int> {
        when (e){
            1 -> return arrayOf(0, 2)
            2 -> return arrayOf(2, 0)
            3 -> return arrayOf(0, -2)
            4 -> return arrayOf(-2, 0)
        }
        return arrayOf(0, 0)
    }

    private fun GetRandomIranyok(iranyok: IntArray): IntArray {
        var halmaz = mutableSetOf<Int>(Random.nextInt(1,5))

        for (i in 1 until 4){
            do{
                var szam = Random.nextInt(1,5)
                var isContains = halmaz.contains(szam)
                if (!isContains){
                    halmaz.add(szam)
                }
            }while (isContains)
        }

        return halmaz.toIntArray()
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