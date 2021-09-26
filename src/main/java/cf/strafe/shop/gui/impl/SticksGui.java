package cf.strafe.shop.gui.impl;

import cf.strafe.data.PlayerData;
import cf.strafe.shop.Item;
import cf.strafe.shop.ItemManager;
import cf.strafe.shop.gui.Menu;
import org.bukkit.Bukkit;
import org.bukkit.Material;

public class SticksGui extends Menu {
    public SticksGui(PlayerData data) {
        super(data, Bukkit.createInventory(null, 54, "Your Sticks"));
    }

    @Override
    public void loadMenu() {
        ItemManager.INSTANCE.getItems().stream().filter(Item::isStick).forEach(item -> {
            inventory.addItem(createGuiItem(item.getIcon().getType(), "&f&l&o" + item.getName(), "Custom Stick", "", "&aClick to Select"));
        });
        inventory.setItem(53, createGuiItem(Material.ARROW, "Back"));
    }
}
