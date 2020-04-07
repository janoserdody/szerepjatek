package com.e.keret

import android.content.Context
import com.e.szerepjatek.MainActivity

class ExitCommand(val context: Context): Command {
    override fun Execute(arg:ArrayList<Any>) {
        var main = context as MainActivity
        main.Exit()
    }
}
