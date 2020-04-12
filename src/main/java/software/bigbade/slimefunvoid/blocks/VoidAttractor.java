package software.bigbade.slimefunvoid.blocks;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockPlaceHandler;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockUseHandler;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import software.bigbade.slimefunvoid.api.research.VoidRecipes;
import software.bigbade.slimefunvoid.items.Items;
import software.bigbade.slimefunvoid.utils.RecipeItems;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class VoidAttractor extends SlimefunItem {
    private final Map<Location, EnderCrystal> crystals = new HashMap<>();

    public VoidAttractor(Category category) {
        super(category, Items.VOID_ATTRACTOR, VoidRecipes.VOID_ALTAR, new ItemStack[]{RecipeItems.ENDER_EYE, RecipeItems.HOPPER, RecipeItems.ENDER_EYE,
                RecipeItems.HOPPER, null, RecipeItems.HOPPER,
                RecipeItems.ENDER_EYE, RecipeItems.HOPPER, RecipeItems.ENDER_EYE});

        BlockTicker ticker = new BlockTicker() {
            @Override
            public boolean isSynchronized() {
                return true;
            }

            @Override
            public void tick(Block block, SlimefunItem slimefunItem, Config config) {
                EnderCrystal crystal = crystals.get(block.getLocation());
                if(crystal == null) {
                    crystal = (EnderCrystal) block.getWorld().spawnEntity(block.getLocation(), EntityType.ENDER_CRYSTAL);
                    crystal.setShowingBottom(false);
                    crystals.put(block.getLocation(), crystal);
                }
                for (Entity entity : block.getWorld().getNearbyEntities(block.getLocation(), 5, 5, 5)) {
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) entity;
                        Vector movement = block.getLocation().subtract(livingEntity.getLocation()).toVector();
                        livingEntity.setVelocity(livingEntity.getVelocity().add(movement.multiply(.1)));
                        crystal.setBeamTarget(livingEntity.getLocation());
                        return;
                    }
                }
                crystal.setBeamTarget(null);
            }
        };

        addItemHandler(ticker);

        BlockPlaceHandler placeHandler = this::onBlockPlace;
        addItemHandler(placeHandler);

        BlockUseHandler blockUseHandler = this::onBlockUse;
        addItemHandler(blockUseHandler);
    }

    private void onBlockUse(PlayerRightClickEvent event) {
        event.setUseBlock(Event.Result.DENY);
        event.setUseItem(Event.Result.DENY);
        Optional<Block> clicked = event.getClickedBlock();
        if (!clicked.isPresent())
            return;
        clicked.get().setType(Material.AIR);
        event.getPlayer().getWorld().dropItemNaturally(clicked.get().getLocation(), Items.VOID_ATTRACTOR);
        crystals.remove(clicked.get().getLocation());
    }

    private boolean onBlockPlace(BlockPlaceEvent event, ItemStack item) {
        Block placing = event.getBlockPlaced();
        placing.setType(Material.BARRIER);
        EnderCrystal crystal = (EnderCrystal) placing.getWorld().spawnEntity(placing.getLocation(), EntityType.ENDER_CRYSTAL);
        crystals.put(placing.getLocation(), crystal);
        crystal.setShowingBottom(false);
        BlockStorage.store(placing, Items.VOID_ATTRACTOR);
        return true;
    }
}
