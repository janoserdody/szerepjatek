package com.e.keret

import com.e.jatekter.JatekElem

class SebzesCommand(val keret: Keret): Command {
    override fun Execute(arg: ArrayList<Any>) {
        val jatekos1 = arg[0] as String
        val jatekos2 = arg[1] as String
        val sebzes1 = arg[2] as Int
        val sebzes2 = arg[3] as Int
        keret.sebzes(jatekos1, jatekos2, sebzes1, sebzes2)
    }
}
