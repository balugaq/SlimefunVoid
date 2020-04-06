package software.bigbade.slimefunvoid.menus;

import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.api.IResearchCategory;
import software.bigbade.slimefunvoid.api.IVoidResearch;
import software.bigbade.slimefunvoid.blocks.VoidResearchBench;
import software.bigbade.slimefunvoid.utils.VoidResearchHelper;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

public class ResearchSelectMenu extends ChestMenu {
    public ResearchSelectMenu() {
        super(SlimefunVoid.getInstance(), "&5Select a Research");
    }

    private void init(IResearchCategory category) {
        setSize(getSize(category.getResearches().size()));

        for(int i = 0; i < category.getResearches().size(); i++) {
            addMenuClickHandler(i, (player, slot, item, cursor, action) -> {
                PersistentDataContainer data = player.getPersistentDataContainer();
                if(!data.has(VoidResearchBench.RESEARCH_KEY, PersistentDataType.STRING)) {
                    addResearch(slot, category, player);
                } else {
                    player.sendMessage(ChatColor.RED + "You are already researching " + data.get(VoidResearchBench.RESEARCH_KEY, PersistentDataType.STRING));
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
                List<String> magicLore = research.getLore().stream().map(line -> ChatColor.MAGIC + category.getColor().toString() + ChatColor.stripColor(line)).collect(Collectors.toList());
                replaceExistingItem(i, new CustomItem(Material.PAPER, category.getColor().toString() + ChatColor.MAGIC + ChatColor.stripColor(research.getName()), magicLore));
                continue;
            }
            replaceExistingItem(i, new CustomItem(Material.PAPER, category.getColor() + research.getName(), research.getLore()));
        }
    }

    private void addResearch(int slot, IResearchCategory category, Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        IVoidResearch research = category.getResearches().get(slot);
        int found = VoidResearchHelper.getResearched(player, category);
        if (found == slot) {
            data.set(VoidResearchBench.RESEARCH_KEY, PersistentDataType.STRING, research.getName());
            data.set(VoidResearchBench.RESEARCH_START, PersistentDataType.LONG, System.currentTimeMillis());
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
