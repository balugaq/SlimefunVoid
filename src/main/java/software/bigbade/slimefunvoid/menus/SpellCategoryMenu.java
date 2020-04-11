package software.bigbade.slimefunvoid.menus;

import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ChestMenu;
import org.bukkit.inventory.meta.ItemMeta;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.api.Elements;
import software.bigbade.slimefunvoid.api.Spells;
import software.bigbade.slimefunvoid.api.WandSpell;
import software.bigbade.slimefunvoid.items.wands.WandItem;
import software.bigbade.slimefunvoid.menus.research.ResearchBenchMenu;

import java.util.Objects;

public class SpellCategoryMenu extends ChestMenu {

    public SpellCategoryMenu() {
        super(SlimefunVoid.getInstance(), "&aSelect the spell element");
        for (int i = 0; i < 54; i++) {
            if (i < 45 && i % 9 != 8 && i % 9 != 0)
                continue;
            addItem(i, ResearchBenchMenu.GREY_GLASS, (player, slot, item, cursor, action) -> false);
        }
        for (int i = 0; i < Elements.values().length; i++) {
            addItem(i+1, Elements.values()[i].getIcon(), (player, slot, item, cursor, action) -> false);
        }
        for (Spells spells : Spells.values()) {
            WandSpell spell = spells.getSpell();
            int slot = spell.getElement().ordinal()+10;
            while(getItemInSlot(slot) != null) {
                slot += 9;
            }
            if(slot > 54)
                continue;
            addItem(slot, spell.getIcon(), (player, clickedSlot, item, cursor, action) -> {
                ItemMeta wandMeta = player.getInventory().getItemInMainHand().getItemMeta();
                Objects.requireNonNull(wandMeta);
                wandMeta.setDisplayName(WandItem.removeSpellFromName(wandMeta.getDisplayName()) + spell.getElement().getColor() + " (" + spell.getName() + ")");
                player.getInventory().getItemInMainHand().setItemMeta(wandMeta);
                player.closeInventory();
                return false;
            });
        }
        setSize(54);
    }
}