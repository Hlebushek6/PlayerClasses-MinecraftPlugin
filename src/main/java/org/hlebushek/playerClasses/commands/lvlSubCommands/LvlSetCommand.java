package org.hlebushek.playerClasses.commands.lvlSubCommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.hlebushek.playerClasses.PlayerClasses;
import org.hlebushek.playerClasses.commands.SubCommand;
import org.hlebushek.playerClasses.dataManage.ConfigManager;
import org.hlebushek.playerClasses.dataManage.DataManager;
import org.hlebushek.playerClasses.dataManage.MessagesManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LvlSetCommand implements SubCommand {
    private final DataManager dataManager;
    private final MessagesManager messages;
    private final ConfigManager config;

    public LvlSetCommand(PlayerClasses plugin) {
        dataManager = plugin.getDataManager();
        messages = plugin.getMessagesManager();
        config = plugin.getConfigManager();
    }

    @Override
    public String getName() {
        return "set";
    }

    @Override
    public List<String> getArguments(String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 2) for (Player p: Bukkit.getOnlinePlayers())
            if(p.getName().toLowerCase().startsWith(args[1].toLowerCase())) completions.add(p.getName());
        return completions;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("playerClasses.admin")) {
            sender.sendMessage(messages.getMessage("no_permission"));
            return;
        }
        if (args.length != 3) {
            sender.sendMessage(messages.getMessage("invalid_syntax"));
            return;
        }

        Player player = Bukkit.getPlayer(args[1]);
        if (player == null) {
            sender.sendMessage(messages.getMessage("player_not_found"));
            return;
        }

        try {
            int pointsToUpgrade = config.getPointsToUpgrade();
            int currPoints = dataManager.getPoints(player);
            int points = (Integer.parseInt(args[2]) - 1) * pointsToUpgrade + currPoints % pointsToUpgrade;
            dataManager.setPoints(player, points);
            String playerName = player.getName();
            Map<String, String> placeholders = Map.of("%player%", playerName, "%lvl%", args[2]);
            sender.sendMessage(messages.getMessage("lvl_set", placeholders));

        } catch (NumberFormatException e) {
            sender.sendMessage(messages.getMessage("NaN"));
        }
    }
}
