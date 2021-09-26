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
            PlayerData playerData = new PlayerData(player);
            playerDataMap.put(player.getUniqueId(), playerData);
            playerData.loadData();
            KnockBackFFA.INSTANCE.getScoreboardManager().create(player);
        });
    }

    public void removePlayer(Player player) {
        KnockBackFFA.INSTANCE.getExecutorService().execute(() -> {
            getPlayer(player).saveData();
            playerDataMap.remove(player.getUniqueId(), new PlayerData(player));
        });
    }

    public PlayerData getPlayer(Player player) {
        return playerDataMap.get(player.getUniqueId());
    }
}
