package com.e.keret

import android.content.Context
import com.e.jatekter.JatekElem
import com.e.szerepjatek.MainActivity

class FightCommand(val context: Context): Command {

    override fun Execute(arg:ArrayList<Any>) {
        val mainActivity = context as MainActivity
        val jatekosTamado = arg[0] as JatekElem
        val jatekosVedo = arg[1] as JatekElem
        mainActivity.Fight(jatekosTamado, jatekosVedo)
    }
}