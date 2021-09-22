package cf.strafe.data;

import cf.strafe.KnockBackFFA;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public enum DataManager {
    INSTANCE;
    private final ConcurrentHashMap<UUID, PlayerData> playerDataMap = new ConcurrentHashMap<>();

    public void addPlayer(Player player) {
        KnockBackFFA.INSTANCE.getExecutorService().execute(() -> {
            playerDataMap.put(player.getUniqueId(), new PlayerData(player));
            getPlayer(player).loadData();
        });
    }

    public void removePlayer(Player player) {
        KnockBackFFA.INSTANCE.getExecutorService().execute(() -> {
            playerDataMap.remove(player.getUniqueId(), new PlayerData(player));
            getPlayer(player).saveData();
        });
    }

    public PlayerData getPlayer(Player player) {
        return playerDataMap.get(player.getUniqueId());
    }
}
