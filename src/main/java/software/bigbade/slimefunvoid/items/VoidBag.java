package software.bigbade.slimefunvoid.items;

import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SimpleSlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.ItemUseHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import software.bigbade.slimefunvoid.SlimefunVoid;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class VoidBag extends SimpleSlimefunItem<ItemUseHandler> {
    public static final NamespacedKey BAG_LOCATION = new NamespacedKey(SlimefunVoid.getInstance(), "bagLocation");

    public VoidBag(Category category) {
        super(category, Items.VOID_BAG, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] { });
    }

    @Override
    public ItemUseHandler getItemHandler() {
        return event -> {
            Objects.requireNonNull(event.getItem().getItemMeta());
            PersistentDataContainer data = event.getItem().getItemMeta().getPersistentDataContainer();
            if(data.has(BAG_LOCATION, PersistentDataType.STRING)) {
                openBag(data, event.getPlayer());
            } else {
                setTargetBlock(data, event.getPlayer());
            }
        };
    }

    private void setTargetBlock(PersistentDataContainer data, Player player) {
        List<Block> targeted = player.getLastTwoTargetBlocks(Collections.singleton(Material.CHEST), 7);
        if(!targeted.isEmpty() && targeted.get(0).getType().equals(Material.CHEST)) {
            Location loc = targeted.get(0).getLocation();
            Objects.requireNonNull(loc.getWorld());
            data.set(BAG_LOCATION, PersistentDataType.STRING, loc.getWorld().getName() + "|" + loc.getX() + "|" + loc.getY() + "|" + loc.getZ());
            player.sendMessage("&aSet bag location!");
            return;
        }
        player.sendMessage("&aTargeted block is not a Chest!");
    }

    private void openBag(PersistentDataContainer data, Player player) {
        String stringLocation = data.get(BAG_LOCATION, PersistentDataType.STRING);
        Objects.requireNonNull(stringLocation);
        Block block = deserializeBlock(stringLocation);
        if(block.getType().equals(Material.CHEST)) {
            Inventory chest = ((Chest) block).getBlockInventory();
            Inventory newInventory = Bukkit.createInventory(null, chest.getSize(), ChatColor.DARK_PURPLE + "Void Bag");
            for(int i = 0; i < chest.getSize(); i++)
                newInventory.setItem(i, chest.getItem(i));
            player.openInventory(newInventory);
        } else {
            data.remove(BAG_LOCATION);
            player.sendMessage("&4Invalid bag location!");
        }
    }

    public static Block deserializeBlock(String data) {
        String[] split = data.split("\\|");
        World world = Bukkit.getWorld(split[0]);
        Location location = new Location(world, Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]));
        return location.getBlock();
    }
}
