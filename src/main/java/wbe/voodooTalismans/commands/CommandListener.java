package wbe.voodooTalismans.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wbe.voodooTalismans.VoodooTalismans;
import wbe.voodooTalismans.config.PlayerTalisman;
import wbe.voodooTalismans.config.Talisman;
import wbe.voodooTalismans.listeners.MenuListener;

public class CommandListener implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("VoodooTalismans")) {
            Player player = null;
            if(sender instanceof Player) {
                player = (Player) sender;
            }

            if(args.length == 0 || args[0].equalsIgnoreCase("help")) {
                if(!sender.hasPermission("voodootalismans.command.help")) {
                    sender.sendMessage(VoodooTalismans.messages.noPermission);
                    return false;
                }

                for(String line : VoodooTalismans.messages.help) {
                    sender.sendMessage(line.replace("&", "§"));
                }
            } else if(args[0].equalsIgnoreCase("give")) {
                if(!sender.hasPermission("voodootalismans.command.give")) {
                    sender.sendMessage(VoodooTalismans.messages.noPermission);
                    return false;
                }

                if(args.length < 2) {
                    sender.sendMessage(VoodooTalismans.messages.notEnoughArgs);
                    sender.sendMessage(VoodooTalismans.messages.talismanGiveArguments);
                    return false;
                }

                if(args.length > 2) {
                    player = Bukkit.getServer().getPlayer(args[2]);
                }

                Talisman talisman = VoodooTalismans.config.talismans.get(args[1]);
                PlayerTalisman playerTalisman = VoodooTalismans.utilities.searchPlayerTalisman(player, talisman);
                if(playerTalisman != null) {
                    boolean ok = playerTalisman.levelUp();
                    String message = ok ?
                            VoodooTalismans.messages.talismanLevelUp
                                    .replace("%name%", playerTalisman.getType().getName())
                                    .replace("%level%", String.valueOf(playerTalisman.getLevel())) :
                            VoodooTalismans.messages.talismanCannotLevelUp
                                    .replace("%name%", playerTalisman.getType().getName());
                    player.sendMessage(message);
                    return true;
                }

                VoodooTalismans.utilities.addTalismanToPlayer(player, talisman);
                sender.sendMessage(VoodooTalismans.messages.talismanGiven.replace("%talisman%", talisman.getName()));
            } else if(args[0].equalsIgnoreCase("remove")) {
                if(!sender.hasPermission("voodootalismans.command.remove")) {
                    sender.sendMessage(VoodooTalismans.messages.noPermission);
                    return false;
                }

                if(args.length < 2) {
                    sender.sendMessage(VoodooTalismans.messages.notEnoughArgs);
                    sender.sendMessage(VoodooTalismans.messages.talismanRemoveArguments);
                    return false;
                }

                Talisman talisman = VoodooTalismans.config.talismans.get(args[1]);
                if(args.length > 2) {
                    player = Bukkit.getServer().getPlayer(args[2]);
                }

                if(VoodooTalismans.utilities.removeTalismanFromPlayer(player, talisman)) {
                    sender.sendMessage(VoodooTalismans.messages.talismanRemoved.replace("%talisman%", talisman.getName()));
                } else {
                    sender.sendMessage(VoodooTalismans.messages.playerDoesNotHaveTalisman);
                    return false;
                }
            } else if(args[0].equalsIgnoreCase("list")) {
                if(!sender.hasPermission("voodootalismans.command.list")) {
                    sender.sendMessage(VoodooTalismans.messages.noPermission);
                    return false;
                }

                try {
                    MenuListener.openMenu(player, 1);
                } catch(Exception e) {
                    sender.sendMessage(e.getMessage());
                }
            } else if(args[0].equalsIgnoreCase("reload")) {
                if(!sender.hasPermission("voodootalismans.command.reload")) {
                    sender.sendMessage(VoodooTalismans.messages.noPermission);
                    return false;
                }

                VoodooTalismans.getInstance().reloadConfiguration();
                sender.sendMessage(VoodooTalismans.messages.reload);
            }
        }

        return true;
    }
}
