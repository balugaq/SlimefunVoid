package software.bigbade.slimefunvoid.impl;

import org.bukkit.ChatColor;
import software.bigbade.slimefunvoid.api.IResearchCategory;
import software.bigbade.slimefunvoid.api.IVoidResearch;
import software.bigbade.slimefunvoid.api.VoidResearches;

import java.util.ArrayList;
import java.util.List;

public class ResourceCategory implements IResearchCategory {
    private final String name;
    private final ChatColor color;
    private final List<IVoidResearch> researches = new ArrayList<>();
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
    public List<IVoidResearch> getResearches() {
        return researches;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ChatColor getColor() {
        return color;
    }

    @Override
    public int getId() {
        return id;
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
