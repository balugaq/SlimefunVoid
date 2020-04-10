package software.bigbade.slimefunvoid.blocks;

import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SimpleSlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockPlaceHandler;
import org.bukkit.inventory.ItemStack;
import software.bigbade.slimefunvoid.api.VoidRecipes;
import software.bigbade.slimefunvoid.items.Items;
import software.bigbade.slimefunvoid.utils.RecipeItems;

public class VoidQuarry extends SimpleSlimefunItem<BlockPlaceHandler> {
    public VoidQuarry(Category category) {
        super(category, Items.VOID_QUARRY, VoidRecipes.VOID_ALTAR, new ItemStack[] {RecipeItems.OBSIDIAN, RecipeItems.ENDER_CHEST, RecipeItems.OBSIDIAN,
                RecipeItems.ENDER_EYE, null, RecipeItems.ENDER_EYE,
                RecipeItems.OBSIDIAN, RecipeItems.ENDER_EYE, RecipeItems.OBSIDIAN
        });
    }

    @Override
    public BlockPlaceHandler getItemHandler() {
        return (event, item) -> {
            return true;
        };
    }
}
