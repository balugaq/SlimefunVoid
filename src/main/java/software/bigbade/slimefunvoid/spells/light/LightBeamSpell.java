package software.bigbade.slimefunvoid.spells.light;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import software.bigbade.slimefunvoid.api.Elements;
import software.bigbade.slimefunvoid.api.research.Researches;
import software.bigbade.slimefunvoid.impl.BasicSpell;
import software.bigbade.slimefunvoid.items.Items;
import software.bigbade.slimefunvoid.utils.SelfCancelableTask;

import java.util.Collection;

public class LightBeamSpell extends BasicSpell {
    public LightBeamSpell() {
        super(Researches.LIGHT_BEAM_SPELL.getResearch(), Elements.LIGHT, Items.LIGHT_BEAM_SPELL, 5);
    }

    @Override
    public boolean onCast(Player player, ItemStack wand) {
        SelfCancelableTask task = new SelfCancelableTask();
        Location location = player.getEyeLocation();
        final int distance = (int) getMultipliedDamage(wand, 20, Elements.LIGHT);
        task.setRunnable(() -> {
            location.add(location.getDirection());
            player.getWorld().spawnParticle(Particle.FIREWORK, location, 1, 0, 0, 0, 0);
            Collection<Entity> nearby = player.getWorld().getNearbyEntities(location, .55, .55, .55);
            if (!nearby.isEmpty()) {
                Entity target = nearby.iterator().next();
                if (target instanceof LivingEntity && target != player) {
                    ((LivingEntity) target).damage(getMultipliedDamage(wand, 5, Elements.LIGHT), player);
                    target.setVelocity(target.getVelocity().add(location.getDirection().normalize().multiply(1.5)));
                    task.cancel();
                }
            }
            if (task.getLoops() == distance)
                task.cancel();
        });
        task.start(1L, 1L);
        return true;
    }

    @Override
    public void onBackfire(Player player, ItemStack wand) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, (int) getBackfireDamage(wand, 100, Elements.LIGHT), 0));
    }
}
