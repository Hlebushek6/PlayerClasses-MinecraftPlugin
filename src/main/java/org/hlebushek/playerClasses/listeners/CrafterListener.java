package org.hlebushek.playerClasses.listeners;

import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.CraftingRecipe;
import org.bukkit.inventory.ItemStack;
import org.hlebushek.playerClasses.PlayerClasses;
import org.hlebushek.playerClasses.dataManage.ConfigManager;
import org.hlebushek.playerClasses.dataManage.DataManager;
import org.hlebushek.playerClasses.model.Classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CrafterListener implements Listener {
    private final DataManager dataManager;
    private final ConfigManager configManager;

    public CrafterListener (PlayerClasses plugin) {
        dataManager = plugin.getDataManager();
        configManager = plugin.getConfigManager();
    }

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        if (!(e.getWhoClicked() instanceof Player player) || dataManager.getClass(player) != Classes.CRAFTER ||
                !(e.getRecipe() instanceof CraftingRecipe)) return;
        double chance = configManager.getCrafterConfig().chance();
        if (Math.random() > chance) return;
        ItemStack[] inv = e.getInventory().getMatrix();
        List<Integer> slots = new ArrayList<>();

        for (int i = 0; i < inv.length; i ++) if (inv[i] != null && !inv[i].getType().isAir()) slots.add(i);
        if (slots.size() < 2) return;

        int slot = slots.get(new Random().nextInt(slots.size()));
        ItemStack item = inv[slot];
        item.setAmount(Math.min(item.getMaxStackSize(), item.getAmount() + 1));
        inv[slot] = item;
        e.getInventory().setMatrix(inv);
    }
}
