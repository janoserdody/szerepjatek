package com.e.szabalyok

import com.e.automatizmus.IAutomatikusanMukodo
import com.e.jatekter.JatekTer
import com.e.keret.CommandProcessor
import com.e.szerepjatek.R
import java.util.*

open class GepiJatekos(_x: Int, _y: Int, _jatekTer: JatekTer,
                       override val mukodesIntervallum: Int, commandProcessor: CommandProcessor) :
    Jatekos(_x, _y, _jatekTer, commandProcessor), IAutomatikusanMukodo {

    override val alak: Int
        get() = R.drawable.fighter2

    fun mozgas() {
        val iranyok = arrayOf(0,0,0,0)
        var random = rnd.nextInt(3)
        iranyok[random] = 1
        var iranyokOsszeg = 0

        while (iranyokOsszeg < 4) {
            try {
                when (random) {
                    0 -> megy(1, 0)
                    1 -> megy(-1, 0)
                    2 -> megy(0, 1)
                    3 -> megy(0, -1)
                    else -> {
                    }
                }
            } catch (e: MozgasHelyHianyMiattNemSikerultKivetel) {
                iranyokOsszeg = 0
                for (i in iranyok){
                    iranyokOsszeg += i
                }
                    do {
                        var random = rnd.nextInt(3)
                    } while (iranyok[random] == 1 && iranyokOsszeg < 4)
                    iranyok[random] = 1
            }
        }
    }

    companion object {
        private val rnd = Random()
    }

    override fun mukodik(){
        mozgas()
    }
}