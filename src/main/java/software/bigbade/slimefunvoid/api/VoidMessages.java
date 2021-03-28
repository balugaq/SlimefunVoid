package software.bigbade.slimefunvoid.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
public enum VoidMessages {
    /*TEMPLATE("Test void message", "Test description", new String[] { "First", "Second", "Third", "Fourth", "Fifth" }, Arrays.asList(
            player -> {}, player -> {}, player -> {}, player -> {}, player -> {})),*/
    START("First Journey into " + ChatColor.DARK_PURPLE + "The Void", "Void energy surrounds you, it feels like something is looking back at you", new String[]{"Exit " + ChatColor.DARK_PURPLE + "The Void", "Reach out", "Wait"}, Arrays.asList(
            player -> player.sendMessage(ChatColor.GREEN + "You successfully exited"), player -> player.sendMessage(ChatColor.GREEN + "Nothing answered, and your concentration broke."), player -> player.sendMessage(ChatColor.GREEN + "Nothing answered, and your concentration broke."))),
    FIERCE_WARRIOR("You see a strong warrior, renown for his raw strength", "It seems to have noticed you", new String[]{"Leave immediately", "Demand its services", "Contact it", "Reach out to it", "Let it contact you"}, Arrays.asList(
            player -> player.sendMessage(ChatColor.GREEN + "You left before anything could happen"), player -> {
                player.sendMessage(ChatColor.GREEN + "The being blessed you with part of its strength");
                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 2, 500));
            }, player -> {
                player.sendMessage(ChatColor.GREEN + "The being blesses you with part of its vitality");
                player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 2, 500));
            }, player ->
                    player.sendMessage(ChatColor.RED + "The being didn't feel you were worth its time"), player -> {
                player.sendMessage(ChatColor.RED + "The being was disgusted by your cowardice to speak up");
                player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 1, 300));
            })),
    WISE_MENTOR("A wise mentor gases upon you", "It seems to acknowledge, and accept your presence", new String[] { "Leave immediately", "Ask for tutoring", "Steal some knowledge", }, Arrays.asList(

    ));
    VoidMessages(String name, String description, String[] items, List<Consumer<Player>> consumers) {
		this.name = name;
		this.description = description;
		this.items = items;
		this.consumers = consumers;
    }
	@Getter
    private final String name;
    @Getter
    private final String description;
    @Getter
    private final String[] items;
    @Getter
    private final List<Consumer<Player>> consumers;
}
