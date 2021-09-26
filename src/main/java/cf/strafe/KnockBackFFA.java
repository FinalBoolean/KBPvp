package cf.strafe;

import cf.strafe.commands.MyHelmetsCommand;
import cf.strafe.commands.MyPacksCommand;
import cf.strafe.commands.MySticksCommand;
import cf.strafe.config.Config;
import cf.strafe.data.DataManager;
import cf.strafe.listener.DataListener;
import cf.strafe.listener.PlayerListener;
import cf.strafe.manager.BroadcastManager;
import cf.strafe.manager.ScoreboardManager;
import cf.strafe.shop.ItemManager;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Getter
public enum KnockBackFFA {
    INSTANCE;

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    private KnockBackPlugin plugin;
    private ScoreboardManager scoreboardManager;
    private BroadcastManager broadcastManager;

    public void onLoad(KnockBackPlugin plugin) {
        this.plugin = plugin;
    }

    public void onEnable() {
        Config.loadConfigurations();
        ItemManager.INSTANCE.init();
        scoreboardManager = new ScoreboardManager();
        broadcastManager = new BroadcastManager();
        handleBukkit();

        Bukkit.getOnlinePlayers().forEach(DataManager.INSTANCE::addPlayer);
    }

    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(DataManager.INSTANCE::removePlayer);
        System.out.println("Disabling KnockBack core");
    }

    public void handleBukkit() {
        System.out.println("Registering bukkit api");
        plugin.getServer().getPluginManager().registerEvents(new PlayerListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new DataListener(), plugin);
        plugin.getCommand("myhelmets").setExecutor(new MyHelmetsCommand());
        plugin.getCommand("mypacks").setExecutor(new MyPacksCommand());
        plugin.getCommand("mysticks").setExecutor(new MySticksCommand());
    }
}
