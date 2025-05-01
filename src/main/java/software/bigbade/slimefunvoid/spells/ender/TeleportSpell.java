package software.bigbade.slimefunvoid.spells.ender;

import org.bukkit.ChatColor;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import software.bigbade.slimefunvoid.api.Elements;
import software.bigbade.slimefunvoid.api.research.Researches;
import software.bigbade.slimefunvoid.impl.BasicSpell;
import software.bigbade.slimefunvoid.items.Items;

import java.util.concurrent.ThreadLocalRandom;

public class TeleportSpell extends BasicSpell {
    public TeleportSpell() {
        super(Researches.TELEPORT_SPELL.getResearch(), Elements.VOID, Items.TELEPORT_SPELL, 5);
    }

    public static void randomTeleport(Player player, ItemStack wand) {
        Location location = player.getLocation().clone();
        double distance = getBackfireDamage(wand, 5, Elements.VOID);
        double half = distance / 2;
        location.add(ThreadLocalRandom.current().nextDouble(distance) - (half), 0, ThreadLocalRandom.current().nextDouble(distance) - (half));
        location.setY(player.getWorld().getHighestBlockAt(location).getLocation().getY() + 1);
        player.teleport(location);
        player.getWorld().spawnParticle(Particle.CRIT_MAGIC, location, 200);
    }

    @Override
    public boolean onCast(Player player, ItemStack wand) {
        Block target = player.getTargetBlockExact((int) getMultipliedDamage(wand, 5, Elements.VOID), FluidCollisionMode.NEVER);
        if (target != null) {
            player.getWorld().spawnParticle(Particle.DRAGON_BREATH, player.getLocation(), 30);
            Location output = target.getLocation().add(.5, 1, .5);
            output.setYaw(player.getLocation().getYaw());
            output.setPitch(player.getLocation().getPitch());
            player.teleport(output, PlayerTeleportEvent.TeleportCause.ENDER_PEARL);
            player.getWorld().spawnParticle(Particle.DRAGON_BREATH, output, 30);
            return true;
        }
        player.sendMessage(ChatColor.RED + "目标超出范围");
        return false;
    }

    @Override
    public void onBackfire(Player player, ItemStack wand) {
        randomTeleport(player, wand);
    }
}
