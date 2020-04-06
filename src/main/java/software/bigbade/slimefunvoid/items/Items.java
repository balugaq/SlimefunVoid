package software.bigbade.slimefunvoid.items;

import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;

public class Items {
    //Private constructor to hide implicit public one
    private Items() {}

    private static final String DANGEROUS_ITEM = "&4Dangerous Item";

    public static final SlimefunItemStack VOID_RESEARCH_BENCH = new SlimefunItemStack("VOID_RESEARCH_BENCH", Material.ENCHANTING_TABLE, "&5Void Research Bench",
            "&aA powerful table capable of tapping into", "&5The Void");
    public static final SlimefunItemStack VOID_BAG = new SlimefunItemStack("VOID_BAG", Material.ENDER_CHEST, "&5Void Bag",
            "&aA bag capable of transporting items through &5The Void", "&5The Void &4will occasionally consume items", "&aShift Right Click to bind the target chest");
    public static final SlimefunItemStack VOID_PORTAL = new SlimefunItemStack("VOID_PORTAL", Material.END_PORTAL_FRAME, "&5Void Portal",
            "&5Allows access into the void, and holds Void Wands", DANGEROUS_ITEM);
    public static final SlimefunItemStack VOID_ALTAR = new SlimefunItemStack("VOID_ALTAR", Material.END_STONE_BRICKS, "&5Void Altar",
            "&5Holds items for tapping into The Void", DANGEROUS_ITEM);
    public static final SlimefunItemStack BASIC_WAND = new SlimefunItemStack("BASIC_WAND", Material.STICK, "&dBasic Wand",
            "&4Capable of precise energy usage from &5The Void", DANGEROUS_ITEM);
}
