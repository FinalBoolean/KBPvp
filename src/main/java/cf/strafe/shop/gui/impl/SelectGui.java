package cf.strafe.shop.gui.impl;

import cf.strafe.data.PlayerData;
import cf.strafe.shop.gui.Menu;
import cf.strafe.util.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class SelectGui extends Menu {

    public SelectGui(PlayerData data) {
        super(data, Bukkit.createInventory(null, 27, "Select..."));
        loadMenu();
    }

    @Override
    public void loadMenu() {
        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, new ItemStack(Material.STAINED_GLASS, 1, DyeColor.LIGHT_BLUE.getDyeData()));
        }
        inventory.setItem(12, createGuiItem(Material.ENDER_CHEST, "&6&o&lItem Shop", "", "&7Purchase custom block packs and knockback sticks using coins!"));
        inventory.setItem(14, createGuiItem(Material.CHEST, "&6&o&lMy Items", "", "&7Browse, equip and preview the cosmetics you own!"));
    }
}
