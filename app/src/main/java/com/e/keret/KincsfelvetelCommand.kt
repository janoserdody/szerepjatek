package com.e.keret

import com.e.szabalyok.Jatekos
import com.e.szabalyok.Kincs

class KincsfelvetelCommand(val keret: Keret) : Command {

    override fun Execute(arg: ArrayList<Any>) {
        if (arg == null) return
        var params = arg as ArrayList<Any>
        if (params.count() >= 2 && params[0] is Kincs && params[1] is Jatekos) {

            keret.KincsFelvetelTortent(params[0] as Kincs, params[1] as Jatekos)
        }
    }
}