package wbe.voodooTalismans.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;
import wbe.voodooTalismans.VoodooTalismans;
import wbe.voodooTalismans.config.PlayerTalisman;
import wbe.voodooTalismans.config.Talisman;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class Utilities {

    public AttributeModifier searchModifier(Collection<AttributeModifier> modifiers, NamespacedKey key) {
        for(AttributeModifier modifier : modifiers) {
            if(modifier.getKey().equals(key)) {
                return modifier;
            }
        }

        return null;
    }

    public void savePlayerData(Player player) {
        try {
            File playerFile = new File(
                    VoodooTalismans.getInstance().getDataFolder(), "saves/" + player.getUniqueId() + ".yml"
            );
            boolean fileCreated = playerFile.createNewFile();
            FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
            ArrayList<PlayerTalisman> talismans = VoodooTalismans.playerTalismans.get(player);

            talismans.forEach(talisman -> {
                String talismanId = talisman.getType().getId();
                playerConfig.set("talismans." + talismanId + ".active", talisman.isActive());
            });

            playerConfig.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while saving the " + player.getName() + " data.");
        }
    }

    public void loadPlayerData(Player player) {
        File playerFile = new File(
                VoodooTalismans.getInstance().getDataFolder(), "saves/" + player.getUniqueId() + ".yml"
        );
        ArrayList<PlayerTalisman> talismans = new ArrayList<>();
        if(!playerFile.exists()) {
            VoodooTalismans.playerTalismans.put(player, talismans);
            return;
        }

        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
        if(playerConfig.getKeys(false).isEmpty()) {
            VoodooTalismans.playerTalismans.put(player, talismans);
            return;
        }

        ArrayList<PlayerTalisman> activeTalismans = new ArrayList<>();
        Set<String> talismanIds = playerConfig.getConfigurationSection("talismans").getKeys(false);
        for(String talismanId : talismanIds) {
            Talisman talisman = VoodooTalismans.config.talismans.get(talismanId);
            if(talisman == null) {
                continue;
            }

            boolean active = playerConfig.getBoolean("talismans." + talismanId + ".active");
            PlayerTalisman playerTalisman = new PlayerTalisman(talisman, player, active);
            talismans.add(playerTalisman);
            if(active) {
                activeTalismans.add(playerTalisman);
            }
        }

        VoodooTalismans.playerTalismans.put(player, talismans);
        if(!activeTalismans.isEmpty()) {
            VoodooTalismans.activeTalismans.put(player, activeTalismans);
        }
    }

    public int getMaxTalismanCount(Player player) {
        int max = 1;
        for(PermissionAttachmentInfo permission : player.getEffectivePermissions()) {
            if(permission.getValue()) {
                if(permission.getPermission().startsWith("voodootalisman.max.")) {
                    int amount = Integer.parseInt(permission.getPermission().replace("voodootalismans.max.", ""));
                    if(amount > max) {
                        max = amount;
                    }
                }
            }
        }

        return max;
    }

    public int getActiveTalismansCount(Player player) {
        ArrayList<PlayerTalisman> talismans = VoodooTalismans.activeTalismans.get(player);
        if(talismans == null || talismans.isEmpty()) {
            return 0;
        }

        return talismans.size();
    }

    public PlayerTalisman getPlayerTalisman(Player player, Talisman talisman) {
        ArrayList<PlayerTalisman> talismans = VoodooTalismans.playerTalismans.get(player);
        if(talismans == null || talismans.isEmpty()) {
            return null;
        }

        for(PlayerTalisman playerTalisman : talismans) {
            if(playerTalisman.getType() == talisman) {
                return playerTalisman;
            }
        }

        return null;
    }


}
