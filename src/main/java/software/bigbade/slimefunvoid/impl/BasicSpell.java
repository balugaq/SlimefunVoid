package software.bigbade.slimefunvoid.impl;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.mrCookieSlime.Slimefun.Objects.Research;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
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
    private final SlimefunItemStack icon;

    private final long baseCooldown;

    @Override
    public boolean onCast(Player player, ItemStack wand) {
        //Overridden by subclass
        return false;
    }

    @Override
    public void onBackfire(Player player, ItemStack wand) {
        //Overridden by subclass
    }

    @Override
    public void onStop(Player player, ItemStack wand) {
        //Overridden by subclass
    }

    @Override
    public String getName() {
        Objects.requireNonNull(icon.getItemMeta());
        return icon.getItemMeta().getDisplayName();
    }

    public static float getMultiplier(ItemStack item, @NonNull WandItem wandItem, Elements element) {
        float amount = WandItem.getElementAmount(item, element);
        if(amount == 0)
            return 1;
        return 1 + amount / wandItem.getMaxElement();
    }

    public static float getMultipliedDamage(ItemStack item, float base, Elements element) {
        WandItem wandItem = WandItem.getWand(item);
        if(wandItem == null)
            return base;
        base *= wandItem.getModifier(element);
        base *= getMultiplier(item, wandItem, Elements.VOID);
        if (element == Elements.WATER || element == Elements.GRASS)
            base /= getMultiplier(item, wandItem, Elements.FIRE);
        else
            base *= getMultiplier(item, wandItem, Elements.FIRE);
        if (element != Elements.WATER)
            base /= getMultiplier(item, wandItem, Elements.WATER);
        if (element != Elements.WATER && element != Elements.GRASS) {
            base /= getMultiplier(item, wandItem, Elements.GRASS);
        }
        if (element != Elements.VOID)
            base *= getMultiplier(item, wandItem, Elements.LIGHT);
        base /= getMultiplier(item, wandItem, Elements.WIND);
        return base;
    }

    public static float getBackfireDamage(ItemStack item, float base, Elements element) {
        WandItem wandItem = WandItem.getWand(item);
        if(wandItem == null)
            return base;
        base /= wandItem.getModifier(element);
        base *= getMultiplier(item, wandItem, Elements.FIRE);
        if (element == Elements.GRASS) {
            base /= getMultiplier(item, wandItem, Elements.FIRE);
            base *= getMultiplier(item, wandItem, Elements.WATER);
        } else if (element == Elements.WATER) {
            base /= getMultiplier(item, wandItem, Elements.GRASS);
            base *= getMultiplier(item, wandItem, Elements.FIRE);
        } else if (element == Elements.FIRE) {
            base /= getMultiplier(item, wandItem, Elements.WATER);
            base *= getMultiplier(item, wandItem, Elements.GRASS);
        } else if(element == Elements.VOID) {
            base *= getMultiplier(item, wandItem, Elements.LIGHT);
        } else if(element == Elements.LIGHT) {
            base *= getMultiplier(item, wandItem, Elements.VOID);
        }
        return base;
    }

    public long getCooldown(ItemStack item) {
        long cooldown = baseCooldown;
        WandItem wandItem = WandItem.getWand(item);
        if(wandItem == null)
            return cooldown;
        cooldown *= getMultiplier(item, wandItem, Elements.LIGHT);
        cooldown /= getMultiplier(item, wandItem, Elements.ELECTRIC);
        cooldown /= getMultiplier(item, wandItem, Elements.WIND);
        return cooldown;
    }
}
