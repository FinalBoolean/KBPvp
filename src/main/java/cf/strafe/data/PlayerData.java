package cf.strafe.data;

import cf.strafe.KnockBackFFA;
import cf.strafe.config.Config;
import cf.strafe.shop.Item;
import cf.strafe.shop.ItemManager;
import cf.strafe.shop.nodes.BlockItem;
import cf.strafe.shop.nodes.HatItem;
import cf.strafe.shop.nodes.StickItem;
import cf.strafe.util.ColorUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Pattern;

@Getter
@Setter
public class PlayerData {
    private final Player player;
    private final UUID uuid;
    private final ArrayList<Item> purchasedItems = new ArrayList<>();
    private StickItem stickItem;
    private BlockItem blockItem;
    private HatItem hatItem;
    private int coins, deaths, kills, killStreak, stickSlot, blockSlot, webSlot, pearlSlot;
    private boolean inArena, editing;
    private PlayerData lastAttacked;

    public PlayerData(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
        stickSlot = 0;
        blockSlot = 1;
        webSlot = 2;
        pearlSlot = 3;
    }

    public boolean purchaseItem(Item item) {
        return purchaseItem(item, false);
    }

    public boolean purchaseItem(Item item, boolean bypassCharge) {
        if (item.getPrice() > coins && !bypassCharge)
            return false;
        coins -= item.getPrice();
        purchasedItems.add(item);
        return true;
    }

    public void clearInventory() {
        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
    }

    public void loadLayout() {
        player.getInventory().setItem(getStickSlot(), stickItem.getStick());
        player.getInventory().setItem(getBlockSlot(), new ItemStack(blockItem.getIcon().getType(), 64));
        player.getInventory().setItem(getPearlSlot(), new ItemStack(Material.ENDER_PEARL));
        player.getInventory().setItem(getWebSlot(), new ItemStack(Material.WEB));
        player.getInventory().setHelmet(hatItem.getIcon());
        player.updateInventory();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void saveData() {
        final File dir = new File(KnockBackFFA.INSTANCE.getPlugin().getDataFolder(), "data");

        if(!dir.exists()) {
            dir.mkdirs();
        }

        final File player = new File(dir, getPlayer().getUniqueId() + ".yml");
        if(!player.exists()) {
            try {
                player.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            final YamlConfiguration load = YamlConfiguration.loadConfiguration(player);
            load.set("kills", getKills());
            load.set("killStreak", getKillStreak());
            load.set("deaths", getDeaths());
            load.set("coins", getCoins());
            load.set("blockItem", blockItem.getName());
            load.set("stickItem", stickItem.getName());
            load.set("hatItem", stickItem == null ? "Null" : stickItem.getName());
            for(Item item : purchasedItems) {
                load.set("items." + item.getName(), item.getName());
            }
        }
    }

    public void killPlayer() {
        if(lastAttacked != null) {
            lastAttacked.loadLayout();
            player.sendMessage(ColorUtil.translate(Config.DEATH_MESSAGE).replace("%player%", lastAttacked.getPlayer().getName()));
            lastAttacked.setKills(lastAttacked.getKills() + 1);
            lastAttacked.setKillStreak(lastAttacked.getKillStreak() + 1);
            setKillStreak(0);
            setDeaths(getDeaths() + 1);
        } else player.sendMessage(ColorUtil.translate(Config.DEATH_MESSAGE));
        clearInventory();
        player.teleport(player.getWorld().getSpawnLocation());
    }
    /*
    https://www.spigotmc.org/threads/get-player-ping-with-reflection.147773/
     */
    public int getPlayerPing() {
        try {
            Class<?> craftPlayer = Class.forName("org.bukkit.craftbukkit." + getServerVersion() + ".entity.CraftPlayer");
            Object converted = craftPlayer.cast(player);
            Method handle = converted.getClass().getMethod("getHandle", new Class[0]);
            Object entityPlayer = handle.invoke(converted, new Object[0]);
            Field pingField = entityPlayer.getClass().getField("ping");
            return pingField.getInt(entityPlayer);
        } catch (Exception e) {
            return 0;
        }
    }

    private String getServerVersion() {
        Pattern brand = Pattern.compile("(v|)[0-9][_.][0-9][_.][R0-9]*");

        String pkg = Bukkit.getServer().getClass().getPackage().getName();
        String version = pkg.substring(pkg.lastIndexOf('.') + 1);
        if (!brand.matcher(version).matches()) {
            version = "";
        }

        return version;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void loadData() {
        final File dir = new File(KnockBackFFA.INSTANCE.getPlugin().getDataFolder(), "data");

        if (!dir.exists())
            dir.mkdirs();

        final File player = new File(dir, getPlayer().getUniqueId() + ".yml");
        if (!player.exists()) {
            try {
                player.createNewFile();
                setBlockItem((BlockItem) ItemManager.INSTANCE.findItem("DEFAULT_PACK"));
                setStickItem((StickItem) ItemManager.INSTANCE.findItem("DEFAULT_STICK"));
                setBlockItem((BlockItem) ItemManager.INSTANCE.findItem("NO_HELMET"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            final YamlConfiguration load = YamlConfiguration.loadConfiguration(player);
            setKills(load.getInt("kills"));
            setKillStreak(load.getInt("killStreak"));
            setDeaths(load.getInt("deaths"));
            setCoins(load.getInt("coins"));
            for(String key : load.getKeys(false)) {
                purchasedItems.add(ItemManager.INSTANCE.findItem(load.getString("items." + key)));
            }
            setBlockItem((BlockItem) ItemManager.INSTANCE.findItem(load.getString("blockItem")));
            setStickItem((StickItem) ItemManager.INSTANCE.findItem(load.getString("stickItem")));
            setHatItem((HatItem) ItemManager.INSTANCE.findItem(load.getString("hatItem")));
        }

    }
}
