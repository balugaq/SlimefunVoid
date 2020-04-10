package software.bigbade.slimefunvoid;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.bstats.bukkit.Metrics;
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

    @Override
    public void onEnable() {
        setInstance(this);

        new Metrics(this, 6993);

        Category category = new Category(new NamespacedKey(this, "slimevoid_category"), new CustomItem(Material.OBSIDIAN, "&5SlimeVoid"));
        category.register();

        Objects.requireNonNull(getCommand("svresearch")).setExecutor(new ResearchCmd());
        Objects.requireNonNull(getCommand("svresearch")).setTabCompleter(new ResearchTabCompleter());

        new ItemManager(category).registerItems();

        new ListenerManager().registerListeners();
    }

    private static void setInstance(SlimefunVoid instance) {
        SlimefunVoid.instance = instance;
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/BigBadE/SlimefunVoid/issues";
    }

    public static SlimefunVoid getInstance() { return instance; }
}