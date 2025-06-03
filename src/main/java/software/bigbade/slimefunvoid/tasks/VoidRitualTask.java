package software.bigbade.slimefunvoid.tasks;

import io.github.thebusybiscuit.slimefun4.utils.compatibility.VersionedParticle;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.blocks.VoidAltar;
import software.bigbade.slimefunvoid.blocks.VoidPortal;

import java.util.Objects;

@RequiredArgsConstructor
public class VoidRitualTask implements Runnable {
    private final VoidPortal.VoidPortalData data;
    private final ItemStack output;

    private int stage = 0;

    @Override
    public void run() {
        Location portal = data.getPortal();
        Vector portalVec = portal.toVector();
        correctPosition(portalVec);
        Objects.requireNonNull(portal.getWorld());
        if (stage < 8) {
            Location pedestal = data.getAltars().get(stage);
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
            while (i < 20) {
                portal.getWorld().spawnParticle(VersionedParticle.WITCH, current.getX(), current.getY(), current.getZ(), 1, 0, 0, 0, 0);
                current.add(movement);
                i++;
            }
            stage += 1;
            Bukkit.getScheduler().runTaskLater(SlimefunVoid.getInstance(), this, 10L);
            return;
        } else if (stage < 16) {
            Location pedestal = data.getAltars().get((stage - 4) % 8);
            Item item = VoidAltar.getItem(pedestal.getBlock());
            if (item == null) {
                stage += 1;
                Bukkit.getScheduler().runTaskLater(SlimefunVoid.getInstance(), this, 15L);
                return;
            }
            Objects.requireNonNull(pedestal.getWorld());
            pedestal.getWorld().spawnParticle(VersionedParticle.WITCH, item.getLocation(), 50, 0, 0, 0, 1);
            pedestal.getWorld().playSound(item.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1L, 1L);
            item.remove();
            stage += 1;
            Bukkit.getScheduler().runTaskLater(SlimefunVoid.getInstance(), this, 15L);
            return;
        }
        Location drop = portal.clone();
        drop.setX(drop.getX() + .5);
        drop.setY(drop.getY() + 1.2);
        drop.setZ(drop.getZ() + .5);
        Block portalBlock = portal.getBlock();
        if (VoidAltar.getItem(portalBlock) != null) {
            VoidAltar.storeItem(output, portalBlock);
        } else {
            portal.getWorld().dropItem(drop, output).setVelocity(new Vector(0, .1, 0));
        }
        portal.getWorld().spawnParticle(Particle.WITCH, drop, 200, 0, 0, 0, 1);
        data.setInUse(false);
    }

    private void correctPosition(Vector vector) {
        vector.setX(vector.getX() + .5);
        vector.setY(vector.getY() + 1);
        vector.setZ(vector.getZ() + .5);
    }
}
