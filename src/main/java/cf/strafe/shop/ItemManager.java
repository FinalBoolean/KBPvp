package cf.strafe.shop;

import cf.strafe.shop.items.blocks.Default;
import cf.strafe.shop.items.sticks.Stick;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum ItemManager {
    INSTANCE;

    private final List<Item> items = new ArrayList<>();


    public void init() {
        items.add(new Default());
        items.add(new Stick());
    }

    public Item findItem(String name) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(name))
                return item;
        }
        return null;
    }
}
