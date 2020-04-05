package software.bigbade.slimefunvoid.menus;

import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.api.IResearchCategory;
import software.bigbade.slimefunvoid.utils.VoidResearchHelper;
import software.bigbade.slimefunvoid.api.VoidCategories;

public class CategorySelectMenu extends ChestMenu {
    public CategorySelectMenu() {
        super(SlimefunVoid.getInstance(), "&5Select a Research");
        init();
    }

    private ResearchSelectMenu selectMenu = new ResearchSelectMenu();

    public void init() {
        setSize(9);

        for(int i = 0; i < VoidCategories.values().length; i++) {
            addMenuClickHandler(i, (player, slot, item, cursor, action) -> {
                selectMenu.open(player, VoidCategories.values()[slot].getCategory());
                return false;
            });
        }

        addMenuOpeningHandler(player -> {
            int research = 1;
            int max = 1;
            for(int i = 0; i < VoidCategories.values().length; i++) {
                IResearchCategory category = VoidCategories.values()[i].getCategory();
                if(research < max) {
                    replaceExistingItem(i, new CustomItem(Material.PAPER, category.getColor().toString() + ChatColor.MAGIC + ChatColor.stripColor(category.getName() + " (0/" + category.getResearches().size() + ")")));
                    continue;
                }
                research = VoidResearchHelper.getResearched(player, category);
                max = category.getResearches().size()-1;
                replaceExistingItem(i, new CustomItem(Material.PAPER, category.getColor() + category.getName() + " (" + research + "/" + category.getResearches().size() + ")"));
            }
        });
    }
}
