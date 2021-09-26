package cf.strafe.shop.items.blocks;

import cf.strafe.shop.nodes.BlockItem;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class DarkPack extends BlockItem {
    public DarkPack() {
        super("DARK_PACK", new ItemStack(Material.WOOL, 1, DyeColor.BLACK.getWoolData()), 800, new ItemStack(Material.COAL_BLOCK), new ItemStack(Material.OBSIDIAN));
    }
}
