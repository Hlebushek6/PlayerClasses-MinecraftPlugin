package org.hlebushek.playerClasses.commands.classSubCommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.hlebushek.playerClasses.PlayerClasses;
import org.hlebushek.playerClasses.commands.SubCommand;
import org.hlebushek.playerClasses.dataManage.DataManager;
import org.hlebushek.playerClasses.dataManage.MessagesManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClassShowCommand implements SubCommand {
    private final DataManager dataManager;
    private final MessagesManager messagesManager;

    public ClassShowCommand(PlayerClasses plugin) {
        dataManager = plugin.getDataManager();
        messagesManager = plugin.getMessagesManager();
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public List<String> getArguments(String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 2) {
            for (Player p: Bukkit.getOnlinePlayers()) if (p.getName().toLowerCase().startsWith(args[1].toLowerCase()))
                completions.add(p.getName());
        }
        return completions;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 1 && sender instanceof Player player) {
            String playerClassString = dataManager.getClass(player).toString().toLowerCase();
            String playerName = player.getName();
            Map<String, String> placeholders = Map.of("%player%", playerName, "%class%", playerClassString);
            sender.sendMessage(messagesManager.getMessage("show_class", placeholders));
        }
        else if (args.length == 2) {
            if (!sender.hasPermission("playerClasses.admin")) {
                sender.sendMessage(messagesManager.getMessage("no_permission"));
            }
            Player player = Bukkit.getPlayer(args[1]);
            if (player == null) {
                sender.sendMessage(messagesManager.getMessage("player_not_found"));
                return;
            }
            String playerClassString = dataManager.getClass(player).toString().toLowerCase();
            String playerName = player.getName();
            Map<String, String> placeholders = Map.of("%player%", playerName, "%class%", playerClassString);
            sender.sendMessage(messagesManager.getMessage("show_class", placeholders));
        }
        else {
            sender.sendMessage(messagesManager.getMessage("invalid_syntax"));
        }
    }
}
