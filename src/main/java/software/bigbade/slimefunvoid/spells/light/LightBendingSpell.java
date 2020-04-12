package software.bigbade.slimefunvoid.spells.light;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.api.Elements;
import software.bigbade.slimefunvoid.api.research.Researches;
import software.bigbade.slimefunvoid.impl.BasicSpell;
import software.bigbade.slimefunvoid.items.Items;

public class LightBendingSpell extends BasicSpell {
    public LightBendingSpell() {
        super(Researches.LIGHT_BENDING_SPELL.getResearch(), Elements.LIGHT, Items.LIGHT_BENDING_SPELL, 30);
    }

    @Override
    public boolean onCast(Player player, ItemStack wand) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1200, 0));
        player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, player.getLocation(), 50);
        return true;
    }

    @Override
    public void onStop(Player player, ItemStack wand) {
        for(Player target : Bukkit.getOnlinePlayers())
            target.showPlayer(SlimefunVoid.getInstance(), player);
    }

    @Override
    public void onBackfire(Player player, ItemStack wand) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 60, 1));
    }
}
