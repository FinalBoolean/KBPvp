package cf.strafe.commands;

import cf.strafe.shop.ItemManager;
import cf.strafe.shop.nodes.BlockItem;
import cf.strafe.shop.nodes.StickItem;
import com.mojang.authlib.yggdrasil.response.MinecraftTexturesPayload;
import net.minecraft.server.v1_8_R3.ItemBlock;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CreatePackCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)) return false;

        Player player = ((Player) sender).getPlayer();
        if(player.hasPermission("ffa.admin")) {
            //createpack [name] [id] [id] [id] [price]

            if(args.length > 4) {
                try {
                    String name = args[0];
                    int price = Integer.parseInt(args[4]);

                    String id = args[1].split(":")[0];
                    String data = args[1].split(":")[1];

                    String id2 = args[2].split(":")[0];
                    String data2 = args[2].split(":")[1];

                    String id3 = args[3].split(":")[0];
                    String data3 = args[3].split(":")[1];

                    ItemStack block1 = new ItemStack(Integer.parseInt(id), 1, (short) Integer.parseInt(data));
                    ItemStack block2 = new ItemStack(Integer.parseInt(id2), 1, (short) Integer.parseInt(data2));
                    ItemStack block3 = new ItemStack(Integer.parseInt(id3), 1, (short) Integer.parseInt(data3));

                    BlockItem blockItem = new BlockItem(name, block1, price, block2, block3);
                    ItemManager.INSTANCE.getItems().add(blockItem);
                    player.sendMessage(ChatColor.GREEN + "Created item!");

                } catch (Exception e) {
                    player.sendMessage(ChatColor.RED + "Usage: createpack [name] [id] [id] [id] [price]");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /createpack [name] [id] [id] [id] [price]");
            }
        } else {
            player.sendMessage(ChatColor.RED + "No permission!");
        }
        return false;
    }
}
