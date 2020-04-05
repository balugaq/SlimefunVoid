package software.bigbade.slimefunvoid.items;

import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;

public class Items {
    //Private constructor to hide implicit public one
    private Items() {}

    public static final SlimefunItemStack VOID_RESEARCH_BENCH = new SlimefunItemStack("VOID_RESEARCH_BENCH", Material.ENCHANTING_TABLE, "&5Void Research Bench",
            "&aA powerful table capable of tapping into", "&5The Void");
    public static final SlimefunItemStack VOID_BAG = new SlimefunItemStack("VOID_BAG", Material.ENDER_CHEST, "&5Void Bag",
            "&aA bag capable of transporting items through &5The Void", "&5The Void &4will occasionally consume items");
}
