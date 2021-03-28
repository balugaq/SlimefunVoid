package software.bigbade.slimefunvoid.blocks;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import lombok.SneakyThrows;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ItemUtils;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import software.bigbade.slimefunvoid.items.Items;
import software.bigbade.slimefunvoid.utils.RecipeItems;

public class VoidAltar extends SlimefunItem {
    private final VoidPortal portal;

    public VoidAltar(Category category, VoidPortal portal) {
        super(category, Items.VOID_ALTAR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{RecipeItems.END_STONE, RecipeItems.END_STONE, RecipeItems.END_STONE,
                RecipeItems.END_STONE, RecipeItems.OBSIDIAN, RecipeItems.END_STONE,
                RecipeItems.END_STONE, RecipeItems.END_STONE, RecipeItems.END_STONE});

        this.portal = portal;

        BlockUseHandler blockUseHandler = this::onBlockUse;
        addItemHandler(blockUseHandler);

        addItemHandler(new BlockPlaceHandler(true) {
			
			@Override
			public void onPlayerPlace(BlockPlaceEvent e) {
		        Item held = getItem(e.getBlock());
		        VoidPortal.VoidPortalData location = findPortal(e.getBlock().getLocation());
		        if (held != null) {
		        	dropItem(e.getPlayer(), new ItemStack(held.getItemStack().getType()), e.getBlock());
		            held.remove();
		        }
		        if (location != null) {
		            location.getAltars().remove(e.getBlock().getLocation());
		        }
			}
		});

        addItemHandler(new BlockBreakHandler(false, false) {
			
			@Override
			public void onPlayerBreak(BlockBreakEvent e, ItemStack item, List<ItemStack> drops) {
		        Item held = getItem(e.getBlock());
		        VoidPortal.VoidPortalData location = findPortal(e.getBlock().getLocation());
		        if (held != null) {
		            drops.add(new ItemStack(held.getItemStack().getType()));
		            held.remove();
		        }
		        if (location != null) {
		            location.getAltars().remove(e.getBlock().getLocation());
		        }
			}
		});
    }

    private void onBlockUse(PlayerRightClickEvent event) {
        Player player = event.getPlayer();
        Block block = prepareMenuOpen(event);
        if (block == null)
            return;
        Item item = getItem(block);
        VoidPortal.VoidPortalData location = findPortal(block.getLocation());
        if (location == null) {
            player.sendMessage("This altar is corrupted!");
            return;
        }
        if (location.isInUse())
            return;
        if (item != null && !location.isInUse()) {
            ItemStack stored = getStoredItem(block);
            if (stored != null) {
                event.getPlayer().getInventory().addItem();
            }
            item.remove();
        } else {
            ItemStack heldItem = player.getInventory().getItemInMainHand();
            if (heldItem.getType() == Material.AIR) {
                player.sendMessage(ChatColor.RED + "You have to hold an item to put on the altar");
                return;
            }
            dropItem(player, player.getInventory().getItemInMainHand(), block);
        }
    }

    @SneakyThrows
    @Nullable
    public static ItemStack getStoredItem(Block block) {
        String data = BlockStorage.getLocationInfo(block.getLocation(), "dropped");
        if (data == null) return null;
        YamlConfiguration configuration = new YamlConfiguration();
        try {
			configuration.loadFromString(data);
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return configuration.getItemStack("item");
    }

    public static Block prepareMenuOpen(PlayerRightClickEvent event) {
        event.setUseBlock(Event.Result.DENY);
        event.setUseItem(Event.Result.DENY);
        Optional<Block> clicked = event.getClickedBlock();
        return clicked.orElse(null);
    }

    public static void dropItem(Player player, ItemStack item, Block block) {
        ItemStack stack = new ItemStack(item);
        stack.setAmount(1);

        if (player.getGameMode() != GameMode.CREATIVE) {
            ItemUtils.consumeItem(item, false);
        }

        String nametag = ItemUtils.getItemName(stack);
        CustomItem dropped = new CustomItem(stack, "VoidItem - " + System.currentTimeMillis());
        storeItem(stack, block);
        Item entity = block.getWorld().dropItem(block.getLocation().add(0.5, 1.2, 0.5), dropped);
        entity.setInvulnerable(true);
        entity.setVelocity(new Vector(0, 0.1, 0));
        entity.setPickupDelay(32767);
        entity.setTicksLived(Integer.MAX_VALUE);
        entity.setCustomNameVisible(true);
        entity.setCustomName(nametag);
        player.playSound(block.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.3F, 0.3F);
    }

    @Nullable
    public static Item getItem(Block block) {
        for (Entity entity : block.getChunk().getEntities()) {
            if (entity instanceof Item && block.getLocation().add(0.5, 1.2, 0.5).distanceSquared(entity.getLocation()) < 0.5D && entity.getCustomName() != null)
                return (Item) entity;
        }
        return null;
    }

    @Nullable
    private VoidPortal.VoidPortalData findPortal(Location location) {
        for (int i = 0; i < VoidPortal.X_OFFSETS.length; i++) {
            VoidPortal.VoidPortalData altarLocation = checkLocation(location, VoidPortal.X_OFFSETS[i], VoidPortal.Z_OFFSETS[i]);
            if (altarLocation != null)
                return altarLocation;
        }
        return null;
    }

    private VoidPortal.VoidPortalData checkLocation(Location location, int x, int z) {
        Location newLocation = location.clone().add(x, 0, z);
        Block block = newLocation.getBlock();
        if (block.getType().equals(Material.END_PORTAL_FRAME) && BlockStorage.check(newLocation, Items.VOID_PORTAL.getItemId())) {
            VoidPortal.VoidPortalData altarLocation = portal.getPortalData(newLocation);
            if (altarLocation == null) {
                altarLocation = new VoidPortal.VoidPortalData(block.getLocation());
                portal.getAltars().add(altarLocation);
                return altarLocation;
            }
            if (!altarLocation.getAltars().contains(location))
                altarLocation.getAltars().add(location);
            return altarLocation;
        }
        return null;
    }

    public static void storeItem(ItemStack item, Block block) {
        YamlConfiguration configuration = new YamlConfiguration();
        configuration.set("item", item);
        BlockStorage.addBlockInfo(block, "dropped", configuration.saveToString());
    }
}
