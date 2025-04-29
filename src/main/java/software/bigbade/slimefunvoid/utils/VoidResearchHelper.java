package software.bigbade.slimefunvoid.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.api.research.IResearchCategory;
import software.bigbade.slimefunvoid.api.research.VoidResearches;

import java.util.Objects;

public class VoidResearchHelper {
    public static final NamespacedKey RESEARCHED = new NamespacedKey(SlimefunVoid.getInstance(), "researched_void");

    //Private constructor to hide implicit public one
    private VoidResearchHelper() {
    }

    public static int getResearched(Player player, IResearchCategory category) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if (!data.has(RESEARCHED, PersistentDataType.INTEGER_ARRAY))
            return 0;
        int[] researchData = data.get(RESEARCHED, PersistentDataType.INTEGER_ARRAY);
        Objects.requireNonNull(researchData);
        if (researchData.length >= category.getId()) {
            return researchData[category.getId() - 1];
        } else {
            return 0;
        }
    }

    public static void addResearch(Player player, VoidResearches researches) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if (data.has(RESEARCHED, PersistentDataType.INTEGER_ARRAY)) {
            int[] researchData = data.get(RESEARCHED, PersistentDataType.INTEGER_ARRAY);
            Objects.requireNonNull(researchData);
            if (researchData.length >= researches.getCategoryID()) {
                researchData[researches.getCategoryID() - 1] += 1;
            } else {
                int[] newData = new int[researchData.length];
                System.arraycopy(researchData, 0, newData, 0, researchData.length - 1);
                newData[researchData.length - 1] = 1;
                researchData = newData;
            }
            data.set(RESEARCHED, PersistentDataType.INTEGER_ARRAY, researchData);
        } else {
            data.set(RESEARCHED, PersistentDataType.INTEGER_ARRAY, new int[]{1});
        }
    }
}
