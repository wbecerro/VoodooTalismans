package wbe.voodooTalismans.config;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Messages {

    public String noPermission;
    public String notEnoughArgs;
    public String reload;
    public String talismanGiven;
    public String talismanGiveArguments;
    public String talismanRemoved;
    public String talismanRemoveArguments;
    public String noTalismansFound;
    public String pageNotFound;
    public String menuNextPage;
    public String menuPreviousPage;
    public String cannotActivateMore;
    public List<String> help = new ArrayList<>();

    public Messages(FileConfiguration config) {
        noPermission = config.getString("Messages.noPermission").replace("&", "§");
        notEnoughArgs = config.getString("Messages.notEnoughArgs").replace("&", "§");
        reload = config.getString("Messages.reload").replace("&", "§");
        talismanGiven = config.getString("Messages.talismanGiven").replace("&", "§");
        talismanGiveArguments = config.getString("Messages.talismanGiveArguments").replace("&", "§");
        talismanRemoved = config.getString("Messages.talismanRemoved").replace("&", "§");
        talismanRemoveArguments = config.getString("Messages.talismanRemoveArguments").replace("&", "§");
        noTalismansFound = config.getString("Messages.noTalismansFound").replace("&", "§");
        pageNotFound = config.getString("Messages.pageNotFound").replace("&", "§");
        menuNextPage = config.getString("Messages.menuNextPage").replace("&", "§");
        menuPreviousPage = config.getString("Messages.menuPreviousPage").replace("&", "§");
        cannotActivateMore = config.getString("Messages.cannotActivateMore").replace("&", "§");
        help = config.getStringList("Messages.help");
    }
}
