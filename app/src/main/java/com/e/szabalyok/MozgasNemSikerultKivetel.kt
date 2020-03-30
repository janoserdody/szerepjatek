package com.e.szabalyok

import com.e.jatekter.JatekElem
import java.lang.Exception

// jatekElem – ez tárolja, hogy ki nem tudott lépni.
    // x – ez tárolja, hogy hova szeretett volna lépni (x koordináta)
    // Y – ez tárolja, hogy hova szeretett volna lépni (y koordináta)
open class MozgasNemSikerultKivetel(val jatekElem : JatekElem, val X: Int, val Y: Int): Exception() {
}