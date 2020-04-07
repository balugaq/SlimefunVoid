package software.bigbade.slimefunvoid.utils;

import me.mrCookieSlime.Slimefun.Objects.Category;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.blocks.VoidAltar;
import software.bigbade.slimefunvoid.blocks.VoidPortal;
import software.bigbade.slimefunvoid.blocks.VoidResearchBench;
import software.bigbade.slimefunvoid.items.VoidBag;
import software.bigbade.slimefunvoid.items.wands.BasicWand;

public class ItemManager {
    private final Category category;

    public ItemManager(Category category) {
        this.category = category;
    }

    public void registerItems() {
        new VoidResearchBench(category).register(SlimefunVoid.getInstance());
        new VoidBag(category).register(SlimefunVoid.getInstance());
        VoidPortal portal = new VoidPortal(category);
        portal.register(SlimefunVoid.getInstance());
        new VoidAltar(category, portal).register(SlimefunVoid.getInstance());
        new BasicWand(category).register(SlimefunVoid.getInstance());
    }
}
