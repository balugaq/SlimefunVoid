package software.bigbade.slimefunvoid.listeners;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.items.VoidBag;

import java.util.Random;

@RequiredArgsConstructor
public class VoidBagListener implements Listener {
    private static final Random random = new Random();

    private final VoidBag voidBag;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null
                && (event.getClickedInventory().getType() != InventoryType.PLAYER || event.isShiftClick())
                && voidBag.getOpenBags().contains(event.getWhoClicked().getUniqueId())
                && (tryRemovingItem(event.getCursor(), event) || tryRemovingItem(event.getCurrentItem(), event))) {
            Bukkit.getScheduler().runTaskLater(SlimefunVoid.getInstance(), ((Player) event.getWhoClicked())::updateInventory, 1L);
        }
    }

    private boolean tryRemovingItem(ItemStack item, InventoryClickEvent event) {
        if (item == null || random.nextInt(100) >= item.getAmount())
            return false;
        int amount = item.getAmount();
        if (amount > 1)
            item.setAmount(amount - 1);
        else {
            event.setCancelled(true);
            event.getView().setCursor(null);
        }
        return true;
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        voidBag.getOpenBags().remove(event.getPlayer().getUniqueId());
    }
}
