package cf.strafe.shop.gui.impl;

import cf.strafe.data.PlayerData;
import cf.strafe.shop.Item;
import cf.strafe.shop.gui.Menu;
import org.bukkit.Bukkit;
import org.bukkit.Material;

public class BlockGui extends Menu {
    public BlockGui(PlayerData data) {
        super(data, Bukkit.createInventory(null, 54, "Your Block Packs"));
    }

    @Override
    public void loadMenu() {
        data.getPurchasedItems().stream().filter(Item::isHat).forEach(item -> {
            inventory.addItem(createGuiItem(item.getIcon().getType(), "&f&o&l" + item.getName(), "&8Block Pack", "", "&aClick to select"));
        });
        inventory.setItem(53, createGuiItem(Material.ARROW, "Back"));
    }
}
