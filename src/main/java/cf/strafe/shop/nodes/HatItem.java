package cf.strafe.shop.nodes;

import cf.strafe.shop.Item;
import org.bukkit.inventory.ItemStack;

public class HatItem extends Item {
    public HatItem(String name, ItemStack icon, int price) {
        super(name, icon, ItemCategory.HELMET, price);
    }
}
