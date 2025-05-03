package software.bigbade.slimefunvoid.listeners;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import software.bigbade.slimefunvoid.api.research.IVoidResearch;
import software.bigbade.slimefunvoid.api.research.VoidCategories;
import software.bigbade.slimefunvoid.utils.VoidResearchHelper;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;

public class VoidResearchNotePopulateListener implements Listener {
    private static final EntityType[] DROPABLE = new EntityType[]{EntityType.IRON_GOLEM, EntityType.ENDERMAN, EntityType.VILLAGER, EntityType.ZOMBIE_VILLAGER, EntityType.EVOKER, EntityType.WITCH, EntityType.ILLUSIONER, EntityType.PILLAGER, EntityType.VINDICATOR};
    private Random random = new Random();

    @EventHandler
    public void onChestOpen(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK
                || event.getClickedBlock() == null
                || event.getClickedBlock().getType() != Material.CHEST) {
            return;
        }
        Chest chest = (Chest) event.getClickedBlock().getState();
        if (chest.getLootTable() == null)
            return;

        boolean inTheEnd = Objects.requireNonNull(event.getPlayer().getLocation().getWorld()).getEnvironment() == World.Environment.THE_END;
        addResearch((inTheEnd ? 5 : 1), chest.getBlockInventory(), event.getPlayer());
        if (inTheEnd && random.nextInt(2) == 0) {
            addResearch(1, chest.getBlockInventory(), event.getPlayer());
        }
    }

    private void addResearch(int chance, Inventory inventory, Player player) {
        if (getsResearch(chance, player)) {
            ItemStack item = getVoidResearch(player);
            if (item != null) {
                inventory.setItem(random.nextInt(28), item);
            }
        }
    }

    @EventHandler
    public void onEntityInteract(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked().getType() != EntityType.MINECART_CHEST)
            return;
        StorageMinecart minecart = (StorageMinecart) event.getRightClicked();
        if (minecart.getLootTable() == null)
            return;
        addResearch(5, minecart.getInventory(), event.getPlayer());
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Player player = event.getEntity().getKiller();
        if (player == null)
            return;
        for (EntityType type : DROPABLE) {
            if (event.getEntity().getType() == type) {
                if (getsResearch(50, player)) {
                    ItemStack item = getVoidResearch(player);
                    if (item != null)
                        event.getDrops().add(item);
                }
                break;
            }
        }
    }

    private boolean getsResearch(int baseChance, Player player) {
        PotionEffect effect = player.getPotionEffect(PotionEffectType.LUCK);
        int chance = 1 + ((effect == null) ? 0 : effect.getAmplifier());
        return random.nextInt(baseChance) <= chance;
    }

    @Nullable
    private ItemStack getVoidResearch(Player player) {
        IVoidResearch research = null;
        for (VoidCategories category : VoidCategories.values()) {
            int researched = VoidResearchHelper.getResearched(player, category.getCategory());
            if (researched < category.getCategory().getResearches().size()) {
                research = category.getCategory().getResearches().get(researched);
                break;
            }
        }
        if (research == null)
            return null;
        return new CustomItemStack(Material.PAPER, "&5虚空研究", research.getName());
    }
}
