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
    VOID_BAG("Void Bag", Integer.MAX_VALUE, Items.VOID_BAG),
    VOID_PORTAL("Void Portal", Integer.MAX_VALUE, Items.VOID_PORTAL),
    VOID_ALTAR("Void Altar", Integer.MAX_VALUE, Items.VOID_ALTAR),
    BASIC_WAND("Basic Wand", Integer.MAX_VALUE, Items.BASIC_WAND),
    ADVANCED_WAND("Advanced Wand", Integer.MAX_VALUE, Items.ADVANCED_WAND),
    FIREBALL("Fireball Spell", Integer.MAX_VALUE, Items.FIREBALL_SPELL);

    private Research research;

    Researches(String key, int cost, SlimefunItemStack item) {
        this.research = new Research(new NamespacedKey(SlimefunVoid.getInstance(), key.toLowerCase().replace(" ", "_")), ResearchIDHandler.nextID(), key, cost);
        Slimefun.registerResearch(research, item);
    }

    public Research getResearch() {
        return research;
    }
}
