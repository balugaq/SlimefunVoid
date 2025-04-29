package software.bigbade.slimefunvoid.menus.ritals;

import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.api.research.VoidRecipes;
import software.bigbade.slimefunvoid.menus.research.ResearchBenchMenu;

public class IndividualRitualMenu extends ChestMenu {
    private static final int[] GLASS_SLOTS = new int[]{1, 2, 3, 9, 11, 18, 19, 20, 13, 7, 8, 9, 15, 17, 24, 25, 26};

    public IndividualRitualMenu() {
        super("&5Void Rituals");
        for (int slot : GLASS_SLOTS) {
            addItem(slot, ResearchBenchMenu.GREY_GLASS, (player, clickSlot, item, action) -> false);
        }
        setPlayerInventoryClickable(false);
    }

    public void open(Player player, ItemStack output) {
        initMenu(output);
        super.open(player);
    }

    private void initMenu(ItemStack output) {
        ItemStack[] required = VoidRecipes.getRecipes().get(output);
        addItem(10, required[5], (player, slot, item, action) -> false);
        for (int i = 0; i < 9; i++) {
            if (i == 5) continue;
            int slot;
            if (i < 4)
                slot = 3 + i;
            else if (i < 7)
                slot = 9 + i;
            else
                slot = 14 + i;
            addItem(slot, required[i], (player, clickSlot, item, action) -> false);
        }
        addItem(16, output, (player, clickSlot, item, action) -> false);
    }
}
