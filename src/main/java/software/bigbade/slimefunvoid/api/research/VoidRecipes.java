package software.bigbade.slimefunvoid.api.research;

import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import software.bigbade.slimefunvoid.recipes.VoidAltarRecipe;

import java.util.HashMap;
import java.util.Map;

public class VoidRecipes {
    @Getter
    private static final Map<ItemStack, ItemStack[]> recipes = new HashMap<>();
    @Getter
    private static final Map<ItemStack, ItemStack> keys = new HashMap<>();

    private static final int[] ALTAR_ORDER = new int[]{0, 3, 6, 7, 4, 8, 5, 2, 1};
    /**
     * Void Altar recipe. Keep in mind the order goes 0, 1, 2, 3, 5, 6, 7, 8.
     * Fourth item is a wand if it is required, if not null.
     */
    public static final RecipeType VOID_ALTAR = new VoidAltarRecipe((items, output) -> {
        ItemStack[] fixedItems = fixOutput(items);
        recipes.put(output, fixedItems);
        keys.put(items[5], output);
    });

    //Private constructor to hide implicit public one
    private VoidRecipes() {
    }

    /**
     * Fixes ItemStack array (0, 3, 6, 7, 4, 8, 5, 2, 1) to the order the altar takes (0, 1, 2, 3, 4, 5, 6, 7, 8)
     *
     * @param old Old ItemStack array
     * @return fixed ItemStack array
     */
    private static ItemStack[] fixOutput(ItemStack[] old) {
        ItemStack[] fixed = new ItemStack[9];
        for (int i = 0; i < 9; i++) {
            fixed[ALTAR_ORDER[i]] = old[i];
        }
        return fixed;
    }
}
