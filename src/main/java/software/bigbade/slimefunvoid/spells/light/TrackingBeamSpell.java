package software.bigbade.slimefunvoid.spells.light;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import software.bigbade.slimefunvoid.api.Elements;
import software.bigbade.slimefunvoid.api.research.Researches;
import software.bigbade.slimefunvoid.impl.BasicSpell;
import software.bigbade.slimefunvoid.items.Items;
import software.bigbade.slimefunvoid.utils.SelfCancelableTask;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;

public class TrackingBeamSpell extends BasicSpell {
    public TrackingBeamSpell() {
        super(Researches.TRACKING_BEAM_SPELL.getResearch(), Elements.LIGHT, Items.TRACKING_BEAM_SPELL, 8);
    }

    /**
     * Casts a tracking beam. Tracking code from https://www.spigotmc.org/threads/1-15-x-particle-beams-vectors-and-locations-oh-my.418399/
     *
     * @param player Player casting the spell
     * @param wand   Item wand
     * @return Whether the spell casts or not
     */
    @Override
    public boolean onCast(Player player, ItemStack wand) {
        SelfCancelableTask task = new SelfCancelableTask();
        Location location = player.getEyeLocation().clone();
        final int distance = (int) getMultipliedDamage(wand, 20, Elements.LIGHT);
        task.setRunnable(() -> {
            location.add(location.getDirection());
            player.getWorld().spawnParticle(Particle.FIREWORK, location, 1, 0, 0, 0, 0);
            Entity target = getTarget(location);
            if (target instanceof LivingEntity && target != player) {
                if (damagesTarget(target, player, location, wand)) {
                    task.cancel();
                    return;
                }
                track(target, location);
            }
            if (task.getLoops() == distance)
                task.cancel();
        });
        task.start(1L, 1L);
        return true;
    }

    @Nullable
    private Entity getTarget(Location location) {
        Objects.requireNonNull(location.getWorld());
        Collection<Entity> nearby = location.getWorld().getNearbyEntities(location, 5, 5, 5);
        Entity target = null;
        for (Entity found : nearby) {
            if (target == null) {
                target = found;
            } else {
                if (found.getLocation().distanceSquared(location) < target.getLocation().distanceSquared(location))
                    target = found;
            }
        }
        return target;
    }

    private boolean damagesTarget(Entity target, Player caster, Location location, ItemStack wand) {
        if (target.getLocation().add(0, target.getHeight() / 2, 0).distanceSquared(location) <= .55) {
            ((LivingEntity) target).damage(getMultipliedDamage(wand, 5, Elements.LIGHT), caster);
            target.setVelocity(target.getVelocity().add(location.getDirection().normalize().multiply(1.5)));
            return true;
        }
        return false;
    }

    private void track(Entity target, Location location) {
        Location targetLoc = target.getLocation().clone().add(0, target.getHeight() / 2, 0);
        Vector particleDirection = location.getDirection();
        Vector inBetween = targetLoc.clone().subtract(location).toVector().normalize();

        inBetween.multiply(0.5);
        particleDirection.add(inBetween).normalize();
        location.setDirection(particleDirection);
    }

    @Override
    public void onBackfire(Player player, ItemStack wand) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, (int) getBackfireDamage(wand, 100, Elements.LIGHT), 0));
    }
}
