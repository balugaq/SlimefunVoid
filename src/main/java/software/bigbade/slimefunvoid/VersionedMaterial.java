package software.bigbade.slimefunvoid;

import org.bukkit.Material;

public class VersionedMaterial {
    public static final Material GRASS = getMaterial("GRASS", "SHORT_GRASS");

    public static Material getMaterial(String m1, String m2) {
        try {
            return Material.valueOf(m1);
        } catch (IllegalArgumentException e) {
            return Material.valueOf(m2);
        }
    }
}
