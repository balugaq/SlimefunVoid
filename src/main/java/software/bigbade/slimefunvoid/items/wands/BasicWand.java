package software.bigbade.slimefunvoid.items.wands;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import org.bukkit.inventory.ItemStack;
import software.bigbade.slimefunvoid.api.research.VoidRecipes;
import software.bigbade.slimefunvoid.items.Items;
import software.bigbade.slimefunvoid.utils.RecipeItems;

public class BasicWand extends WandItem {
    public BasicWand(Category category) {
        super(category, Items.BASIC_WAND, VoidRecipes.VOID_ALTAR, new ItemStack[]{
                RecipeItems.OBSIDIAN, RecipeItems.ENDER_EYE, RecipeItems.OBSIDIAN,
                RecipeItems.ENDER_EYE, null, RecipeItems.ENDER_EYE,
                RecipeItems.OBSIDIAN, RecipeItems.ENDER_EYE, RecipeItems.OBSIDIAN}, 100, 300, .3D);
    }
}
