package fr.linedev.infinityliquid.managers;

import fr.linedev.infinityliquid.InfinityLiquidMain;
import fr.linedev.infinityliquid.listeners.PlayersEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class EventsManager {

    public static void register(InfinityLiquidMain instance) {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new PlayersEvent(), instance);
    }
}
