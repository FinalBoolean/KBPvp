package cf.strafe.shop;

import cf.strafe.KnockBackFFA;
import cf.strafe.shop.items.blocks.Default;
import cf.strafe.shop.items.hats.NoHat;
import cf.strafe.shop.items.hats.SpaceHat;
import cf.strafe.shop.items.sticks.Stick;
import cf.strafe.shop.nodes.BlockItem;
import cf.strafe.shop.nodes.HatItem;
import cf.strafe.shop.nodes.StickItem;
import cf.strafe.util.ConcurrentEvictingList;
import cf.strafe.util.CountDown;
import lombok.Getter;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
public enum ItemManager {
    INSTANCE;

    private final List<Item> items = new ArrayList<>();
    private final ConcurrentEvictingList<Item> availableItems = new ConcurrentEvictingList<>(3);

    private final CountDown date = new CountDown(3600);

    public void init() {
        items.add(new SpaceHat());
        items.add(new NoHat());
        items.add(new Default());
        items.add(new Stick());
        countDown();
    }

    public void countDown() {
        new BukkitRunnable() {
            public void run() {
                date.countDown();

                if(date.isFinished()) {
                    /*
                    If you have any other better way please send
                     */
                    final List<BlockItem> blockItems = new ArrayList<>();
                    final List<StickItem> stickItems = new ArrayList<>();
                    final List<HatItem> hatItems = new ArrayList<>();

                    for(Item item : items) {
                        if(item.getPrice() > 0) {
                            if (item instanceof BlockItem) {
                                blockItems.add((BlockItem) item);
                            } else if (item instanceof StickItem) {
                                stickItems.add((StickItem) item);
                            } else if (item instanceof HatItem) {
                                hatItems.add((HatItem) item);
                            }
                        }
                    }

                    if(blockItems.size() > 1) {
                        while(true) {
                            BlockItem blockItem = blockItems.get(new Random().nextInt(blockItems.size()));
                            if(!availableItems.contains(blockItem)) {
                                availableItems.add(blockItem);
                                break;
                            }
                        }

                    } else if(stickItems.size() > 1) {
                        while(true) {
                            StickItem stickItem = stickItems.get(new Random().nextInt(stickItems.size()));
                            if(!availableItems.contains(stickItem)) {
                                availableItems.add(stickItem);
                                break;
                            }
                        }
                    } else if(hatItems.size() > 1) {
                        while(true) {
                            HatItem hatItem = hatItems.get(new Random().nextInt(hatItems.size()));
                            if(!availableItems.contains(hatItem)) {
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
