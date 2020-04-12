package software.bigbade.slimefunvoid.spells.grass;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import software.bigbade.slimefunvoid.api.Elements;
import software.bigbade.slimefunvoid.api.research.Researches;
import software.bigbade.slimefunvoid.impl.BasicSpell;
import software.bigbade.slimefunvoid.items.Items;
import software.bigbade.slimefunvoid.spells.ender.SwapSpell;
import software.bigbade.slimefunvoid.spells.water.IceShieldSpell;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TreeTrapSpell extends BasicSpell {
    private final Map<UUID, Location> toRemove = new HashMap<>();

    public TreeTrapSpell() {
        super(Researches.TREE_TRAP_SPELL.getResearch(), Elements.GRASS, Items.TREE_TRAP_SPELL, 10);
    }

    @Override
    public boolean onCast(Player player, ItemStack wand) {
        float distance = getMultipliedDamage(wand, 20, Elements.GRASS);
        for(Entity entity : player.getNearbyEntities(distance, 5, distance)) {
            if(entity instanceof LivingEntity && SwapSpell.getLookingAt(player, entity)) {
                trapEntity((LivingEntity) entity, wand);
                toRemove.put(player.getUniqueId(), entity.getLocation());
                return true;
            }
        }
        player.sendMessage(ChatColor.RED + "You have to look at a target in range!");
        return false;
    }

    @Override
    public void onBackfire(Player player, ItemStack wand) {
        trapEntity(player, wand);
        toRemove.put(player.getUniqueId(), player.getLocation());
    }

    @Override
    public void onStop(Player player, ItemStack wand) {
        IceShieldSpell.makeSphere(toRemove.get(player.getUniqueId()), Material.AIR, Material.OAK_WOOD, getMultipliedDamage(wand, 3, Elements.GRASS), true);
        toRemove.remove(player.getUniqueId());
    }

    private void trapEntity(LivingEntity entity, ItemStack wand) {
        IceShieldSpell.makeSphere(entity.getLocation(), Material.OAK_WOOD, Material.AIR, getBackfireDamage(wand, 3, Elements.GRASS), true);
        entity.getLocation().getBlock().setType(Material.AIR);
        entity.getLocation().add(0, 1, 0).getBlock().setType(Material.AIR);
    }
}
