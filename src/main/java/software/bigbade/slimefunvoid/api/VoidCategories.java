package software.bigbade.slimefunvoid.api;

import org.bukkit.ChatColor;
import software.bigbade.slimefunvoid.impl.ResourceCategory;

public enum VoidCategories {
    THE_BEGINNING(new ResourceCategory("The Beginning", ChatColor.GREEN, 1, VoidResearches.THE_DISCOVERY, VoidResearches.HARNESSING_THE_VOID)),
    BREAKING_THE_VEIL(new ResourceCategory("Breaking the Veil", ChatColor.LIGHT_PURPLE, 2, VoidResearches.ENTERING_THE_VOID, VoidResearches.BUILDING_THE_PORTAL, VoidResearches.BUILDING_THE_PORTAL, VoidResearches.CREATING_THE_ALTAR, VoidResearches.TAPPING_THE_VOID));

    private IResearchCategory category;

    VoidCategories(IResearchCategory category) {
        this.category = category;
    }

    public IResearchCategory getCategory() {
        return category;
    }
}
