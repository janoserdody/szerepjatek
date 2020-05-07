package com.e.keret

import kotlin.collections.ArrayList

class CommandProcessor() {
    var commands = mutableMapOf<CommandId, Command>()

    fun setCommand(commandId: CommandId, command: Command){
        commands.put(commandId, command)
    }

    fun execute(commandId: CommandId, arg:ArrayList<Any>){
        val command = commands[commandId] as Command;

        if (command == null){
            return
        }

        command.execute(arg)
    }
}