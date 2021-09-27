package cf.strafe.commands;

import cf.strafe.data.DataManager;
import cf.strafe.shop.gui.impl.SelectGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            new SelectGui(DataManager.INSTANCE.getPlayer(player)).openGui();
        }
        return false;
    }
}
