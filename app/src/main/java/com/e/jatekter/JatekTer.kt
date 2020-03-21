package com.e.jatekter

import java.util.*
import kotlin.math.sqrt

class JatekTer(val meretX: Int, val meretY: Int) {
    val MAX_ELEMSZAM = 1000
    private var elemN = 0
    private val elemek =
        ArrayList<JatekElem>(MAX_ELEMSZAM) //JatekElem[MAX_ELEMSZAM];

    fun Felvetel(jatekElem: JatekElem) {
        elemek.add(jatekElem)
        elemN++
    }

    fun Torles(jatekElem: JatekElem?) {
        elemek.remove(jatekElem)
        elemN--
    }

    @JvmOverloads
    fun MegadottHelyenLevok(x: Int, y: Int, tavolsag: Int = 0): ArrayList<JatekElem> {
        val jatekElemek = ArrayList<JatekElem>()
        for (jatekElem in elemek) {
            val mertTavolsag =
                sqrt((jatekElem.x - x) * (jatekElem.x - x) + (jatekElem.y - y) * (jatekElem.y - y).toDouble())
            if (tavolsag <= mertTavolsag) {
                jatekElemek.add(jatekElem)
            }
        }
        return jatekElemek
    }

}