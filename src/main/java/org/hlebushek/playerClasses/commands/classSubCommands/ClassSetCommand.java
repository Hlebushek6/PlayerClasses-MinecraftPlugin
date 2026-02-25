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

import java.util.*;

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
        if (args.length == 2)
            for (Player p: Bukkit.getOnlinePlayers()) if (p.getName().toLowerCase().startsWith(args[1].toLowerCase()))
                completions.add(p.getName());
        if (args.length == 3)
            for (String c: Arrays.stream(Classes.values()).map(e -> e.name().toLowerCase()).toList())
                if (c.startsWith(args[2].toLowerCase())) completions.add(c);
        return completions;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 3) {
            sender.sendMessage(messagesManager.getMessage("invalid_syntax"));
            return;
        }
        Player player = Bukkit.getPlayer(args[1]);
        if (player == null) {
            sender.sendMessage(messagesManager.getMessage("player_not_found"));
            return;
        }
        if (!sender.hasPermission("playerClasses.admin") && !(sender instanceof Player senderPlayer &&
                player.equals(senderPlayer) && dataManager.getClass(player) == Classes.NONE)) {
            sender.sendMessage(messagesManager.getMessage("no_permission"));
            return;
        }
        try {
            Classes playerClass = Classes.valueOf(args[2].toUpperCase());
            dataManager.setClass(player, playerClass);
            HobbitListener.setSize(player, dataManager, configManager);
            String playerName = player.getName();
            String playerClassString = playerClass.toString().toLowerCase();
            Map<String, String> placeholders = Map.of("%player%", playerName, "%class%", playerClassString);
            sender.sendMessage(messagesManager.getMessage("class_set", placeholders));
        } catch (IllegalArgumentException e) {
            sender.sendMessage(messagesManager.getMessage("class_not_found"));
        }
    }
}
