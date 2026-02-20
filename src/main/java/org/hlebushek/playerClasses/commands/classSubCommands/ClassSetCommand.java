package org.hlebushek.playerClasses.commands.classSubCommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.hlebushek.playerClasses.PlayerClasses;
import org.hlebushek.playerClasses.commands.SubCommand;
import org.hlebushek.playerClasses.dataManage.ConfigManager;
import org.hlebushek.playerClasses.listeners.HobbitListener;
import org.hlebushek.playerClasses.model.Classes;
import org.hlebushek.playerClasses.dataManage.DataManager;
import org.hlebushek.playerClasses.dataManage.MessagesManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassSetCommand implements SubCommand {
    private final DataManager dataManager;
    private final MessagesManager messagesManager;
    private final ConfigManager configManager;

    public ClassSetCommand(PlayerClasses plugin) {
        dataManager = plugin.getDataManager();
        messagesManager = plugin.getMessagesManager();
        configManager = plugin.getConfigManager();
    }

    @Override
    public String getName() {
        return "set";
    }

    @Override
    public List<String> getArguments(String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 2) {
            for (Player p: Bukkit.getOnlinePlayers()) if (p.getName().toLowerCase().startsWith(args[1].toLowerCase()))
                completions.add(p.getName());
        }
        if (args.length == 3) {
            completions.addAll(Arrays.stream(Classes.values()).map(Enum::name).toList());
        }
        return completions;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("playerClasses.admin")) {
            sender.sendMessage(messagesManager.getMessage("no_permission"));
            return;
        }
        if (args.length != 3) {
            sender.sendMessage(messagesManager.getMessage("invalid_syntax"));
            return;
        }
        Player player = Bukkit.getPlayer(args[1]);
        if (player == null) {
            sender.sendMessage(messagesManager.getMessage("player_not_found"));
            return;
        }
        try {
            Classes playerClass = Classes.valueOf(args[2].toUpperCase());
            dataManager.setClass(player, playerClass);
            HobbitListener.setSize(player, dataManager, configManager);
            sender.sendMessage(messagesManager.getMessage("class_set").
                    replace("%player%", player.getName()).replace("%class%", playerClass.toString()));
        } catch (IllegalArgumentException e) {
            sender.sendMessage(messagesManager.getMessage("class_not_found"));
        }
    }
}
