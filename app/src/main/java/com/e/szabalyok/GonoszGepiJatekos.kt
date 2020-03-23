package com.e.szabalyok

import com.e.jatekter.JatekElem
import com.e.jatekter.JatekTer

class GonoszGepiJatekos(_x: Int, _y: Int, _jatekTer: JatekTer) :
    GepiJatekos(_x, _y, _jatekTer) {
    override val alak: Char
        get() = '\u2642'

    override fun utkozes(jatekElem: JatekElem) {
        utkozes(jatekElem)
        if (aktiv && jatekElem is Jatekos) {
            jatekElem.serul(10)
        }
    }
}