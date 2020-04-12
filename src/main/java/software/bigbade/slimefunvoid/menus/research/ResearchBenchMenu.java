package software.bigbade.slimefunvoid.menus.research;

import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.api.research.IResearchCategory;
import software.bigbade.slimefunvoid.api.research.IVoidResearch;
import software.bigbade.slimefunvoid.api.research.VoidCategories;
import software.bigbade.slimefunvoid.api.research.VoidResearches;
import software.bigbade.slimefunvoid.utils.VoidResearchHelper;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ResearchBenchMenu extends ChestMenu {
    public static final ItemStack GREY_GLASS = new CustomItem(Material.GRAY_STAINED_GLASS_PANE, " ");
    public static final NamespacedKey RESEARCH_KEY = new NamespacedKey(SlimefunVoid.getInstance(), "void_research_bench");

    public static final NamespacedKey RESEARCH_START = new NamespacedKey(SlimefunVoid.getInstance(), "research_start");

    private final CategorySelectMenu researchMenu = new CategorySelectMenu();

    private static final CustomItem NO_RESEARCH = new CustomItem(Material.PAPER, "&aCurrent Research:", ChatColor.WHITE + "None");

    private final Map<UUID, Integer> runningTasks = new HashMap<>();

    public ResearchBenchMenu() {
        super(SlimefunVoid.getInstance(), "&5Void Research Bench");
        initMenu();
    }

    private void initMenu() {
        for (int i = 0; i < 27; i++) {
            if (i > 9 && i < 17 && i % 2 == 1)
                continue;
            addItem(i, GREY_GLASS, (player, slot, item, cursor, action) -> false);
        }

        addItem(11, new CustomItem(Material.PAPER, ChatColor.GREEN + "Select Void Research"), (player, slot, item, cursor, action) -> {
            researchMenu.open(player);
            return false;
        });

        addItem(15, new CustomItem(Material.BARRIER, ChatColor.GREEN + "Put Researches here"), (player, slot, item, cursor, action) -> {
            if (addResearchNotes(player, cursor))
                player.setItemOnCursor(null);
            return false;
        });

        addMenuOpeningHandler(this::onMenuOpen);

        addMenuCloseHandler(this::onMenuClose);
    }

    private void onMenuClose(@Nonnull Player player) {
        if (runningTasks.containsKey(player.getUniqueId())) {
            Bukkit.getScheduler().cancelTask(runningTasks.get(player.getUniqueId()));
        }
    }

    private void onMenuOpen(@Nonnull Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        ItemStack researchItem;
        if (data.has(RESEARCH_KEY, PersistentDataType.STRING)) {
            String researchName = data.get(RESEARCH_KEY, PersistentDataType.STRING);
            Objects.requireNonNull(researchName);
            VoidResearches research = VoidResearches.valueOf(getEnumName(researchName));
            Long start = data.get(RESEARCH_START, PersistentDataType.LONG);
            Objects.requireNonNull(start);
            AtomicLong timeRemaining = new AtomicLong(getRemainingTime(start, research.getResearch()));
            IResearchCategory category = VoidCategories.values()[research.getCategoryID() - 1].getCategory();
            if (timeRemaining.get() < 0) {
                addResearch(player, research, false);
                researchItem = NO_RESEARCH;
            } else {
                researchItem = new CustomItem(Material.PAPER, "&aCurrent Research:", category.getColor() + researchName, ChatColor.WHITE.toString() + timeRemaining + "s");
                runningTasks.put(player.getUniqueId(), Bukkit.getScheduler().scheduleSyncRepeatingTask(SlimefunVoid.getInstance(), () -> {
                    ItemStack running = getItemInSlot(13);
                    ItemMeta meta = running.getItemMeta();
                    Objects.requireNonNull(meta);
                    List<String> lore = meta.getLore();
                    Objects.requireNonNull(lore);
                    long left = getRemainingTime(start, research.getResearch());
                    if (left < 0) {
                        addResearch(player, research, false);
                        replaceExistingItem(13, NO_RESEARCH);
                        Bukkit.getScheduler().cancelTask(runningTasks.get(player.getUniqueId()));
                    }
                    timeRemaining.set(left);
                    lore.set(1, ChatColor.WHITE.toString() + timeRemaining.get() + "s");
                    meta.setLore(lore);
                    running.setItemMeta(meta);
                }, 20L, 20L));
            }
        } else {
            researchItem = NO_RESEARCH;
        }
        replaceExistingItem(13, researchItem);
    }

    private void addResearch(Player player, VoidResearches research, boolean unlockedByNotes) {
        VoidResearchHelper.addResearch(player, research);
        if (!unlockedByNotes) {
            PersistentDataContainer data = player.getPersistentDataContainer();
            data.remove(RESEARCH_START);
            data.remove(RESEARCH_KEY);
        }
        if (research.getResearch().getUnlock() != null) {
            research.getResearch().getUnlock().unlock(player, true);
        }
        player.sendMessage(ChatColor.GREEN + "You unlocked " + research.getResearch().getName());
    }

    /**
     * Checks if the cursor item is a research note, and if it is correct adds it.
     *
     * @param player The player
     * @param cursor Item on the cursor
     * @return whether the research was added or not
     */
    private boolean addResearchNotes(Player player, ItemStack cursor) {
        if (cursor.getType() != Material.PAPER)
            return false;
        ItemMeta meta = cursor.getItemMeta();
        Objects.requireNonNull(meta);
        if (!meta.getDisplayName().equals(ChatColor.DARK_PURPLE + "Void Research") || meta.getLore() == null || meta.getLore().isEmpty())
            return false;
        VoidResearches research = VoidResearches.valueOf(getEnumName(meta.getLore().get(0)));
        IResearchCategory category = VoidCategories.values()[research.getCategoryID() - 1].getCategory();
        long researched = VoidResearchHelper.getResearched(player, category);
        long found = category.getResearches().indexOf(research.getResearch());
        if (category.getResearches().contains(research.getResearch())) {
            if (researched == found) {
                addResearch(player, research, true);
                player.sendMessage(ChatColor.GREEN + "Researched " + category.getColor() + research.getResearch().getName() + ChatColor.GREEN + " with notes.");
                return true;
            } else if (researched > found) {
                player.sendMessage(ChatColor.RED + "You have already researched " + category.getColor() + research.getResearch().getName());
            } else {
                player.sendMessage(ChatColor.RED + "You do not understand the notes.");
            }
        } else {
            player.sendMessage(ChatColor.RED + "You do not understand the notes.");
        }
        return false;
    }

    public static String getEnumName(String name) {
        return ChatColor.stripColor(name.toUpperCase().replace(" ", "_"));
    }

    public static long getRemainingTime(long time, IVoidResearch research) {
        return research.getResearchTime() - (System.currentTimeMillis() - time) / 1000;
    }
}
