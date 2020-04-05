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
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.api.IResearchCategory;
import software.bigbade.slimefunvoid.api.IVoidResearch;
import software.bigbade.slimefunvoid.items.Items;
import software.bigbade.slimefunvoid.menus.CategorySelectMenu;
import software.bigbade.slimefunvoid.utils.RecipeItems;
import software.bigbade.slimefunvoid.utils.VoidCategories;
import software.bigbade.slimefunvoid.utils.VoidResearchHelper;
import software.bigbade.slimefunvoid.utils.VoidResearches;

public final class VoidResearchBench extends SimpleSlimefunItem<BlockUseHandler> {
    private static final ItemStack GREY_GLASS = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);

    public static final NamespacedKey RESEARCH_KEY = new NamespacedKey(SlimefunVoid.getInstance(), "void_research_bench");

    public static final NamespacedKey RESEARCH_START = new NamespacedKey(SlimefunVoid.getInstance(), "research_start");

    private CategorySelectMenu researchMenu = new CategorySelectMenu();

    private ChestMenu menu;

    public VoidResearchBench(Category category) {
        super(category, Items.VOID_RESEARCH_BENCH, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{RecipeItems.ENDER_EYE, RecipeItems.BOOK, RecipeItems.ENDER_EYE,
                RecipeItems.OBSIDIAN, RecipeItems.END_CRYSTAL, RecipeItems.OBSIDIAN,
                RecipeItems.ENDER_EYE, RecipeItems.ENCHANTING_TABLE, RecipeItems.ENDER_EYE}, Items.VOID_RESEARCH_BENCH);

        menu = new ChestMenu(SlimefunVoid.getInstance(), "&5Void Research Bench");
        initMenu();
    }

    @SuppressWarnings("ConstantConditions")
    private void initMenu() {
        for(int i = 0; i < 27; i++) {
            if(i > 9 && i < 17 && i%2==1)
                continue;
            menu.addItem(i, GREY_GLASS);
        }

        menu.addItem(11, new CustomItem(Material.PAPER, ChatColor.GREEN + "Select Void Research"), (player, slot, item, cursor, action) -> {
            researchMenu.open(player);
            return false;
        });

        menu.addMenuOpeningHandler(player -> {
            PersistentDataContainer data = player.getPersistentDataContainer();
            ItemStack researchItem;
            if(data.has(RESEARCH_KEY, PersistentDataType.STRING)) {
                String researchName = ChatColor.stripColor(data.get(RESEARCH_KEY, PersistentDataType.STRING).replace(" ", "_").toUpperCase());
                VoidResearches research = VoidResearches.valueOf(researchName);
                long time = getRemainingTime(data.get(RESEARCH_START, PersistentDataType.LONG), research.getResearch());
                IResearchCategory category = VoidCategories.values()[research.getCategoryID()-1].getCategory();
                if(time < 0) {
                    VoidResearchHelper.addResearch(player, category);
                    data.remove(RESEARCH_START);
                    data.remove(RESEARCH_KEY);
                    researchItem = new CustomItem(Material.PAPER, "&aCurrent Void Research: None");
                } else {
                    researchItem = new CustomItem(Material.PAPER, "&aCurrent Void Research: " + category.getColor() + researchName + "&r (" + time + "s)");
                }
            } else {
                researchItem = new CustomItem(Material.PAPER, "&aCurrent Void Research: None");
            }
            menu.replaceExistingItem(13, researchItem);
        });
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
