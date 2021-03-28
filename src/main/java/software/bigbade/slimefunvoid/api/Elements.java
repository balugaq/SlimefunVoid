package software.bigbade.slimefunvoid.api;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import software.bigbade.slimefunvoid.SlimefunVoid;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public enum Elements {
    VOID(ChatColor.DARK_PURPLE, new CustomItem(Material.ENDER_EYE, "&5Void", "&aContains void spells", "&acharged with items like ender pearls and end stone", "&aIncreases spell power and", "&abackfire chance"), new NamespacedKey(SlimefunVoid.getInstance(), "element_void"), Sets.newHashSet(Material.ENDER_EYE, Material.OBSIDIAN, Material.ENDER_PEARL, Material.END_STONE, Material.END_ROD, Material.END_CRYSTAL)),
    FIRE(ChatColor.DARK_RED, new CustomItem(Material.FIRE_CHARGE, "&cFire", "&aContains fiery spells", "&acharged with items like coal and fire charges", "&aMakes certain spells and", "&abackfires more powerful"), new NamespacedKey(SlimefunVoid.getInstance(), "element_fire"), Sets.newHashSet(Material.FLINT_AND_STEEL, Material.FIRE_CHARGE, Material.FIREWORK_ROCKET, Material.FIREWORK_STAR, Material.COAL, Material.COAL_BLOCK, Material.CHARCOAL, Material.NETHERRACK)),
    WATER(ChatColor.DARK_BLUE, new CustomItem(Material.WATER_BUCKET, "&1Water", "&aContains water spells", "&acharged with items like fish", "&aDecreases backfire chance and", "&acertain spells power"), new NamespacedKey(SlimefunVoid.getInstance(), "element_water"), Sets.newHashSet(Material.PUFFERFISH, Material.SALMON, Material.TROPICAL_FISH, Material.COD, Material.COOKED_COD)),
    GRASS(ChatColor.GREEN, new CustomItem(Material.OAK_SAPLING, "&aGrass", "&aContains grass spells", "&acharged with items like grass and saplings", "&aDecreases backfire chance and", "&acertain spells power"), new NamespacedKey(SlimefunVoid.getInstance(), "element_grass"), Sets.newHashSet(Material.SPRUCE_SAPLING, Material.ACACIA_SAPLING, Material.BAMBOO_SAPLING, Material.BIRCH_SAPLING, Material.DARK_OAK_SAPLING, Material.JUNGLE_SAPLING, Material.OAK_SAPLING, Material.SPRUCE_LEAVES, Material.ACACIA_LEAVES, Material.BIRCH_LEAVES, Material.DARK_OAK_LEAVES, Material.JUNGLE_LEAVES, Material.OAK_LEAVES, Material.GRASS, Material.GRASS_BLOCK)),
    LIGHT(ChatColor.WHITE, new CustomItem(Material.GLOWSTONE, "&fLight", "&aContains light spells", "&acharged with light sources", "&aIncreases spell power of certain spells,", "&abut increases spell cooldown"), new NamespacedKey(SlimefunVoid.getInstance(), "element_light"), Sets.newHashSet(Material.TORCH, Material.REDSTONE_TORCH, Material.GLOWSTONE, Material.GLOWSTONE_DUST, Material.REDSTONE_LAMP, Material.REDSTONE)),
    ELECTRIC(ChatColor.YELLOW, new CustomItem(Material.IRON_INGOT, "&eElectric", "&aContains electric spells", "&acharged with mechanical items", "&aDecreases spell cooldown,", "&abut increases backfire chance"), new NamespacedKey(SlimefunVoid.getInstance(), "element_steel"), Sets.newHashSet(Material.IRON_INGOT, Material.IRON_BARS, Material.IRON_BLOCK, Material.IRON_DOOR, Material.IRON_NUGGET)),
    WIND(ChatColor.GRAY, new CustomItem(Material.GLASS, "&7Wind", "&aContains wind spells", "&acharged with items like feathers and wool", "&aDecreases spell cooldown and damage"), new NamespacedKey(SlimefunVoid.getInstance(), "element_wind"), Sets.newHashSet(Material.FEATHER, Material.ELYTRA, Material.WHITE_WOOL, Material.BLACK_WOOL, Material.BLUE_WOOL, Material.BROWN_WOOL, Material.CYAN_WOOL, Material.GRAY_WOOL, Material.GREEN_WOOL, Material.LIGHT_BLUE_WOOL, Material.LIGHT_GRAY_WOOL, Material.LIME_WOOL, Material.MAGENTA_WOOL, Material.ORANGE_WOOL, Material.PINK_WOOL, Material.PURPLE_WOOL, Material.RED_WOOL, Material.YELLOW_WOOL));

    Elements(ChatColor color, CustomItem icon, NamespacedKey key, HashSet<Material> items) {
		this.color = color;
		this.icon = icon;
		this.key = key;
		this.items = items;
	}
	@Getter
    private final ChatColor color;
    @Getter
    private final ItemStack icon;
    @Getter
    private final NamespacedKey key;
    @Getter
    private final Set<Material> items;
}