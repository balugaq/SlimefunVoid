package software.bigbade.slimefunvoid.api;

import lombok.Getter;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import org.bukkit.inventory.ItemStack;
import software.bigbade.slimefunvoid.recipes.VoidAltarRecipe;

import java.util.HashMap;
import java.util.Map;

public class VoidRecipes {
    @Getter
    private static final Map<ItemStack, ItemStack[]> recipes = new HashMap<>();
    @Getter
    private static final Map<ItemStack, ItemStack> keys = new HashMap<>();

    //Private constructor to hide implicit public one
    private VoidRecipes() {}

    public static final RecipeType VOID_ALTAR = new VoidAltarRecipe((items, output) -> {
        recipes.put(output, items);
        keys.put(items[5], output);
    });
}
