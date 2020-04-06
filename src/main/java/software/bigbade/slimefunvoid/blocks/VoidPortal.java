package software.bigbade.slimefunvoid.blocks;

import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SimpleSlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockUseHandler;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.items.Items;
import software.bigbade.slimefunvoid.menus.RitualMenu;
import software.bigbade.slimefunvoid.menus.VoidMenu;
import software.bigbade.slimefunvoid.utils.RecipeItems;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Optional;

public class VoidPortal extends SimpleSlimefunItem<BlockUseHandler> {
    private ChestMenu menu = new ChestMenu(SlimefunVoid.getInstance(), "&5Void Portal");

    private VoidMenu voidMenu = new VoidMenu();

    private RitualMenu ritualMenu = new RitualMenu();

    public VoidPortal(Category category) {
        super(category, Items.VOID_PORTAL, RecipeType.ANCIENT_ALTAR, new ItemStack[]{Items.VOID_ALTAR, RecipeItems.ENCHANTING_TABLE, Items.VOID_ALTAR,
                RecipeItems.ENDER_EYE, RecipeItems.ENDER_EYE,
                RecipeItems.OBSIDIAN, Items.VOID_ALTAR, RecipeItems.OBSIDIAN});

        initMenu();
    }

    private void initMenu() {
        for (int i = 0; i < 27; i++) {
            if (i > 11 && i < 15 && i % 2 == 0)
                continue;
            menu.addItem(i, VoidMenu.PURPLE_GLASS, (player, slot, item, cursor, action) -> false);
        }

        menu.addItem(12, new CustomItem(Material.END_PORTAL_FRAME, ChatColor.GREEN + "Enter The Void"), (player, slot, item, cursor, action) -> {
            PersistentDataContainer data = player.getPersistentDataContainer();
            if(data.has(VoidMenu.VOID_COOLDOWN, PersistentDataType.LONG)) {
                Long start = data.get(VoidMenu.VOID_COOLDOWN, PersistentDataType.LONG);
                Objects.requireNonNull(start);
                long cooldown = getCooldownTime(start);
                if(cooldown < 0)
                    voidMenu.open(player);
                else
                    player.sendMessage(ChatColor.RED + "You cannot enter the void for " + cooldown + " more seconds!");
                return false;
            }
            voidMenu.open(player);
            return false;
        });

        menu.addItem(14, new CustomItem(Material.STICK, ChatColor.GREEN + "Conduct a Ritual"), (player, slot, item, cursor, action) -> {
            ritualMenu.open(player);
            return false;
        });

        menu.addMenuOpeningHandler(this::onMenuOpen);
    }

    private void onMenuOpen(@Nonnull Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if(data.has(VoidMenu.VOID_COOLDOWN, PersistentDataType.LONG)) {
            Long start = data.get(VoidMenu.VOID_COOLDOWN, PersistentDataType.LONG);
            Objects.requireNonNull(start);
            long cooldown = getCooldownTime(start);
            if (cooldown > 0) {
                menu.replaceExistingItem(12, new CustomItem(Material.END_PORTAL_FRAME, ChatColor.GREEN + "Enter The Void " + ChatColor.WHITE + "(" + cooldown + "s)"));
            }
        }
    }

    private long getCooldownTime(long start) {
        return 300-(System.currentTimeMillis()-start)/1000;
    }

    @Override
    public BlockUseHandler getItemHandler() {
        return event -> {
            event.setUseBlock(Event.Result.DENY);
            event.setUseItem(Event.Result.DENY);
            Optional<Block> optional = event.getClickedBlock();
            if (!optional.isPresent())
                return;
            Block block = optional.get();
            String owner = BlockStorage.getLocationInfo(block.getLocation(), "owner");
            if (owner == null) {
                BlockStorage.addBlockInfo(block, "owner", event.getPlayer().getUniqueId().toString());
                menu.open(event.getPlayer());
            } else if (owner.equals(event.getPlayer().getUniqueId().toString())) {
                menu.open(event.getPlayer());
            }
        };
    }
}
