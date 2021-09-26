package cf.strafe.shop.gui.impl;

import cf.strafe.data.PlayerData;
import cf.strafe.shop.Item;
import cf.strafe.shop.ItemManager;
import cf.strafe.shop.gui.Menu;
import cf.strafe.shop.nodes.BlockItem;
import cf.strafe.shop.nodes.HatItem;
import cf.strafe.shop.nodes.StickItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;

public class ItemShopGui extends Menu {
    public ItemShopGui(PlayerData data) {
        super(data, Bukkit.createInventory(null, 27, "Item Shop"));
    }

    @Override
    public void loadMenu() {
        for (Item item : ItemManager.INSTANCE.getAvailableItems()) {
            if(item instanceof BlockItem) {
                inventory.setItem(10, createGuiItem(item.getIcon(), "&f&l&o"+item.getName(), "&8Block Pack", "", "&7Cost: &6" + item.getPrice() + " coins", "", "&aClick to buy!"));
            } else if(item instanceof HatItem) {
                inventory.setItem(11, createGuiItem(item.getIcon(), "&f&l&o" + item.getName(), "&8Helmet", "", "&7Cost: &6" + item.getPrice() + " coins", "", "&aClick to buy!"));
            } else if(item instanceof StickItem) {
                inventory.setItem(12, createGuiItem(item.getIcon(), "&f&l&o" + item.getName(), "&8Stick", "", "&7Cost: &6" + item.getPrice() + " coins", "", "&aClick to buy!"));
            }
        }
        inventory.setItem(16, createGuiItem(Material.WATCH, "&7New items in:", "&6" + ItemManager.INSTANCE.getDate().convertTime()));
        inventory.setItem(26, createGuiItem(Material.ARROW, "&cBack"));
    }
}
