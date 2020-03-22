package com.e.szabalyok

import com.e.jatekter.JatekElem
import com.e.jatekter.JatekTer
import com.e.jatekter.MozgoJatekElem

class Jatekos(val nev: String, x: Int, y: Int, jatekTer: JatekTer?) :
    MozgoJatekElem(x, y, jatekTer!!) {
    private var eletero = 100
    private val pontszam = 0

    override val meret: Double
        get() = 0.2

    override fun utkozes(jatekElem: JatekElem) {}
    fun serul(sebzes: Int) {
        if (eletero == 0) return
        if (eletero - sebzes < 0) {
            eletero = 0
            aktiv = false
        } else eletero -= sebzes
    }

    fun Megy(rx: Int, ry: Int) {
        val ujX = x + rx
        val ujY = y + ry
        AtHelyez(ujX, ujY)
    }

}