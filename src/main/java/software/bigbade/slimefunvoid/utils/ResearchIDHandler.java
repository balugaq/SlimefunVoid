package software.bigbade.slimefunvoid.utils;

public final class ResearchIDHandler {
    //Private constructor to hide implicit public one
    private ResearchIDHandler() {}

    private static int nextID = 275600;

    public static int nextID() {
        nextID++;
        return nextID;
    }
}
