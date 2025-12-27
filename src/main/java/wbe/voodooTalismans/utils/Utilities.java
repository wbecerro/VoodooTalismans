package wbe.voodooTalismans.utils;

import me.pustinek.itemfilter.ItemFilterPlugin;
import me.pustinek.itemfilter.users.User;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.PermissionAttachmentInfo;
import wbe.voodooTalismans.VoodooTalismans;
import wbe.voodooTalismans.config.PlayerTalisman;
import wbe.voodooTalismans.config.Talisman;
import wbe.voodooTalismans.events.PlayerGetTalismanEvent;

import java.io.File;
import java.io.IOException;
import java.util.*;

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
                playerConfig.set("talismans." + talismanId + ".level", talisman.getLevel());
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
            int level = playerConfig.getInt("talismans." + talismanId + ".level");
            PlayerTalisman playerTalisman = new PlayerTalisman(talisman, player, active, level);
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
                if(permission.getPermission().startsWith("voodootalismans.max.")) {
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

    public PlayerTalisman searchPlayerTalisman(Player player, Talisman talisman) {
        ArrayList<PlayerTalisman> talismans = VoodooTalismans.playerTalismans.getOrDefault(player, new ArrayList<>());
        for(PlayerTalisman playerTalisman : talismans) {
            if(playerTalisman.getType().getId().equalsIgnoreCase(talisman.getId())) {
                return playerTalisman;
            }
        }

        return null;
    }

    public void addTalismanToPlayer(Player player, Talisman talisman) {
        int current = VoodooTalismans.playerTalismans.get(player).size();
        VoodooTalismans.getInstance().getServer().getPluginManager().callEvent(new PlayerGetTalismanEvent(player,
                new PlayerTalisman(talisman, player, false, 1),
                current,
                current + 1));
    }

    public boolean removeTalismanFromPlayer(Player player, Talisman talisman) {
        ArrayList<PlayerTalisman> talismans = VoodooTalismans.playerTalismans.getOrDefault(player, new ArrayList<>());
        boolean removed = talismans.remove(searchPlayerTalisman(player, talisman));
        ArrayList<PlayerTalisman> activeTalismans = VoodooTalismans.activeTalismans.get(player);
        if(removed && activeTalismans != null) {
            for(PlayerTalisman activeTalisman : activeTalismans) {
                if(activeTalisman.getType().getId().equalsIgnoreCase(talisman.getId())) {
                    activeTalismans.remove(activeTalisman);
                    break;
                }
            }
        }
        VoodooTalismans.activeTalismans.put(player, activeTalismans);
        VoodooTalismans.playerTalismans.put(player, talismans);

        return removed;
    }

    public List<Talisman> getMissingTalismans(Player player) {
        List<Talisman> talismans = new ArrayList<>(VoodooTalismans.config.talismans.values());
        for(PlayerTalisman playerTalisman : VoodooTalismans.playerTalismans.get(player)) {
            talismans.remove(playerTalisman.getType());
        }

        return talismans;
    }

    public boolean canAddItemToInventory(Player player, ItemStack item) {
        // Si hay un espacio libre
        if(player.getInventory().firstEmpty() != -1) {
            if(isFilterPrevented(item, player)) {
                return false;
            }
            return true;
        } else {
            // Si no hay hueco, pero tiene ese objeto en el inventario
            if(player.getInventory().contains(item)) {
                int remaining = getRemainingAmount(player.getInventory().all(item));
                if(remaining > 0) {
                    if(isFilterPrevented(item, player)) {
                        return false;
                    }
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    private boolean isFilterPrevented(ItemStack item, Player player) {
        Optional<User> user = ItemFilterPlugin.getInstance().getUserManager().getUser(player.getUniqueId());
        if(!user.isEmpty()) {
            if(user.get().isEnabled()) {
                if(user.get().getMaterials().contains(item.getType())) {
                    ItemMeta meta = item.getItemMeta();
                    if(meta == null) {
                        return true;
                    } else if(!meta.hasDisplayName() && !meta.hasLore() && !meta.hasEnchants()) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }

        return false;
    }

    private int getRemainingAmount(HashMap<Integer, ? extends ItemStack> slots) {
        int remaining = 0;
        for(ItemStack item : slots.values()) {
            remaining += item.getMaxStackSize() - item.getAmount();
        }

        return remaining;
    }
}
