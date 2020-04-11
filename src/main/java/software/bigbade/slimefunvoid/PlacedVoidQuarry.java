package software.bigbade.slimefunvoid;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

@RequiredArgsConstructor
public class PlacedVoidQuarry {
    @Getter
    @Setter
    @Nullable
    private Location bottomCorner;

    @Getter
    @Nonnull
    private final Vector size = new Vector(0, 0, 0);

    @Getter
    @Setter
    @Nullable
    private Location currentBlock;

    @Getter
    @Setter
    @Nullable
    private Location chest;

    @Getter
    private final Location quarry;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof PlacedVoidQuarry) {
            PlacedVoidQuarry quarry = (PlacedVoidQuarry) obj;
            return quarry.getSize().equals(size) && Objects.requireNonNull(quarry.getBottomCorner()).equals(getBottomCorner());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.requireNonNull(bottomCorner).hashCode();
    }
}
