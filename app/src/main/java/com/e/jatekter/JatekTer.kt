package com.e.jatekter

import com.e.megjelenites.IKirajzolhato
import com.e.megjelenites.IMegjelenitheto
import com.e.datalayer.Mezo
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.sqrt
import kotlin.reflect.typeOf

// konstruktorban a var jelenti a publikus property-t
// típust a kettőspont után adjuk meg
class JatekTer(val meretX: Int, val meretY: Int): IMegjelenitheto {
    val MAX_ELEMSZAM = 1000
    private var elemN = 0
    private val elemek =
        ArrayList<JatekElem>(MAX_ELEMSZAM) //JatekElem[MAX_ELEMSZAM];
    private var terkep = arrayOf<Array<ArrayList<JatekElem>>>()

    init{
        for (x in 0 until meretX) {
            var array = arrayOf<ArrayList<JatekElem>>()
            for (y in 0 until meretY) {
                array += ArrayList<JatekElem>()
            }
            terkep += array
        }
    }


    fun Felvetel(jatekElem: JatekElem) {
        elemek.add(jatekElem)

        elemN++

        terkep[jatekElem.x][jatekElem.y].add(jatekElem)
    }

    fun Torles(jatekElem: JatekElem?) {
        elemek.remove(jatekElem)
        elemN--
    }

    fun MegadottHelyenLevok(x: Int, y: Int, tavolsag: Int = 0): ArrayList<JatekElem> {
        val JatekElemek = ArrayList<JatekElem>()
        for (jatekElem in elemek) {
            var mertTavolsag =
                sqrt((jatekElem.x - x) * (jatekElem.x - x) + (jatekElem.y - y) * (jatekElem.y - y).toDouble())
            if (tavolsag >= mertTavolsag) {
                JatekElemek.add(jatekElem)
            }
        }
        return JatekElemek
    }

    override val megjelenitendoMeret: IntArray?
        get() {
            return intArrayOf(meretX, meretY)
        }

    override fun MegjelenitendoElemek(): ArrayList<IKirajzolhato> {
        var count: Int = 0
        val vissza = ArrayList<IKirajzolhato>()
        for (elem in elemek){
            if (elem is IKirajzolhato){
                count++
                vissza.add(elem)
            }
        }
        return vissza
    }
}