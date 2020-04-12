package software.bigbade.slimefunvoid.spells.ender;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import software.bigbade.slimefunvoid.api.Elements;
import software.bigbade.slimefunvoid.api.research.Researches;
import software.bigbade.slimefunvoid.impl.BasicSpell;
import software.bigbade.slimefunvoid.items.Items;

public class SwapSpell extends BasicSpell {
    public SwapSpell() {
        super(Researches.SWAP_SPELL.getResearch(), Elements.VOID, Items.SWAP_SPELL, 15);
    }

    @Override
    public boolean onCast(Player player, ItemStack wand) {
        float distance = getMultipliedDamage(wand, 20, Elements.VOID);
        for(Entity entity : player.getNearbyEntities(distance, 5, distance)) {
            if(getLookingAt(player, entity)) {
                Location playerLoc = player.getLocation();
                player.teleport(entity.getLocation());
                player.getWorld().spawnParticle(Particle.DRAGON_BREATH, entity.getLocation(), 50);
                player.getWorld().spawnParticle(Particle.DRAGON_BREATH, playerLoc, 50);
                entity.teleport(playerLoc);
                return true;
            }
        }
        player.sendMessage(ChatColor.RED + "You have to look at a target in range!");
        return false;
    }

    @Override
    public void onBackfire(Player player, ItemStack wand) {
        TeleportSpell.randomTeleport(player, wand);
    }

    public static boolean getLookingAt(Player player, Entity target) {
        Location eye = player.getEyeLocation();
        Vector toEntity = target.getLocation().subtract(eye).toVector();
        double dot = toEntity.normalize().dot(eye.getDirection());

        return dot > 0.99D;
    }
}
