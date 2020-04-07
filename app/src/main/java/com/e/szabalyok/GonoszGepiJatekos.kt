package com.e.szabalyok

import com.e.automatizmus.IAutomatikusanMukodo
import com.e.jatekter.JatekElem
import com.e.jatekter.JatekTer
import com.e.keret.CommandProcessor
import com.e.szerepjatek.R

class GonoszGepiJatekos(_x: Int, _y: Int, _jatekTer: JatekTer, mukodesIntervallum: Int,
                        commandProcessor: CommandProcessor) :
    GepiJatekos(_x, _y, _jatekTer, mukodesIntervallum, commandProcessor), IAutomatikusanMukodo {
    override val alak: Int
        get() = R.drawable.monster2
    override var Sebzes = 20

}