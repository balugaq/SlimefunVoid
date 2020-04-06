package software.bigbade.slimefunvoid.impl;

import lombok.Getter;
import org.bukkit.ChatColor;
import software.bigbade.slimefunvoid.api.IResearchCategory;
import software.bigbade.slimefunvoid.api.IVoidResearch;
import software.bigbade.slimefunvoid.api.VoidResearches;

import java.util.ArrayList;
import java.util.List;

public class ResourceCategory implements IResearchCategory {
    @Getter
    private final String name;
    @Getter
    private final ChatColor color;
    @Getter
    private final List<IVoidResearch> researches = new ArrayList<>();
    @Getter
    private final int id;

    public ResourceCategory(String name, ChatColor color, int id, VoidResearches... researches) {
        this.name = ChatColor.translateAlternateColorCodes('&', name);
        this.color = color;
        this.id = id;
        for (VoidResearches research : researches) {
            this.researches.add(research.getResearch());
        }
        this.researches.forEach(research -> research.updateStrings(color));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ResourceCategory)
            return ((IResearchCategory) obj).getId() == id;
        return false;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
