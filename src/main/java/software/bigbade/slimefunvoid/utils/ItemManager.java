package software.bigbade.slimefunvoid.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.mrCookieSlime.Slimefun.Objects.Category;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.blocks.VoidAltar;
import software.bigbade.slimefunvoid.blocks.VoidPortal;
import software.bigbade.slimefunvoid.blocks.VoidQuarry;
import software.bigbade.slimefunvoid.blocks.VoidResearchBench;
import software.bigbade.slimefunvoid.items.VoidBag;
import software.bigbade.slimefunvoid.items.wands.BasicWand;

@RequiredArgsConstructor
public class ItemManager {
    private final Category category;

    @Getter
    private VoidQuarry quarry;

    public void registerItems() {
        new VoidResearchBench(category).register(SlimefunVoid.getInstance());
        new VoidBag(category).register(SlimefunVoid.getInstance());
        VoidPortal portal = new VoidPortal(category);
        portal.register(SlimefunVoid.getInstance());
        new VoidAltar(category, portal).register(SlimefunVoid.getInstance());
        new BasicWand(category).register(SlimefunVoid.getInstance());
        quarry = new VoidQuarry(category);
        quarry.register(SlimefunVoid.getInstance());
    }
}
