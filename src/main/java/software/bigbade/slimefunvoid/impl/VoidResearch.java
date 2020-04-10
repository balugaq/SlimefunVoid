package software.bigbade.slimefunvoid.impl;

import lombok.Getter;
import me.mrCookieSlime.Slimefun.Objects.Research;
import org.bukkit.ChatColor;
import software.bigbade.slimefunvoid.api.research.IVoidResearch;

import java.util.Arrays;
import java.util.List;

public class VoidResearch implements IVoidResearch {
    @Getter
    private String name;
    @Getter
    private final Research unlock;
    @Getter
    private final List<String> lore;
    @Getter
    private final long researchTime;

    public VoidResearch(String name, long researchTime, Research unlock, String... lore) {
        this.name = ChatColor.translateAlternateColorCodes('&', name);
        this.unlock = unlock;
        this.researchTime = researchTime;
        this.lore = Arrays.asList(lore);
        this.lore.forEach(line -> ChatColor.translateAlternateColorCodes('&', line));
    }

    public VoidResearch(String name, long researchTime, String... lore) {
        this.name = ChatColor.translateAlternateColorCodes('&', name);
        this.unlock = null;
        this.lore = Arrays.asList(lore);
        this.researchTime = researchTime;
        this.lore.forEach(line -> ChatColor.translateAlternateColorCodes('&', line));
    }

    @Override
    public void updateStrings(ChatColor color) {
        for (int i = 0; i < lore.size(); i++)
            lore.set(i, color + lore.get(i));
        name = color + name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IVoidResearch)
            return ((IVoidResearch) obj).getName().equals(name);
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
