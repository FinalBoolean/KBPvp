package cf.strafe.manager;

import cf.strafe.KnockBackFFA;
import cf.strafe.events.map.SumoMap;
import cf.strafe.util.LocationUtil;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
public enum MapManager {
    INSTANCE;

    private final List<SumoMap> sumoMaps = new ArrayList<>();

    public void load() {
        loadSumo();
    }

    public void save() {
        saveSumo();
    }

    public SumoMap getSumoMap(String name) {
        for(SumoMap maps : sumoMaps) {
            if(maps.getMapName().equalsIgnoreCase(name)) {
                return maps;
            }
        }
        return null;
    }


    private void saveSumo() {
        File file = new File(KnockBackFFA.INSTANCE.getPlugin().getDataFolder(), "SumoArenas.yml");

        if (!file.exists()) {


            try {
                file.createNewFile();
            } catch (IOException ignored) {

            }

        }
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        for (SumoMap arena : sumoMaps) {
            yml.set(arena.getMapName() + ".spawnLocation", LocationUtil.parseToString(arena.getSpawnLocation()));
            yml.set(arena.getMapName() + ".fight1", LocationUtil.parseToString(arena.getFightLocation1()));
            yml.set(arena.getMapName() + ".fight2", LocationUtil.parseToString(arena.getFightLocation2()));
            yml.set(arena.getMapName() + ".fallLevel", arena.getFallLevel());
            yml.set(arena.getMapName() + ".name", arena.getMapName());
        }

        try {
            yml.save(file);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    private void loadSumo() {
        File file = new File(KnockBackFFA.INSTANCE.getPlugin().getDataFolder(), "SumoArenas.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            for (String key : config.getKeys(false)) {
                String arenaName = config.getString(key + ".name");
                Location spawnLocation = LocationUtil.parseToLocation(config.getString(key + ".spawnLocation"));
                Location fight1 = LocationUtil.parseToLocation(config.getString(key + ".fight1"));
                Location fight2 = LocationUtil.parseToLocation(config.getString(key + ".fight2"));
                int fallLevel = config.getInt(key + ".fallLevel");
                sumoMaps.add(new SumoMap(arenaName, spawnLocation, fight1, fight2, fallLevel));
                System.out.println("Loading Arena " + arenaName);
            }
        }
    }
}
