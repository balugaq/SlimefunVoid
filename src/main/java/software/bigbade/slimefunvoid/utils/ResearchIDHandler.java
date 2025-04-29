package software.bigbade.slimefunvoid.utils;

public final class ResearchIDHandler {
    private static int nextID = 275600;

    //Private constructor to hide implicit public one
    private ResearchIDHandler() {
    }

    public static int nextID() {
        nextID++;
        return nextID;
    }
}
