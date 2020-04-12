package software.bigbade.slimefunvoid.api.research;

import me.mrCookieSlime.Slimefun.Objects.Research;
import me.mrCookieSlime.Slimefun.api.Slimefun;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.NamespacedKey;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.items.Items;
import software.bigbade.slimefunvoid.utils.ResearchIDHandler;

public enum Researches {
    VOID_BENCH("Void Research Bench", 30, Items.VOID_RESEARCH_BENCH),
    VOID_BAG("Void Bag", 10, Items.VOID_BAG),
    VOID_PORTAL("Void Portal", 30, Items.VOID_PORTAL),
    VOID_ALTAR("Void Altar", 15, Items.VOID_ALTAR),
    BASIC_WAND("Basic Wand", 20, Items.BASIC_WAND),
    ADVANCED_WAND("Advanced Wand", 30, Items.ADVANCED_WAND),
    COMBUSTION_SPELL("Combustion Spell", 10, Items.COMBUSTION_SPELL),
    FIREBALL_SPELL("Fireball Spell", 15, Items.FIREBALL_SPELL),
    LIGHT_BENDING_SPELL("Light Bending Spell", 5, Items.LIGHT_BENDING_SPELL),
    LIGHT_BEAM_SPELL("Light Beam Spell", 15, Items.LIGHT_BEAM_SPELL),
    TRACKING_BEAM_SPELL("Tracking Beam Spell", 25, Items.TRACKING_BEAM_SPELL),
    TELEPORT_SPELL("Teleport Spell", 5, Items.TELEPORT_SPELL),
    SWAP_SPELL("Swap Spell", 15, Items.SWAP_SPELL),
    LIGHTNING_SPELL("Lightning Spell", 10, Items.LIGHTNING_SPELL),
    WATER_SHOCK_SPELL("Water Shock Spell", 15, Items.WATER_SHOCK_SPELL),
    LEVITATE_SPELL("Levitate Spell", 5, Items.LEVITATE_SPELL),
    FAST_SWIMMING_SPELL("Fast Swimming Spell", 5, Items.FAST_SWIMMING_SPELL),
    ICE_SHIELD_SPELL("Ice Shield Spell", 15, Items.ICE_SHIELD_SPELL),
    TREE_TRAP_SPELL("Tree Trap Spell", 5, Items.TREE_TRAP_SPELL);

    private final Research research;

    Researches(String key, int cost, SlimefunItemStack item) {
        this.research = new Research(new NamespacedKey(SlimefunVoid.getInstance(), key.toLowerCase().replace(" ", "_")), ResearchIDHandler.nextID(), key, cost);
        Slimefun.registerResearch(research, item);
    }

    public Research getResearch() {
        return research;
    }
}
