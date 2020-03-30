package com.e.szabalyok

import com.e.jatekter.JatekElem
import com.e.jatekter.JatekTer
//import com.e.jatekter.Jatekos
import com.e.jatekter.RogzitettJatekElem
import com.e.keret.CommandId
import com.e.keret.CommandProcessor
import com.e.keret.ObservableKotlin
import com.e.megjelenites.IKirajzolhato
import com.e.szerepjatek.R

class Kincs(x: Int, y: Int, jatekTer: JatekTer, private val commandProcessor: CommandProcessor) :
    RogzitettJatekElem(x, y, jatekTer), IKirajzolhato, KincsFelvetelKezelo {
    override val meret: Double
        get() = 1.0

   override fun utkozes(jatekElem: JatekElem) {
        if (jatekElem is Jatekos) {
            jatekElem.pontotSzerez(50)
        }
        ter.Torles(this)

        KincsFelvetel(this, jatekElem as Jatekos)
    }

    override val alak: Int
        get() = R.drawable.treasure1

    // a CommandProcessorra kell feliratkozni, ha valamelyik osztály
    // akar értesülni a kincsfelvételről
    override fun KincsFelvetel(kincs: Kincs, jatekos: Jatekos) {
        var args = ArrayList<Any>(2)
        args.add(kincs)
        args.add(jatekos)
        commandProcessor.Execute(CommandId.KincsFelvetel, args)
    }
    //• Új publikus esemény: KincsFelvetel – legyen egy KincsFelvetelKezelo típusú eseménykezelő.
    //• Meglévő metódus módosítása: Utkozes – amennyiben egy játékos felvette a kincset, és
    //valaki feliratkozott a fenti eseménykezelőre, akkor küldjön az eseményről egy értesítést.
}