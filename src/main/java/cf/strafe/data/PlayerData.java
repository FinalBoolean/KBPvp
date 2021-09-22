package cf.strafe.data;

import cf.strafe.KnockBackFFA;
import cf.strafe.shop.Item;
import cf.strafe.shop.ItemManager;
import cf.strafe.shop.nodes.BlockItem;
import cf.strafe.shop.nodes.HatItem;
import cf.strafe.shop.nodes.StickItem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
public class PlayerData {
    private final Player player;
    private final UUID uuid;
    @Getter(AccessLevel.NONE)
    private final ArrayList<Item> items = new ArrayList<>();
    private StickItem stickItem;
    private BlockItem blockItem;
    private HatItem hatItem;
    private int coins, deaths, kills;
    private boolean inArena;

    public PlayerData(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
    }

    public boolean purchaseItem(Item item) {
        return purchaseItem(item, false);
    }

    public boolean purchaseItem(Item item, boolean bypassCharge) {
        if (item.getPrice() > coins && !bypassCharge)
            return false;
        coins -= item.getPrice();
        items.add(item);
        return true;
    }

    public void spawnPlayer() {
        player.getInventory().clear();
        player.getPlayer().getInventory().setHelmet(null);
        player.getPlayer().getInventory().setChestplate(null);
        player.getPlayer().getInventory().setLeggings(null);
        player.getPlayer().getInventory().setBoots(null);
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
            load.set("coins", getCoins());
            load.set("blockItem", blockItem.getName());
            load.set("stickItem", stickItem.getName());
            load.set("hatItem", stickItem == null ? "Null" : stickItem.getName());
            for(Item item : items) {
                load.set("items." + item.getName(), item.getName());
            }
        }
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
                setBlockItem((BlockItem) ItemManager.INSTANCE.findItem("wool"));
                setStickItem((StickItem) ItemManager.INSTANCE.findItem("stick"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            final YamlConfiguration load = YamlConfiguration.loadConfiguration(player);
            setKills(load.getInt("kills"));
            setCoins(load.getInt("coins"));
            for(String key : load.getKeys(false)) {
                items.add(ItemManager.INSTANCE.findItem(load.getString("items." + key)));
            }
            setBlockItem((BlockItem) ItemManager.INSTANCE.findItem(load.getString("blockItem")));
            setStickItem((StickItem) ItemManager.INSTANCE.findItem(load.getString("stickItem")));
            setHatItem((HatItem) ItemManager.INSTANCE.findItem(load.getString("hatItem")));
        }

    }
}
