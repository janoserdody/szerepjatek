package com.e.keret

import com.e.jatekter.JatekTer
import com.e.szabalyok.Fal
import com.e.szabalyok.Kincs
import kotlin.random.Random

class Keret {
    constructor(){
        ter = JatekTer(PALYA_MERET_X, PALYA_MERET_Y)
        PalyaGeneralas()
    }
    private val PALYA_MERET_X: Int = 21
    private val PALYA_MERET_Y: Int = 11
    private val KINCSEK_SZAMA: Int = 10
    lateinit private var ter: JatekTer

    fun PalyaGeneralas(){
        for (x in 0 until PALYA_MERET_X){
            var fal = Fal(x, 0, ter)
        }
        for (x in 0 until PALYA_MERET_X) {
            var fal = Fal(x, PALYA_MERET_Y, ter)
        }
        for (y in 0 until PALYA_MERET_Y){
            var fal = Fal(0, y, ter)
        }
        for (y in 0 until PALYA_MERET_Y){
            var fal = Fal(PALYA_MERET_X, y, ter)
        }
        for (i in 0 until KINCSEK_SZAMA) {
            var kincsX = Random.nextInt(0, PALYA_MERET_X)
            var kincsY = Random.nextInt(0, PALYA_MERET_Y)
            var megadottHelyenLevo: Double = 0.0

            for (elem in ter.MegadottHelyenLevok(kincsX, kincsY)){
                megadottHelyenLevo += elem.meret
            }
            if (!(kincsX == 1 && kincsY == 1) && megadottHelyenLevo < 1) {
                var kincs = Kincs(kincsX, kincsY, ter)
            }
        }
    }

}