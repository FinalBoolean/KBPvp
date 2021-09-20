package cf.strafe.shop.nodes;

import cf.strafe.shop.Item;
import lombok.Getter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

@Getter
public class StickItem extends Item {
    private final ItemStack stick;
    public StickItem(String name, ItemStack icon, int price) {
        super(name, icon, ItemCategory.STICK, price);
        ItemStack stick = new ItemStack(icon);
        stick.addEnchantment(Enchantment.KNOCKBACK, 1);
        this.stick = stick;
    }
}
