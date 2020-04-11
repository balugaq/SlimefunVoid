package software.bigbade.slimefunvoid.items;

import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public final class Items {
    //Private constructor to hide implicit public one
    private Items() {}

    private static final String DANGEROUS_ITEM = "&4Dangerous Item";

    /**
     * Blocks
     */
    public static final SlimefunItemStack VOID_RESEARCH_BENCH = new SlimefunItemStack("VOID_RESEARCH_BENCH", Material.ENCHANTING_TABLE, "&5Void Research Bench",
            "&aA powerful table, allows for researching of", "&5The Void");
    public static final SlimefunItemStack VOID_PORTAL = new SlimefunItemStack("VOID_PORTAL", Material.END_PORTAL_FRAME, "&5Void Portal",
            "&5Allows access into the void", "&5and Void Rituals with Void Altars", DANGEROUS_ITEM);
    public static final SlimefunItemStack VOID_ALTAR = new SlimefunItemStack("VOID_ALTAR", Material.END_STONE_BRICKS, "&5Void Altar",
            "&5Holds items for tapping into The Void", DANGEROUS_ITEM);
    public static final SlimefunItemStack VOID_QUARRY = new SlimefunItemStack("VOID_QUARRY", Material.ENDER_CHEST, "&5Void Quarry",
            "&5Allows the mining of","&5large areas of blocks", "&5The Void &cconsumes some blocks mined");

    /**
     * Items
     */
    public static final SlimefunItemStack VOID_BAG = new SlimefunItemStack("VOID_BAG", Material.ENDER_CHEST, "&5Void Bag",
            "&aA bag capable of transporting items", "&athrough &5The Void", "&5The Void &4will occasionally consume items", "&aShift Right Click to bind to a chest");

    /**
     * Wands
     */
    public static final SlimefunItemStack BASIC_WAND = new SlimefunItemStack("BASIC_WAND", Material.STICK, "&dBasic Wand",
            "&aCapable of precise energy usage", "&afrom &5The Void", "&aMax Elements: 300", DANGEROUS_ITEM);
    public static final SlimefunItemStack ADVANCED_WAND = new SlimefunItemStack("ADVANCED_WAND", Material.BLAZE_ROD, "&dBasic Wand",
            "&aA refined wand, allowing for stronger spells", "&aMax Elements: 500", DANGEROUS_ITEM);

    /**
     * Spells
     */
    public static final SlimefunItemStack FIREBALL_SPELL = new SlimefunItemStack("FIREBALL_SPELL", Material.FIRE_CHARGE, "&cFireball Spell",
            "&4Allows wands to shoot out", "&4large bursts of flames", DANGEROUS_ITEM);
    public static final SlimefunItemStack LIGHT_BENDING_SPELL = new SlimefunItemStack("LIGHT_BENDING_SPELL", Material.GLASS, "&fLight Bending Spell",
            "&4Manipulates the light around you", "&4Making you, but not your armor, completely invisible");
    public static final SlimefunItemStack TELEPORT_SPELL = new SlimefunItemStack("TELEPORT_SPELL", Material.ENDER_EYE, "&5Teleport Spell",
            "&5A portal through the void allows", "&5instantaneous teleportation");

    private static final SlimefunItemStack[] WANDS = new SlimefunItemStack[]{Items.BASIC_WAND, Items.ADVANCED_WAND};

    public static SlimefunItemStack[] getWands() {
        return WANDS;
    }
}