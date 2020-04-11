package com.e.keret

//import androidx.annotation.RequiresApi
import android.R.attr.resource
import android.content.Context
import com.e.datalayer.JatekosFactory
import com.e.datalayer.Music
import com.e.datalayer.TapasztalatiPontok
import com.e.jatekter.JatekTer
import com.e.szabalyok.*
//import java.util.concurrent.locks.Lock
//import java.util.concurrent.locks.ReentrantLock
import kotlin.random.Random

class Keret(val ter: JatekTer, val KINCSEK_SZAMA: Int, val commandProcessor: CommandProcessor, val context: Context)
    : ObservableKotlin() {

    private var megtalaltKincsek: Int = 0
    private var jatekVege: Boolean = false
    private val PALYA_MERET_X: Int
    private val PALYA_MERET_Y: Int
    //private val MAXFAL = 200
    private val FALMAXHOSSZ = 8
    private val MAX_JATEKOS = 10
    private var jatekos: Jatekos? = null
    private val pontMap = TapasztalatiPontok.pontok
    private lateinit var jatekosFactory: JatekosFactory
    var gepiJatekosok = ArrayList<Jatekos?>(MAX_JATEKOS)

    var eletero = 0
    var XP = 0


    init {
        PALYA_MERET_X = ter.meretX
        PALYA_MERET_Y = ter.meretY
        PalyaGeneralas()
    }

    fun PalyaGeneralas(){
        var falak = ArrayList<Fal>((PALYA_MERET_X + 1) * (PALYA_MERET_Y + 1))

        for (x in 0 until PALYA_MERET_X){
            var f = Fal(x, 0, ter)
        }
        for (x in 0 until PALYA_MERET_X) {
            var f = Fal(x, PALYA_MERET_Y - 1, ter)
        }
        for (y in 1 until PALYA_MERET_Y - 1){
            var f = Fal(0, y, ter)
        }
        for (y in 1 until PALYA_MERET_Y - 1){
            var f = Fal(PALYA_MERET_X - 1, y, ter)
        }

        var iranyok = IntArray(4, {i -> i})
        var falIndex = 0
        var elmozdulas : Array<Int>
        var nemZsakutca = 1
        var maxFal = 0

        var fal: Fal? = Fal(2,2, ter)
        if (fal != null){
            falak.add(fal)
        }

        for (i in 0 until 2){
            fal = RandomFal()
            if (fal != null){
                falak.add(fal)
            }
        }

        var iranyFal = fal

        while (!falak.isEmpty() ){

            if (fal == null){
                falIndex++
                maxFal++
                continue
            }

            var x = fal.x
            var y = fal.y
            falak.remove(fal)

            do{
                iranyok = GetRandomIranyok(iranyok)

                nemZsakutca = 0
                var falMaxHossz = Random.nextInt(1, FALMAXHOSSZ)

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
                        falMaxHossz--
                    }
                }
                fal = iranyFal
                if (fal == null){
                    break
                }
                var x = fal.x
                var y = fal.y
                // ha a nemZsakutca = 0, akkor nem talált új irányt
            } while(nemZsakutca > 0 && falMaxHossz > 0)

            if (!falak.isEmpty()){
                falIndex = Random.nextInt(0, falak.count())
                fal = falak[falIndex]
            }
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

        // vakfolt feltöltése
        for(x in 2 until PALYA_MERET_X - 2 step 2)
        {
            for (y in 2 until PALYA_MERET_Y - 2 step 2){
                if (ter.MegadottHelyenLevok(x, y).isEmpty()){
                    var fal = Helykitolto(x, y, ter)
                }

            }
        }
    }

    private fun RandomFal(): Fal? {
        var x = 0
        var y = 0
        var maxKereses = 30

        do{
            x = Random.nextInt(2, PALYA_MERET_X / 2 - 1) * 2
            y =Random.nextInt(2, PALYA_MERET_Y / 2 - 1) * 2
            maxKereses--
        } while (ter.MegadottHelyenFal(x, y) && maxKereses > 0)

        if (ter.MegadottHelyenFal(x, y)){
            return null
        }

        return Fal(x, y, ter)
    }

    fun Kattint(x: Int?, y: Int?){
        if (x == null || y == null || jatekos == null){
            //throw Exception("invalid type, fun Kattint in Keret class")
            return
        }

        var elmozdulX = 0
        var elmozdulY = 0

        var args = ArrayList<Int>(2)

        if(x - jatekos!!.x > 0){ elmozdulX = 2}
        else if(x - jatekos!!.x < 0){ elmozdulX = -2}
        if(y - jatekos!!.y > 0){ elmozdulY = 2}
        else if(y - jatekos!!.y < 0){ elmozdulY = -2}

        try{
            jatekos!!.megy(elmozdulX, elmozdulY)
        }
        catch (e: MozgasHelyHianyMiattNemSikerultKivetel){
            beep()
        }
        catch (e: MozgasHalalMiattNemSikerultKivetel){
            playGameoverMusic()
            createExitCommand()

        }

        //Megjelenites()
    }

    fun Megjelenites(){

        var args = ArrayList<Int>(3)

        args.add(jatekos!!.x)
        args.add(jatekos!!.y)
        args.add(XP)

        notifyObservers(args)
    }

    fun Futtatas(){

        jatekosFactory = JatekosFactory(context)

        var jatekosNevLista = jatekosFactory.getNevLista()
        var koordinatak = ArrayList<Int>()

        for (nev in jatekosNevLista){
            do{
                koordinatak = getFreePozition()
            } while (koordinatak.isEmpty())

            if (nev == "ember"){
                jatekos = jatekosFactory.createJatekos(1, 1, ter, "ember", commandProcessor)
            }
            else {
                var gep = jatekosFactory.createJatekos(koordinatak[0], koordinatak[1], ter, nev, commandProcessor)
                gepiJatekosok.add(gep)
            }
        }

           // do {
                try {
                   // laci.mozgas()
                    //kati.mozgas()
                    //Thread.sleep(3000)

                    //megjelenito.Megjelenites()
                    //Megjelenites()
                }
                catch (e: MozgasHelyHianyMiattNemSikerultKivetel){
                    beep()
                }
                catch(e: MozgasHalalMiattNemSikerultKivetel){
                    playGameoverMusic()
                    createExitCommand()
                }
        //    } while (!jatekVege)
        //Megjelenites()
    }

    private fun getFreePozition(): ArrayList<Int> {
        var koordinatak = ArrayList<Int>(2)

        for (x in 1 until PALYA_MERET_X ){
            var koordinataX = Random.nextInt(2, PALYA_MERET_X - 1)
            koordinataX -=  koordinataX % 2 - 1
                for (y in 1 until PALYA_MERET_Y){
                    var koordinataY = Random.nextInt(2, PALYA_MERET_X - 1)
                    koordinataY -= koordinataY % 2 -1
                    if (isFreePosition(koordinataX, koordinataY)){
                        koordinatak.add(koordinataX)
                        koordinatak.add(koordinataY)
                        return koordinatak
                    }
                }
            }
        return koordinatak
        }

    private fun isFreePosition(koordinataX: Int, koordinataY: Int): Boolean {
        var isFree = false
        var megadottHelyenLevok = ter.MegadottHelyenLevok(koordinataX, koordinataY)

        if (megadottHelyenLevok.isEmpty())
        { isFree = true}

        return isFree
    }


    private fun beep() {
        var args = ArrayList<Any>(2)
        args.add(Music.Beep2)
        commandProcessor.Execute(CommandId.PlayBeep, args)
    }

    private fun playGameoverMusic() {
        var args = ArrayList<Any>(2)
        args.add(Music.Gameover)
        commandProcessor.Execute(CommandId.PlayBeep, args)
    }

    fun KincsFelvetelTortent(kincs: Kincs, jatekos: Jatekos){
        megtalaltKincsek++
        XP += pontMap["kincs"] as Int

        if (megtalaltKincsek == KINCSEK_SZAMA)
        {
            jatekVege = true
        }
    }

    fun JatekosValtozasTortent(jatekos: Jatekos, ujXP: Int, ujEletero: Int){
        // emberjátékos volt?
        eletero = ujEletero
        XP = ujXP
        if (eletero == 0 && (jatekos !is GepiJatekos)){
            jatekVege = true
            playGameoverMusic()
            createExitCommand()
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

    private fun createExitCommand() {
            var args = ArrayList<Any>(2)
            args.add(0)
            commandProcessor.Execute(CommandId.Exit, args)
    }
}