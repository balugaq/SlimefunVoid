package software.bigbade.slimefunvoid.items.wands;

import com.google.common.base.Strings;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import lombok.Getter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import software.bigbade.slimefunvoid.api.Elements;
import software.bigbade.slimefunvoid.api.Spells;
import software.bigbade.slimefunvoid.api.WandSpell;
import software.bigbade.slimefunvoid.items.Items;
import software.bigbade.slimefunvoid.menus.SpellCategoryMenu;
import software.bigbade.slimefunvoid.utils.SelfCancelableTask;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;

public class WandItem extends SimpleSlimefunItem<ItemUseHandler> {
    private static final String WAND_REGEX = ChatColor.COLOR_CHAR + ". \\(";
    private static final int BAR_LENGTH = 40;
    @Getter
    private final int maxElements;
    @Getter
    private final int maxElement;
    private final double baseBackfireChance;
    private final Random random = new Random();
    private final SpellCategoryMenu menu = new SpellCategoryMenu();
    private final HashMap<Player, Double> cooldowns = new HashMap<>();
    private final boolean useCooldown;

    public WandItem(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int maxElement, int maxElements, double baseBackfireChance) {
        super(category, item, recipeType, recipe);
        this.maxElement = maxElement;
        this.maxElements = maxElements;
        this.baseBackfireChance = baseBackfireChance;
        boolean spigot;
        try {
            Class.forName("net.md_5.bungee.api.ChatMessageType");
            spigot = true;
        } catch (ClassNotFoundException e) {
            spigot = false;
        }
        useCooldown = spigot;
    }

    @Nullable
    public static WandItem getWand(ItemStack item) {
        Optional<String> idOptional = Slimefun.getItemDataService().getItemData(item.getItemMeta());
        if (!idOptional.isPresent()) {
            return null;
        }

        String id = idOptional.get();
        for (SlimefunItemStack wand : Items.getWands()) {
            if (wand.getItemId().equals(id)) {
                return (WandItem) wand.getItem();
            }
        }
        return null;
    }

    public static String removeSpellFromName(String name) {
        String[] split = name.split(WAND_REGEX);
        if (split.length == 2 || split.length == 1)
            return split[0];
        else {
            StringBuilder newName = new StringBuilder();
            for (int i = 0; i < split.length - 1; i++)
                newName.append(split[i]);
            return newName.toString();
        }
    }

    public static void chargeItem(ItemStack item, Elements element, int amount) {
        WandItem wand = WandItem.getWand(item);
        Objects.requireNonNull(wand);
        ItemMeta meta = item.getItemMeta();
        Objects.requireNonNull(meta);
        PersistentDataContainer data = meta.getPersistentDataContainer();
        Integer previous;
        if (data.has(element.getKey(), PersistentDataType.INTEGER)) {
            previous = data.get(element.getKey(), PersistentDataType.INTEGER);
        } else {
            previous = 0;
        }
        Objects.requireNonNull(previous);
        String base = element.getColor() + Objects.requireNonNull(element.getIcon().getItemMeta()).getDisplayName() + ": ";
        List<String> lore = meta.getLore();
        if (lore == null)
            lore = new ArrayList<>();
        else
            lore.remove(base + previous + "/" + wand.getMaxElement());
        int added = getElementAmount(item, wand, previous + amount);
        lore.add(base + added + "/" + wand.getMaxElement());
        meta.setLore(lore);
        data.set(element.getKey(), PersistentDataType.INTEGER, previous + added);
        item.setItemMeta(meta);
    }

    private static int getElementAmount(ItemStack item, WandItem wand, int adding) {
        int maxElement = Math.min(wand.getMaxElement(), adding);
        return Math.min(wand.getMaxElements() - getAllElements(item), maxElement);
    }

    private static int getAllElements(ItemStack item) {
        int total = 0;
        for (Elements element : Elements.values()) {
            total += getElementAmount(item, element);
        }
        return total;
    }

    public static int getElementAmount(ItemStack wand, Elements element) {
        ItemMeta meta = wand.getItemMeta();
        Objects.requireNonNull(meta);
        PersistentDataContainer data = meta.getPersistentDataContainer();
        if (data.has(element.getKey(), PersistentDataType.INTEGER)) {
            return Objects.requireNonNull(data.get(element.getKey(), PersistentDataType.INTEGER));
        }
        return 0;
    }

