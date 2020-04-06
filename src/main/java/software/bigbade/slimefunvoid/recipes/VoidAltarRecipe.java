package software.bigbade.slimefunvoid.recipes;

import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.items.Items;

import java.util.function.BiConsumer;

public class VoidAltarRecipe extends RecipeType {
    public VoidAltarRecipe(BiConsumer<ItemStack[], ItemStack> callback) {
        super(new NamespacedKey(SlimefunVoid.getInstance(), "void_altar"), Items.VOID_ALTAR, callback, ChatColor.DARK_PURPLE + "Crafted with the Void Alter");
    }
}
