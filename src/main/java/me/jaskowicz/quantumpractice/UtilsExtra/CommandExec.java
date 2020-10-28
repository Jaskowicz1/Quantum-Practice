package me.jaskowicz.quantumpractice.UtilsExtra;

import org.bukkit.command.CommandExecutor;

public interface CommandExec extends CommandExecutor {

    String name();

    String[] otherNames();

}
