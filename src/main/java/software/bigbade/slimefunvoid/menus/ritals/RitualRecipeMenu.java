package software.bigbade.slimefunvoid.menus.ritals;

import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ChestMenu;
import org.bukkit.inventory.ItemStack;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.api.research.VoidRecipes;

public class RitualRecipeMenu extends ChestMenu {
    private IndividualRitualMenu ritualMenu = new IndividualRitualMenu();

    public RitualRecipeMenu() {
        super(SlimefunVoid.getInstance(), "&5Void Rituals");

        setSize(27);

        initMenu();
    }

    private void initMenu() {
        setPlayerInventoryClickable(false);
        int i = 0;
        for(ItemStack output : VoidRecipes.getRecipes().keySet()) {
            addItem(i, output, (player, slot, item, cursor, action) -> {
                ritualMenu.open(player, output);
                return false;
            });
            i++;
        }
    }
}
