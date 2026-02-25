package org.hlebushek.playerClasses.listeners;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.hlebushek.playerClasses.PlayerClasses;
import org.hlebushek.playerClasses.dataManage.ConfigManager;
import org.hlebushek.playerClasses.dataManage.DataManager;
import org.hlebushek.playerClasses.model.Classes;

import java.util.Objects;

public class HobbitListener implements Listener {
    private final DataManager dataManager;
    private final ConfigManager config;

    public HobbitListener (PlayerClasses plugin) {
        dataManager = plugin.getDataManager();
        config = plugin.getConfigManager();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        setSize(e.getPlayer(), dataManager, config);
    }

    @EventHandler
    public void onEat(PlayerItemConsumeEvent e) {
        Player player = e.getPlayer();
        if (dataManager.getClass(player) != Classes.HOBBIT || !e.getItem().getType().isEdible()) return;

        int food_bonus = config.getHobbitConfig(dataManager.getLevel(player)).food_bonus();
        player.setFoodLevel(player.getFoodLevel() + food_bonus);
        player.setSaturation(player.getSaturation() + food_bonus);
    }

    public static void setSize(Player player, DataManager dataManager, ConfigManager config) {
        double scale = config.getHobbitConfig(dataManager.getLevel(player)).scale();
        if (dataManager.getClass(player) == Classes.HOBBIT)
            Objects.requireNonNull(player.getAttribute(Attribute.SCALE)).setBaseValue(scale);
        else Objects.requireNonNull(player.getAttribute(Attribute.SCALE)).setBaseValue(1);
    }
}