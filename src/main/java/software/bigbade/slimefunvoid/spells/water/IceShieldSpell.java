package software.bigbade.slimefunvoid.spells.water;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import software.bigbade.slimefunvoid.api.Elements;
import software.bigbade.slimefunvoid.api.research.Researches;
import software.bigbade.slimefunvoid.impl.BasicSpell;
import software.bigbade.slimefunvoid.items.Items;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class IceShieldSpell extends BasicSpell {
    private final Map<UUID, Location> toRemove = new HashMap<>();

    public IceShieldSpell() {
        super(Researches.ICE_SHIELD_SPELL.getResearch(), Elements.WATER, Items.ICE_SHIELD_SPELL, 15);
    }

    @Override
    public boolean onCast(Player player, ItemStack wand) {
        makeSphere(player.getLocation(), Material.ICE, Material.AIR, getMultipliedDamage(wand, 3, Elements.WATER), false);
        toRemove.put(player.getUniqueId(), player.getLocation());
        return true;
    }

    @Override
    public void onBackfire(Player player, ItemStack wand) {
        makeSphere(player.getLocation(), Material.WATER, Material.AIR, getMultipliedDamage(wand, 3, Elements.WATER), false);
        toRemove.put(player.getUniqueId(), player.getLocation());
    }

    @Override
    public void onStop(Player player, ItemStack wand) {
        makeSphere(toRemove.get(player.getUniqueId()), Material.AIR, Material.ICE, getMultipliedDamage(wand, 3, Elements.WATER), false);
        toRemove.remove(player.getUniqueId());
    }

    /**
     * Makes a sphere of a given material replacing other material with given radius
     * From https://github.com/IntellectualSites/FastAsyncWorldEdit/blob/1.15/worldedit-core/src/main/java/com/sk89q/worldedit/EditSession.java
     * I know this method is a mess, I have <24 hours and don't wanna do math.
     * @param pos Center of the sphere
     * @param block Material the sphere is made of
     * @param replacing Material to replace with block
     * @param radius Radius of the sphere
     */
    public static void makeSphere(Location pos, Material block, Material replacing, double radius, boolean filled) {
        radius += 0.5;

        final double invRadius = 1 / radius;

        int px = pos.getBlockX();
        int py = pos.getBlockY();
        int pz = pos.getBlockZ();

        final int ceilRadius = (int) Math.ceil(radius);

        int yy;

        double nextXn = invRadius;
        forX:
        for (int x = 0; x <= ceilRadius; ++x) {
            final double xn = nextXn;
            double dx = xn * xn;
            nextXn = (x + 1) * invRadius;
            double nextZn = invRadius;
            forZ:
            for (int z = 0; z <= ceilRadius; ++z) {
                final double zn = nextZn;
                double dz = zn * zn;
                double dxz = dx + dz;
                nextZn = (z + 1) * invRadius;
                double nextYn = invRadius;

                for (int y = 0; y <= ceilRadius; ++y) {
                    final double yn = nextYn;
                    double dy = yn * yn;
                    double dxyz = dxz + dy;
                    nextYn = (y + 1) * invRadius;

                    if (dxyz > 1) {
                        if (y == 0) {
                            if (z == 0) {
                                break forX;
                            }
                            break forZ;
                        }
                        break;
                    }

                    if (!filled && nextXn * nextXn + dy + dz <= 1 && nextYn * nextYn + dx + dz <= 1 && nextZn * nextZn + dx + dy <= 1) {
                        continue;
                    }
                    yy = py + y;
                    if (yy <= 256) {
                        setBlock(new Location(pos.getWorld(), px + x, py + y, pz + z), replacing, block);
                        if (x != 0) setBlock(new Location(pos.getWorld(), px - x, py + y, pz + z), replacing, block);
                        if (z != 0) {
                            setBlock(new Location(pos.getWorld(), px + x, py + y, pz - z), replacing, block);
                            if (x != 0) setBlock(new Location(pos.getWorld(), px - x, py + y, pz - z), replacing, block);
                        }
                    }
                    if (y != 0 && (yy = py - y) >= 0) {
                        setBlock(new Location(pos.getWorld(), px + x, yy, pz + z), replacing, block);
                        if (x != 0) setBlock(new Location(pos.getWorld(), px - x, yy, pz + z), replacing, block);
                        if (z != 0) {
                            setBlock(new Location(pos.getWorld(), px + x, yy, pz - z), replacing, block);
                            if (x != 0) setBlock(new Location(pos.getWorld(), px - x, yy, pz - z), replacing, block);
                        }
                    }
                }
            }
        }
    }

    private static void setBlock(Location location, Material replacing, Material block) {
        Block target = location.getBlock();
        if(target.getType() == replacing)
            target.setType(block);
    }
}
