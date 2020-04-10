package software.bigbade.slimefunvoid.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.mrCookieSlime.Slimefun.Objects.Research;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import software.bigbade.slimefunvoid.api.Elements;
import software.bigbade.slimefunvoid.api.WandSpell;
import software.bigbade.slimefunvoid.items.wands.WandItem;

import java.util.Objects;

@RequiredArgsConstructor
public class BasicSpell implements WandSpell {
    @Getter
    private final Research research;
    @Getter
    private final Elements element;
    @Getter
    private final ItemStack icon;

    private final long baseCooldown;

    @Override
    public void onCast(Player player, ItemStack wand) {
        //Overridden by subclass
    }

    @Override
    public void onBackfire(Player player, ItemStack wand) {
        //Overridden by subclass
    }

    public static float getMultiplier(ItemStack item, Elements element) {
        float amount = WandItem.getElementAmount(item, element);
        if(amount == 0)
            return 1;
        WandItem wand = WandItem.getWand(item);
        if (wand == null)
            return 1;
        return 1 + amount / wand.getMaxElement();
    }

    public static float getMultipliedDamage(ItemStack item, float base, Elements element) {
        base *= getMultiplier(item, Elements.VOID);
        if (element == Elements.WATER || element == Elements.GRASS)
            base /= getMultiplier(item, Elements.FIRE);
        else
            base *= getMultiplier(item, Elements.FIRE);
        if (element != Elements.WATER)
            base /= getMultiplier(item, Elements.WATER);
        if (element != Elements.WATER && element != Elements.GRASS) {
            base /= getMultiplier(item, Elements.GRASS);
        }
        if (element != Elements.VOID)
            base *= getMultiplier(item, Elements.LIGHT);
        base /= getMultiplier(item, Elements.WIND);
        return base;
    }

    public static float getBackfireDamage(ItemStack item, float base, Elements element) {
        base *= getMultiplier(item, Elements.FIRE);
        if (element == Elements.GRASS) {
            base /= getMultiplier(item, Elements.FIRE);
            base *= getMultiplier(item, Elements.WATER);
        } else if (element == Elements.WATER) {
            base /= getMultiplier(item, Elements.GRASS);
            base *= getMultiplier(item, Elements.FIRE);
        } else if (element == Elements.FIRE) {
            base /= getMultiplier(item, Elements.WATER);
            base *= getMultiplier(item, Elements.GRASS);
        } else if(element == Elements.VOID) {
            base *= getMultiplier(item, Elements.LIGHT);
        } else if(element == Elements.LIGHT) {
            base *= getMultiplier(item, Elements.VOID);
        }
        return base;
    }

    @Override
    public String getName() {
        return Objects.requireNonNull(icon.getItemMeta()).getDisplayName();
    }

    public long getCooldown(ItemStack item) {
        long cooldown = baseCooldown;
        cooldown *= getMultiplier(item, Elements.LIGHT);
        cooldown /= getMultiplier(item, Elements.ELECTRIC);
        cooldown /= getMultiplier(item, Elements.WIND);
        return cooldown;
    }
}
