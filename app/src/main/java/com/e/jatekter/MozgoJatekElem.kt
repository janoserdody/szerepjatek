package com.e.jatekter

import com.e.szabalyok.MozgasHalalMiattNemSikerultKivetel
import com.e.szabalyok.MozgasHelyHianyMiattNemSikerultKivetel

abstract class MozgoJatekElem(_x: Int, _y: Int, _jatekTer: JatekTer)
    : JatekElem(_x, _y, _jatekTer) {

    var aktiv: Boolean = true

    fun AtHelyez(ujX: Int, ujY: Int){
        var jatekElemek = ter.MegadottHelyenLevok(ujX, ujY)

        for (elem in jatekElemek){
            elem.utkozes(this)
            if (this.aktiv) {
                utkozes(elem)
            }
            if(!this.aktiv){
                throw MozgasHalalMiattNemSikerultKivetel(this, x, y)
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
            if (osszesMeret < 1){
                x = ujX
                y = ujY
            }
            else{
                throw MozgasHelyHianyMiattNemSikerultKivetel(this,x, y, jatekElemek)
            }
          }
        }
}
