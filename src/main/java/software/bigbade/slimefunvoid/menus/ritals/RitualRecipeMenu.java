package software.bigbade.slimefunvoid.menus.ritals;

import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import software.bigbade.slimefunvoid.api.research.VoidRecipes;

public class RitualRecipeMenu extends ChestMenu {
    private IndividualRitualMenu ritualMenu = new IndividualRitualMenu();

    public RitualRecipeMenu() {
        super("&5Void Rituals");

        addItem(26, new ItemStack(Material.AIR));

        initMenu();
    }

    private void initMenu() {
        setPlayerInventoryClickable(false);
        int i = 0;
        for (ItemStack output : VoidRecipes.getRecipes().keySet()) {
            addItem(i, output, (player, slot, item, action) -> {
                ritualMenu.open(player, output);
                return false;
            });
            i++;
        }
    }
}
