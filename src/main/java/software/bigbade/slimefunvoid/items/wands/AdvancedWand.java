package software.bigbade.slimefunvoid.items.wands;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import org.bukkit.inventory.ItemStack;
import software.bigbade.slimefunvoid.api.research.VoidRecipes;
import software.bigbade.slimefunvoid.items.Items;

public class AdvancedWand extends WandItem {
    public AdvancedWand(ItemGroup category) {
        super(category, Items.ADVANCED_WAND, VoidRecipes.VOID_ALTAR, new ItemStack[]{}, 150, 500, .15);
    }
}
