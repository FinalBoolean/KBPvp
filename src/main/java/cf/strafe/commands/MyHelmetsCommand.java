package cf.strafe.commands;

import cf.strafe.data.DataManager;
import cf.strafe.shop.gui.impl.HelmetGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MyHelmetsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            new HelmetGui(DataManager.INSTANCE.getPlayer(player)).openGui();
        }
        return false;
    }
}
