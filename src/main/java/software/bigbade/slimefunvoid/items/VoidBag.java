package software.bigbade.slimefunvoid.items;

import lombok.Getter;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SimpleSlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.NotPlaceable;
import me.mrCookieSlime.Slimefun.Objects.handlers.ItemUseHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.utils.RecipeItems;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class VoidBag extends SimpleSlimefunItem<ItemUseHandler> implements NotPlaceable {
    public static final NamespacedKey BAG_LOCATION = new NamespacedKey(SlimefunVoid.getInstance(), "bagLocation");

    @Getter
    private final Set<UUID> openBags = new HashSet<>();

    public VoidBag(Category category) {
        super(category, Items.VOID_BAG, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{RecipeItems.OBSIDIAN, RecipeItems.ENDER_EYE, RecipeItems.OBSIDIAN,
                RecipeItems.OBSIDIAN, RecipeItems.ENDER_CHEST, RecipeItems.OBSIDIAN,
                RecipeItems.OBSIDIAN, RecipeItems.OBSIDIAN, RecipeItems.OBSIDIAN});
    }

    @Override
    public ItemUseHandler getItemHandler() {
        return event -> {
            event.setUseItem(Event.Result.DENY);
            event.setUseBlock(Event.Result.DENY);
            Objects.requireNonNull(event.getItem().getItemMeta());
            ItemMeta meta = event.getItem().getItemMeta();
            PersistentDataContainer data = meta.getPersistentDataContainer();
            if (data.has(BAG_LOCATION, PersistentDataType.STRING) && !event.getPlayer().isSneaking()) {
                openBag(data, event.getPlayer());
            } else if (event.getPlayer().isSneaking()) {
                setTargetBlock(data, event.getPlayer());
            } else {
                event.getPlayer().sendMessage(ChatColor.RED + "You must bind the Void Bag to a chest!");
            }
            event.getItem().setItemMeta(meta);
        };
    }

    private void setTargetBlock(PersistentDataContainer data, Player player) {
        Block block = player.getTargetBlockExact(7, FluidCollisionMode.NEVER);
        if (block == null || !block.getType().equals(Material.CHEST)) {
            player.sendMessage(ChatColor.RED + "Targeted block is not a Chest!");
            return;
        }
        Location loc = block.getLocation();
        Objects.requireNonNull(loc.getWorld());
        data.set(BAG_LOCATION, PersistentDataType.STRING, loc.getWorld().getName() + "|" + loc.getX() + "|" + loc.getY() + "|" + loc.getZ());
        player.sendMessage(ChatColor.GREEN + "Set bag location!");
}

    private void openBag(PersistentDataContainer data, Player player) {
        String stringLocation = data.get(BAG_LOCATION, PersistentDataType.STRING);
        Objects.requireNonNull(stringLocation);
        Block block = deserializeBlock(stringLocation);
        if (block.getType().equals(Material.CHEST)) {
            Inventory chest = ((Chest) block.getState()).getBlockInventory();
            openBags.add(player.getUniqueId());
            player.openInventory(chest);
        } else {
            data.remove(BAG_LOCATION);
            player.sendMessage(ChatColor.RED + "Invalid bag location! Target chest might of been broken.");
        }
    }

    public static Block deserializeBlock(String data) {
        String[] split = data.split("\\|");
        World world = Bukkit.getWorld(split[0]);
        Location location = new Location(world, Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]));
        return location.getBlock();
    }
}
