package software.bigbade.slimefunvoid;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import software.bigbade.slimefunvoid.commands.ResearchCmd;
import software.bigbade.slimefunvoid.commands.ResearchTabCompleter;
import software.bigbade.slimefunvoid.utils.ItemManager;
import software.bigbade.slimefunvoid.utils.ListenerManager;

import java.util.Objects;

public class SlimefunVoid extends JavaPlugin implements SlimefunAddon {

    private static SlimefunVoid instance;

    @Getter
    private ItemManager itemManager;

    public static SlimefunVoid getInstance() {
        return instance;
    }

    private static void setInstance(SlimefunVoid instance) {
        SlimefunVoid.instance = instance;
    }

    @Override
    public void onEnable() {
        setInstance(this);

        ItemGroup category = new ItemGroup(new NamespacedKey(this, "slimevoid_category"), new CustomItemStack(Material.ENDER_EYE, "&5SlimeVoid"));
        category.register(this);

        Objects.requireNonNull(getCommand("svresearch")).setExecutor(new ResearchCmd());
        Objects.requireNonNull(getCommand("svresearch")).setTabCompleter(new ResearchTabCompleter());

        itemManager = new ItemManager(category);
        itemManager.registerItems();

        new ListenerManager(this).registerListeners();
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/BigBadE/SlimefunVoid/issues";
    }
}