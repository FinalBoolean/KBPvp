package cf.strafe.kb;

import org.bukkit.plugin.java.JavaPlugin;

public class KBPvp extends JavaPlugin {

    public static KBPvp INSTANCE;

    @Override
    public void onEnable() {
        System.out.println("Initializing plugin!");
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onLoad() {
        INSTANCE = this;
        super.onLoad();
    }
}
