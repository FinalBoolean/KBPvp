package cf.strafe;

import org.bukkit.plugin.java.JavaPlugin;

public class KnockBackPlugin extends JavaPlugin {

    @Override
    public void onLoad() {
        KnockBackFFA.INSTANCE.onLoad(this);

        super.onLoad();
    }

    @Override
    public void onEnable() {
        KnockBackFFA.INSTANCE.onEnable();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        KnockBackFFA.INSTANCE.onDisable();
        super.onDisable();
    }
}
