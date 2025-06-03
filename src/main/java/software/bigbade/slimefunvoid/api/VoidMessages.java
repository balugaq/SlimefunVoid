package software.bigbade.slimefunvoid.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import io.github.thebusybiscuit.slimefun4.utils.compatibility.VersionedPotionEffectType;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
public enum VoidMessages {
    /*TEMPLATE("测试虚无信息", "测试描述", new String[] { "第一", "第二", "第三", "第四", "第五" }, Arrays.asList(
            player -> {}, player -> {}, player -> {}, player -> {}, player -> {})),*/
    START("首次踏入 " + ChatColor.DARK_PURPLE + "虚无", "虚无能量环绕着你，仿佛有什么东西在注视着你", new String[]{"退出 " + ChatColor.DARK_PURPLE + "虚无", "尝试接触", "等待"}, Arrays.asList(
            player -> player.sendMessage(ChatColor.GREEN + "你成功地退出了"), player -> player.sendMessage(ChatColor.GREEN + "没有任何回应，你的专注被打破了。"), player -> player.sendMessage(ChatColor.GREEN + "没有任何回应，你的专注被打破了。"))),
    FIERCE_WARRIOR("你看到一位以蛮力著称的强大战士", "它似乎注意到了你", new String[]{"立即离开", "要求它服务", "与它接触", "向它伸出援手", "让它与你接触"}, Arrays.asList(
            player -> player.sendMessage(ChatColor.GREEN + "你在任何事情发生之前就离开了"), player -> {
                player.sendMessage(ChatColor.GREEN + "这个存在赋予了你部分它的力量");
                player.addPotionEffect(new PotionEffect(VersionedPotionEffectType.STRENGTH, 2, 500));
            }, player -> {
                player.sendMessage(ChatColor.GREEN + "这个存在赋予了你部分它的活力");
                player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 2, 500));
            }, player ->
                    player.sendMessage(ChatColor.RED + "这个存在觉得你根本不值得它的时间"), player -> {
                player.sendMessage(ChatColor.RED + "这个存在对你的怯懦感到厌恶");
                player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 1, 300));
            })),
    WISE_MENTOR("一位智慧导师注视着你", "它似乎承认并接受了你的存在", new String[]{"立即离开", "请求指导", "偷取一些知识",}, Arrays.asList(
    ));

    @Getter
    private final String name;
    @Getter
    private final String description;
    @Getter
    private final String[] items;
    @Getter
    private final List<Consumer<Player>> consumers;
}
