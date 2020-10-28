package me.jaskowicz.quantumpractice.Commands;

import me.jaskowicz.quantumpractice.UtilsExtra.CommandExec;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class PracticeCommand implements CommandExec {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {



        return true;
    }

    @Override
    public String name() {
        return "Practice";
    }

    @Override
    public String[] otherNames() {

        String[] aliases = new String[1];

        aliases[0] = "prac";

        return aliases;
    }
}
