package com.e.szerepjatek.jatekter

import java.util.*

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
        val JatekElemek = ArrayList<JatekElem>()
        for (jatekElem in elemek) {
            val mertTavolsag =
                Math.sqrt((jatekElem.x - x) * (jatekElem.x - x) + (jatekElem.y - y) * (jatekElem.y - y).toDouble())
            if (tavolsag <= mertTavolsag) {
                JatekElemek.add(jatekElem)
            }
        }
        return JatekElemek
    }

}