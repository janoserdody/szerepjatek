package com.e.szabalyok

import com.e.jatekter.JatekTer
import java.util.*

open class GepiJatekos(_x: Int, _y: Int, _jatekTer: JatekTer) :
    Jatekos(_x, _y, _jatekTer) {
    override val alak: Char
        get() = '\u2640'

    fun mozgas() {
        when (rnd.nextInt(3) + 1) {
            0 -> megy(1, 0)
            1 -> megy(-1, 0)
            2 -> megy(0, 1)
            3 -> megy(0, -1)
            else -> {
            }
        }
    }

    companion object {
        private val rnd = Random()
    }
}