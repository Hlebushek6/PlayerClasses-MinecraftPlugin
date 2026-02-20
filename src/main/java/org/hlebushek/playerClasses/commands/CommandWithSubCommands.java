package org.hlebushek.playerClasses.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.hlebushek.playerClasses.PlayerClasses;
import org.hlebushek.playerClasses.commands.classSubCommands.ClassSetCommand;
import org.hlebushek.playerClasses.commands.classSubCommands.ClassShowCommand;
import org.hlebushek.playerClasses.dataManage.MessagesManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CommandWithSubCommands implements CommandExecutor, TabCompleter {
    protected final MessagesManager messages;
    protected final Map<String, SubCommand> subCommands = new HashMap<>();

    public CommandWithSubCommands(PlayerClasses plugin) {
        messages = plugin.getMessagesManager();
    }

    protected void register(SubCommand subCommand) {
        subCommands.put(subCommand.getName(), subCommand);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            sender.sendMessage(messages.getMessage("invalid_syntax"));
        }

        SubCommand subCommand = subCommands.get(args[0].toLowerCase());
        if (subCommand == null) {
            sender.sendMessage(messages.getMessage("invalid_syntax"));
            return true;
        }

        subCommand.execute(sender, args);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.addAll(subCommands.keySet().stream().filter(s ->
                    s.toLowerCase().startsWith(args[0].toLowerCase())).toList());
        }
        else {
            SubCommand subCommand = subCommands.get(args[0].toLowerCase());
            if (subCommand != null) completions = subCommand.getArguments(args);
        }
        return completions;
    }
}
