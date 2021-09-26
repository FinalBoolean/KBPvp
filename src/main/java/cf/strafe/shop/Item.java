package cf.strafe.shop;

import cf.strafe.shop.nodes.BlockItem;
import cf.strafe.shop.nodes.HatItem;
import cf.strafe.shop.nodes.StickItem;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.inventory.ItemStack;

@Getter
public class Item {
    private final String name;
    private final ItemStack icon;
    private final ItemCategory category;
    private final int price;

    public Item(String name, ItemStack icon, ItemCategory category, int price) {
        this.name = name;
        this.icon = icon;
        this.category = category;
        this.price = price;
    }

    public boolean isStick() {
        return this instanceof StickItem;
    }

    public boolean isHat() {
        return this instanceof HatItem;
    }

    public boolean isBlock() {
        return this instanceof BlockItem;
    }


    public enum ItemCategory {
        STICK, HELMET, BLOCKS;

        public String format() {
            return StringUtils.capitalize(this.name());
        }
    }
}
