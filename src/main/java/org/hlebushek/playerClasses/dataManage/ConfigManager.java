package org.hlebushek.playerClasses.dataManage;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.hlebushek.playerClasses.model.CrafterConfig;
import org.hlebushek.playerClasses.model.HobbitConfig;
import org.hlebushek.playerClasses.model.RunnerConfig;

public class ConfigManager {
    private final FileConfiguration config;

    public ConfigManager(JavaPlugin plugin) {
        plugin.saveDefaultConfig();
        config = plugin.getConfig();
    }

    public RunnerConfig getRunnerConfig(int playerLvl) {
        int lvl = Math.min(playerLvl, config.getInt("max_lvl"));
        int speed_level = config.getInt("runner.lvl" + lvl + ".speed_level");
        double fall_damage_multiplier = config.getDouble("runner.lvl" + lvl + ".fall_damage_multiplier");
        return new RunnerConfig(speed_level, fall_damage_multiplier);
    }

    public HobbitConfig getHobbitConfig(int playerLvl) {
        int lvl = Math.min(playerLvl, config.getInt("max_lvl"));
        double scale = config.getDouble("hobbit.scale");
        int food_bonus = config.getInt("hobbit.lvl" + lvl + ".food_bonus");
        return new HobbitConfig(scale, food_bonus);
    }

    public CrafterConfig getCrafterConfig(int playerLvl) {
        int lvl = Math.min(playerLvl, config.getInt("max_lvl"));
        double chance = config.getDouble("crafter.lvl" + lvl + ".chance");
        return new CrafterConfig(chance);
    }

    public int getPointsToUpgrade() {
        return config.getInt("points_to_upgrade");
    }
}
