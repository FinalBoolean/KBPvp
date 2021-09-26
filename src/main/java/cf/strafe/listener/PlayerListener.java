package cf.strafe.listener;

import cf.strafe.KnockBackFFA;
import cf.strafe.config.Config;
import cf.strafe.data.DataManager;
import cf.strafe.data.PlayerData;
import cf.strafe.shop.Item;
import cf.strafe.shop.ItemManager;
import cf.strafe.shop.gui.impl.*;
import cf.strafe.shop.nodes.BlockItem;
import cf.strafe.shop.nodes.HatItem;
import cf.strafe.shop.nodes.StickItem;
import cf.strafe.util.ColorUtil;
import cf.strafe.util.DecayBlock;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerListener implements Listener {


    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        PlayerData data = DataManager.INSTANCE.getPlayer(event.getPlayer());
        if (event.getTo().getY() < Config.Y_LEVEL && event.getTo().getY() > -30) {
            if (data.isEditing()) {
                data.getPlayer().sendMessage(ChatColor.RED + "You cannot do jump down right now!");
                data.getPlayer().sendMessage(ChatColor.RED + "Run /kitedit save to jump down!");
                data.getPlayer().teleport(data.getPlayer().getWorld().getSpawnLocation());
                return;
            }
            if (!data.isInArena()) {
                data.setInArena(true);
                data.loadLayout();
            }
        } else if (event.getTo().getY() < -30) {
            data.killPlayer();
        } else {
            if (data.isInArena()) {
                data.setInArena(false);
                //Don't wanna spam clear inv
                data.clearInventory();
            }
        }
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        if (event.isCancelled())
            return;
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            PlayerData player = DataManager.INSTANCE.getPlayer((Player) event.getDamager());
            PlayerData entity = DataManager.INSTANCE.getPlayer((Player) event.getEntity());

            entity.setLastAttacked(player);
            event.setDamage(0);
        }
    }

    @EventHandler
    public void onHungerDeplete(FoodLevelChangeEvent e) {
        e.setCancelled(true);
        Player player = (Player) e.getEntity();
        player.setFoodLevel(20);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.isCancelled())
            return;
        PlayerData data = DataManager.INSTANCE.getPlayer(event.getPlayer());
        Item item = ItemManager.INSTANCE.findItem(ChatColor.stripColor(event.getItemInHand().getItemMeta().getDisplayName()));
        if (item != null && item.isBlock()) {
            event.getPlayer().getInventory().setItem(event.getPlayer().getInventory().getHeldItemSlot(), data.getBlockItem().getBlock());
            new DecayBlock(event.getBlock().getLocation(), (BlockItem) item);
        } else {
            if (event.getItemInHand().getType() == Material.WEB) {
                Bukkit.getScheduler().runTaskLater(KnockBackFFA.INSTANCE.getPlugin(), () -> {
                    event.getBlock().setType(Material.AIR);
                }, 60);
            }
        }

    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            Player player = (Player) e.getWhoClicked();
            if (e.getClickedInventory() != null && e.getClickedInventory().getName() != null) {
                Inventory inventory = e.getInventory();
                PlayerData data = DataManager.INSTANCE.getPlayer(player);
                switch (inventory.getName().toLowerCase()) {
                    case "select...": {
                        e.setCancelled(true);
                        if (e.getCurrentItem() == null) return;
                        if (e.getCurrentItem().getItemMeta() == null) return;
                        if (e.getCurrentItem().getItemMeta().getDisplayName() == null) return;
                        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Item Shop")) {
                            ItemShopGui shopGui = new ItemShopGui(data);
                            shopGui.openGui();
                        } else if (e.getCurrentItem().getItemMeta().getDisplayName().contains("My Items")) {
                            ChooseGui chooseGui = new ChooseGui(data);
                            chooseGui.openGui();
                        }
                        break;
                    }
                    case "item shop": {
                        e.setCancelled(true);
                        if (e.getCurrentItem() == null) return;
                        if (e.getCurrentItem().getItemMeta() == null) return;
                        if (e.getCurrentItem().getItemMeta().getDisplayName() == null) return;
                        ;
                        Item item = ItemManager.INSTANCE.findItem(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
                        if (item != null) {
                            if (data.getPurchasedItems().contains(item)) {
                                player.sendMessage(ChatColor.RED + "You already bought this item!");
                                return;
                            }
                            if (data.purchaseItem(item)) {
                                if (item instanceof BlockItem) {
                                    player.sendMessage(ColorUtil.translate("&aYou now have the &2" + item.getName() + " block pack!"));
                                    player.sendMessage(ColorUtil.translate("&2Run /mypacks to see it"));
                                } else if (item instanceof StickItem) {
                                    player.sendMessage(ColorUtil.translate("&aYou now have the &2" + item.getName() + " stick!"));
                                    player.sendMessage(ColorUtil.translate("&2Run /mysticks to see it"));
                                } else if (item instanceof HatItem) {
                                    player.sendMessage(ColorUtil.translate("&aYou now have the &2" + item.getName() + " helmet!"));
                                    player.sendMessage(ColorUtil.translate("&2Run /myhelmets to see it"));
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "You don't have enough coins to buy this.");
                            }
                        } else if (e.getSlot() == 26) {
                            player.closeInventory();
                            new SelectGui(data).openGui();
                        }

                        break;
                    }
                    case "choose...": {
                        e.setCancelled(true);
                        if (e.getSlot() == 12) {
                            player.closeInventory();
                            new SticksGui(data).openGui();
                        } else if (e.getSlot() == 13) {
                            player.closeInventory();
                            new HelmetGui(data).openGui();
                        } else if (e.getSlot() == 14) {
                            player.closeInventory();
                            new BlockGui(data).openGui();
                        } else if (e.getSlot() == 26) {
                            player.closeInventory();
                            new SelectGui(data).openGui();
                        }
                        break;
                    }
                    case "your block packs":
                    case "your helmets":
                    case "your sticks": {
                        e.setCancelled(true);
                        if (e.getCurrentItem() == null) return;
                        if (e.getCurrentItem().getItemMeta() == null) return;
                        if (e.getCurrentItem().getItemMeta().getDisplayName() == null) return;

                        Item item = ItemManager.INSTANCE.findItem(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
                        if (item != null) {
                            if (item instanceof BlockItem) {
                                data.setBlockItem((BlockItem) item);
                            } else if (item instanceof StickItem) {
                                data.setStickItem((StickItem) item);
                            } else if (item instanceof HatItem) {
                                data.setHatItem((HatItem) item);
                            }

                            player.sendMessage(ChatColor.GREEN + "You selected " + item.getName());

                        }
                        if (e.getSlot() == 53) {
                            player.closeInventory();
                            new ChooseGui(data).openGui();
                        }
                        break;
                    }
                }
            }
        }
    }

}
