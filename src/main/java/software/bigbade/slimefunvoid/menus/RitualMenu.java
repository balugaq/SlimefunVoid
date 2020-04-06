package software.bigbade.slimefunvoid.menus;

import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.blocks.VoidPortal;
import software.bigbade.slimefunvoid.tasks.VoidRitualTask;

import javax.annotation.Nonnull;

public class RitualMenu extends ChestMenu {

    private final RitualRecipeMenu recipeMenu = new RitualRecipeMenu();

    private static final CustomItem wandItem = new CustomItem(Material.STICK, ChatColor.GREEN + "Put a wand here");

    public RitualMenu(VoidPortal portal) {
        super(SlimefunVoid.getInstance(), "&5Insert a Wand");

        for (int i = 0; i < 27; i++) {
            if (i > 10 && i < 16 && i % 2 == 1)
                continue;
            addItem(i, VoidMenu.PURPLE_GLASS, (player, slot, item, cursor, action) -> false);
        }

        addItem(11, new CustomItem(Material.BOOK, ChatColor.GREEN + "View Recipes"), (player, slot, item, cursor, action) -> {
            recipeMenu.open(player);
            return false;
        });

        addItem(13, wandItem, (player, slot, item, cursor, action) -> setWand(cursor, item, player));

        addItem(15, new CustomItem(Material.END_PORTAL_FRAME, ChatColor.DARK_PURPLE + "Start the Ritual"), (player, slot, item, cursor, action) -> startRitual(player, portal));

        setSize(27);
    }

    private boolean setWand(ItemStack cursor, ItemStack item, Player player) {
        if (cursor != null && item != null && item.equals(wandItem)) {
            item.setType(Material.AIR);
        }
        if(cursor != null) {
            if(cursor instanceof SlimefunItemStack) {
                Block altar = player.getTargetBlockExact(7, FluidCollisionMode.NEVER);
                if (altar == null || !altar.getType().equals(Material.END_PORTAL_FRAME)) {
                    return false;
                }
                BlockStorage.addBlockInfo(altar.getLocation(), "wand", ((SlimefunItemStack) cursor).getItemID());
            } else {
                player.sendMessage(ChatColor.RED + "That is not a wand!");
                return false;
            }
        }
        return true;
    }

    private boolean startRitual(Player player, VoidPortal portal) {
        Block altar = player.getTargetBlockExact(7, FluidCollisionMode.NEVER);
        if (altar == null || !altar.getType().equals(Material.END_PORTAL_FRAME)) {
            return false;
        }
        VoidPortal.VoidAltarLocation location = portal.getAltar(altar.getLocation());
        if(location == null) {
            player.sendMessage(ChatColor.RED + "Broken portal location. Try replacing the portal.");
            player.closeInventory();
            return false;
        }
        startRitual(location);
        player.closeInventory();
        return false;
    }

    private void initMenu(VoidPortal.VoidAltarLocation location) {
        addMenuOpeningHandler(player -> onMenuOpen(player, location));
    }

    public void open(VoidPortal.VoidAltarLocation location, Player... players) {
        initMenu(location);
        super.open(players);
    }

    private void onMenuOpen(@Nonnull Player player, VoidPortal.VoidAltarLocation location) {
        if (location.getAltars().size() != 8) {
            player.sendMessage(ChatColor.RED + "You need to place 8 altars around the portal to preform a ritual!");
            player.closeInventory();
        }

        SlimefunItem wand = null;
        if (BlockStorage.hasBlockInfo(location.getPortal())) {
            wand = BlockStorage.check(location.getPortal());
        }

        if (wand != null)
            replaceExistingItem(13, wand.getItem());
    }

    private void startRitual(VoidPortal.VoidAltarLocation location) {
        location.setInUse(true);
        Bukkit.getScheduler().runTaskLater(SlimefunVoid.getInstance(), new VoidRitualTask(location), 1L);
    }
}
