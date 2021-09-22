package cf.strafe.listener;

import cf.strafe.config.Config;
import cf.strafe.data.DataManager;
import cf.strafe.data.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerListener implements Listener {


    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        PlayerData data = DataManager.INSTANCE.getPlayer(event.getPlayer());
        if(event.getTo().getY() < Config.Y_LEVEL) {
            if(!data.isInArena()) {
                data.setInArena(true);
                //TODO: Give kit here
            }
        } else {
            if(data.isInArena()) {
                data.setInArena(false);
                data.spawnPlayer();

            }
        }
    }

}
