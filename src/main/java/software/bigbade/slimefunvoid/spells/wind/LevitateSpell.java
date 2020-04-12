package software.bigbade.slimefunvoid.spells.wind;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import software.bigbade.slimefunvoid.api.Elements;
import software.bigbade.slimefunvoid.api.research.Researches;
import software.bigbade.slimefunvoid.impl.BasicSpell;
import software.bigbade.slimefunvoid.items.Items;
import software.bigbade.slimefunvoid.spells.ender.SwapSpell;

public class LevitateSpell extends BasicSpell {
    public LevitateSpell() {
        super(Researches.LEVITATE_SPELL.getResearch(), Elements.WIND, Items.LEVITATE_SPELL, 5);
    }

    @Override
    public boolean onCast(Player player, ItemStack wand) {
        float distance = getMultipliedDamage(wand, 20, Elements.VOID);
        for(Entity entity : player.getNearbyEntities(distance, 5, distance)) {
            if(entity instanceof LivingEntity && SwapSpell.getLookingAt(player, entity)) {
                addLevitation((LivingEntity) entity, wand);
                return true;
            }
        }
        player.sendMessage(ChatColor.RED + "You have to look at a target in range!");
        return false;
    }

    @Override
    public void onBackfire(Player player, ItemStack wand) {
        addLevitation(player, wand);
    }

    private void addLevitation(LivingEntity target, ItemStack wand) {
        target.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, (int) getMultipliedDamage(wand, 1, Elements.WIND), (int) getMultipliedDamage(wand, 10, Elements.WIND)*20));
    }
}
