package software.bigbade.slimefunvoid.menus;

import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.api.Element;
import software.bigbade.slimefunvoid.api.VoidRecipes;
import software.bigbade.slimefunvoid.blocks.VoidAltar;
import software.bigbade.slimefunvoid.blocks.VoidPortal;
import software.bigbade.slimefunvoid.items.wands.WandItem;
import software.bigbade.slimefunvoid.tasks.VoidRitualTask;

import javax.annotation.Nonnull;
import java.util.Objects;

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

        addItem(15, new CustomItem(Material.END_PORTAL_FRAME, ChatColor.DARK_PURPLE + "Start the Ritual"), (player, slot, item, cursor, action) -> checkRitual(player, portal));

        setSize(27);
    }

    private boolean setWand(ItemStack cursor, ItemStack item, Player player) {
        if (cursor != null && item != null && item.equals(wandItem)) {
            item.setType(Material.AIR);
        }
        if (cursor != null) {
            if (cursor instanceof SlimefunItemStack && ((SlimefunItemStack) cursor).getItem() instanceof WandItem) {
                Block altar = player.getTargetBlockExact(7, FluidCollisionMode.NEVER);
                if (altar == null || !altar.getType().equals(Material.END_PORTAL_FRAME)) {
                    return false;
                }
                VoidAltar.storeItem(cursor, altar);
                VoidAltar.dropItem(player, cursor, altar);
            } else {
                player.sendMessage(ChatColor.RED + "That is not a wand!");
                return false;
            }
        }
        return true;
    }

    private boolean checkRitual(Player player, VoidPortal portal) {
        Block target = player.getTargetBlockExact(7, FluidCollisionMode.NEVER);
        if (target == null || !target.getType().equals(Material.END_PORTAL_FRAME)) {
            return false;
        }
        VoidPortal.VoidPortalData data = portal.getPortalData(target.getLocation());
        Objects.requireNonNull(data);
        ItemStack[] ingredients = getIngredients(data);
        ItemStack output = null;
        if(ingredients[4] instanceof SlimefunItemStack && ((SlimefunItemStack) ingredients[4]).getItem() instanceof WandItem) {
            output = ingredients[4].clone();
            checkWandIngredients(ingredients, output);
        }
        for (ItemStack foundOutput : VoidRecipes.getRecipes().keySet()) {
            ItemStack[] items = VoidRecipes.getRecipes().get(foundOutput);
            if (isArrayEqual(ingredients, items)) {
                output = foundOutput;
                break;
            }
        }
        if (output == null)
            player.sendMessage(ChatColor.RED + "That ritual is not correct!");
        else
            startRitual(data, output);
        player.closeInventory();
        return false;
    }

    private ItemStack[] getIngredients(VoidPortal.VoidPortalData data) {
        ItemStack[] ingredients = new ItemStack[9];
        int i = 0;
        for (Location altars : data.getAltars()) {
            Item item = VoidAltar.getItem(altars.getBlock());
            if (item == null)
                ingredients[i] = null;
            else
                ingredients[i] = item.getItemStack();
            i++;
            if (i == 4)
                i++;
        }
        return ingredients;
    }

    private boolean checkWandIngredients(ItemStack[] items, ItemStack wand) {
        for(int i = 0; i < 8; i++) {
            if(i==4) continue;
            if(!isElementItem(items[i], wand))
                return false;
        }
        return true;
    }

    public static boolean isElementItem(ItemStack item, ItemStack wand) {
        for(Element element : Element.values())
            if(element.getItems().contains(item.getType())) {
                WandItem.chargeItem(wand, element);
                return true;
            }
        return false;
    }

    private boolean isArrayEqual(ItemStack[] target, ItemStack[] checking) {
        if (target.length != checking.length)
            return false;
        for (int i = 0; i < target.length; i++) {
            if (!target[i].equals(checking[i]))
                return false;
        }
        return true;
    }

    private void initMenu(VoidPortal.VoidPortalData location) {
        addMenuOpeningHandler(player -> onMenuOpen(player, location));
    }

    public void open(VoidPortal.VoidPortalData location, Player... players) {
        initMenu(location);
        super.open(players);
    }

    private void onMenuOpen(@Nonnull Player player, VoidPortal.VoidPortalData location) {
        VoidPortal.checkAltars(location, location.getPortal());
        if (location.getAltars().size() != 8) {
            player.sendMessage(ChatColor.RED + "You need to place 8 altars around the portal to preform a ritual!");
            player.closeInventory();
        }
        ItemStack wand = VoidAltar.getStoredItem(location.getPortal().getBlock());
        if (wand != null)
            replaceExistingItem(13, wand);
    }

    private void startRitual(VoidPortal.VoidPortalData location, ItemStack output) {
        location.setInUse(true);
        Bukkit.getScheduler().runTaskLater(SlimefunVoid.getInstance(), new VoidRitualTask(location, output), 1L);
    }
}
