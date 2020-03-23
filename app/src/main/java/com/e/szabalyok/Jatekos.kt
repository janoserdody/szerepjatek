package com.e.szabalyok

import com.e.jatekter.JatekElem
import com.e.jatekter.JatekTer
import com.e.jatekter.MozgoJatekElem
import com.e.megjelenites.IKirajzolhato
import com.e.megjelenites.IMegjelenitheto

open class Jatekos(_x: Int, _y: Int, _jatekTer: JatekTer) :
    MozgoJatekElem(_x, _y, _jatekTer), IMegjelenitheto, IKirajzolhato {
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

    override val megjelenitendoMeret: IntArray?
        get() {
            if (megjelenitendoMeret == null){
                return intArrayOf(ter.meretX, ter.meretY)
            }
            return megjelenitendoMeret
        }

    override fun MegjelenitendoElemek(): ArrayList<IKirajzolhato> {
        val vissza = ArrayList<IKirajzolhato>()
        val jatekElemek = ter.MegadottHelyenLevok(_x, _y, 5)
        for (elem in jatekElemek){
            if (elem is IKirajzolhato){
                vissza.add(elem)
            }
        }
        return vissza
    }

    override val alak: Char
        get() {
            if (aktiv){
                return '\u263A'
            }
            return '\u263B'
        }
}