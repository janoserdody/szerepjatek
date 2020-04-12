package com.e.szabalyok

import com.e.jatekter.JatekElem
import com.e.jatekter.JatekTer
import com.e.jatekter.MozgoJatekElem
import com.e.keret.CommandId
import com.e.keret.CommandProcessor
import com.e.megjelenites.IKirajzolhato
import com.e.megjelenites.IMegjelenitheto
import com.e.szerepjatek.R
import org.json.simple.JSONObject

open class Jatekos(_x: Int, _y: Int, _jatekTer: JatekTer, open var nev: String,
                   private val commandProcessor: CommandProcessor, tulajdonsagok: JSONObject) :
    MozgoJatekElem(_x, _y, _jatekTer), IMegjelenitheto, IKirajzolhato, JatekosValtozasKezelo {

    var eletero = 100
    var ero = 0
    var allokepesseg = 0
    var gyorsasag = 0
    var ugyesseg = 0
    var szepseg = 0
    var egeszseg = 0
    var akaratero = 0
    var asztral = 0
    var intelligencia = 0
    var muveltseg = 0

    //var pontszam = 0 pontszám helyett XP
    var XP = 0
    open var Sebzes = 90


    override val meret: Double
        get() = 0.2

    init{
        setTulajdonsagok(tulajdonsagok)
    }

    fun setTulajdonsagok(obj: JSONObject){
        ero = Integer.parseInt(obj["ero"].toString())
        allokepesseg = Integer.parseInt(obj["allokepesseg"].toString())
        gyorsasag = Integer.parseInt(obj["gyorsasag"].toString())
        ugyesseg = Integer.parseInt(obj["ugyesseg"].toString())
        szepseg = Integer.parseInt(obj["szepseg"].toString())
        egeszseg = Integer.parseInt(obj["egeszseg"].toString())
        akaratero = Integer.parseInt(obj["akaratero"].toString())
        asztral = Integer.parseInt(obj["asztral"].toString())
        intelligencia = Integer.parseInt(obj["intelligencia"].toString())
        muveltseg = Integer.parseInt(obj["muveltseg"].toString())
    }

    override fun utkozes(jatekElem: JatekElem) {
        if (aktiv && jatekElem is Jatekos) {
            jatekElem.serul(Sebzes)
        }
    }

    fun serul(sebzes: Int) {
        if (eletero == 0) return

        if (eletero - sebzes < 0) {

            eletero = 0

            aktiv = false

            ter.Torles(this)

        } else eletero -= sebzes

        if (sebzes > 0){

            JatekosValtozas(this, XP, eletero)
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
        XP += pont
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

    override fun JatekosValtozas(jatekos: Jatekos, ujXp: Int, ujEletero: Int) {
        if (this is GepiJatekos){
            return
        }

        var args = ArrayList<Any>(5)
        args.add(jatekos)
        args.add(ujXp)
        args.add(ujEletero)
        commandProcessor.Execute(CommandId.JatekosValtozas, args)
    }
}