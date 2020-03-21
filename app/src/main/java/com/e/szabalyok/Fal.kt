package com.e.szabalyok

import com.e.jatekter.JatekElem
import com.e.jatekter.JatekTer
import com.e.jatekter.RogzitettJatekElem

class Fal(x: Int, y: Int, jatekTer: JatekTer) : RogzitettJatekElem(x, y, jatekTer) {
    override val meret: Double
        get() = 1.0

    override fun utkozes(jatekElem: JatekElem) {}
}