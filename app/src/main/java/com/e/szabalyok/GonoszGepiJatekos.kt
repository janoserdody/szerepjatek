package com.e.szabalyok

import com.e.automatizmus.AutomatikusanMukodo
import com.e.jatekter.JatekTer
import com.e.keret.CommandProcessor
import com.e.szerepjatek.R
import org.json.simple.JSONObject

class GonoszGepiJatekos(_x: Int, _y: Int, _jatekTer: JatekTer, override var nev: String, mukodesIntervallum: Int,
                        commandProcessor: CommandProcessor, tulajdonsagok: JSONObject
) : GepiJatekos(_x, _y, _jatekTer, nev, mukodesIntervallum, commandProcessor, tulajdonsagok), AutomatikusanMukodo {
    override var alak: Int = 0

    override var Sebzes = 1
}