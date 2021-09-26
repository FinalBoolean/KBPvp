package cf.strafe.shop.items.sticks;

import cf.strafe.shop.nodes.StickItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class DiamondStick extends StickItem {
    public DiamondStick() {
        super("DIAMOND_STICK", new ItemStack(Material.DIAMOND_SWORD), 800);
    }
}
