package software.bigbade.slimefunvoid.spells.electricity;

import org.bukkit.ChatColor;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import software.bigbade.slimefunvoid.api.Elements;
import software.bigbade.slimefunvoid.api.research.Researches;
import software.bigbade.slimefunvoid.impl.BasicSpell;
import software.bigbade.slimefunvoid.items.Items;
import software.bigbade.slimefunvoid.utils.SelfCancelableTask;

import java.util.Objects;

public class LightningSpell extends BasicSpell {
    public LightningSpell() {
        super(Researches.LIGHTNING_SPELL.getResearch(), Elements.ELECTRIC, Items.LIGHTNING_SPELL, 10);
    }

    @Override
    public boolean onCast(Player player, ItemStack wand) {
        Block target = player.getTargetBlockExact((int) getMultipliedDamage(wand, 10, Elements.ELECTRIC), FluidCollisionMode.NEVER);
        if (target != null) {
            strikeLightning(target.getLocation(), (int) getMultipliedDamage(wand, 1, Elements.ELECTRIC));
            return true;
        }
        player.sendMessage(ChatColor.RED + "That target is out of range! You must target a block");
        return false;
    }

    @Override
    public void onBackfire(Player player, ItemStack wand) {
        strikeLightning(player.getLocation(), (int) getBackfireDamage(wand, 1, Elements.ELECTRIC));
    }

    private void strikeLightning(Location location, final int amount) {
        Objects.requireNonNull(location.getWorld());
        SelfCancelableTask task = new SelfCancelableTask();
        task.setRunnable(() -> {
            if(amount == task.getLoops()) {
                task.cancel();
                return;
            }
            location.getWorld().strikeLightning(location);
        });
        task.start(1, 1);
    }
}
