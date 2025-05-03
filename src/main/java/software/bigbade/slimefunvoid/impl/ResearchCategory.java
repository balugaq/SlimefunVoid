package software.bigbade.slimefunvoid.impl;

import lombok.Getter;
import org.bukkit.ChatColor;
import software.bigbade.slimefunvoid.api.research.IResearchCategory;
import software.bigbade.slimefunvoid.api.research.IVoidResearch;
import software.bigbade.slimefunvoid.api.research.VoidResearches;

import java.util.ArrayList;
import java.util.List;

public class ResearchCategory implements IResearchCategory {
    private static int nextID = 1;
    @Getter
    private final String name;
    @Getter
    private final ChatColor color;
    @Getter
    private final List<IVoidResearch> researches = new ArrayList<>();
    @Getter
    private final int id;

    public ResearchCategory(String name, ChatColor color, VoidResearches... researches) {
        this.name = ChatColor.translateAlternateColorCodes('&', name);
        this.color = color;
        this.id = nextID;
        nextID++;
        for (VoidResearches research : researches) {
            this.researches.add(research.getResearch());
        }
        this.researches.forEach(research -> research.updateStrings(color));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ResearchCategory)
            return ((IResearchCategory) obj).getId() == id;
        return false;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
