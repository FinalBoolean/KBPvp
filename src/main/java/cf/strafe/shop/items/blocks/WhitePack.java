package cf.strafe.shop.items.blocks;

import cf.strafe.shop.nodes.BlockItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class WhitePack extends BlockItem {
    public WhitePack() {
        super("WHITE_PACK", new ItemStack(Material.IRON_ORE), 800, new ItemStack(Material.SNOW_BLOCK), new ItemStack(Material.QUARTZ));
    }
}
