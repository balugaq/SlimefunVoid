package software.bigbade.slimefunvoid.listeners;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.util.Vector;
import software.bigbade.slimefunvoid.PlacedVoidQuarry;
import software.bigbade.slimefunvoid.blocks.VoidQuarry;

import java.util.Objects;

@RequiredArgsConstructor
public class QuarryBreakListener implements Listener {
    private final VoidQuarry quarryItem;

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() != Material.OAK_FENCE)
            return;
        for (PlacedVoidQuarry quarry : quarryItem.getQuarries().keySet()) {
            if (!checkQuarryLocation(quarry, event.getBlock().getLocation())) {
                continue;
            }
            Bukkit.getScheduler().cancelTask(quarryItem.getQuarries().get(quarry));
            quarryItem.getQuarries().remove(quarry);
            return;
        }
    }

    private boolean checkQuarryLocation(PlacedVoidQuarry quarry, Location block) {
        Vector offset = block.toVector().subtract(Objects.requireNonNull(quarry.getBottomCorner()).toVector());
        if (offset.getX() < 0 || offset.getX() > quarry.getSize().getX() || offset.getY() < 0 ||offset.getY() > quarry.getSize().getY() || offset.getZ() < 0 || offset.getZ() > quarry.getSize().getZ())
            return false;
        //Not a side, but maybe a corner
        if (offset.getY() != 0 && offset.getY() != quarry.getSize().getY()) {
            return checkCorner(offset, quarry.getSize());
        }
        //Just check if it is a side then
        return checkSide(offset, quarry.getSize());
    }

    private boolean checkCorner(Vector coordinates, Vector size) {
        return isOnSide(coordinates.getX(), size.getX()) || isOnSide(coordinates.getZ(), size.getZ());
    }

    private boolean checkSide(Vector coordinates, Vector size) {
        return isOnSide(coordinates.getX(), size.getX()) || isOnSide(coordinates.getZ(), size.getZ());
    }

    private boolean isOnSide(double x, double maxX) {
        return x == 0 || x == maxX;
    }
}
