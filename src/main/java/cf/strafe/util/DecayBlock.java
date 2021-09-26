package cf.strafe.util;

import cf.strafe.KnockBackFFA;
import cf.strafe.shop.nodes.BlockItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class DecayBlock {

    public DecayBlock(Location location, BlockItem blockItem) {

        final int[] stage = {0};
        final int[] id = {0};
        id[0] = Bukkit.getScheduler().scheduleSyncRepeatingTask(KnockBackFFA.INSTANCE.getPlugin(), () -> {
            stage[0]++;
            switch (stage[0]) {
                case 1:
                    location.getBlock().setType(blockItem.getIcon().getType());
                    break;
                case 2:
                    location.getBlock().setType(blockItem.getPhase1().getType());
                    break;
                case 3:
                    location.getBlock().setType(blockItem.getPhase2().getType());
                    break;
                case 4:
                    Bukkit.getScheduler().cancelTask(id[0]);
                    break;
            }
        }, 0, 40);
    }
}
