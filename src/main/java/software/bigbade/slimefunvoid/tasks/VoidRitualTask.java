package software.bigbade.slimefunvoid.tasks;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.blocks.VoidAltar;
import software.bigbade.slimefunvoid.blocks.VoidPortal;

import java.util.Objects;

@RequiredArgsConstructor
public class VoidRitualTask implements Runnable {
    private final VoidPortal.VoidPortalData location;
    private final ItemStack output;

    private int stage = 0;

    @Override
    public void run() {
        Location portal = location.getPortal();
        Vector portalVec = portal.toVector();
        correctPosition(portalVec);
        Objects.requireNonNull(portal.getWorld());
        if (stage < 8) {
            Location pedestal = location.getAltars().get(stage);
            Item item = VoidAltar.getItem(pedestal.getBlock());
            if (item == null) {
                stage += 1;
                Bukkit.getScheduler().runTaskLater(SlimefunVoid.getInstance(), this, 1L);
                return;
            }
            Vector pedestalVec = item.getLocation().toVector();
            Vector movement = portalVec.clone().subtract(pedestalVec);
            movement.multiply(.05);
            Vector current = portalVec.clone();
            int i = 0;
            while(i < 20) {
                portal.getWorld().spawnParticle(Particle.SPELL_WITCH, current.getX(), current.getY(), current.getZ(), 1, 0, 0, 0, 0);
                current.add(movement);
                i++;
            }
            stage += 1;
            Bukkit.getScheduler().runTaskLater(SlimefunVoid.getInstance(), this, 5L);
            return;
        } else if(stage < 16) {
            Location pedestal = location.getAltars().get(stage-8);
            Item item = VoidAltar.getItem(pedestal.getBlock());
            if (item == null) {
                stage += 1;
                Bukkit.getScheduler().runTaskLater(SlimefunVoid.getInstance(), this, 1L);
                return;
            }
            Objects.requireNonNull(pedestal.getWorld());
            pedestal.getWorld().spawnParticle(Particle.SPELL_WITCH, item.getLocation(), 500, 0, 0, 0, .1);
            pedestal.getWorld().playSound(item.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1L, 1L);
            item.remove();
            stage += 1;
            Bukkit.getScheduler().runTaskLater(SlimefunVoid.getInstance(), this, 1L);
            return;
        }
        portal.getWorld().dropItemNaturally(portal, output);
        location.setInUse(false);
    }

    private void correctPosition(Vector vector) {
        vector.setX(vector.getX()+.5);
        vector.setY(vector.getY()+1);
        vector.setZ(vector.getZ()+.5);
    }
}
