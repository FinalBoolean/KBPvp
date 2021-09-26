package cf.strafe.util;

import cf.strafe.KnockBackFFA;
import cf.strafe.shop.nodes.BlockItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

public class DecayBlock {

    public DecayBlock(Location location, BlockItem blockItem) {

        final int[] stage = {0};
        final int[] id = {0};
        id[0] = Bukkit.getScheduler().scheduleSyncRepeatingTask(KnockBackFFA.INSTANCE.getPlugin(), () -> {
            stage[0]++;
            switch (stage[0]) {
                case 1:
                    location.getBlock().setTypeIdAndData(blockItem.getIcon().getData().getItemTypeId(), blockItem.getIcon().getData().getData(), false);

                    break;
                case 2:
                    location.getBlock().setTypeIdAndData(blockItem.getPhase1().getData().getItemTypeId(), blockItem.getPhase1().getData().getData(), false);
                    break;
                case 3:
                    location.getBlock().setTypeIdAndData(blockItem.getPhase2().getData().getItemTypeId(), blockItem.getPhase2().getData().getData(), false);
                    break;
                case 4:
                    location.getBlock().setType(Material.AIR);
                    Bukkit.getScheduler().cancelTask(id[0]);
                    break;
            }
        }, 0, 40);
    }
}
