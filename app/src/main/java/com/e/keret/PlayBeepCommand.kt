package com.e.keret

import android.content.Context
import com.e.datalayer.Music
import com.e.szerepjatek.MainActivity

class PlayBeepCommand(val context: Context) : Command {

    override fun execute(arg: ArrayList<Any>) {
        if (arg == null) {
            return
        }
        var mainActivity = context as MainActivity
        var music = arg[0]
        mainActivity.playBeep(music as Music)
    }
}
