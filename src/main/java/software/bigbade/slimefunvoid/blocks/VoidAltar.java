package software.bigbade.slimefunvoid.blocks;

import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.inventory.ItemStack;
import software.bigbade.slimefunvoid.items.Items;

public class VoidAltar extends SlimefunItem {
    public VoidAltar(Category category) {
        super(category, Items.VOID_ALTAR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] { });
        SlimefunItem.registerBlockHandler(getID(), (p, b, tool, reason) -> {
            /*AncientAltarListener listener = SlimefunPlugin.getAncientAltarListener();
            Item stack = listener.findItem(b);

            if (stack != null) {
                stack.removeMetadata("item_placed", SlimefunPlugin.instance);
                b.getWorld().dropItem(b.getLocation(), listener.fixItemStack(stack.getItemStack(), stack.getCustomName()));
                stack.remove();
            }*/
            return true;
        });
    }
}
