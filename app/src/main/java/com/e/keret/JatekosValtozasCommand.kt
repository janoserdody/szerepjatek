package com.e.keret

import com.e.szabalyok.Jatekos
import com.e.szabalyok.Kincs

class JatekosValtozasCommand(val keret: Keret) : Command {

    override fun Execute(arg: ArrayList<Any>) {
        if (arg == null) return
        var params = arg as ArrayList<Any>
        if (params.count() >= 3 && params[0] is Jatekos && params[1] is Int && params[2] is Int) {

            keret.JatekosValtozasTortent(params[0] as Jatekos, params[1] as Int, params[2] as Int)
        }


    }
}