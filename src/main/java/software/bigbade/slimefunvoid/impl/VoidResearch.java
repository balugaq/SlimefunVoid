package software.bigbade.slimefunvoid.impl;

import me.mrCookieSlime.Slimefun.Objects.Research;
import org.bukkit.ChatColor;
import software.bigbade.slimefunvoid.api.IVoidResearch;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class VoidResearch implements IVoidResearch {
    private String name;
    private Research unlock;
    private List<String> lore;
    private long researchTime;

    public VoidResearch(String name, long researchTime, Research unlock, String... lore) {
        this.name = ChatColor.translateAlternateColorCodes('&', name);
        this.unlock = unlock;
        this.researchTime = researchTime;
        this.lore = Arrays.asList(lore);
        this.lore.forEach(line -> ChatColor.translateAlternateColorCodes('&', line));
    }

    public VoidResearch(String name, long researchTime, String... lore) {
        this.name = ChatColor.translateAlternateColorCodes('&', name);
        this.lore = Arrays.asList(lore);
        this.researchTime = researchTime;
        this.lore.forEach(line -> ChatColor.translateAlternateColorCodes('&', line));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getLore() {
        return lore;
    }

    @Nullable
    @Override
    public Research getUnlock() {
        return unlock;
    }

    @Override
    public void updateStrings(ChatColor color) {
        for(int i = 0; i < lore.size(); i++)
            lore.set(i, color + lore.get(i));
        name = color + name;
    }

    @Override
    public long getResearchTime() {
        return researchTime;
    }
}
