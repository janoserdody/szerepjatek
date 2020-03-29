package com.e.keret

import android.widget.TableRow
import java.util.*
import kotlin.collections.ArrayList

class CommandProcessor() {
    var commands = mutableMapOf<CommandId, Command>()

    fun SetCommand(commandId: CommandId, command: Command){
        commands.put(commandId, command)
    }

    fun OnKattint(commandId: CommandId, arg:ArrayList<Any>){
        val command = commands[commandId] as KattintCommand;

        if (command == null){
            return
        }

        command.Execute(arg)
    }
}