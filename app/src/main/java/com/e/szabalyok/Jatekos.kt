package com.e.szabalyok

import com.e.jatekter.JatekElem
import com.e.jatekter.JatekTer
import com.e.jatekter.MozgoJatekElem
import com.e.keret.CommandId
import com.e.keret.CommandProcessor
import com.e.megjelenites.IKirajzolhato
import com.e.megjelenites.IMegjelenitheto
import com.e.szerepjatek.R

open class Jatekos(_x: Int, _y: Int, _jatekTer: JatekTer,
                   private val commandProcessor: CommandProcessor) :
    MozgoJatekElem(_x, _y, _jatekTer), IMegjelenitheto, IKirajzolhato, JatekosValtozasKezelo {

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

        if (sebzes > 0){

            JatekosValtozas(this, pontszam, eletero)

        }
    }

    fun megy(rx: Int, ry: Int) {
        val ujX = x + rx
        val ujY = y + ry
        var iranyX = 0
        var iranyY = 0
        if (rx > 0) { iranyX = 1}
        if (rx < 0) { iranyX = -1}
        if (ry > 0) { iranyY = 1}
        if (ry < 0) { iranyY = -1}

        while (ujX != x){
            AtHelyez(x + iranyX, y)
        }
        while (ujY != y){
            AtHelyez(x, y + iranyY)
        }
    }

    fun pontotSzerez(pont: Int) {
        pontszam += pont
    }

    override val megjelenitendoMeret: Array<Int>
        get() {
            if (megjelenitendoMeret == null){
                return arrayOf(ter.meretX, ter.meretY)
            }
            return megjelenitendoMeret
        }

    override fun MegjelenitendoElemek(): ArrayList<IKirajzolhato> {
        val vissza = ArrayList<IKirajzolhato>()
        val jatekElemek = ter.MegadottHelyenLevok(x, y, 5)
        for (elem in jatekElemek){
            if (elem is IKirajzolhato){
                vissza.add(elem)
            }
        }
        return vissza
    }

    override val alak: Int
        get() {
            if (aktiv){
                return R.drawable.fighter1
            }
            return R.drawable.fighter2
        }

    override fun JatekosValtozas(jatekos: Jatekos, ujPontszam: Int, ujEletero: Int) {
        var args = ArrayList<Any>(2)
        args.add(jatekos)
        args.add(ujPontszam)
        args.add(ujEletero)
        commandProcessor.Execute(CommandId.JatekosValtozas, args)
    }
}