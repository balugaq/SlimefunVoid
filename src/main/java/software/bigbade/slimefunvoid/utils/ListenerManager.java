package software.bigbade.slimefunvoid.utils;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.listeners.VoidBagListener;
import software.bigbade.slimefunvoid.listeners.VoidResearchNotePopulateListener;

@RequiredArgsConstructor
public class ListenerManager {
    private final SlimefunVoid main;

    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new VoidBagListener(main.getItemManager().getVoidBag()), SlimefunVoid.getInstance());
        Bukkit.getPluginManager().registerEvents(new VoidResearchNotePopulateListener(), SlimefunVoid.getInstance());
        //Bukkit.getPluginManager().registerEvents(new QuarryBreakListener(main.getItemManager().getQuarry()), SlimefunVoid.getInstance());
    }
}
