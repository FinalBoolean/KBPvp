package cf.strafe.commands;

import cf.strafe.shop.ItemManager;
import cf.strafe.shop.nodes.BlockItem;
import cf.strafe.shop.nodes.HatItem;
import cf.strafe.shop.nodes.StickItem;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CreateHelmetCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)) return false;

        Player player = ((Player) sender).getPlayer();
        if(player.hasPermission("ffa.admin")) {
            //createstick [name] [id] [price]

            if(args.length > 2) {
                try {
                    String name = args[0];
                    String id = args[1].split(":")[0];
                    String data = args[1].split(":")[1];
                    int price = Integer.parseInt(args[2]);

                    ItemStack itemStack = new ItemStack(Integer.parseInt(id), 1, (short) Integer.parseInt(data));
                    HatItem stickItem = new HatItem(name, itemStack, price);

                    ItemManager.INSTANCE.getItems().add(stickItem);
                    player.sendMessage(ChatColor.GREEN + "Created item!");
                } catch (Exception e) {
                    player.sendMessage(ChatColor.RED + "Usage: /createhelmet [name] [id] [price]");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /createhelmet [name] [id] [price]");
            }
        } else {
            player.sendMessage(ChatColor.RED + "No permission!");
        }
        return false;
    }
}
