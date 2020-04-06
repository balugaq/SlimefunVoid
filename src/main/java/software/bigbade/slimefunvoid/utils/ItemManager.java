package software.bigbade.slimefunvoid.utils;

import me.mrCookieSlime.Slimefun.Objects.Category;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.blocks.VoidPortal;
import software.bigbade.slimefunvoid.blocks.VoidResearchBench;
import software.bigbade.slimefunvoid.items.VoidBag;

public class ItemManager {
    private final Category category;

    public ItemManager(Category category) {
        this.category = category;
    }

    public void registerItems() {
        new VoidResearchBench(category).register(SlimefunVoid.getInstance());
        new VoidBag(category).register(SlimefunVoid.getInstance());
        new VoidPortal(category).register(SlimefunVoid.getInstance());
    }
}
