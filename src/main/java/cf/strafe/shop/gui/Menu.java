package cf.strafe.shop.gui;

import cf.strafe.data.PlayerData;
import cf.strafe.util.ColorUtil;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public abstract class Menu {
    public final PlayerData data;
    public final Inventory inventory;

    public Menu(PlayerData data, Inventory inventory) {
        this.data = data;
        this.inventory = inventory;
        loadMenu();
    }

    public void openGui() {
        data.getPlayer().openInventory(inventory);
    }

    public abstract void loadMenu();

    public ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(ColorUtil.translate(name));

        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

}
