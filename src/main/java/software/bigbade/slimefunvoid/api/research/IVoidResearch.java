package software.bigbade.slimefunvoid.api.research;

import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.ChatColor;

import io.github.thebusybiscuit.slimefun4.core.researching.Research;

public interface IVoidResearch {
    String getName();

    List<String> getLore();

    @Nullable
    Research getUnlock();

    void updateStrings(ChatColor chatColor);

    long getResearchTime();
}
