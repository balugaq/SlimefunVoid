package software.bigbade.slimefunvoid;

import org.bukkit.entity.EntityType;

public class VersionedEntityType {
    public static final EntityType END_CRYSTAL = getEntityType("END_CRYSTAL", "ENDER_CRYSTAL");
    public static final EntityType CHEST_MINECART = getEntityType("CHEST_MINECART", "MINECART_CHEST");

    public static EntityType getEntityType(String m1, String m2) {
        try {
            return EntityType.valueOf(m1);
        } catch (IllegalArgumentException e) {
            return EntityType.valueOf(m2);
        }
    }
}
