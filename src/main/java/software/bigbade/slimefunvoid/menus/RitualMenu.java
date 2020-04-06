package software.bigbade.slimefunvoid.menus;

import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ChestMenu;
import org.bukkit.entity.Player;
import software.bigbade.slimefunvoid.SlimefunVoid;

import javax.annotation.Nonnull;

public class RitualMenu extends ChestMenu {
    public RitualMenu() {
        super(SlimefunVoid.getInstance(), "&5Insert a Wand");

        initMenu();
    }

    private void initMenu() {
        addMenuOpeningHandler(this::onMenuOpen);
    }

    private void onMenuOpen(@Nonnull Player player) {

    }
}
