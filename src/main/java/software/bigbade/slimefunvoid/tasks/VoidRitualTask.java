package software.bigbade.slimefunvoid.tasks;

import lombok.RequiredArgsConstructor;
import me.mrCookieSlime.Slimefun.api.Slimefun;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Item;
import org.bukkit.util.Vector;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.blocks.VoidAltar;
import software.bigbade.slimefunvoid.blocks.VoidPortal;

import java.util.Objects;

@RequiredArgsConstructor
public class VoidRitualTask implements Runnable {
    private final VoidPortal.VoidAltarLocation location;

    private int stage = 0;

    @Override
    public void run() {
        System.out.println("Testing");
        Location portal = location.getPortal();
        Objects.requireNonNull(portal.getWorld());
        System.out.println("Stage " + stage);
        if (stage <= 8) {
            Location pedestal = location.getAltars().get(stage);
            Item item = VoidAltar.getItem(pedestal.getBlock());
            if (item == null)
                return;
            Vector portalVec = pedestal.toVector();
            Vector vector = portalVec.subtract(portal.toVector());
            vector.multiply(.05);
            Vector current = portalVec.clone();
            System.out.println("Trying " + current + " to " + pedestal.toVector());
            while(!current.equals(pedestal.toVector())) {
                System.out.println("Spawning particle at " + current);
                portal.getWorld().spawnParticle(Particle.DRAGON_BREATH, current.getX(), current.getY(), current.getZ(), 1);
                current.add(vector);
            }
            stage += 1;
            Bukkit.getScheduler().runTaskLater(SlimefunVoid.getInstance(), this, 1L);
        }
        location.setInUse(false);
    }
}
