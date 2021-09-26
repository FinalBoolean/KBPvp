package cf.strafe.shop.items.blocks;

import cf.strafe.shop.nodes.BlockItem;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Default extends BlockItem {
    public Default() {
        super("DEFAULT_PACK",new ItemStack(Material.WOOL, 1, DyeColor.GREEN.getData()), 0, new ItemStack(Material.WOOL, 1, DyeColor.YELLOW.getData()), new ItemStack(Material.WOOL, 1, DyeColor.RED.getData()));
    }
}