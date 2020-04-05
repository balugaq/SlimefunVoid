package software.bigbade.slimefunvoid;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.bstats.bukkit.Metrics;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import software.bigbade.slimefunvoid.blocks.VoidResearchBench;
import software.bigbade.slimefunvoid.items.VoidBag;

import java.util.logging.Logger;

public class SlimefunVoid extends JavaPlugin implements SlimefunAddon {

    private static SlimefunVoid instance;

    @Override
    public void onEnable() {
        setInstance(this);

        new Metrics(this, 6993);
        Config cfg = new Config(this);

        Category category = new Category(new NamespacedKey(this, "slimevoid_category"), new CustomItem(Material.OBSIDIAN, "&5SlimeVoid"));
        category.register();

        registerItems(category);
    }

    private static void setInstance(SlimefunVoid instance) {
        SlimefunVoid.instance = instance;
    }

    private void registerItems(Category category) {
        new VoidResearchBench(category).register(this);
        new VoidBag(category).register(this);
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/BigBadE/SlimefunVoid/issues";
    }

    public static JavaPlugin getInstance() { return instance; }

    public static Logger getPluginLogger() {
        return instance.getLogger();
    }
}