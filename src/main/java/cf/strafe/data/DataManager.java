package cf.strafe.data;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public enum DataManager {
    INSTANCE;
    private final ConcurrentHashMap<UUID, PlayerData> playerDataMap = new ConcurrentHashMap<>();

    public void addPlayer(Player player) {
        playerDataMap.put(player.getUniqueId(), new PlayerData(player));
    }

    public void removePlayer(Player player) {
        playerDataMap.remove(player.getUniqueId(), new PlayerData(player));
    }

    public PlayerData getPlayer(Player player) {
        return playerDataMap.get(player.getUniqueId());
    }
}
