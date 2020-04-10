package software.bigbade.slimefunvoid.blocks;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockPlaceHandler;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockUseHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Container;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import software.bigbade.slimefunvoid.PlacedVoidQuarry;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.api.research.VoidRecipes;
import software.bigbade.slimefunvoid.items.Items;
import software.bigbade.slimefunvoid.utils.RecipeItems;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class VoidQuarry extends SlimefunItem {
    @Getter
    private Map<PlacedVoidQuarry, Integer> quarries = new HashMap<>();

    private static final int MAX_QUARRY_SIZE = 20;
    private static final int MAX_QUARRY_HEIGHT = 7;

    private static final String WARNING = "Make sure it is attached to a cube of fences (max of " + MAX_QUARRY_SIZE + "x" + MAX_QUARRY_HEIGHT + "x" + MAX_QUARRY_SIZE + ")";
    private Random random = new Random();

    private static final BlockFace[] FACES = new BlockFace[]{BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN};

    public VoidQuarry(Category category) {
        super(category, Items.VOID_QUARRY, VoidRecipes.VOID_ALTAR, new ItemStack[]{RecipeItems.OBSIDIAN, RecipeItems.ENDER_CHEST, RecipeItems.OBSIDIAN,
                RecipeItems.ENDER_EYE, null, RecipeItems.ENDER_EYE,
                RecipeItems.OBSIDIAN, RecipeItems.ENDER_EYE, RecipeItems.OBSIDIAN
        });
        BlockPlaceHandler blockPlaceHandler = this::onPlace;
        addItemHandler(blockPlaceHandler);

        BlockUseHandler blockUseHandler = this::onClick;
        addItemHandler(blockUseHandler);
    }

    private void onClick(PlayerRightClickEvent event) {
        event.getClickedBlock().ifPresent(this::setupQuarry);
        event.getPlayer().sendMessage(ChatColor.GREEN + "Re-setup quarry. " + WARNING);
    }

    private boolean onPlace(BlockPlaceEvent event, ItemStack item) {
        setupQuarry(event.getBlock());
        event.getPlayer().sendMessage(ChatColor.GREEN + "Setup quarry. " + WARNING);
        return true;
    }

    private void setupQuarry(Block block) {
        Bukkit.getScheduler().runTaskAsynchronously(SlimefunVoid.getInstance(), () -> {
            PlacedVoidQuarry quarry = getQuarry(block.getLocation());
            for (BlockFace face : FACES) {
                Block found = block.getRelative(face);
                if (found.getState() instanceof Container) {
                    quarry.setChest(found.getLocation());
                    break;
                }
            }
            getSize(quarry);
            quarry.setCurrentBlock(Objects.requireNonNull(quarry.getBottomCorner()).add(new Vector(1, quarry.getSize().getY() - 1, 1)));
            quarries.put(quarry, Bukkit.getScheduler().scheduleSyncRepeatingTask(SlimefunVoid.getInstance(), () -> {
                Block currentBlock = Objects.requireNonNull(quarry.getCurrentBlock()).getBlock();
                addBlock(Objects.requireNonNull(quarry.getChest()).getBlock(), currentBlock.getType());
                currentBlock.setType(Material.AIR);
                getNextBlock(quarry);
            }, 20L, 20L));
        });
    }

    private void getNextBlock(PlacedVoidQuarry quarry) {
        if (Objects.requireNonNull(quarry.getCurrentBlock()).getX() == Objects.requireNonNull(quarry.getBottomCorner()).getX() + quarry.getSize().getX()) {
            quarry.getCurrentBlock().setX(quarry.getBottomCorner().getX() + 1);
            if (quarry.getCurrentBlock().getZ() == quarry.getBottomCorner().getZ() + quarry.getSize().getZ()) {
                quarry.getCurrentBlock().setZ(quarry.getBottomCorner().getZ() + 1);
                quarry.getCurrentBlock().setY(quarry.getCurrentBlock().getY() + 1);
                if (quarry.getCurrentBlock().getY() == quarry.getBottomCorner().getY()) {
                    Bukkit.getScheduler().cancelTask(quarries.get(quarry));
                }
            }
        }
    }

    private void addBlock(Block target, Material adding) {
        if (random.nextInt(5) != 1 && target.getState() instanceof Container) {
            ((Container) target.getState()).getInventory().addItem(new ItemStack(adding));
        }
    }

    private void getSize(PlacedVoidQuarry quarry) {
        quarry.getSize().setY(getSizeAlongAxis(Objects.requireNonNull(quarry.getBottomCorner()).clone(), new Vector(0, 1, 0), MAX_QUARRY_HEIGHT) - 2);
        quarry.getSize().setZ(getSizeAlongAxis(quarry.getBottomCorner().clone(), new Vector(0, 1, 0), MAX_QUARRY_HEIGHT) - 2);
    }

    private PlacedVoidQuarry getQuarry(Location location) {
        PlacedVoidQuarry quarry = new PlacedVoidQuarry();
        quarry.getSize().setX(getSizeAlongAxis(location, new Vector(1, 0, 0), MAX_QUARRY_SIZE) - 1);
        quarry.getSize().setX(getSizeAlongAxis(location, new Vector(-1, 0, 0), (int) (MAX_QUARRY_SIZE - quarry.getSize().getX())) - 1);
        return quarry;
    }

    private int getSizeAlongAxis(Location location, Vector adding, int max) {
        for (int i = 0; i < max; i++) {
            location.add(adding);
            if (location.getBlock().getType() != Material.OAK_FENCE) {
                return i;
            }
        }
        return max;
    }
}
