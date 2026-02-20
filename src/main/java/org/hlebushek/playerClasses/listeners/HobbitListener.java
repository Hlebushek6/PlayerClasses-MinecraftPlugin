package org.hlebushek.playerClasses.listeners;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.hlebushek.playerClasses.PlayerClasses;
import org.hlebushek.playerClasses.dataManage.ConfigManager;
import org.hlebushek.playerClasses.dataManage.DataManager;
import org.hlebushek.playerClasses.model.Classes;

public class HobbitListener implements Listener {
    private final DataManager dataManager;
    private final ConfigManager configManager;

    public HobbitListener (PlayerClasses plugin) {
        dataManager = plugin.getDataManager();
        configManager = plugin.getConfigManager();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        setSize(e.getPlayer(), dataManager, configManager);
    }

    public static void setSize(Player player, DataManager dataManager, ConfigManager configManager) {
        double scale = configManager.getHobbitConfig().scale();
        if (dataManager.getClass(player) == Classes.HOBBIT) player.getAttribute(Attribute.SCALE).setBaseValue(scale);
        else player.getAttribute(Attribute.SCALE).setBaseValue(1);
    }
}