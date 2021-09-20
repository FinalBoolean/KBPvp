package cf.strafe.shop;

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


    public enum ItemCategory {
        STICK, HELMET, BLOCKS;

        public String format() {
            return StringUtils.capitalize(this.name());
        }
    }
}
