package com.e.szabalyok

import com.e.jatekter.JatekElem
import com.e.jatekter.JatekTer
import com.e.jatekter.Jatekos
import com.e.jatekter.RogzitettJatekElem

class Kincs(x: Int, y: Int, jatekTer: JatekTer?) :
    RogzitettJatekElem(x, y, jatekTer) {
    override val meret: Double
        get() = 1.0

    override fun utkozes(jatekElem: JatekElem?) {
        if (jatekElem is Jatekos) {
            jatekElem.pontotSzerez(50)
        }
        ter.Torles(this)
    }
}