package com.e.szabalyok

import com.e.jatekter.JatekElem
import com.e.jatekter.JatekTer
//import com.e.jatekter.Jatekos
import com.e.jatekter.RogzitettJatekElem
import com.e.keret.CommandId
import com.e.keret.CommandProcessor
import com.e.megjelenites.Kirajzolhato
import com.e.szerepjatek.R

class Kincs(x: Int, y: Int, jatekTer: JatekTer, private val commandProcessor: CommandProcessor) :
    RogzitettJatekElem(x, y, jatekTer), Kirajzolhato, KincsFelvetelKezelo {
    override val meret: Double
        get() = 1.0

   override fun utkozes(jatekElem: JatekElem, serul: Int) {
        if (jatekElem is Jatekos) {
            jatekElem.pontotSzerez(50)
        }
        ter.torles(this)

        kincsFelvetel(this, jatekElem as Jatekos)
    }

    override var alak: Int = R.drawable.treasure1

    // a CommandProcessorra kell feliratkozni, ha valamelyik osztály
    // akar értesülni a kincsfelvételről
    override fun kincsFelvetel(kincs: Kincs, jatekos: Jatekos) {
        var args = ArrayList<Any>(2)
        args.add(kincs)
        args.add(jatekos)
        commandProcessor.execute(CommandId.KincsFelvetel, args)
    }
}