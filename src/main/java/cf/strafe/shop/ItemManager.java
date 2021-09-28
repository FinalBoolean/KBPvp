package cf.strafe.shop;

import cf.strafe.KnockBackFFA;
import cf.strafe.shop.items.blocks.Default;
import cf.strafe.shop.items.hats.NoHat;
import cf.strafe.shop.items.sticks.*;
import cf.strafe.shop.nodes.BlockItem;
import cf.strafe.shop.nodes.HatItem;
import cf.strafe.shop.nodes.StickItem;
import cf.strafe.util.ConcurrentEvictingList;
import cf.strafe.util.CountDown;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
public enum ItemManager {
    INSTANCE;


    private final List<BlockItem> blockItems = new ArrayList<>();
    private final List<StickItem> stickItems = new ArrayList<>();
    private final List<HatItem> hatItems = new ArrayList<>();

    private final List<Item> items = new ArrayList<>();
    private final ConcurrentEvictingList<Item> availableItems = new ConcurrentEvictingList<>(3);

    private final CountDown date = new CountDown(3600);

    public void init() {
        //Add hard coded base items ...
        items.add(new NoHat());
        items.add(new Default());
        items.add(new Stick());
        loadItems();
        pickRandom();
        countDown();
    }

    public void pickRandom() {
        if(blockItems.size() > 0) {
            BlockItem blockItem = blockItems.get(new Random().nextInt(blockItems.size()));
            availableItems.add(blockItem);
        }
        if(stickItems.size() > 0) {
            StickItem stickItem = stickItems.get(new Random().nextInt(stickItems.size()));
            availableItems.add(stickItem);
        }
        if(hatItems.size() > 0) {
            HatItem hatItem = hatItems.get(new Random().nextInt(hatItems.size()));
            availableItems.add(hatItem);
        }
    }

    public void loadItems() {
        File file = new File(KnockBackFFA.INSTANCE.getPlugin().getDataFolder(), "items.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        YamlConfiguration load = YamlConfiguration.loadConfiguration(file);

        for (String key : load.getKeys(false)) {
            String name = load.getString(key + ".name");
            ItemStack icon = load.getItemStack(key + ".icon");
            int price = load.getInt(key + ".price");
            Item.ItemCategory category = Item.ItemCategory.valueOf(load.getString(key + ".category"));

            switch (category) {
                case STICK: {
                    StickItem stickItem = new StickItem(name, icon, price);
                    items.add(stickItem);
                    stickItems.add(stickItem);
                    break;
                }
                case BLOCKS: {
                    ItemStack phase2 = load.getItemStack(key + ".phase2");
                    ItemStack phase3 = load.getItemStack(key + ".phase3");
                    BlockItem blockItem = new BlockItem(name, icon, price, phase2, phase3);
                    items.add(blockItem);
                    blockItems.add(blockItem);
                    break;
                }
                case HELMET: {
                    HatItem hatItem = new HatItem(name, icon, price);
                    items.add(new HatItem(name, icon, price));
                    hatItems.add(hatItem);
                    break;
                }
            }
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void saveItems() {
        //Remove hard coded items
        items.remove(ItemManager.INSTANCE.findItem("NO_HELMET"));
        items.remove(ItemManager.INSTANCE.findItem("DEFAULT_PACK"));
        items.remove(ItemManager.INSTANCE.findItem("DEFAULT_STICK"));

        File file = new File(KnockBackFFA.INSTANCE.getPlugin().getDataFolder(), "items.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        YamlConfiguration load = YamlConfiguration.loadConfiguration(file);
        for (Item item : items) {
            load.set(item.getName() + ".name", item.getName());
            load.set(item.getName() + ".icon", item.getIcon());
            load.set(item.getName() + ".category", item.getCategory().name());
            load.set(item.getName() + ".price", item.getPrice());
            if (item.isBlock()) {
                BlockItem blockItem = (BlockItem) item;
                load.set(item.getName() + ".phase2", blockItem.getPhase1());
                load.set(item.getName() + ".phase3", blockItem.getPhase2());
            }
        }
        try {
            load.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void countDown() {
        new BukkitRunnable() {
            public void run() {
                date.countDown();

                if (date.isFinished()) {
                    /*
                    If you have any other better way please send
                     */

                    if (blockItems.size() > 1) {
                        while (true) {
                            BlockItem blockItem = blockItems.get(new Random().nextInt(blockItems.size()));
                            if (!availableItems.contains(blockItem)) {
                                availableItems.add(blockItem);
                                break;
                            }
                        }

                    }
                    if (stickItems.size() > 1) {
                        while (true) {
                            StickItem stickItem = stickItems.get(new Random().nextInt(stickItems.size()));
                            if (!availableItems.contains(stickItem)) {
                                availableItems.add(stickItem);
                                break;
                            }
                        }
                    }
                    if (hatItems.size() > 1) {
                        while (true) {
                            HatItem hatItem = hatItems.get(new Random().nextInt(hatItems.size()));
                            if (!availableItems.contains(hatItem)) {
                                availableItems.add(hatItem);
                                break;
                            }
                        }
                    }

                    date.resetTime();
                }
            }
        }.runTaskTimerAsynchronously(KnockBackFFA.INSTANCE.getPlugin(), 0, 20);
    }

    public Item findItem(String name) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(name))
                return item;
        }
        return null;
    }
}
