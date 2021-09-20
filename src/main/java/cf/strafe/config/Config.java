package cf.strafe.config;

import cf.strafe.KnockBackFFA;
import lombok.experimental.UtilityClass;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;


public class Config {


    public static String KILL_MESSAGE, PREFIX, DEATH_MESSAGE, STREAK_MESSAGE;

    /**
     * messages:
     *   prefix: "&aKBPvp"
     *   streak:
     *     message: "&a%player% is on a %streak% streak!"
     *   killMessages:
     *     deathMessage: "&cYou were knocked by %player%"
     *     killMessage: "&aYou knocked %player%"
     */

    public static void loadConfigurations() {
        FileConfiguration config = KnockBackFFA.INSTANCE.getPlugin().getConfig();
        KILL_MESSAGE = config.getString("messages.killMessages.killMessage");
        PREFIX = config.getString("messages.prefix");
        DEATH_MESSAGE = config.getString("messages.killMessages.deathMessage");
        STREAK_MESSAGE = config.getString("messages.streak.message");
    }
}
