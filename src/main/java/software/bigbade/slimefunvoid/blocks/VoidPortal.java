package software.bigbade.slimefunvoid.blocks;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockBreakHandler;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockPlaceHandler;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockUseHandler;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.items.Items;
import software.bigbade.slimefunvoid.menus.RitualMenu;
import software.bigbade.slimefunvoid.menus.VoidMenu;
import software.bigbade.slimefunvoid.utils.RecipeItems;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VoidPortal extends SlimefunItem {
    private ChestMenu menu = new ChestMenu(SlimefunVoid.getInstance(), "&5Void Portal");

    private VoidMenu voidMenu = new VoidMenu();

    private RitualMenu ritualMenu = new RitualMenu(this);

    @Getter
    private List<VoidPortalData> altars = new ArrayList<>();

    public VoidPortal(Category category) {
        super(category, Items.VOID_PORTAL, RecipeType.ANCIENT_ALTAR, new ItemStack[]{Items.VOID_ALTAR, RecipeItems.ENCHANTING_TABLE, Items.VOID_ALTAR,
                RecipeItems.ENDER_EYE, RecipeItems.ENDER_EYE,
                RecipeItems.OBSIDIAN, Items.VOID_ALTAR, RecipeItems.OBSIDIAN});

        initMenu();

        BlockPlaceHandler blockPlaceHandler = this::onBlockPlace;
        addItemHandler(blockPlaceHandler);

        BlockUseHandler blockUseHandler = this::onBlockUse;
        addItemHandler(blockUseHandler);

        BlockBreakHandler blockBreakHandler = this::onBlockBreak;
        addItemHandler(blockBreakHandler);
    }

    private void initMenu() {
        for (int i = 0; i < 27; i++) {
            if (i > 11 && i < 15 && i % 2 == 0)
                continue;
            menu.addItem(i, VoidMenu.PURPLE_GLASS, (player, slot, item, cursor, action) -> false);
        }

        menu.addItem(12, new CustomItem(Material.END_PORTAL_FRAME, ChatColor.GREEN + "Enter The Void"), (player, slot, item, cursor, action) -> {
            PersistentDataContainer data = player.getPersistentDataContainer();
            if (data.has(VoidMenu.VOID_COOLDOWN, PersistentDataType.LONG)) {
                Long start = data.get(VoidMenu.VOID_COOLDOWN, PersistentDataType.LONG);
                Objects.requireNonNull(start);
                long cooldown = getCooldownTime(start);
                if (cooldown < 0)
                    voidMenu.open(player);
                else
                    player.sendMessage(ChatColor.RED + "You cannot enter the void for " + cooldown + " more seconds!");
                return false;
            }
            voidMenu.open(player);
            return false;
        });

        menu.addItem(14, new CustomItem(Material.STICK, ChatColor.GREEN + "Conduct a Ritual"), (player, slot, item, cursor, action) -> {
            Block altar = player.getTargetBlockExact(7, FluidCollisionMode.NEVER);
            if (altar == null || !altar.getType().equals(Material.END_PORTAL_FRAME)) {
                return false;
            }
            ritualMenu.open(getPortalData(altar.getLocation()), player);
            return false;
        });

        menu.addMenuOpeningHandler(this::onMenuOpen);
    }

    private void onMenuOpen(@Nonnull Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if (data.has(VoidMenu.VOID_COOLDOWN, PersistentDataType.LONG)) {
            Long start = data.get(VoidMenu.VOID_COOLDOWN, PersistentDataType.LONG);
            Objects.requireNonNull(start);
            long cooldown = getCooldownTime(start);
            if (cooldown > 0) {
                menu.replaceExistingItem(12, new CustomItem(Material.END_PORTAL_FRAME, ChatColor.GREEN + "Enter The Void " + ChatColor.WHITE + "(" + cooldown + "s)"));
            }
        }
    }

    private long getCooldownTime(long start) {
        return 300 - (System.currentTimeMillis() - start) / 1000;
    }

    private void onBlockUse(PlayerRightClickEvent event) {
        Block block = VoidAltar.prepareMenuOpen(event);
        if(block == null)
            return;
        String owner = BlockStorage.getLocationInfo(block.getLocation(), "owner");
        if (getPortalData(block.getLocation()) == null)
            altars.add(new VoidPortalData(block.getLocation()));
        if (owner.equals(event.getPlayer().getUniqueId().toString())) {
            menu.open(event.getPlayer());
        }
    }

    private boolean onBlockPlace(BlockPlaceEvent event, ItemStack item) {
        BlockStorage.addBlockInfo(event.getBlock(), "owner", event.getPlayer().getUniqueId().toString());
        altars.add(new VoidPortalData(event.getBlock().getLocation()));
        return true;
    }

    private boolean onBlockBreak(BlockBreakEvent event, ItemStack item, int fortune, List<ItemStack> drops) {
        VoidPortalData data = getPortalData(event.getBlock().getLocation());
        if(data==null) {
            return true;
        }
        if(data.getAltars().size() > 0) {
            event.getPlayer().sendMessage(ChatColor.RED + "You must break all altars before you break the portal.");
            event.setCancelled(true);
            return false;
        }
        return true;
    }

    public static void checkAltars(VoidPortalData data, Location location) {
        for (int x = -2; x < 3; x += 2) {
            for (int z = -2; z < 3; z += 2) {
                Location newLocation = location.clone().add(x, 0, z);
                Block block = newLocation.getBlock();
                if (block.getType().equals(Material.END_STONE_BRICKS) && BlockStorage.check(newLocation, Items.VOID_ALTAR.getItemID()) && !data.getAltars().contains(newLocation)) {
                    data.getAltars().add(newLocation);
                }
            }
        }
    }

    @Nullable
    public VoidPortalData getPortalData(Location location) {
        for (VoidPortalData altar : altars) {
            if (altar.getPortal().equals(location))
                return altar;
        }
        return null;
    }

    @RequiredArgsConstructor
    public static class VoidPortalData {
        @Getter
        @Setter
        private boolean inUse = false;

        @Getter
        private List<Location> altars = new ArrayList<>();
        @Getter
        private final Location portal;
    }
}
