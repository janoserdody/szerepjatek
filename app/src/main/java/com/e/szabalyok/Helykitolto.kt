package com.e.szabalyok

import com.e.jatekter.JatekElem
import com.e.jatekter.JatekTer
import com.e.jatekter.RogzitettJatekElem
import com.e.megjelenites.IKirajzolhato
import com.e.szerepjatek.R

class Helykitolto(x: Int, y: Int, jatekTer: JatekTer) : RogzitettJatekElem(x, y, jatekTer), IKirajzolhato {
    override val meret: Double
        get() = 2.0

    override fun utkozes(jatekElem: JatekElem) {}
    override val alak: Int
        get() = R.drawable.background_negyzet
}




