package software.bigbade.slimefunvoid.api.research;

import io.github.thebusybiscuit.slimefun4.api.researches.Research;
import org.bukkit.ChatColor;

import javax.annotation.Nullable;
import java.util.List;

public interface IVoidResearch {
    String getName();

    List<String> getLore();

    @Nullable
    Research getUnlock();

    void updateStrings(ChatColor chatColor);

    long getResearchTime();
}
