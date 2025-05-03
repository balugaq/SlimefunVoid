package software.bigbade.slimefunvoid.menus.research;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.api.research.IResearchCategory;
import software.bigbade.slimefunvoid.api.research.VoidCategories;
import software.bigbade.slimefunvoid.utils.VoidResearchHelper;

import java.util.Objects;

public class CategorySelectMenu extends ChestMenu {
    private ResearchSelectMenu selectMenu = new ResearchSelectMenu();

    public CategorySelectMenu() {
        super("&5Select a Research");
        init();
    }

    public void init() {
        addItem(8, new ItemStack(Material.AIR));

        for (int i = 0; i < VoidCategories.values().length; i++) {
            addMenuClickHandler(i, (player, slot, item, action) -> {
                Objects.requireNonNull(item.getItemMeta());
                if (item.getItemMeta().getDisplayName().startsWith(ChatColor.MAGIC.toString(), 2))
                    return false;
                selectMenu.open(player, VoidCategories.values()[slot].getCategory());
                return false;
            });
        }

        addMenuOpeningHandler(player -> {
            int research = 1;
            int max = 1;
            for (int i = 0; i < VoidCategories.values().length; i++) {
                IResearchCategory category = VoidCategories.values()[i].getCategory();
                if (research < max) {
                    replaceExistingItem(i, new CustomItemStack(Material.PAPER, category.getColor().toString() + ChatColor.MAGIC + ChatColor.stripColor(category.getName() + " (0/" + category.getResearches().size() + ")")));
                    continue;
                }
                research = VoidResearchHelper.getResearched(player, category);
                max = category.getResearches().size();
                replaceExistingItem(i, new CustomItemStack(Material.PAPER, category.getColor() + category.getName() + " (" + research + "/" + category.getResearches().size() + ")"));
            }
        });
    }
}
