package com.e.szabalyok

import com.e.jatekter.JatekElem
import com.e.jatekter.RogzitettJatekElem

class Fal(x: Int, y: Int) : RogzitettJatekElem(x, y, null) {
    override val meret: Double
        get() = 1.0

    override fun utkozes(jatekElem: JatekElem?) {}
}