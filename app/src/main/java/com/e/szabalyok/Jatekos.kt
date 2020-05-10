package com.e.szabalyok

import com.e.jatekter.JatekElem
import com.e.jatekter.JatekTer
import com.e.jatekter.MozgoJatekElem
import com.e.keret.CommandId
import com.e.keret.CommandProcessor
import com.e.megjelenites.Kirajzolhato
import com.e.szerepjatek.R
import org.json.simple.JSONObject

open class Jatekos(_x: Int, _y: Int, _jatekTer: JatekTer, open var nev: String,
                   private val commandProcessor: CommandProcessor, tulajdonsagok: JSONObject) :
    MozgoJatekElem(_x, _y, _jatekTer, commandProcessor), Kirajzolhato, JatekosValtozasKezelo {

    override var alak: Int = 0

    var eletero = 20
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

    var XP = 0
    open var Sebzes = 90


    override val meret: Double
        get() = 0.2

    private val kepek = mapOf("fighter1" to R.drawable.fighter1,
        "orkharcos" to R.drawable.okrharcos,
        "farkas" to R.drawable.farkas,
        "medve" to R.drawable.medve ,
        "tigris" to R.drawable.tigris ,
        "patkany" to R.drawable.patkany ,
        "pok" to R.drawable.pok ,
        "solyom" to R.drawable.solyom ,
        "anatyda" to R.drawable.anatyda ,
        "arnyjaro" to R.drawable.arnyjaro ,
        "demon" to R.drawable.demon ,
        "sytix" to R.drawable.sytix ,
        "shereb" to R.drawable.shereb ,
        "morquor" to R.drawable.morquor ,
        "xing" to R.drawable.xing ,
        "molamoth" to R.drawable.molamoth ,
        "golem" to R.drawable.golem ,
        "kaoszleny" to R.drawable.kaoszleny ,
        "pegazus" to R.drawable.pegazus ,
        "sarkany" to R.drawable.sarkany ,
        "zombi" to R.drawable.zombi ,
        "varazslo" to R.drawable.varazslo ,
        "kereskedo" to R.drawable.kereskedo ,
        "kocsmaros" to R.drawable.kocsmaros ,
        "fighter2" to R.drawable.fighter2 ,
        "pap" to R.drawable.pap ,
        "vandor" to R.drawable.vandor)

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
        alak = kepek[obj["kep"].toString()] as Int
    }

    override fun utkozes(jatekElem: JatekElem, sebzes: Int) {
        if (aktiv && jatekElem is Jatekos) {
            jatekElem.serul(sebzes)
        }
    }

    fun serul(sebzes: Int) {
        if (eletero == 0) return

        if (eletero - sebzes <= 0) {

            eletero = 0

            aktiv = false

            if (this is GepiJatekos){
                ter.torles(this)
            }
        } else eletero -= sebzes

        if (sebzes > 0){

            jatekosValtozas(this, XP, eletero)
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
            atHelyez(x + iranyX, y)
        }
        while (ujY != y){
            atHelyez(x, y + iranyY)
        }
    }

    fun pontotSzerez(pont: Int) {
        XP += pont
    }

    override fun jatekosValtozas(jatekos: Jatekos, ujXp: Int, ujEletero: Int) {
        if (this is GepiJatekos){
            return
        }

        var args = ArrayList<Any>(5)
        args.add(jatekos)
        args.add(ujXp)
        args.add(ujEletero)
        commandProcessor.execute(CommandId.JatekosValtozas, args)
    }
}