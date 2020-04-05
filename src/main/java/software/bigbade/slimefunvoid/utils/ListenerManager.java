package software.bigbade.slimefunvoid.utils;

import org.bukkit.Bukkit;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.listeners.VoidBagListener;
import software.bigbade.slimefunvoid.listeners.VoidResearchPopulateListener;

public class ListenerManager {
    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new VoidBagListener(), SlimefunVoid.getInstance());
        Bukkit.getPluginManager().registerEvents(new VoidResearchPopulateListener(), SlimefunVoid.getInstance());
    }
}
