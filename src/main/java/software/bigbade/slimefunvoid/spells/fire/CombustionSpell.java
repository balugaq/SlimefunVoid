package software.bigbade.slimefunvoid.spells.fire;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import software.bigbade.slimefunvoid.api.Elements;
import software.bigbade.slimefunvoid.api.research.Researches;
import software.bigbade.slimefunvoid.impl.BasicSpell;
import software.bigbade.slimefunvoid.items.Items;
import software.bigbade.slimefunvoid.spells.ender.SwapSpell;

public class CombustionSpell extends BasicSpell {
    public CombustionSpell() {
        super(Researches.COMBUSTION_SPELL.getResearch(), Elements.FIRE, Items.COMBUSTION_SPELL, 3);
    }

    @Override
    public boolean onCast(Player player, ItemStack wand) {
        float distance = getMultipliedDamage(wand, 20, Elements.FIRE);
        for (Entity entity : player.getNearbyEntities(distance, 5, distance)) {
            if (entity instanceof LivingEntity && SwapSpell.getLookingAt(player, entity)) {
                igniteEntity((LivingEntity) entity, wand);
                return true;
            }
        }
        player.sendMessage(ChatColor.RED + "You have to look at a target in range!");
        return false;
    }

    @Override
    public void onBackfire(Player player, ItemStack wand) {
        igniteEntity(player, wand);
    }

    private void igniteEntity(LivingEntity entity, ItemStack wand) {
        entity.setFireTicks(((int) getBackfireDamage(wand, 4, Elements.FIRE)) * 20);
    }
}
