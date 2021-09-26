package cf.strafe.manager;

import cf.strafe.KnockBackFFA;
import cf.strafe.data.DataManager;
import cf.strafe.data.PlayerData;
import cf.strafe.util.ColorUtil;
import cf.strafe.util.scoreboard.FastBoard;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author - Eths
 */
public class ScoreboardManager {

    private final DataManager data = DataManager.INSTANCE;
    private final Map<PlayerData, FastBoard> boards = new HashMap<>();

    public ScoreboardManager() {

        new BukkitRunnable() {
            @Override
            public void run() {
                update();
            }
        }.runTaskTimerAsynchronously(KnockBackFFA.INSTANCE.getPlugin(), 0, 2);

    }

    private void update() {
        for (Map.Entry<PlayerData, FastBoard> entry : boards.entrySet()) {
            PlayerData pData = entry.getKey();
            FastBoard board = entry.getValue();

            entry.getKey().getPlayer().setPlayerListName(entry.getKey().getPlayer().getDisplayName());


            board.updateTitle(ColorUtil.translate("&3&lStrafedKit"));
            board.updateLine(0, ColorUtil.translate("&7&m------------------"));
            board.updateLine(1, ColorUtil.translate("&8» &7Ping: &b" + pData.getPlayerPing()));
            board.updateLine(2, ColorUtil.translate("&8» &7Coins: &b" + pData.getCoins()));
            board.updateLine(3, "");
            board.updateLine(4, ColorUtil.translate("&8» &7Kills: &b" + pData.getKills()));
            board.updateLine(5, ColorUtil.translate("&8» &7Deaths: &b" + pData.getDeaths()));
            board.updateLine(6, ColorUtil.translate("&8» &7Streak: &b" + pData.getKillStreak()));
            board.updateLine(7, ColorUtil.translate("&7&m------------------"));

        }
    }

    public void create(Player player) {
        FastBoard board = new FastBoard(player);

        this.boards.put(data.getPlayer(player), board);
    }

    public void remove(Player player) {
        FastBoard board = this.boards.get(data.getPlayer(player));

        this.boards.remove(data.getPlayer(player));

        if (board != null) {
            board.delete();
        }

    }

    public FastBoard get(Player player) {
        return this.boards.get(data.getPlayer(player));
    }

}