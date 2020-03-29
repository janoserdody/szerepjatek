package com.e.szabalyok

import com.e.jatekter.JatekTer
import com.e.szerepjatek.R
import java.util.*

open class GepiJatekos(_x: Int, _y: Int, _jatekTer: JatekTer) :
    Jatekos(_x, _y, _jatekTer) {
    override val alak: Int
        get() = R.drawable.fighter2

    fun mozgas() {
        when (rnd.nextInt(3)) {
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