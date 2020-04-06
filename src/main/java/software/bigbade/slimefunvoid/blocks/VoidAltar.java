package software.bigbade.slimefunvoid.blocks;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockBreakHandler;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockPlaceHandler;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockUseHandler;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ItemUtils;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import software.bigbade.slimefunvoid.items.Items;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;

public class VoidAltar extends SlimefunItem {
    private VoidPortal portal;

    public VoidAltar(Category category, VoidPortal portal) {
        super(category, Items.VOID_ALTAR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{});

        this.portal = portal;

        BlockUseHandler blockUseHandler = this::onBlockUse;
        addItemHandler(blockUseHandler);

        BlockPlaceHandler blockPlaceHandler = this::onBlockPlace;
        addItemHandler(blockPlaceHandler);

        BlockBreakHandler blockBreakHandler = this::onBlockBreak;
        addItemHandler(blockBreakHandler);
    }

    private void onBlockUse(PlayerRightClickEvent event) {
        Player player = event.getPlayer();
        Block block = prepareMenuOpen(event);
        if(block == null)
            return;
        Item item = getItem(block);
        VoidPortal.VoidAltarLocation location = findPortal(block.getLocation(), player);
        if (location == null) {
            player.sendMessage("This altar is corrupted!");
            return;
        }
        if(location.isInUse())
            return;
        if (item != null) {
            event.getPlayer().getInventory().addItem(item.getItemStack());
            item.remove();
        } else {
            ItemStack heldItem = player.getInventory().getItemInMainHand();
            if (heldItem.getType() == Material.AIR) {
                player.sendMessage(ChatColor.RED + "You have to hold an item to put on the altar");
                return;
            }
            System.out.println("Dropping item");
            dropItem(player, block);
        }
    }

    public static Block prepareMenuOpen(PlayerRightClickEvent event) {
        event.setUseBlock(Event.Result.DENY);
        event.setUseItem(Event.Result.DENY);
        Optional<Block> clicked = event.getClickedBlock();
        return clicked.orElse(null);
    }

    private void dropItem(Player player, Block block) {
        ItemStack hand = player.getInventory().getItemInMainHand();
        ItemStack stack = new CustomItem(hand, 1);

        if (player.getGameMode() != GameMode.CREATIVE) {
            ItemUtils.consumeItem(hand, false);
        }

        String nametag = ItemUtils.getItemName(stack);
        Item entity = block.getWorld().dropItem(block.getLocation().add(0.5, 1.2, 0.5), new CustomItem(stack, "&5&dALTAR &3Probe - &e" + System.nanoTime()));
        entity.setVelocity(new Vector(0, 0.1, 0));
        SlimefunUtils.markAsNoPickup(entity, "void_altar_item");
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

    private boolean onBlockBreak(BlockBreakEvent event, ItemStack item, int fortune, List<ItemStack> drops) {
        Item held = getItem(event.getBlock());
        VoidPortal.VoidAltarLocation location = portal.getAltar(event.getBlock().getLocation());
        if (held != null) {
            event.getPlayer().getInventory().addItem(held.getItemStack());
            held.remove();
        }
        if (location != null) {
            location.getAltars().remove(event.getBlock().getLocation());
        }
        return true;
    }

    private boolean onBlockPlace(BlockPlaceEvent event, ItemStack item) {
        if (findPortal(event.getBlock().getLocation(), event.getPlayer()) != null)
            return true;
        event.getPlayer().sendMessage(ChatColor.RED + "You have to place this diagonal, horizontal, or vertical from a Void Portal.");
        event.setCancelled(true);
        return false;
    }

    private VoidPortal.VoidAltarLocation findPortal(Location location, @Nullable Player player) {
        for (int x = -2; x < 3; x += 2) {
            for (int z = -2; z < 3; z += 2) {
                VoidPortal.VoidAltarLocation altarLocation = checkLocation(location, player, x, z);
                if(altarLocation != null)
                    return altarLocation;
            }
        }
        return null;
    }

    private VoidPortal.VoidAltarLocation checkLocation(Location location, Player player, int x, int z) {
        Location newLocation = location.add(x, 0, z);
        Block block = newLocation.getBlock();
        if (block.getType().equals(Material.END_PORTAL_FRAME) && BlockStorage.check(newLocation, Items.VOID_PORTAL.getItemID())) {
            VoidPortal.VoidAltarLocation altarLocation = portal.getAltar(newLocation);
            if (altarLocation == null) {
                if (player != null)
                    player.sendMessage(ChatColor.RED + "Found corrupted portal, please report this!");
                return null;
            }
            altarLocation.getAltars().add(location);
            return altarLocation;
        }
        return null;
    }
}
