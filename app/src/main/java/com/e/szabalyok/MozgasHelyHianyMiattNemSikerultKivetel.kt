package com.e.szabalyok

import com.e.jatekter.JatekElem

class MozgasHelyHianyMiattNemSikerultKivetel(jatekElem : JatekElem, X: Int, Y: Int,
                                             var elemek : ArrayList<JatekElem>)
    : MozgasNemSikerultKivetel(jatekElem, X, Y) {
}