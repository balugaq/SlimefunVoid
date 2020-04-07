package software.bigbade.slimefunvoid.items.wands;

import lombok.Getter;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import software.bigbade.slimefunvoid.api.Element;

import java.util.Objects;

public class WandItem extends SlimefunItem {
    @Getter
    private final int maxElements;
    @Getter
    private final int maxElement;

    public WandItem(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int maxElement, int maxElements) {
        super(category, item, recipeType, recipe);
        this.maxElement = maxElement;
        this.maxElements = maxElements;
    }

    public static void chargeItem(ItemStack wand, Element element) {
        ItemMeta meta = wand.getItemMeta();
        Objects.requireNonNull(meta);
        PersistentDataContainer data = meta.getPersistentDataContainer();
        if(data.has(element.getKey(), PersistentDataType.INTEGER)) {
            Integer amount = data.get(element.getKey(), PersistentDataType.INTEGER);
            Objects.requireNonNull(amount);
            data.set(element.getKey(), PersistentDataType.INTEGER, amount+10);
        }
    }
}
