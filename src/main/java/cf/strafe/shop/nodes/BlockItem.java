package cf.strafe.shop.nodes;


import cf.strafe.shop.Item;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
public abstract class BlockItem extends Item {

    private final ItemStack phase1, phase2;

    public BlockItem(String name, ItemStack icon, int price, ItemStack phase1, ItemStack phase2) {
        super(name, icon, ItemCategory.BLOCKS, price);
        this.phase1 = phase1;
        this.phase2 = phase2;
    }
}
