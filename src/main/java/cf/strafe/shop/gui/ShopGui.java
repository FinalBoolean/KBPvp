package cf.strafe.shop.gui;

import cf.strafe.data.PlayerData;
import cf.strafe.util.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ShopGui {
    private final PlayerData data;
    private final Inventory inventory;

    public ShopGui(PlayerData playerData) {
        this.data = playerData;
        this.inventory = Bukkit.createInventory(null, 27, "Select...");
        loadMenu();
    }

    public void openGui(HumanEntity entity) {
        entity.openInventory(inventory);
    }

    public void loadMenu() {
        for(int i = 0; i < 9; i++) {
            inventory.setItem(i, new ItemStack(Material.STAINED_GLASS));
        }
    }


    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(ColorUtil.translate(name));

        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

}
