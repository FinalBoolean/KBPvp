package cf.strafe.events;

import cf.strafe.KnockBackFFA;
import cf.strafe.data.PlayerData;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

@Getter
public enum EventManager {
    INSTANCE;

    //Check null if running!
    private Event event;
    private int task;

    public void runGame(Event event) {
        this.event = event;
        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(KnockBackFFA.INSTANCE.getPlugin(), () -> {
            if (event != null) event.update();
        }, 0, 1);
    }

    public void end(String reason) {
        if (event == null) return;
        for(PlayerData players : event.getPlayers()) {
            players.getPlayer().sendMessage(ChatColor.RED + "The event was cancelled; reason: " + reason);
        }
        event.state = Event.GameState.ENDING;
    }

    public void terminate() {
        Bukkit.getScheduler().cancelTask(task);
        event = null;
    }
}
