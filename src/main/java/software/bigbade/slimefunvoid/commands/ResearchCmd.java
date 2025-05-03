package software.bigbade.slimefunvoid.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import software.bigbade.slimefunvoid.api.research.VoidResearches;
import software.bigbade.slimefunvoid.menus.research.ResearchBenchMenu;
import software.bigbade.slimefunvoid.utils.VoidResearchHelper;

import javax.annotation.Nonnull;

public class ResearchCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String label, String[] args) {
        if (args.length != 3) {
            return false;
        }
        if (!commandSender.isOp() && !commandSender.hasPermission("slimevoid.research")) {
            commandSender.sendMessage(ChatColor.RED + "您没有运行此命令的权限!");
            return true;
        }
        boolean adding = args[0].equals("add");
        if (!adding && !args[0].equals("remove")) {
            return false;
        }
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            commandSender.sendMessage(ChatColor.RED + "无法找到玩家 " + args[1] + " (玩家不在线)");
            return true;
        }
        VoidResearches researches;
        try {
            researches = VoidResearches.valueOf(ResearchBenchMenu.getEnumName(args[2]));
        } catch (IllegalArgumentException e) {
            commandSender.sendMessage(ChatColor.RED + "研究 " + args[2] + " 不存在!");
            return true;
        }
        if (adding) {
            VoidResearchHelper.addResearch(target, researches);
            commandSender.sendMessage(ChatColor.GREEN + "成功添加研究");
        }
        return true;
    }
}
