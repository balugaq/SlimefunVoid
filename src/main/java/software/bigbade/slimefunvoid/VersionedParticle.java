package software.bigbade.slimefunvoid;

import org.bukkit.Particle;

public class VersionedParticle {
    public static final Particle CRIT = getParticle("CRIT", "CRIT_MAGIC");

    public static Particle getParticle(String m1, String m2) {
        try {
            return Particle.valueOf(m1);
        } catch (IllegalArgumentException e) {
            return Particle.valueOf(m2);
        }
    }
}
