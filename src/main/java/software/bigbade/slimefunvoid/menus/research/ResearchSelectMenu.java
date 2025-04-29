package software.bigbade.slimefunvoid.menus.research;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.api.research.IResearchCategory;
import software.bigbade.slimefunvoid.api.research.IVoidResearch;
import software.bigbade.slimefunvoid.utils.VoidResearchHelper;

import javax.annotation.Nonnull;

public class ResearchSelectMenu extends ChestMenu {
    public ResearchSelectMenu() {
        super("&5Select a Research");
    }

    private void init(IResearchCategory category) {
        addItem(Math.min(53, getSize(category.getResearches().size()) - 1), new ItemStack(Material.AIR));

        for (int i = 0; i < category.getResearches().size(); i++) {
            addMenuClickHandler(i, (player, slot, item, action) -> {
                PersistentDataContainer data = player.getPersistentDataContainer();
                if (!data.has(ResearchBenchMenu.RESEARCH_KEY, PersistentDataType.STRING)) {
                    addResearch(slot, category, player);
                } else {
                    player.sendMessage(ChatColor.RED + "You are already researching " + data.get(ResearchBenchMenu.RESEARCH_KEY, PersistentDataType.STRING));
                }
                return false;
            });
        }

        addMenuOpeningHandler(player -> onMenuOpen(player, category));
    }

    private void onMenuOpen(@Nonnull Player player, IResearchCategory category) {
        int researched = VoidResearchHelper.getResearched(player, category);
        for (int i = 0; i < category.getResearches().size(); i++) {
            IVoidResearch research = category.getResearches().get(i);
            if (researched < i) {
                replaceExistingItem(i, new CustomItemStack(Material.PAPER, ChatColor.RED + ChatColor.stripColor(research.getName())));
                continue;
            }
            replaceExistingItem(i, new CustomItemStack(Material.PAPER, category.getColor() + research.getName(), research.getLore()));
        }
    }

    private void addResearch(int slot, IResearchCategory category, Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        IVoidResearch research = category.getResearches().get(slot);
        int found = VoidResearchHelper.getResearched(player, category);
        if (found == slot) {
            data.set(ResearchBenchMenu.RESEARCH_KEY, PersistentDataType.STRING, research.getName());
            data.set(ResearchBenchMenu.RESEARCH_START, PersistentDataType.LONG, System.currentTimeMillis());
            player.sendMessage(ChatColor.GREEN + "Researching " + research.getName());
            player.closeInventory();
        } else if (found > slot) {
            player.sendMessage(ChatColor.RED + "You have already researched " + research.getName());
        } else {
            player.sendMessage(ChatColor.RED + "You aren't practiced enough to research " + research.getName());
        }
    }

    /**
     * Uses the properties of integers to find the size needed for an inventory
     *
     * @param researches number of researches
     * @return number of rows needed
     */
    private int getSize(int researches) {
        //Adds the number by 8 (so it will always round up) then divides it by 9 (to round it)
        return (researches + 8) / 9;
    }

    public void open(Player player, IResearchCategory category) {
        init(category);
        super.open(player);
    }
}
