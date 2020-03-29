package com.e.szabalyok

import com.e.jatekter.JatekElem
import com.e.jatekter.JatekTer
import com.e.szerepjatek.R

class GonoszGepiJatekos(_x: Int, _y: Int, _jatekTer: JatekTer) :
    GepiJatekos(_x, _y, _jatekTer) {
    override val alak: Int
        get() = R.drawable.monster2

    override fun utkozes(jatekElem: JatekElem) {
        utkozes(jatekElem)
        if (aktiv && jatekElem is Jatekos) {
            jatekElem.serul(10)
        }
    }
}