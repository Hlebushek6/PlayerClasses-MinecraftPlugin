package org.hlebushek.playerClasses.commands.classSubCommands;

import org.bukkit.command.CommandSender;
import org.hlebushek.playerClasses.PlayerClasses;
import org.hlebushek.playerClasses.commands.SubCommand;
import org.hlebushek.playerClasses.dataManage.MessagesManager;
import org.hlebushek.playerClasses.model.Classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("ClassCanBeRecord")
public class ClassInfoCommand implements SubCommand {
    private final MessagesManager messages;

    public ClassInfoCommand (PlayerClasses plugin) {
        messages = plugin.getMessagesManager();
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public List<String> getArguments(String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 2)
            for (String c: Arrays.stream(Classes.values()).map(e -> e.name().toLowerCase()).toList())
                if (c.startsWith(args[1].toLowerCase()) && !c.equals("none")) completions.add(c);
        return completions;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length > 2) {
            sender.sendMessage(messages.getMessage("invalid_syntax"));
            return;
        }
        if (args.length == 1) sender.sendMessage(messages.getMessage("class_info.general"));
        if (args.length == 2) {
            String playerClass = args[1].toLowerCase();
            List<String> classes = Arrays.stream(Classes.values()).map(e -> e.name().toLowerCase()).toList();
            if (!classes.contains(playerClass) || playerClass.equals("none")) {
                sender.sendMessage(messages.getMessage("class_not_found"));
                return;
            }
            sender.sendMessage(messages.getMessage("class_info." + playerClass));
        }
    }
}
