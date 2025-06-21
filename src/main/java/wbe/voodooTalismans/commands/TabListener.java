package wbe.voodooTalismans.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import wbe.voodooTalismans.VoodooTalismans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabListener implements TabCompleter {

    private final List<String> subCommands = Arrays.asList("help", "give", "remove", "list", "reload");

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        if(!command.getName().equalsIgnoreCase("VoodooTalismans")) {
            return completions;
        }

        // Mostrar subcomandos
        if(args.length == 1) {
            StringUtil.copyPartialMatches(args[0], subCommands, completions);
        }

        // Argumento 1
        if(args.length == 2) {
            switch(args[0].toLowerCase()) {
                case "give":
                case "remove":
                    for(String talisman : VoodooTalismans.config.talismans.keySet()) {
                        if(args[1].isEmpty()) {
                            completions.add(talisman);
                        } else if(talisman.startsWith(args[1])) {
                            completions.add(talisman);
                        }
                    }
                    break;
            }
        }

        // Argumento 2
        if(args.length == 3) {
            switch(args[0].toLowerCase()) {
                case "give":
                case "remove":
                    for(Player player : Bukkit.getOnlinePlayers()) {
                        if(args[2].isEmpty()) {
                            completions.add(player.getName());
                        } else if(player.getName().startsWith(args[2])) {
                            completions.add(player.getName());
                        }
                    }
                    break;
            }
        }

        return completions;
    }
}
