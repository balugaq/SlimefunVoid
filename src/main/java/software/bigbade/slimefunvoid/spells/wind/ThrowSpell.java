package software.bigbade.slimefunvoid.spells.wind;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.api.Elements;
import software.bigbade.slimefunvoid.api.research.Researches;
import software.bigbade.slimefunvoid.impl.BasicSpell;
import software.bigbade.slimefunvoid.items.Items;
import software.bigbade.slimefunvoid.spells.ender.SwapSpell;

public class ThrowSpell extends BasicSpell {
    public ThrowSpell() {
        super(Researches.THROW_SPELL.getResearch(), Elements.WIND, Items.THROW_SPELL, 8);
    }

    @Override
    public boolean onCast(Player player, ItemStack wand) {
        float distance = getMultipliedDamage(wand, 20, Elements.WIND);
        for (Entity entity : player.getNearbyEntities(distance, 5, distance)) {
            if (entity instanceof LivingEntity && SwapSpell.getLookingAt(player, entity)) {
                final float yaw = player.getEyeLocation().getYaw();
                final float pitch = player.getEyeLocation().getPitch();
                final Vector change = player.getLocation().toVector().subtract(entity.getLocation().toVector()).normalize().multiply(getMultipliedDamage(wand, 1.5f, Elements.WIND));
                Bukkit.getScheduler().scheduleSyncDelayedTask(SlimefunVoid.getInstance(), () -> {
                    float deltaYaw = (yaw - player.getEyeLocation().getYaw()) * .05f;
                    float deltaPitch = (pitch - player.getEyeLocation().getPitch()) * .05f;
                    Vector adding = new Vector(0, 0, 0);
                    adding.add(rotate(change, new Vector(1, 0, 0)).multiply(-deltaYaw));
                    if (deltaPitch < 0)
                        adding.add(change.multiply(-deltaPitch));
                    else
                        adding.add(rotate(change, new Vector(0, 1, 0)).multiply(deltaPitch));
                    entity.setVelocity(entity.getVelocity().add(adding));
                }, 30L);
                return true;
            }
        }
        player.sendMessage(ChatColor.RED + "You have to look at a target in range!");
        return false;
    }

    private Vector rotate(Vector vector, Vector axis) {
        return vector.clone().rotateAroundAxis(axis, 90);
    }

    @Override
    public void onBackfire(Player player, ItemStack wand) {
        player.setVelocity(player.getVelocity().add(LaunchSpell.getVelocity(wand)));
    }
}
