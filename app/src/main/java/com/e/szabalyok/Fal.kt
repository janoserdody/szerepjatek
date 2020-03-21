package com.e.szerepjatek.szabalyok

import com.e.szerepjatek.jatekter.JatekElem
import com.e.szerepjatek.jatekter.RogzitettJatekElem

class Fal(x: Int, y: Int) : RogzitettJatekElem(x, y, null) {
    override val meret: Double
        get() = 1.0

    override fun utkozes(jatekElem: JatekElem?) {}
}