package software.bigbade.slimefunvoid.spells.electricity;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import software.bigbade.slimefunvoid.api.Elements;
import software.bigbade.slimefunvoid.api.research.Researches;
import software.bigbade.slimefunvoid.impl.BasicSpell;
import software.bigbade.slimefunvoid.items.Items;

public class WaterShockSpell extends BasicSpell {
    public WaterShockSpell() {
        super(Researches.WATER_SHOCK_SPELL.getResearch(), Elements.ELECTRIC, Items.WATER_SHOCK_SPELL, 10);
    }

    @Override
    public boolean onCast(Player player, ItemStack wand) {
        if(player.getLocation().getBlock().getType() != Material.WATER) {
            player.sendMessage(ChatColor.RED + "You must stand in water to cast this spell!");
            return false;
        }
        float range = getMultipliedDamage(wand, 20, Elements.ELECTRIC);
        float damage = getMultipliedDamage(wand, 2, Elements.ELECTRIC);
        for(Entity entity : player.getNearbyEntities(range, range, range)) {
            if(entity instanceof LivingEntity && entity.getLocation().getBlock().getType() == Material.WATER) {
                if(entity instanceof Player) {
                    entity.sendMessage(ChatColor.YELLOW + "You were shocked by " + player.getName());
                }
                player.getWorld().spawnParticle(Particle.BUBBLE_COLUMN_UP, entity.getLocation(), 80);
                ((LivingEntity) entity).damage(damage, player);
            }
        }
        return true;
    }

    @Override
    public void onBackfire(Player player, ItemStack wand) {
        if(player.getLocation().getBlock().getType() != Material.WATER)
            return;
        player.damage(getMultipliedDamage(wand, 2, Elements.ELECTRIC), player);
    }
}
