package software.bigbade.slimefunvoid.spells.water;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import software.bigbade.slimefunvoid.api.Elements;
import software.bigbade.slimefunvoid.api.research.Researches;
import software.bigbade.slimefunvoid.impl.BasicSpell;
import software.bigbade.slimefunvoid.items.Items;

public class FastSwimmingSpell extends BasicSpell {
    public FastSwimmingSpell() {
        super(Researches.FAST_SWIMMING_SPELL.getResearch(), Elements.WATER, Items.FAST_SWIMMING_SPELL, 5);
    }

    @Override
    public boolean onCast(Player player, ItemStack wand) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, (int) getMultipliedDamage(wand, 10, Elements.WATER) * 20, (int) getMultipliedDamage(wand, 1, Elements.WATER)));
        return true;
    }

    @Override
    public void onBackfire(Player player, ItemStack wand) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int) getBackfireDamage(wand, 100, Elements.WATER), 0));
    }
}
