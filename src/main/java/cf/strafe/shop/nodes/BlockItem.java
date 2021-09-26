package cf.strafe.shop.nodes;


import cf.strafe.shop.Item;
import cf.strafe.util.ColorUtil;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Getter
public class BlockItem extends Item {

    private final ItemStack phase1, phase2;
    private final ItemStack block;

    public BlockItem(String name, ItemStack icon, int price, ItemStack phase1, ItemStack phase2) {
        super(name, icon, ItemCategory.BLOCKS, price);
        this.phase1 = phase1;
        this.phase2 = phase2;
        ItemStack block = new ItemStack(icon);
        block.setAmount(64);
        ItemMeta itemMeta = block.getItemMeta();
        itemMeta.setDisplayName(ColorUtil.translate("&f&l&o" +name));
        block.setItemMeta(itemMeta);
        this.block = block;
    }
}
