package software.bigbade.slimefunvoid.utils;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.blocks.VoidAltar;
import software.bigbade.slimefunvoid.blocks.VoidAttractor;
import software.bigbade.slimefunvoid.blocks.VoidPortal;
import software.bigbade.slimefunvoid.blocks.VoidResearchBench;
import software.bigbade.slimefunvoid.items.VoidBag;
import software.bigbade.slimefunvoid.items.wands.AdvancedWand;
import software.bigbade.slimefunvoid.items.wands.BasicWand;

@RequiredArgsConstructor
public class ItemManager {
    private final ItemGroup category;

    //@Getter
    //private VoidQuarry quarry;

    @Getter
    private VoidBag voidBag;

    public void registerItems() {
        new VoidResearchBench(category).register(SlimefunVoid.getInstance());
        voidBag = new VoidBag(category);
        voidBag.register(SlimefunVoid.getInstance());
        new VoidAttractor(category).register(SlimefunVoid.getInstance());
        VoidPortal portal = new VoidPortal(category);
        portal.register(SlimefunVoid.getInstance());
        new VoidAltar(category, portal).register(SlimefunVoid.getInstance());
        new BasicWand(category).register(SlimefunVoid.getInstance());
        new AdvancedWand(category).register(SlimefunVoid.getInstance());
        //quarry = new VoidQuarry(category);
        //quarry.register(SlimefunVoid.getInstance());
    }
}
