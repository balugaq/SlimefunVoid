package software.bigbade.slimefunvoid.items.wands;

import me.mrCookieSlime.Slimefun.Objects.Category;
import org.bukkit.inventory.ItemStack;
import software.bigbade.slimefunvoid.api.VoidRecipes;
import software.bigbade.slimefunvoid.items.Items;

public class AdvancedWand extends WandItem {
    public AdvancedWand(Category category) {
        super(category, Items.ADVANCED_WAND, VoidRecipes.VOID_ALTAR, new ItemStack[] {}, 150, 500, .15);
    }
}
