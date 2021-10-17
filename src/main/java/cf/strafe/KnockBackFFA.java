package cf.strafe;

import cf.strafe.commands.*;
import cf.strafe.config.Config;
import cf.strafe.data.DataManager;
import cf.strafe.listener.DataListener;
import cf.strafe.listener.PlayerListener;
import cf.strafe.manager.BroadcastManager;
import cf.strafe.manager.MapManager;
import cf.strafe.manager.ScoreboardManager;
import cf.strafe.shop.Item;
import cf.strafe.shop.ItemManager;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Getter
public enum KnockBackFFA {
    INSTANCE;

    /**
     * TODO: Add rotational maps?
     */

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    private KnockBackPlugin plugin;
    private ScoreboardManager scoreboardManager;
    private BroadcastManager broadcastManager;

    public void onLoad(KnockBackPlugin plugin) {
        this.plugin = plugin;
        final File f = new File(plugin.getDataFolder(), "config.yml");
        if (!f.exists()) {
            plugin.saveResource("config.yml", true);
        }
    }

    public void onEnable() {
        Config.loadConfigurations();
        ItemManager.INSTANCE.init();
        MapManager.INSTANCE.load();
        scoreboardManager = new ScoreboardManager();
        broadcastManager = new BroadcastManager();
        handleBukkit();

        Bukkit.getOnlinePlayers().forEach(DataManager.INSTANCE::addPlayer);
    }

    public void onDisable() {
        MapManager.INSTANCE.save();
        ItemManager.INSTANCE.saveItems();
        DataManager.INSTANCE.saveAll();
        System.out.println("Disabling KnockBack core");
    }

    public void handleBukkit() {
        System.out.println("Registering bukkit api");
        plugin.getServer().getPluginManager().registerEvents(new PlayerListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new DataListener(), plugin);
        plugin.getCommand("myhelmets").setExecutor(new MyHelmetsCommand());
        plugin.getCommand("myhelmets").setExecutor(new MyHelmetsCommand());
        plugin.getCommand("mypacks").setExecutor(new MyPacksCommand());
        plugin.getCommand("mysticks").setExecutor(new MySticksCommand());
        plugin.getCommand("kitedit").setExecutor(new KitEditCommand());
        plugin.getCommand("shop").setExecutor(new ShopCommand());
        plugin.getCommand("createhelmet").setExecutor(new CreateHelmetCommand());
        plugin.getCommand("createpack").setExecutor(new CreatePackCommand());
        plugin.getCommand("createstick").setExecutor(new CreateStickCommand());
    }
}