    public static WandSpell getCurrentSpell(String name) {
        String[] splitName = name.split(WAND_REGEX);
        String spellName = splitName[splitName.length - 1].replace(")", "");
        for (Spells spells : Spells.values()) {
            if (spells.getSpell().getName().equals(spellName))
                return spells.getSpell();
        }
        return null;
    }

    public double getModifier(Elements element) {
        //Overridden by subclasses
        return 1;
    }

    public boolean backfires(WandItem wand) {
        double chance = wand.baseBackfireChance;
        chance *= getElementChance(Elements.VOID);
        chance *= getElementChance(Elements.ELECTRIC);
        chance /= getElementChance(Elements.WATER);
        chance /= getElementChance(Elements.GRASS);
        return random.nextDouble() <= chance;
    }

    private double getElementChance(Elements elements) {
        float element = getElementAmount(new ItemStack(Material.STONE), elements);
        if (element != 0)
            return 1 + (maxElement / element);
        return 1;
    }

    @Override
    public ItemUseHandler getItemHandler() {
        return event -> {
            Player player = event.getPlayer();
            ItemStack item = event.getItem();
            if (event.getHand() != EquipmentSlot.HAND)
                return;
            if (player.isSneaking()) {
                menu.open(player);
            } else {
                onSpellCast(item, player);
            }
        };
    }

    private void cooldown(Player player, ItemStack wand, WandSpell spell) {
        final long cooldown = spell.getCooldown(wand);
        cooldowns.put(player, (double) cooldown);
        player.setCooldown(wand.getType(), (int) cooldown);
        if (useCooldown) {
            addActionBarRunnable(player, spell, cooldown);
        } else {
            SelfCancelableTask task = new SelfCancelableTask();
            task.setRunnable(() -> {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(""));
                task.cancel();
                spell.onStop(player, wand);
            });
            task.start(0, cooldown * 20);
        }
    }

    private void addActionBarRunnable(Player player, WandSpell spell, long cooldown) {
        ItemStack wand = player.getInventory().getItemInMainHand();
        SelfCancelableTask task = new SelfCancelableTask();
        task.setRunnable(() -> {
            Double left = cooldowns.get(player);
            if (left == null) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(""));
                task.cancel();
                spell.onStop(player, wand);
                return;
            }
            if (left <= 0) {
                cooldowns.remove(player);
            }
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(getCooldownBar(left, cooldown)));
            cooldowns.replace(player, left - .05);
        });
        task.start(0L, 1L);
    }

    private String getCooldownBar(double remaining, float start) {
        StringBuilder cooldownBar = new StringBuilder(ChatColor.GOLD + "[");
        int currentPosition;
        if (remaining > 0) {
            currentPosition = (int) (BAR_LENGTH * (start - remaining) / start);
        } else {
            currentPosition = BAR_LENGTH;
        }
        if (currentPosition > 0)
            cooldownBar.append(ChatColor.GREEN);
        cooldownBar.append(Strings.repeat(":", currentPosition));
        cooldownBar.append(ChatColor.RED);
        cooldownBar.append(Strings.repeat(":", BAR_LENGTH - currentPosition));
        cooldownBar.append(ChatColor.GOLD).append("]");
        return cooldownBar.toString();
    }

    private void onSpellCast(ItemStack item, Player player) {
        String name = Objects.requireNonNull(item.getItemMeta()).getDisplayName();
        if (!Pattern.compile(WAND_REGEX).matcher(name).find()) {
            player.sendMessage(ChatColor.RED + "你必须先使用Shift右键选择一个术语!");
            return;
        }
        WandSpell wandSpell = getCurrentSpell(name);
        if (wandSpell == null) {
            player.sendMessage(ChatColor.RED + "你必须先使用Shift右键选择一个术语!");
            return;
        }
        WandItem wand = WandItem.getWand(item);
        if (wand == null)
            return;
        if (!cooldowns.containsKey(player)) {
            if (backfires(wand)) {
                wandSpell.onBackfire(player, item);
                cooldown(player, item, wandSpell);
            } else {
                if (wandSpell.onCast(player, item))
                    cooldown(player, item, wandSpell);
            }
        }
    }
}
