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
import java.util.concurrent.atomic.AtomicInteger;

public class DecayBlock {

    public DecayBlock(Location location, BlockItem blockItem) {
        final int breakId = new Random().nextInt(2000);
        final AtomicInteger stage = new AtomicInteger(0);
        final AtomicInteger progress = new AtomicInteger(-1);
        final AtomicInteger count = new AtomicInteger(0);
        final AtomicInteger id = new AtomicInteger(0);
        id.set(Bukkit.getScheduler().scheduleSyncRepeatingTask(KnockBackFFA.INSTANCE.getPlugin(), () -> {
            if(count.getAndIncrement() > 2) {
                stage.getAndIncrement();
                count.set(0);
            }
            if(stage.get() > 3) {
                progress.set(0);
            }
            if(progress.get() >= 0) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    PacketPlayOutBlockBreakAnimation packetPlayOutBlockBreakAnimation = new PacketPlayOutBlockBreakAnimation(breakId, new BlockPosition(location.getX(), location.getY(), location.getZ()), progress.get());
                    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutBlockBreakAnimation);
                }
            }
            switch (stage.get()) {
                case 1:
                    location.getBlock().setTypeIdAndData(blockItem.getPhase1().getData().getItemTypeId(), blockItem.getPhase1().getData().getData(), false);
                    break;
                case 2:
                    location.getBlock().setTypeIdAndData(blockItem.getPhase2().getData().getItemTypeId(), blockItem.getPhase2().getData().getData(), false);
                    break;
                case 3:
                    location.getBlock().setType(Material.AIR);
                    Bukkit.getScheduler().cancelTask(id.get());
                    break;
            }
            progress.getAndIncrement();
        }, 0, 10));
    }
}
