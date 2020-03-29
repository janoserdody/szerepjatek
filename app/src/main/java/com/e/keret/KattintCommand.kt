package com.e.keret

class KattintCommand(val keret: Keret) : Command {

    override fun Execute(arg:ArrayList<Any>) {
        val x = arg[0] as Int
        val y = arg[1] as Int
        keret.Kattint(x, y)
    }



}