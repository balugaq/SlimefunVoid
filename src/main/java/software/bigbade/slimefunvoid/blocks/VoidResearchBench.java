package software.bigbade.slimefunvoid.blocks;

import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SimpleSlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockUseHandler;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.api.IResearchCategory;
import software.bigbade.slimefunvoid.api.IVoidResearch;
import software.bigbade.slimefunvoid.items.Items;
import software.bigbade.slimefunvoid.menus.CategorySelectMenu;
import software.bigbade.slimefunvoid.utils.RecipeItems;
import software.bigbade.slimefunvoid.api.VoidCategories;
import software.bigbade.slimefunvoid.utils.VoidResearchHelper;
import software.bigbade.slimefunvoid.api.VoidResearches;

import javax.annotation.Nonnull;
import java.util.Objects;

public final class VoidResearchBench extends SimpleSlimefunItem<BlockUseHandler> {
    private static final ItemStack GREY_GLASS = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);

    public static final NamespacedKey RESEARCH_KEY = new NamespacedKey(SlimefunVoid.getInstance(), "void_research_bench");

    public static final NamespacedKey RESEARCH_START = new NamespacedKey(SlimefunVoid.getInstance(), "research_start");

    private CategorySelectMenu researchMenu = new CategorySelectMenu();

    private ChestMenu menu = new ChestMenu(SlimefunVoid.getInstance(), "&5Void Research Bench");

    public VoidResearchBench(@Nonnull Category category) {
        super(category, Items.VOID_RESEARCH_BENCH, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{RecipeItems.ENDER_EYE, RecipeItems.BOOK, RecipeItems.ENDER_EYE,
                RecipeItems.OBSIDIAN, RecipeItems.END_CRYSTAL, RecipeItems.OBSIDIAN,
                RecipeItems.ENDER_EYE, RecipeItems.ENCHANTING_TABLE, RecipeItems.ENDER_EYE}, Items.VOID_RESEARCH_BENCH);

        initMenu();
    }

    private void initMenu() {
        for(int i = 0; i < 27; i++) {
            if(i > 9 && i < 17 && i%2==1)
                continue;
            menu.addItem(i, GREY_GLASS, (player, slot, item, cursor, action) -> false);
        }

        menu.addItem(11, new CustomItem(Material.PAPER, ChatColor.GREEN + "Select Void Research"), (player, slot, item, cursor, action) -> {
            researchMenu.open(player);
            return false;
        });

        menu.addItem(15, new CustomItem(Material.BARRIER, ChatColor.GREEN + "Put Researches here"), (player, slot, item, cursor, action) -> {
            if(addResearchNotes(player, cursor))
                player.setItemOnCursor(null);
            return false;
        });

        menu.addMenuOpeningHandler(this::onMenuOpen);
    }

    private void onMenuOpen(@Nonnull Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        ItemStack researchItem;
        if(data.has(RESEARCH_KEY, PersistentDataType.STRING)) {
            String researchName = data.get(RESEARCH_KEY, PersistentDataType.STRING);
            Objects.requireNonNull(researchName);
            VoidResearches research = VoidResearches.valueOf(getEnumName(researchName));
            Long start = data.get(RESEARCH_START, PersistentDataType.LONG);
            Objects.requireNonNull(start);
            long time = getRemainingTime(start, research.getResearch());
            IResearchCategory category = VoidCategories.values()[research.getCategoryID()-1].getCategory();
            if(time < 0) {
                VoidResearchHelper.addResearch(player, category);
                data.remove(RESEARCH_START);
                data.remove(RESEARCH_KEY);
                researchItem = new CustomItem(Material.PAPER, "&aCurrent Research: None");
            } else {
                researchItem = new CustomItem(Material.PAPER, "&aCurrent Research: " + category.getColor() + researchName + "&r (" + time + "s)");
            }
        } else {
            researchItem = new CustomItem(Material.PAPER, "&aCurrent Research: None");
        }
        menu.replaceExistingItem(13, researchItem);
    }

    /**
     * Checks if the cursor item is a research note, and if it is correct adds it.
     * @param player The player
     * @param cursor Item on the cursor
     * @return whether the research was added or not
     */
    private boolean addResearchNotes(Player player, ItemStack cursor) {
        if(cursor.getType() == Material.PAPER) {
            ItemMeta meta = cursor.getItemMeta();
            Objects.requireNonNull(meta);
            if(meta.getDisplayName().equals(ChatColor.DARK_PURPLE + "Void Research") && meta.getLore() != null && !meta.getLore().isEmpty()) {
                VoidResearches research = VoidResearches.valueOf(getEnumName(meta.getLore().get(0)));
                IResearchCategory category = VoidCategories.values()[research.getCategoryID()-1].getCategory();
                if(VoidResearchHelper.getResearched(player, category) == category.getResearches().indexOf(research.getResearch())) {
                    VoidResearchHelper.addResearch(player, category);
                    player.sendMessage(ChatColor.GREEN + "Researched " + category.getColor() + research.getResearch().getName() + ChatColor.GREEN + " with notes.");
                    return true;
                }
            }
        }
        return false;
    }

    private String getEnumName(String name) {
        return ChatColor.stripColor(name.toUpperCase().replace(" ", "_"));
    }

    public static long getRemainingTime(long time, IVoidResearch research) {
        return research.getResearchTime()-(System.currentTimeMillis()-time)/1000;
    }

    @Override
    public BlockUseHandler getItemHandler() {
        return event -> {
            menu.open(event.getPlayer());
            event.setUseBlock(Event.Result.DENY);
        };
    }
}
