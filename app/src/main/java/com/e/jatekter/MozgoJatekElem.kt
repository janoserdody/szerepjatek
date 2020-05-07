package com.e.jatekter

import com.e.keret.CommandId
import com.e.keret.CommandProcessor
import com.e.szabalyok.GepiJatekos
import com.e.szabalyok.Jatekos
import com.e.szabalyok.MozgasHelyHianyMiattNemSikerultKivetel

abstract class MozgoJatekElem(_x: Int, _y: Int, _jatekTer: JatekTer, private val commandProcessor: CommandProcessor)
    : JatekElem(_x, _y, _jatekTer) {

    var aktiv: Boolean = true

    fun AtHelyez(ujX: Int, ujY: Int){
        var jatekElemek = ter.MegadottHelyenLevok(ujX, ujY)

        for (elem in jatekElemek){
            if (elem !is Jatekos){
                elem.utkozes(this, 0)
            }
            if (this.aktiv) {
                if (elem is Jatekos)
                {
                    fight(elem)
                }
            }
        }
        if (this.aktiv) {
            var jatekElemek = ter.MegadottHelyenLevok(ujX, ujY)
            var osszesMeret: Double = 0.0;
            for (elem in jatekElemek) {
                osszesMeret += elem.meret
            }
            osszesMeret += this.meret

            // átmozgatja az új koordináta alapján az új mezőre
            if (osszesMeret <= 1){
                ter.terkepRemoveAndAdd(x, y, ujX, ujY, this)
                x = ujX
                y = ujY
            }
            else{
                throw MozgasHelyHianyMiattNemSikerultKivetel(this,x, y, jatekElemek)
            }
          }
        }

    fun fight(jatekos1: JatekElem){
        if (this is GepiJatekos && jatekos1 is GepiJatekos){
            return
        }

        var args = ArrayList<Any>(5)

        if (jatekos1 is GepiJatekos){
            args.add(this)
            args.add(jatekos1)
        }
        else{
            args.add(jatekos1)
            args.add(this)
        }

        commandProcessor.Execute(CommandId.Fight, args)
    }
}
