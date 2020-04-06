package software.bigbade.slimefunvoid.api;

import me.mrCookieSlime.Slimefun.Objects.Research;
import me.mrCookieSlime.Slimefun.api.Slimefun;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.NamespacedKey;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.items.Items;

public enum Researches {
    VOID_BENCH(new Research(new NamespacedKey(SlimefunVoid.getInstance(), "void_research_bench"), 600, "Void Research Bench", 30), Items.VOID_RESEARCH_BENCH),
    VOID_BAG(new Research(new NamespacedKey(SlimefunVoid.getInstance(), "void_bag"), 601, "Void Bag", Integer.MAX_VALUE), Items.VOID_BAG),
    VOID_PORTAL(new Research(new NamespacedKey(SlimefunVoid.getInstance(), "void_portal"), 602, "Void Portal", Integer.MAX_VALUE), Items.VOID_PORTAL),
    VOID_ALTAR(new Research(new NamespacedKey(SlimefunVoid.getInstance(), "void_altar"), 603, "Void Altar", Integer.MAX_VALUE), Items.VOID_ALTAR),
    BASIC_WAND(new Research(new NamespacedKey(SlimefunVoid.getInstance(), "basic_wand"), 604, "Basic Wand", Integer.MAX_VALUE), Items.BASIC_WAND);

    private Research research;

    Researches(Research research, SlimefunItemStack item) {
        this.research = research;
        Slimefun.registerResearch(research, item);
    }

    public Research getResearch() {
        return research;
    }
}
