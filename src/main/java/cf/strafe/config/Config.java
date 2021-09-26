package cf.strafe.config;

import cf.strafe.KnockBackFFA;
import org.bukkit.configuration.file.FileConfiguration;


public class Config {


    public static String KILL_MESSAGE, PREFIX, DEATH_MESSAGE, STREAK_MESSAGE;
    public static int Y_LEVEL;
    public static long DELAY;

    public static int BROADCASTS;

    /**
     * messages:
     *   prefix: "&aKBPvp"
     *   streak:
     *     message: "&a%player% is on a %streak% streak!"
     *   killMessages:
     *     deathMessage: "&cYou were knocked by %player%"
     *     killMessage: "&aYou knocked %player%"
     *
     * settings:
     *   yLevel:
     */

    public static void loadConfigurations() {
        FileConfiguration config = KnockBackFFA.INSTANCE.getPlugin().getConfig();
        KILL_MESSAGE = config.getString("messages.killMessages.killMessage");
        PREFIX = config.getString("messages.prefix");
        DEATH_MESSAGE = config.getString("messages.killMessages.deathMessage");
        STREAK_MESSAGE = config.getString("messages.streak.message");
        Y_LEVEL = config.getInt("settings.yLevel");
        DELAY = config.getLong("broadcast-delay");

        for (String key : config.getConfigurationSection("announcements").getKeys(false)) {
            BROADCASTS++;
        }

    }
}
