package cf.strafe.data;

import cf.strafe.shop.Item;
import cf.strafe.shop.nodes.BlockItem;
import cf.strafe.shop.nodes.HatItem;
import cf.strafe.shop.nodes.StickItem;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

@Getter
public class PlayerData {
    private final Player player;
    private final UUID uuid;
    private StickItem stickItem;
    private BlockItem blockItem;
    private HatItem hatItem;

    @Getter(AccessLevel.NONE) private final ArrayList<Item> items = new ArrayList<>();
    private int coins;

    public PlayerData(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
    }

    public boolean purchaseItem(Item item) {
        return purchaseItem(item, false);
    }

    public boolean purchaseItem(Item item, boolean bypassCharge) {
        if(item.getPrice() > coins && !bypassCharge)
            return false;
        coins -= item.getPrice();
        items.add(item);
        return true;
    }
}
