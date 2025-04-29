package software.bigbade.slimefunvoid.blocks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Container;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import lombok.Getter;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import software.bigbade.slimefunvoid.PlacedVoidQuarry;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.api.research.VoidRecipes;
import software.bigbade.slimefunvoid.items.Items;
import software.bigbade.slimefunvoid.utils.RecipeItems;

public class VoidQuarry extends SlimefunItem {
    @Getter
    private Map<PlacedVoidQuarry, Integer> quarries = new HashMap<>();

    private static final int MAX_QUARRY_SIZE = 20;
    private static final int MAX_QUARRY_HEIGHT = 7;

    private static final String WARNING = " Make sure it is attached to a cube of fences (max of " + MAX_QUARRY_SIZE + "x" + MAX_QUARRY_HEIGHT + "x" + MAX_QUARRY_SIZE + ")";
    private Random random = new Random();

    private static final BlockFace[] FACES = new BlockFace[] { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN };

    public VoidQuarry(Category category) {
        super(category, Items.VOID_QUARRY, VoidRecipes.VOID_ALTAR, new ItemStack[]{RecipeItems.OBSIDIAN, RecipeItems.ENDER_CHEST, RecipeItems.OBSIDIAN,
                RecipeItems.ENDER_EYE, null, RecipeItems.ENDER_EYE,
                RecipeItems.OBSIDIAN, RecipeItems.ENDER_EYE, RecipeItems.OBSIDIAN
        });
        addItemHandler(new BlockPlaceHandler(true) {
			
			@Override
			public void onPlayerPlace(BlockPlaceEvent e) {
		        Bukkit.getScheduler().runTaskAsynchronously(SlimefunVoid.getInstance(), () -> {
		            if (setupQuarry(e.getBlock()))
		                e.getPlayer().sendMessage(ChatColor.GREEN + "Setup quarry." + WARNING);
		            else
		                e.getPlayer().sendMessage(ChatColor.RED + "Could not setup quarry!" + WARNING);
		        });
			}
		});

        BlockUseHandler blockUseHandler = this::onClick;
        addItemHandler(blockUseHandler);

        addItemHandler(new BlockBreakHandler(false, false) {
			
			@Override
			public void onPlayerBreak(BlockBreakEvent e, ItemStack item, List<ItemStack> drops) {
		        Optional<Map.Entry<PlacedVoidQuarry, Integer>> entry = quarries.entrySet().stream()
		                .filter(quarry -> quarry.getKey().getQuarry().equals(e.getBlock().getLocation()))
		                .findFirst();
		        entry.ifPresent(placedVoidQuarryIntegerEntry -> Bukkit.getScheduler().cancelTask(placedVoidQuarryIntegerEntry.getValue()));
			}
		});
    }

    private boolean onBreak(BlockBreakEvent event, ItemStack item, int fortune, List<ItemStack> drops) {
        Optional<Map.Entry<PlacedVoidQuarry, Integer>> entry = quarries.entrySet().stream()
                .filter(quarry -> quarry.getKey().getQuarry().equals(event.getBlock().getLocation()))
                .findFirst();
        entry.ifPresent(placedVoidQuarryIntegerEntry -> Bukkit.getScheduler().cancelTask(placedVoidQuarryIntegerEntry.getValue()));
        return true;
    }

    private void onClick(PlayerRightClickEvent event) {
        event.setUseBlock(Event.Result.DENY);
        event.setUseItem(Event.Result.DENY);
        Optional<Block> block = event.getClickedBlock();
        if(!block.isPresent())
            return;
        Bukkit.getScheduler().runTaskAsynchronously(SlimefunVoid.getInstance(), () -> {
            if (setupQuarry(block.get()))
                event.getPlayer().sendMessage(ChatColor.GREEN + "Setup quarry." + WARNING);
            else
                event.getPlayer().sendMessage(ChatColor.RED + "Could not setup quarry!" + WARNING);
        });
    }

    private boolean onPlace(BlockPlaceEvent event, ItemStack item) {
        Bukkit.getScheduler().runTaskAsynchronously(SlimefunVoid.getInstance(), () -> {
            if (setupQuarry(event.getBlock()))
                event.getPlayer().sendMessage(ChatColor.GREEN + "Setup quarry." + WARNING);
            else
                event.getPlayer().sendMessage(ChatColor.RED + "Could not setup quarry!" + WARNING);
        });
        return true;
    }

