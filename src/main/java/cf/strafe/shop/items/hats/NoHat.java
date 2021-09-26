package cf.strafe.shop.items.hats;

import cf.strafe.shop.nodes.HatItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class NoHat extends HatItem {
    public NoHat() {
        super("NO_HELMET", new ItemStack(Material.BARRIER), 0);
    }
}
