package cf.strafe.events.impl;

import cf.strafe.data.PlayerData;
import cf.strafe.events.Event;
import cf.strafe.events.EventManager;
import cf.strafe.events.map.SumoMap;
import cf.strafe.util.ColorUtil;
import cf.strafe.util.Pair;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Sumo extends Event {
    private final Pair<PlayerData, PlayerData> roundedPlayers = new Pair<>();
    private final SumoMap map;
    private int round;

    public Sumo(SumoMap map, PlayerData host) {
        super(host);
        this.map = map;
        time = 600;
    }

    private List<PlayerData> participants = new ArrayList<>();

    private int seconds;

    @Override
    public void addPlayer(PlayerData player) {
        player.getPlayer().teleport(map.getSpawnLocation());
        for(PlayerData data : getPlayers()) {
            //TODO: Customize this message
            data.getPlayer().sendMessage(ChatColor.GREEN + player.getPlayer().getName() + " has joined the event.");
        }
        player.getPlayer().getInventory().clear();
        player.getPlayer().getInventory().setHelmet(null);
        player.getPlayer().getInventory().setChestplate(null);
        player.getPlayer().getInventory().setLeggings(null);
        player.getPlayer().getInventory().setBoots(null);
        player.getPlayer().updateInventory();
        super.addPlayer(player);
    }

    @Override
    public void removePlayer(PlayerData player) {
        player.getPlayer().teleport(player.getPlayer().getWorld().getSpawnLocation());
        super.removePlayer(player);
    }

    @Override
    public void update() {
        switch (state) {
            case WAITING: {
                time--;
                // This should work
                if(time % 100 == 0) {
                    TextComponent textComponent = new TextComponent(ColorUtil.translate("&6[Event] &f" + getHost().getPlayer().getName() + " &7is hosting a &fSumo Event! &a[Click to join]"));
                    textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/event join"));
                    Bukkit.broadcastMessage(ColorUtil.translate("&7"));
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.spigot().sendMessage(textComponent);
                    }
                    Bukkit.broadcastMessage(ColorUtil.translate("&7"));
                }

                if(time == 0) {
                    if (getPlayers().size() > 1) {
                        state = GameState.RUNNING;

                        Bukkit.broadcastMessage(ColorUtil.translate("&6[Event] &fSumo Event &7has started!"));
                        participants = new ArrayList<>(getPlayers());
                    } else {
                        EventManager.INSTANCE.end("Not enough players");
                    }
                }
                break;
            }
            case RUNNING: {
                if(roundedPlayers.getX() == null && roundedPlayers.getY() == null) {
                    time = 100;
                    round++;

                    Random random = new Random();

                    PlayerData randomPlayer = participants.get(random.nextInt(participants.size()));

                    PlayerData randomPlayer2 = participants.get(random.nextInt(participants.size()));

                    while (randomPlayer == randomPlayer2 && participants.size() > 1) {
                        randomPlayer2 = participants.get(random.nextInt(participants.size()));
                    }
                    if (participants.size() < 2) {
                        Bukkit.broadcastMessage(ColorUtil.translate("&6[Event] &f" + participants.get(0).getPlayer().getName() + " &7has won the &fSumo Event&7!"));
                        state = GameState.ENDING;
                    }

                    roundedPlayers.setX(randomPlayer);
                    roundedPlayers.setY(randomPlayer2);
                } else {
                    if (time == 5) {
                        getPlayers().forEach(playerData -> playerData.getPlayer().sendMessage(ColorUtil.translate("&6[Event] &eRound #" + round + ": &f" + roundedPlayers.getX().getPlayer().getName() + " &7vs &f" + roundedPlayers.getY().getPlayer().getName())));

                        roundedPlayers.getX().getPlayer().teleport(map.getFightLocation1());
                        roundedPlayers.getY().getPlayer().teleport(map.getFightLocation2());

                        PotionEffect potionEffect = PotionEffectType.JUMP.createEffect(100, 200);
                        PotionEffect potionEffect2 = PotionEffectType.SLOW.createEffect(100, 100);

                        roundedPlayers.getX().getPlayer().addPotionEffect(potionEffect);
                        roundedPlayers.getY().getPlayer().addPotionEffect(potionEffect2);

                        roundedPlayers.getX().getPlayer().addPotionEffect(potionEffect);
                        roundedPlayers.getY().getPlayer().addPotionEffect(potionEffect2);

                        roundedPlayers.getX().getPlayer().sendMessage(ColorUtil.translate("&6[Event] &7Round against &f" + roundedPlayers.getY().getPlayer().getName() + " &7starting in &f5s..."));
                        roundedPlayers.getY().getPlayer().sendMessage(ColorUtil.translate("&6[Event] &7Round against &f" + roundedPlayers.getX().getPlayer().getName() + " &7starting in &f5s..."));

                    }
                    if (time > 0 && time < 80) {
                        roundedPlayers.getX().getPlayer().teleport(map.getFightLocation1());
                        roundedPlayers.getY().getPlayer().teleport(map.getFightLocation2());

                        String roundMSG = ColorUtil.translate("&6[Event] &7Round starting in &f" + (time / 20) + "s");
                        roundedPlayers.getX().getPlayer().sendMessage(roundMSG);
                        roundedPlayers.getY().getPlayer().sendMessage(roundMSG);

                    }
                    if (participants.size() < 2) {
                        Bukkit.broadcastMessage(ColorUtil.translate("&6[Event] &f" + participants.get(0).getPlayer().getName() + " &7has won the &fSumo Event&7!"));

                        state = GameState.ENDING;
                    }
                    if (time == 20) {
                        roundedPlayers.getX().getPlayer().teleport(map.getFightLocation1());
                        roundedPlayers.getY().getPlayer().teleport(map.getFightLocation2());

                        for (PotionEffect loopEffectX : roundedPlayers.getX().getPlayer().getActivePotionEffects()) {
                            roundedPlayers.getX().getPlayer().removePotionEffect(loopEffectX.getType());
                        }

                        for (PotionEffect loopEffectY : roundedPlayers.getY().getPlayer().getActivePotionEffects()) {
                            roundedPlayers.getY().getPlayer().removePotionEffect(loopEffectY.getType());
                        }

                        roundedPlayers.getX().getPlayer().getInventory().clear();
                        roundedPlayers.getY().getPlayer().getInventory().clear();

                        PotionEffect potionEffect = PotionEffectType.DAMAGE_RESISTANCE.createEffect(999999, 100);
                        roundedPlayers.getX().getPlayer().addPotionEffect(potionEffect);
                        roundedPlayers.getY().getPlayer().addPotionEffect(potionEffect);

                        String roundMSG = ColorUtil.translate("&6[Event] &aRound has started!");
                        roundedPlayers.getX().getPlayer().sendMessage(roundMSG);
                        roundedPlayers.getY().getPlayer().sendMessage(roundMSG);
                    }
                    if (time == 0) {

                        Location player1 = roundedPlayers.getX().getPlayer().getLocation();
                        Location player2 = roundedPlayers.getY().getPlayer().getLocation();

                        if (player1.getY() <= map.getFallLevel() || !roundedPlayers.getX().getPlayer().isOnline()) {
                            participants.remove(roundedPlayers.getX());
                            roundedPlayers.getX().getPlayer().teleport(map.getSpawnLocation());
                            roundedPlayers.getY().getPlayer().teleport(map.getSpawnLocation());
                            roundedPlayers.setX(null);
                            roundedPlayers.setY(null);
                        }

                        if (player2.getY() <= map.getFallLevel() || !roundedPlayers.getY().getPlayer().isOnline()) {
                            participants.remove(roundedPlayers.getY());
                            roundedPlayers.getY().getPlayer().teleport(map.getSpawnLocation());
                            roundedPlayers.getX().getPlayer().teleport(map.getSpawnLocation());
                            roundedPlayers.setX(null);
                            roundedPlayers.setY(null);
                        }
                    }

                    if (time != 0) {
                        time--;
                    }
                }

                break;
            }
            case ENDING: {
                if(getPlayers().isEmpty()) {
                    EventManager.INSTANCE.terminate();
                } else {
                    removePlayer(getPlayers().get(0));
                }
                break;
            }
        }
    }
}

