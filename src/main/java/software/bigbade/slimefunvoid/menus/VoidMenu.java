package software.bigbade.slimefunvoid.menus;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.api.VoidMessages;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Consumer;

public class VoidMenu extends ChestMenu {
    public static final NamespacedKey VOID_COOLDOWN = new NamespacedKey(SlimefunVoid.getInstance(), "void_cooldown");
    public static final ItemStack PURPLE_GLASS = new CustomItemStack(Material.PURPLE_STAINED_GLASS_PANE, " ");
    private static final NamespacedKey VOID_JOURNEYS = new NamespacedKey(SlimefunVoid.getInstance(), "void_journeys");
    private final Random random = new Random();

    public VoidMenu() {
        super("&5The Void");
        for (int i = 0; i < 27; i++) {
            if (i > 9 && i < 17 && i % 2 == 1)
                continue;
            addItem(i, PURPLE_GLASS, (player, slot, item, action) -> false);
        }
    }

    private void init(Player player) {
        VoidMessages message;
        if (player.getPersistentDataContainer().has(VOID_JOURNEYS, PersistentDataType.LONG)) {
            message = VoidMessages.values()[random.nextInt(VoidMessages.values().length - 1) + 1];
        } else {
            message = VoidMessages.START;
        }
        addItem(4, new CustomItemStack(Material.MAGENTA_STAINED_GLASS_PANE, ChatColor.DARK_PURPLE + message.getName(), ChatColor.LIGHT_PURPLE + message.getDescription()));
        int choices = message.getItems().length;
        int[] chosen = random.ints(0, choices).distinct().limit(choices).sorted().toArray();
        String[] options = new String[]{message.getItems()[chosen[0]], message.getItems()[chosen[1]], message.getItems()[chosen[2]]};
        List<Consumer<Player>> consumers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            consumers.add(message.getConsumers().get(chosen[i]));
        }
        for (int i = 0; i < 3; i++) {
            addItem(11 + (i * 2), new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, ChatColor.GREEN + options[i]), (clicker, slot, item, action) -> {
                PersistentDataContainer data = player.getPersistentDataContainer();
                data.set(VOID_COOLDOWN, PersistentDataType.LONG, System.currentTimeMillis());
                if (data.has(VOID_JOURNEYS, PersistentDataType.LONG)) {
                    data.set(VOID_JOURNEYS, PersistentDataType.LONG, Objects.requireNonNull(data.get(VOID_JOURNEYS, PersistentDataType.LONG)));
                } else {
                    data.set(VOID_JOURNEYS, PersistentDataType.LONG, 1L);
                }
                consumers.get((slot - 11) / 2).accept(clicker);
                player.closeInventory();
                return false;
            });
        }
    }

    public void open(Player player) {
        init(player);
        super.open(player);
    }
}
