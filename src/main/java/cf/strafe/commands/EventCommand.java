package cf.strafe.commands;

import cf.strafe.data.DataManager;
import cf.strafe.data.PlayerData;
import cf.strafe.events.Event;
import cf.strafe.events.EventManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EventCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            PlayerData data = DataManager.INSTANCE.getPlayer(player);
            if (EventManager.INSTANCE.getEvent() == null) return false;
            if (EventManager.INSTANCE.getEvent().state != Event.GameState.WAITING) return false;
            switch (args[0].toLowerCase()) {
                case "join": {
                    if (EventManager.INSTANCE.getEvent().getPlayers().contains(data)) return false;
                    EventManager.INSTANCE.getEvent().addPlayer(data);
                    break;
                }
                case "leave": {
                    if (!EventManager.INSTANCE.getEvent().getPlayers().contains(data)) return false;
                    EventManager.INSTANCE.getEvent().removePlayer(data);
                    break;
                }
            }
        }
        return false;
    }
}
