package cf.strafe;

import cf.strafe.config.Config;
import cf.strafe.listener.DataListener;
import cf.strafe.listener.PlayerListener;
import cf.strafe.shop.ItemManager;
import lombok.Getter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Getter
public enum KnockBackFFA {
    INSTANCE;

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    private KnockBackPlugin plugin;

    public void onLoad(KnockBackPlugin plugin) {
        this.plugin = plugin;
    }

    public void onEnable() {
        handleBukkit();
        ItemManager.INSTANCE.init();
        Config.loadConfigurations();
    }

    public void onDisable() {
        System.out.println("Disabling KnockBack core");
    }

    public void handleBukkit() {
        System.out.println("Registering bukkit api");
        plugin.getServer().getPluginManager().registerEvents(new PlayerListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new DataListener(), plugin);
    }
}
