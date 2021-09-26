package cf.strafe.shop.gui.impl;

import cf.strafe.data.PlayerData;
import cf.strafe.shop.gui.Menu;
import org.bukkit.Bukkit;
import org.bukkit.Material;

public class ChooseGui extends Menu {
    public ChooseGui(PlayerData data) {
        super(data, Bukkit.createInventory(null, 27, "Choose..."));
    }

    @Override
    public void loadMenu() {
        inventory.setItem(12, createGuiItem(Material.STICK, "&6My Sticks", "", "&7Browse, equip and preview", "the knockback sticks you own!"));
        inventory.setItem(13, createGuiItem(Material.SKULL, "&6My Helmets", "", "Browse, equip and preview", "the helmets you own!"));
        inventory.setItem(14, createGuiItem(Material.SANDSTONE, "&6My Blocks", "", "Browse, equip and preview", "the blocks you own!"));
        inventory.setItem(26, createGuiItem(Material.ARROW, "&cBack"));
    }
}
