package com.e.szabalyok

import com.e.automatizmus.AutomatikusanMukodo
import com.e.jatekter.JatekTer
import com.e.keret.CommandProcessor
import com.e.szerepjatek.R
import org.json.simple.JSONObject
import java.util.*

open class GepiJatekos(_x: Int, _y: Int, _jatekTer: JatekTer, override var nev: String,
                       override val mukodesIntervallum: Int, commandProcessor: CommandProcessor,
                       tulajdonsagok: JSONObject
) : Jatekos(_x, _y, _jatekTer, nev, commandProcessor, tulajdonsagok), AutomatikusanMukodo {

    override val alak: Int
        get() = R.drawable.fighter2

    fun mozgas() {
        val iranyok = arrayOf(0, 0, 0, 0)
        var random = rnd.nextInt(3)
        iranyok[random] = 1
        var iranyokOsszeg = 0

        while (iranyokOsszeg < 4) {
            try {
                elmozdul(random)
                break
            }
            catch (e: MozgasHelyHianyMiattNemSikerultKivetel) {
                iranyokOsszeg = 0
                for (i in iranyok) {
                    iranyokOsszeg += i
                }
                do {
                    random = rnd.nextInt(4)
                } while (iranyok[random] == 1 && iranyokOsszeg < 4)
                iranyok[random] = 1
            }
        }
    }

    private fun elmozdul(random: Int) {
        when (random) {
            0 -> megy(2, 0)
            1 -> megy(-2, 0)
            2 -> megy(0, 2)
            3 -> megy(0, -2)
            }
        }

    companion object {
        private val rnd = Random()
    }

    override fun mukodik(){
        mozgas()
    }
}