package software.bigbade.slimefunvoid.api;

import com.google.common.collect.Sets;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import software.bigbade.slimefunvoid.SlimefunVoid;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public enum Elements {
    VOID(ChatColor.DARK_PURPLE, new CustomItemStack(Material.ENDER_EYE, "&5虚无", "&a包含虚无系魔法", "&a通过末影珍珠和末地石等物品充能", "&a提升魔法强度与", "&a反噬几率"), new NamespacedKey(SlimefunVoid.getInstance(), "element_void"), Sets.newHashSet(Material.ENDER_EYE, Material.OBSIDIAN, Material.ENDER_PEARL, Material.END_STONE, Material.END_ROD, Material.END_CRYSTAL)),
    FIRE(ChatColor.DARK_RED, new CustomItemStack(Material.FIRE_CHARGE, "&c火", "&a包含火焰系魔法", "&a通过煤炭和火药等物品充能", "&a使某些魔法能力与", "&a反噬更加强力"), new NamespacedKey(SlimefunVoid.getInstance(), "element_fire"), Sets.newHashSet(Material.FLINT_AND_STEEL, Material.FIRE_CHARGE, Material.FIREWORK_ROCKET, Material.FIREWORK_STAR, Material.COAL, Material.COAL_BLOCK, Material.CHARCOAL, Material.NETHERRACK)),
    WATER(ChatColor.DARK_BLUE, new CustomItemStack(Material.WATER_BUCKET, "&1水", "&a包含水系魔法", "&a通过鱼类充能", "&a降低反噬几率和", "&a某些魔法的强度"), new NamespacedKey(SlimefunVoid.getInstance(), "element_water"), Sets.newHashSet(Material.PUFFERFISH, Material.SALMON, Material.TROPICAL_FISH, Material.COD, Material.COOKED_COD)),
    GRASS(ChatColor.GREEN, new CustomItemStack(Material.OAK_SAPLING, "&a草", "&a包含草系魔法", "&a通过草和树苗充能", "&a降低反噬几率和", "&a某些魔法的强度"), new NamespacedKey(SlimefunVoid.getInstance(), "element_grass"), Sets.newHashSet(Material.SPRUCE_SAPLING, Material.ACACIA_SAPLING, Material.BAMBOO_SAPLING, Material.BIRCH_SAPLING, Material.DARK_OAK_SAPLING, Material.JUNGLE_SAPLING, Material.OAK_SAPLING, Material.SPRUCE_LEAVES, Material.ACACIA_LEAVES, Material.BIRCH_LEAVES, Material.DARK_OAK_LEAVES, Material.JUNGLE_LEAVES, Material.OAK_LEAVES, Material.GRASS, Material.GRASS_BLOCK)),
    LIGHT(ChatColor.WHITE, new CustomItemStack(Material.GLOWSTONE, "&f光", "&a包含光系魔法", "&a通过光源充能", "&a提升某些魔法的强度，", "&a但增加魔法冷却时间"), new NamespacedKey(SlimefunVoid.getInstance(), "element_light"), Sets.newHashSet(Material.TORCH, Material.REDSTONE_TORCH, Material.GLOWSTONE, Material.GLOWSTONE_DUST, Material.REDSTONE_LAMP, Material.REDSTONE)),
    ELECTRIC(ChatColor.YELLOW, new CustomItemStack(Material.IRON_INGOT, "&e电", "&a包含电系魔法", "&a通过机械物品充能", "&a降低魔法冷却时间，", "&a但增加反噬几率"), new NamespacedKey(SlimefunVoid.getInstance(), "element_steel"), Sets.newHashSet(Material.IRON_INGOT, Material.IRON_BARS, Material.IRON_BLOCK, Material.IRON_DOOR, Material.IRON_NUGGET)),
    WIND(ChatColor.GRAY, new CustomItemStack(Material.GLASS, "&7风", "&a包含风系魔法", "&a通过羽毛和羊毛充能", "&a降低魔法冷却时间和伤害"), new NamespacedKey(SlimefunVoid.getInstance(), "element_wind"), Sets.newHashSet(Material.FEATHER, Material.ELYTRA, Material.WHITE_WOOL, Material.BLACK_WOOL, Material.BLUE_WOOL, Material.BROWN_WOOL, Material.CYAN_WOOL, Material.GRAY_WOOL, Material.GREEN_WOOL, Material.LIGHT_BLUE_WOOL, Material.LIGHT_GRAY_WOOL, Material.LIME_WOOL, Material.MAGENTA_WOOL, Material.ORANGE_WOOL, Material.PINK_WOOL, Material.PURPLE_WOOL, Material.RED_WOOL, Material.YELLOW_WOOL));

    @Getter
    private final ChatColor color;
    @Getter
    private final ItemStack icon;
    @Getter
    private final NamespacedKey key;
    @Getter
    private final Set<Material> items;
    Elements(ChatColor color, CustomItemStack icon, NamespacedKey key, HashSet<Material> items) {
        this.color = color;
        this.icon = icon;
        this.key = key;
        this.items = items;
    }
}