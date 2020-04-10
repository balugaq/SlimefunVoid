package software.bigbade.slimefunvoid.menus.ritals;

import me.mrCookieSlime.Slimefun.api.BlockStorage;
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
import org.bukkit.inventory.meta.ItemMeta;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.api.Elements;
import software.bigbade.slimefunvoid.api.research.VoidRecipes;
import software.bigbade.slimefunvoid.blocks.VoidAltar;
import software.bigbade.slimefunvoid.blocks.VoidPortal;
import software.bigbade.slimefunvoid.items.wands.WandItem;
import software.bigbade.slimefunvoid.menus.VoidMenu;
import software.bigbade.slimefunvoid.tasks.VoidRitualTask;

import javax.annotation.Nonnull;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class RitualMenu extends ChestMenu {

    private final RitualRecipeMenu recipeMenu = new RitualRecipeMenu();

    private static final CustomItem wandItem = new CustomItem(Material.STICK, ChatColor.GREEN + "Put a wand here");

    private static final int[] RECIPE_ITEMS = new int[]{0, 1, 2, 3, 5, 6, 7, 8};

    public RitualMenu() {
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

        addItem(15, new CustomItem(Material.END_PORTAL_FRAME, ChatColor.DARK_PURPLE + "Start the Ritual"), (player, slot, item, cursor, action) -> checkRitual(player));

        setSize(27);
    }

    private boolean setWand(ItemStack cursor, ItemStack item, Player player) {
        Objects.requireNonNull(cursor);

        Block altar = player.getTargetBlockExact(7, FluidCollisionMode.NEVER);
        if (altar == null || !altar.getType().equals(Material.END_PORTAL_FRAME)) {
            return false;
        }
        if (cursor.getType() != Material.AIR && item != null && item.equals(wandItem)) {
            replaceExistingItem(13, null);
            return true;
        }
        if (cursor.getType() != Material.AIR) {
            if (WandItem.getWand(cursor) != null) {
                VoidAltar.storeItem(cursor, altar);
                VoidAltar.dropItem(player, cursor, altar);
                replaceExistingItem(13, null);
                return true;
            }
            player.sendMessage(ChatColor.RED + "That is not a wand!");
            return false;
        } else if (item != null && !isWandItem(item) && cursor.getType() == Material.AIR) {
            Bukkit.getScheduler().runTaskLater(SlimefunVoid.getInstance(), () ->
                    replaceExistingItem(13, wandItem), 1L);
            BlockStorage.addBlockInfo(altar, "dropped", null);
            Item droppedItem = VoidAltar.getItem(altar);
            if(droppedItem != null) {
                droppedItem.remove();
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean isWandItem(ItemStack target) {
        ItemMeta checkingMeta = wandItem.getItemMeta();
        ItemMeta targetMeta = target.getItemMeta();
        Objects.requireNonNull(checkingMeta);
        Objects.requireNonNull(targetMeta);
        return targetMeta.getDisplayName().equals(checkingMeta.getDisplayName());
    }

    private boolean checkRitual(Player player) {
        Block target = player.getTargetBlockExact(7, FluidCollisionMode.NEVER);
        if (target == null || !target.getType().equals(Material.END_PORTAL_FRAME)) {
            return false;
        }
        VoidPortal.VoidPortalData data = new VoidPortal.VoidPortalData(target.getLocation());
        VoidPortal.checkAltars(data);
        ItemStack[] ingredients = getIngredients(data);
        ItemStack output = null;
        if (ingredients[4] != null && WandItem.getWand(ingredients[4]) != null) {
            output = ingredients[4].clone();
            if (!checkWandIngredients(ingredients, output))
                output = null;
        }
        if(output == null) {
            for (ItemStack foundOutput : VoidRecipes.getRecipes().keySet()) {
                ItemStack[] items = VoidRecipes.getRecipes().get(foundOutput);
                if (isArrayEqual(ingredients, items)) {
                    output = foundOutput.clone();
                    break;
                }
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
        for (int i : RECIPE_ITEMS) {
            Location altar = data.getAltars().get((i > 4) ? i - 1 : i);
            ingredients[i] = VoidAltar.getStoredItem(altar.getBlock());
        }
        ingredients[4] = VoidAltar.getStoredItem(data.getPortal().getBlock());
        return ingredients;
    }

    private boolean checkWandIngredients(ItemStack[] items, ItemStack wand) {
        Map<Elements, Integer> elements = new EnumMap<>(Elements.class);
        for (int i : RECIPE_ITEMS) {
            if(items[i] == null)
                continue;
            Elements element = isElementItem(items[i]);
            if (element == null)
                return false;
            else {
                elements.compute(element, (current, amount) -> amount == null ? 10 : amount + 10);
            }
        }
        for(Map.Entry<Elements, Integer> elementEntry : elements.entrySet()) {
            WandItem.chargeItem(wand, elementEntry.getKey(), elementEntry.getValue());
        }
        return true;
    }

    public static Elements isElementItem(ItemStack item) {
        for (Elements element : Elements.values())
            if (element.getItems().contains(item.getType())) {
                return element;
            }
        return null;
    }

    private boolean isArrayEqual(ItemStack[] target, ItemStack[] checking) {
        if (target.length != checking.length)
            return false;
        for (int i = 0; i < target.length; i++) {
            if (target[i] == null)
                continue;
            if (i == 4) {
                //If it needs a wand, check that the wand isn't null, or if it doesn't need one, the wand should be null.
                if (checking[i] != null && target[i] == null || checking[i] == null && target[i] != null)
                    return false;
            } else if (!target[i].equals(checking[i])) {
                return false;
            }
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
        VoidPortal.checkAltars(location);
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
