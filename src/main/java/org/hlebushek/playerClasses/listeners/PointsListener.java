package org.hlebushek.playerClasses.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRecipeDiscoverEvent;
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

    //hobbit & runner
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (dataManager.getClass(player) == Classes.RUNNER && !player.isSprinting() && e.hasChangedBlock())
            addPoint(player);
        if (dataManager.getClass(player) == Classes.HOBBIT && player.isSneaking() && e.hasChangedBlock())
            addPoint(player);
    }

    //crafter
    @EventHandler
    public void onAnvilUse(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player player && dataManager.getClass(player) == Classes.CRAFTER &&
                e.getInventory().getType() == InventoryType.ANVIL && e.getSlotType() == InventoryType.SlotType.RESULT)
            addPoint(player);
    }

    @EventHandler
    public void onRecipeUnlock(PlayerRecipeDiscoverEvent e) {
        if (dataManager.getClass(e.getPlayer()) == Classes.CRAFTER) addPoint(e.getPlayer());
    }

    private void addPoint(Player player) {
        dataManager.setPoints(player, dataManager.getPoints(player) + 1);
    }
}
