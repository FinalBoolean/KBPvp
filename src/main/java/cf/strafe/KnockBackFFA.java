package cf.strafe;

import cf.strafe.shop.Item;
import cf.strafe.shop.ItemManager;
import lombok.Getter;

@Getter
public enum KnockBackFFA {
    INSTANCE;

    private KnockBackPlugin plugin;

    public void onLoad(KnockBackPlugin plugin) {
        this.plugin = plugin;
    }

    public void onEnable() {
        ItemManager.INSTANCE.init();
    }

    public void onDisable() {

    }
}
