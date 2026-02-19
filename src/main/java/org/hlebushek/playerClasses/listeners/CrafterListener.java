package org.hlebushek.playerClasses.listeners;

import org.bukkit.event.Listener;
import org.hlebushek.playerClasses.PlayerClasses;
import org.hlebushek.playerClasses.dataManage.DataManager;

public class CrafterListener implements Listener {
    private final DataManager dataManager;

    public CrafterListener (PlayerClasses plugin) {
        dataManager = plugin.getDataManager();
    }

    //TODO
}
