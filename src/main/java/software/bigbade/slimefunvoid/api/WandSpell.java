package software.bigbade.slimefunvoid.api;

import io.github.thebusybiscuit.slimefun4.api.researches.Research;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface WandSpell {
    boolean onCast(Player player, ItemStack wand);

    void onBackfire(Player player, ItemStack wand);

    void onStop(Player player, ItemStack wand);

    Research getResearch();

    Elements getElement();

    ItemStack getIcon();

    String getName();

    long getCooldown(ItemStack item);
}
