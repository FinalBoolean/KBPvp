package cf.strafe.shop.nodes;

import cf.strafe.shop.Item;
import cf.strafe.util.ColorUtil;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Getter
public class StickItem extends Item {
    private final ItemStack stick;
    public StickItem(String name, ItemStack icon, int price) {
        super(name, icon, ItemCategory.STICK, price);
        ItemStack stick = new ItemStack(icon);
        ItemMeta itemMeta = stick.getItemMeta();
        itemMeta.setDisplayName(ColorUtil.translate("&f&l&o" + name));
        stick.setItemMeta(itemMeta);
        stick.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
        this.stick = stick;
    }
}