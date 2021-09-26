package cf.strafe.util;

import cf.strafe.KnockBackFFA;
import cf.strafe.shop.nodes.BlockItem;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockBreakAnimation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Random;

public class DecayBlock {

    public DecayBlock(Location location, BlockItem blockItem) {
        final int breakId = new Random().nextInt(2000);
        final int[] stage = {0};
        final int[] progress = {-1};
        final int[] count = {0};
        final int[] id = {0};
        id[0] = Bukkit.getScheduler().scheduleSyncRepeatingTask(KnockBackFFA.INSTANCE.getPlugin(), () -> {
            if(count[0]++ > 2) {
                stage[0]++;
                count[0] = 0;
            }
            if(stage[0] > 3) {
                progress[0] = 0;
            }
            if(progress[0] >= 0) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    PacketPlayOutBlockBreakAnimation packetPlayOutBlockBreakAnimation = new PacketPlayOutBlockBreakAnimation(breakId, new BlockPosition(location.getX(), location.getY(), location.getZ()), progress[0]);
                    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutBlockBreakAnimation);
                }
            }
            switch (stage[0]) {
                case 1:
                    location.getBlock().setTypeIdAndData(blockItem.getPhase1().getData().getItemTypeId(), blockItem.getPhase1().getData().getData(), false);
                    break;
                case 2:
                    location.getBlock().setTypeIdAndData(blockItem.getPhase2().getData().getItemTypeId(), blockItem.getPhase2().getData().getData(), false);
                    break;
                case 3:
                    location.getBlock().setType(Material.AIR);
                    Bukkit.getScheduler().cancelTask(id[0]);
                    break;
            }
            progress[0]++;
        }, 0, 10);
    }
}
