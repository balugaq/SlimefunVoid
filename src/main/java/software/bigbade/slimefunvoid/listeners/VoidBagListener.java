package software.bigbade.slimefunvoid.listeners;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import software.bigbade.slimefunvoid.items.VoidBag;

import java.util.Objects;
import java.util.Random;

public class VoidBagListener implements Listener {
    private static final Random random = new Random();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(ChatColor.DARK_PURPLE + "Void Bag")
                && event.getSlot() != -1
                && event.getCursor() != null
                && random.nextInt(100) < event.getCursor().getAmount()) {
            int amount = event.getCursor().getAmount();
            if (amount > 1)
                event.getCursor().setAmount(amount - 1);
            else {
                event.setCancelled(true);
                event.getView().setCursor(null);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getView().getTitle().equals(ChatColor.DARK_PURPLE + "Void Bag")) {
            ItemMeta meta = event.getPlayer().getInventory().getItemInMainHand().getItemMeta();
            Objects.requireNonNull(meta);
            PersistentDataContainer data = meta.getPersistentDataContainer();
            if(data.has(VoidBag.BAG_LOCATION, PersistentDataType.STRING)) {
                String stringLocation = data.get(VoidBag.BAG_LOCATION, PersistentDataType.STRING);
                Objects.requireNonNull(stringLocation);
                Block block = VoidBag.deserializeBlock(stringLocation);
                ((Chest) block).getBlockInventory().setContents(event.getInventory().getContents());
            }
        }
    }
}
