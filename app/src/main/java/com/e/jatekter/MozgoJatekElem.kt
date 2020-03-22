package com.e.jatekter

abstract class MozgoJatekElem(var _x: Int, var _y: Int, _jatekTer: JatekTer)
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
                break;
            }
        }
        if (this.aktiv) {
            var jatekElemek = ter.MegadottHelyenLevok(ujX, ujY)
            var osszesMeret: Double = 0.0;
            for (elem in jatekElemek) {
                osszesMeret += elem.meret
            }
            osszesMeret += this.meret

            if (osszesMeret <= 1){
                x = ujX
                y = ujY
            }
          }
        }
}
