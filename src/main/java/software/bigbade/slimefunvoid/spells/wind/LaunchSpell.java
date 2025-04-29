package software.bigbade.slimefunvoid.spells.wind;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import software.bigbade.slimefunvoid.api.Elements;
import software.bigbade.slimefunvoid.api.research.Researches;
import software.bigbade.slimefunvoid.impl.BasicSpell;
import software.bigbade.slimefunvoid.items.Items;
import software.bigbade.slimefunvoid.spells.ender.SwapSpell;

public class LaunchSpell extends BasicSpell {
    public LaunchSpell() {
        super(Researches.LAUNCH_SPELL.getResearch(), Elements.WIND, Items.LAUNCH_SPELL, 6);
    }

    public static Vector getVelocity(ItemStack wand) {
        return new Vector(0, getMultipliedDamage(wand, 1.5f, Elements.WIND), 0);
    }

    @Override
    public boolean onCast(Player player, ItemStack wand) {
        float distance = getMultipliedDamage(wand, 20, Elements.WIND);
        for (Entity entity : player.getNearbyEntities(distance, 5, distance)) {
            if (entity instanceof LivingEntity && SwapSpell.getLookingAt(player, entity)) {
                entity.setVelocity(entity.getVelocity().add(getVelocity(wand)));
                return true;
            }
        }
        player.sendMessage(ChatColor.RED + "You have to look at a target in range!");
        return false;
    }

    @Override
    public void onBackfire(Player player, ItemStack wand) {
        player.setVelocity(player.getVelocity().add(getVelocity(wand)));
    }
}
