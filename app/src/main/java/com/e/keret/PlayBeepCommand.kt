package com.e.keret

import android.content.Context
import com.e.szerepjatek.MainActivity

class PlayBeepCommand(val context: Context) : Command {

    override fun Execute(arg: ArrayList<Any>) {
        var mainActivity = context as MainActivity
        mainActivity.PlayBeep()
    }
}