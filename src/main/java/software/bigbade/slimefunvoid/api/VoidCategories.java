package software.bigbade.slimefunvoid.api;

import org.bukkit.ChatColor;
import software.bigbade.slimefunvoid.impl.ResearchCategory;

public enum VoidCategories {
    THE_BEGINNING(new ResearchCategory("The Beginning", ChatColor.GREEN, VoidResearches.THE_DISCOVERY, VoidResearches.HARNESSING_THE_VOID, VoidResearches.ENTERING_THE_VOID)),
    BREAKING_THE_VEIL(new ResearchCategory("Breaking the Veil", ChatColor.LIGHT_PURPLE, VoidResearches.BUILDING_THE_PORTAL, VoidResearches.CREATING_THE_ALTAR, VoidResearches.TAPPING_THE_VOID)),
    ADVANCED_WIZARDRY(new ResearchCategory("Advanced Wizardry", ChatColor.DARK_PURPLE, VoidResearches.ADVANCED_WIZARDRY, VoidResearches.SPECIALIZATION));

    private IResearchCategory category;

    VoidCategories(IResearchCategory category) {
        this.category = category;
    }

    public IResearchCategory getCategory() {
        return category;
    }
}
