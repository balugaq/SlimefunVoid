package software.bigbade.slimefunvoid.api;

import software.bigbade.slimefunvoid.api.IVoidResearch;
import software.bigbade.slimefunvoid.api.Researches;
import software.bigbade.slimefunvoid.impl.VoidResearch;

public enum VoidResearches {
    THE_DISCOVERY(1, new VoidResearch("The Discovery", 120, "&5The Void &acould always be felt,", "but the discovery and subsequent harnessing of it", "is still no small feat")),
    BASIC_INNOVATION(1, new VoidResearch("Harnessing &5The Void", 300, Researches.VOID_BAG.getResearch(), "This bag can be linked to a Chest", "&5The Void &awill consume some items sent through", "Unlocks: &5Void Bag")),
    ENTERING_THE_VOID(2, new VoidResearch("Entering &5The Void", 500, "With extreme concentration, &5The Void &dcan be tapped into mentally,", "allowing siphoning of its chaotic energy"));

    private IVoidResearch research;
    private int categoryID;

    VoidResearches(int categoryID, IVoidResearch research) {
        this.categoryID = categoryID;
        this.research = research;
    }

    public IVoidResearch getResearch() {
        return research;
    }

    public Integer getCategoryID() {
        return categoryID;
    }
}
