package cf.strafe.events;

import cf.strafe.data.PlayerData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public abstract class Event {
    private final PlayerData host;
    public GameState state = GameState.WAITING;

    private final List<PlayerData> players = new ArrayList<>();

    public int time;

    public enum GameState {
        WAITING, RUNNING, ENDING
    }

    public void addPlayer(PlayerData player) {
        players.add(player);
    }

    public void removePlayer(PlayerData player) {
        players.remove(player);
    }

    public abstract void update();
}