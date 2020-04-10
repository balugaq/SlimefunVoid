package software.bigbade.slimefunvoid.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import software.bigbade.slimefunvoid.api.VoidResearches;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResearchTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if(args.length == 1) {
            if("add".startsWith(args[0])) {
                return Collections.singletonList("add");
            } else if("remove".startsWith(args[0])) {
                return Collections.singletonList("remove");
            }
        } else if(args.length == 2) {
            return getPlayers(args[1]);
        } else if(args.length == 3) {
            return getResearches(args[2]);
        }
        return Collections.emptyList();
    }

    private List<String> getResearches(String arg) {
        List<String> researches = new ArrayList<>();
        for(VoidResearches research : VoidResearches.values()) {
            String name = ChatColor.stripColor(research.getResearch().getName()).replace(" ", "_");
            if(name.startsWith(arg)) {
                researches.add(name);
                if(researches.size() > 10)
                    break;
            }
        }
        return researches;
    }

    private List<String> getPlayers(String arg) {
        List<String> players = new ArrayList<>();
        for(Player player : Bukkit.getOnlinePlayers()) {
            if(player.getName().startsWith(arg)) {
                players.add(player.getName());
                if(players.size() > 10)
                    break;
            }
        }
        return players;
    }
}
