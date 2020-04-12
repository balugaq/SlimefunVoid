package software.bigbade.slimefunvoid.items;

import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
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
    public static final SlimefunItemStack VOID_ATTRACTOR = new SlimefunItemStack("VOID_ATTRACTOR", Material.END_CRYSTAL, "&5Void Attractor",
            "&5Sucks all nearby entities", "&5towards it", DANGEROUS_ITEM);
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
    public static final SlimefunItemStack ADVANCED_WAND = new SlimefunItemStack("ADVANCED_WAND", Material.BLAZE_ROD, "&Advanced Wand",
            "&aA refined wand, allowing for stronger spells", "&aMax Elements: 500", DANGEROUS_ITEM);

    /**
     * Spells
     */
    public static final SlimefunItemStack FIREBALL_SPELL = new SlimefunItemStack("FIREBALL_SPELL", Material.FIRE_CHARGE, "&cFireball Spell",
            "&aAllows wands to shoot out", "&alarge bursts of flames", DANGEROUS_ITEM);
    public static final SlimefunItemStack COMBUSTION_SPELL = new SlimefunItemStack("COMBUSTION_SPELL", Material.FLINT_AND_STEEL, "&cCombustion Spell",
            "&aSpontaniously ignites whatever", "&ayou are looking at");
    public static final SlimefunItemStack LIGHT_BENDING_SPELL = new SlimefunItemStack("LIGHT_BENDING_SPELL", Material.GLASS, "&fLight Bending Spell",
            "&aManipulates the light around you", "&aMaking you, but not your armor, completely invisible");
    public static final SlimefunItemStack LIGHT_BEAM_SPELL = new SlimefunItemStack("LIGHT_BEAM_SPELL", Material.GLOWSTONE_DUST, "&fLight Beam Spell",
            "&aSummons a beam of pure light", "&ato damage enemies");
    public static final SlimefunItemStack TRACKING_BEAM_SPELL = new SlimefunItemStack("TRACKING_BEAM_SPELL", Material.GLOWSTONE, "&fTracking Beam Spell",
            "&aSummons a beam of pure light", "&athat tracks enemies");
    public static final SlimefunItemStack TELEPORT_SPELL = new SlimefunItemStack("TELEPORT_SPELL", Material.ENDER_PEARL, "&5Teleport Spell",
            "&aA portal through the void allows", "&ainstantaneous teleportation");
    public static final SlimefunItemStack SWAP_SPELL = new SlimefunItemStack("SWAP_SPELL", Material.ENDER_EYE, "&5Swap Spell",
            "&aA small portal switches the location", "&aof two living beings");
    public static final SlimefunItemStack LIGHTNING_SPELL = new SlimefunItemStack("LIGHTNING_SPELL", Material.DIAMOND_AXE, "&eLightning Spell",
            "&aElectricity can be channeled into a bolt", "&aAllowing for high damage,", "&aamong other properties.");
    public static final SlimefunItemStack WATER_SHOCK_SPELL = new SlimefunItemStack("WATER_SHOCK_SPELL", Material.WATER_BUCKET, "&eWater Shock Spell",
            "&aConducts electricity through the water,", "&ashocking all other players in water");
    public static final SlimefunItemStack LEVITATE_SPELL = new SlimefunItemStack("LEVITATE_SPELL", Material.SHULKER_SHELL, "&7Levitate Spell",
            "&aBy increasing the density of", "&athe air beneath the target", "&aThe target will start to float");
    public static final SlimefunItemStack LAUNCH_SPELL = new SlimefunItemStack("LAUNCH_SPELL", Material.SHULKER_BOX, "&7Launch Spell",
            "&aUse a large amount of upwards momentum", "&ato send your target flying");
    public static final SlimefunItemStack THROW_SPELL = new SlimefunItemStack("THROW_SPELL", Material.FEATHER, "&7Throw Spell",
            "&aAllows you to send your target", "&awhere you are looking");
    public static final SlimefunItemStack FAST_SWIMMING_SPELL = new SlimefunItemStack("FAST_SWIMMING_SPELL", Material.OAK_BOAT, "&1Fast Swimming Spell",
            "&aAllows you to swim", "&athrough the water faster");
    public static final SlimefunItemStack ICE_SHIELD_SPELL = new SlimefunItemStack("ICE_SHIELD_SPELL", Material.ICE, "&1Ice Shield Spell",
            "&aFreezes water in the air", "&aForming a sphere of ice");
    public static final SlimefunItemStack TREE_TRAP_SPELL = new SlimefunItemStack("TREE_TRAP_SPELL", Material.OAK_SAPLING, "&aTree Trap Spell",
            "&aTraps target in a tree");

    private static final SlimefunItemStack[] WANDS = new SlimefunItemStack[]{Items.BASIC_WAND, Items.ADVANCED_WAND};

    public static SlimefunItemStack[] getWands() {
        return WANDS;
    }
}