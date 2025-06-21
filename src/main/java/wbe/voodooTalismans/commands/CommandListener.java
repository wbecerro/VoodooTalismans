package wbe.voodooTalismans.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wbe.voodooTalismans.VoodooTalismans;

public class CommandListener implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("VoodooTalismans")) {
            Player player = null;
            if(sender instanceof Player) {
                player =(Player) sender;
            }

            if(args.length == 0 || args[0].equalsIgnoreCase("help")) {
                if(!sender.hasPermission("voodootalismans.command.help")) {
                    sender.sendMessage(VoodooTalismans.messages.noPermission);
                    return false;
                }

                for(String line : VoodooTalismans.messages.help) {
                    sender.sendMessage(line.replace("&", "§"));
                }
            }
        }

        return true;
    }
}
