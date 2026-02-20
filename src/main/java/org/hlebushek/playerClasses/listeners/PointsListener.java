package org.hlebushek.playerClasses.listeners;

import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Enemy;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.CraftingRecipe;
import org.hlebushek.playerClasses.PlayerClasses;
import org.hlebushek.playerClasses.dataManage.DataManager;
import org.hlebushek.playerClasses.model.Classes;

public class PointsListener implements Listener {
    private final DataManager dataManager;

    public PointsListener(PlayerClasses plugin) {
        dataManager = plugin.getDataManager();
    }

    @EventHandler
    public void onAdvancement(PlayerAdvancementDoneEvent e) {
        addPoint(e.getPlayer());
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (dataManager.getClass(player) == Classes.RUNNER && !player.isSprinting() && e.hasChangedBlock())
            addPoint(player);
        if (dataManager.getClass(player) == Classes.HOBBIT && player.isSneaking() && e.hasChangedBlock())
            addPoint(player);
    }

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        if (e.getWhoClicked() instanceof Player player && dataManager.getClass(player) == Classes.CRAFTER &&
                !(e.getRecipe() instanceof CraftingRecipe)) addPoint(player);
    }

    private void addPoint(Player player) {
        dataManager.setPoints(player, dataManager.getPoints(player) + 1);
    }
}