    private boolean setupQuarry(Block block) {
        Location corner = getCorner(block);
        if (corner == null) {
            return false;
        }
        PlacedVoidQuarry quarry = new PlacedVoidQuarry(block.getLocation());

        for(BlockFace face : FACES) {
            Block relative = block.getRelative(face);
            if(relative.getState() instanceof Container) {
                quarry.setChest(relative.getLocation());
            }
        }
        quarry.setBottomCorner(corner);
        Objects.requireNonNull(quarry.getBottomCorner());
        quarry.getSize().setX(getLength(quarry.getBottomCorner().clone(), new Vector(1, 0, 0), MAX_QUARRY_SIZE));
        quarry.getSize().setY(getLength(quarry.getBottomCorner().clone(), new Vector(0, 1, 0), MAX_QUARRY_SIZE));
        quarry.getSize().setZ(getLength(quarry.getBottomCorner().clone(), new Vector(0, 0, 1), MAX_QUARRY_SIZE));
        if(!checkQuarry(quarry))
            return false;
        quarry.setCurrentBlock(quarry.getBottomCorner().clone().add(1, quarry.getSize().getY()-1, 1));
        quarries.put(quarry, Bukkit.getScheduler().scheduleSyncRepeatingTask(SlimefunVoid.getInstance(), () -> {
            Objects.requireNonNull(quarry.getCurrentBlock());
            Block current = quarry.getCurrentBlock().getBlock();
            if(current.getType() != Material.AIR && random.nextInt(5)==1) {
                if(quarry.getChest() == null) {
                    Objects.requireNonNull(quarry.getQuarry().getWorld());
                    quarry.getQuarry().getWorld().dropItemNaturally(quarry.getQuarry(), new ItemStack(current.getType()));
                } else {
                    addBlock(quarry, current);
                }
            }
            moveCurrent(quarry);
        }, 20L, 20L));
        return true;
    }

    private void addBlock(PlacedVoidQuarry quarry, Block block) {
        Objects.requireNonNull(quarry.getChest());
        Block chest = quarry.getChest().getBlock();
        if(chest.getState() instanceof Container) {
            Container container = (Container) chest.getState();
            container.getInventory().addItem(new ItemStack(block.getType()));
        } else {
            quarry.setChest(null);
            Objects.requireNonNull(quarry.getQuarry().getWorld());
            quarry.getQuarry().getWorld().dropItemNaturally(quarry.getQuarry(), new ItemStack(block.getType()));
        }
    }

    private void moveCurrent(PlacedVoidQuarry quarry) {
        Location current = quarry.getCurrentBlock();
        Objects.requireNonNull(current);
        Objects.requireNonNull(quarry.getBottomCorner());
        current.add(1, 0, 0);
        if(current.getX() == quarry.getBottomCorner().getX()+quarry.getSize().getX()) {
            current.add(-quarry.getSize().getX()+1, 0, 1);
            if(current.getZ() == quarry.getBottomCorner().getZ()+quarry.getSize().getZ()) {
                current.add(0, -1, -quarry.getSize().getX()+1);
                if(current.getZ() == quarry.getBottomCorner().getY()) {
                    Bukkit.getScheduler().cancelTask(quarries.get(quarry));
                    quarries.remove(quarry);
                }
            }
        }
    }

    public static boolean checkQuarry(PlacedVoidQuarry quarry) {
        //Run along the rectangle checking it
        Objects.requireNonNull(quarry.getBottomCorner());
        Location location = quarry.getBottomCorner().clone();
        if(isBrokenSide(location, quarry, true))
            return false;
        if(getLength(location, new Vector(0, 1, 0), MAX_QUARRY_HEIGHT) != quarry.getSize().getY())
            return false;
        return !isBrokenSide(location, quarry, false);
    }

    private static boolean isBrokenSide(Location location, PlacedVoidQuarry quarry, boolean checkHeight) {
        if(getLength(location, new Vector(1, 0, 0), MAX_QUARRY_SIZE) != quarry.getSize().getX())
            return true;
        if(checkHeight && getLength(location.clone(), new Vector(0, 1, 0), MAX_QUARRY_HEIGHT) != quarry.getSize().getY())
            return true;
        if(getLength(location, new Vector(0, 0, 1), MAX_QUARRY_SIZE) != quarry.getSize().getZ())
            return true;
        if(checkHeight && getLength(location.clone(), new Vector(0, 1, 0), MAX_QUARRY_HEIGHT) != quarry.getSize().getY())
            return true;
        if(getLength(location, new Vector(-1, 0, 0), MAX_QUARRY_SIZE) != quarry.getSize().getX())
            return true;
        if(checkHeight && getLength(location.clone(), new Vector(0, 1, 0), MAX_QUARRY_HEIGHT) != quarry.getSize().getY())
            return true;
        return getLength(location, new Vector(0, 0, -1), MAX_QUARRY_SIZE) != quarry.getSize().getZ();
    }

    @Nullable
    private Location getCorner(Block block) {
        if (block.getRelative(BlockFace.NORTH).getType() == Material.OAK_FENCE) {
            return goDownSide(block.getLocation(), new Vector(-1, 0, 0));
        } else if(block.getRelative(BlockFace.EAST).getType() == Material.OAK_FENCE) {
            Location side = goDownSide(block.getLocation(), new Vector(0, 0, -1));
            return goDownSide(side, new Vector(-1, 0, 0));
        } else if(block.getRelative(BlockFace.SOUTH).getType() == Material.OAK_FENCE) {
            Location side = goDownSide(block.getLocation(), new Vector(1, 0, 0));
            return goDownSide(side, new Vector(0, 0, -1));
        } else if(block.getRelative(BlockFace.WEST).getType() == Material.OAK_FENCE) {
            return goDownSide(block.getLocation(), new Vector(0, 0, -1));
        }
        return null;
    }

    private static Location goDownSide(final Location start, Vector direction) {
        return start.add(direction.multiply(getLength(start, direction, MAX_QUARRY_HEIGHT)));
    }

    private static int getLength(Location start, Vector direction, int max) {
        for(int i = 0; i < max; i++) {
            start.add(direction);
            if(start.getBlock().getType() != Material.OAK_FENCE) {
                return i;
            }
        }
        return max;
    }
}