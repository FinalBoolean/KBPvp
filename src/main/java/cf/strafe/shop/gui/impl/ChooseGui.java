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
        inventory.setItem(12, createGuiItem(data.getStickItem().getIcon().getType(), "&6My Sticks", "", "&7Browse, equip and preview", "&7the knockback sticks you own!"));
        inventory.setItem(13, createGuiItem(data.getHatItem().getIcon().getType(), "&6My Helmets", "", "&7Browse, equip and preview", "&7the helmets you own!"));
        inventory.setItem(14, createGuiItem(data.getBlockItem().getIcon().getType(), "&6My Blocks", "", "&7Browse, equip and preview", "&7the blocks you own!"));
        inventory.setItem(26, createGuiItem(Material.ARROW, "&cBack"));
    }
}
