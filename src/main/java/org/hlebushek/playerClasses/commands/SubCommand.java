package org.hlebushek.playerClasses.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface SubCommand {
    String getName();
    List<String> getArguments(String[] args);
    void execute(CommandSender sender, String[] args);
}
