package software.bigbade.slimefunvoid.api;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import software.bigbade.slimefunvoid.SlimefunVoid;

import java.util.Set;

@RequiredArgsConstructor
public enum Element {
    TEST(new ItemStack(Material.COMMAND_BLOCK), new NamespacedKey(SlimefunVoid.getInstance(), "element_test"), Sets.newHashSet(Material.SAND));

    @Getter
    private final ItemStack icon;
    @Getter
    private final NamespacedKey key;
    @Getter
    private final Set<Material> items;
}
