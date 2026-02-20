package org.hlebushek.playerClasses.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.hlebushek.playerClasses.PlayerClasses;
import org.hlebushek.playerClasses.model.Classes;
import org.hlebushek.playerClasses.dataManage.ConfigManager;
import org.hlebushek.playerClasses.dataManage.DataManager;

public class RunnerListener implements Listener {
    private final DataManager dataManager;
    private final ConfigManager configManager;

    public RunnerListener (PlayerClasses plugin) {
        dataManager = plugin.getDataManager();
        configManager = plugin.getConfigManager();
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (dataManager.getClass(player) != Classes.RUNNER || !player.isSprinting()) return;
        PotionEffect effect;
        int speed_level = configManager.getRunnerConfig().speed_level();
        if (player.hasPotionEffect(PotionEffectType.SPEED)) {
            PotionEffect currentEffect = player.getPotionEffect(PotionEffectType.SPEED);
            effect = new PotionEffect(PotionEffectType.SPEED, 40, Math.max(
                    currentEffect.getAmplifier(), speed_level
            ), currentEffect.isAmbient(), currentEffect.hasParticles(), currentEffect.hasIcon());
        }
        else
            effect = new PotionEffect(
                    PotionEffectType.SPEED, 40, speed_level, true, false, false
            );
        player.addPotionEffect(effect);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player player)) return;
        if (dataManager.getClass(player) != Classes.RUNNER || !(e.getCause() == EntityDamageEvent.DamageCause.FALL))
            return;
        double fall_damage_multiplier = configManager.getRunnerConfig().fall_damage_multiplier();
        e.setDamage(e.getDamage() * fall_damage_multiplier);
    }
}
