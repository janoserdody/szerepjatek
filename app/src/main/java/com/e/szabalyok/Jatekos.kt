package com.e.szabalyok

import com.e.jatekter.JatekElem
import com.e.jatekter.JatekTer
import com.e.jatekter.MozgoJatekElem

class Jatekos(_x: Int, _y: Int, _jatekTer: JatekTer) :
    MozgoJatekElem(_x, _y, _jatekTer) {
    val nev: String? = null
    private var eletero = 100
    private var pontszam = 0

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

    fun megy(rx: Int, ry: Int) {
        val ujX = x + rx
        val ujY = y + ry
        AtHelyez(ujX, ujY)
    }

    fun pontotSzerez(pont: Int) {
        pontszam += pont
    }
}