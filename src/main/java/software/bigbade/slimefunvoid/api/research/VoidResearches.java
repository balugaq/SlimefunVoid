package software.bigbade.slimefunvoid.api.research;

import software.bigbade.slimefunvoid.impl.VoidResearch;

public enum VoidResearches {
    THE_DISCOVERY(1, new VoidResearch("The Discovery", 120, "&5The Void &acould always be felt,", "but the discovery and subsequent harnessing of it", "is still no small feat")),
    HARNESSING_THE_VOID(1, new VoidResearch("Harnessing &5The Void", 300, Researches.VOID_BAG.getResearch(), "This bag can be linked to a Chest", "&5The Void &awill consume some items sent through", "Unlocks: &5Void Bag")),
    ENTERING_THE_VOID(1, new VoidResearch("Entering &5The Void", 500, "With extreme concentration,", "&5The Void &acan be tapped into mentally,", "allowing siphoning of its chaotic energy")),
    CREATING_THE_ALTAR(2, new VoidResearch("Creating The Altar", 300, Researches.VOID_ALTAR.getResearch(), "Allows for the crafting of items using pure Void energy")),
    BUILDING_THE_PORTAL(2, new VoidResearch("Building The Portal", 300, Researches.VOID_PORTAL.getResearch(), "A gateway into the void can be constructed", "Allowing for tapping into the raw power of &5The Void")),
    TAPPING_THE_VOID(2, new VoidResearch("Tapping &5The Void", 400, Researches.BASIC_WAND.getResearch(), "A precise instrument, a wand can tap into", "large amounts of void energy")),
    ADVANCED_WIZARDRY(3, new VoidResearch("Advanced Wizardry", 500, Researches.ADVANCED_WAND.getResearch(), "A refined wand, allowing for advanced spells")),
    SPECIALIZATION(3, new VoidResearch("Specialization", 500, "Specializing into a certain element", "allows for improved spell power", "Unlocks: &5Advanced Wand"));

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
