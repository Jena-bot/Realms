package main.realms.java.Land;

import main.realms.java.Human.Human;
import main.realms.java.Realm.Realm;
import main.realms.java.RealmsAPI;
import main.realms.java.RealmsMain;
import main.realms.java.objects.WorldCoord;
import main.realms.utils.exceptions.RealmsException;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Land {
    public String name;
    public UUID uuid;
    public List<WorldCoord> coords;
    public Realm realm;
    public Human owner;
    private File data;

    //todo contructors
    public Land(List<WorldCoord> coords, Human owner, String name) {
        this.name = name;
        this.coords = coords;
        this.owner = owner;
        this.realm = owner.getRealm();
        this.uuid = UUID.randomUUID();

        this.data = new File(RealmsMain.database + "/humans", uuid.toString());
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.set("name", name);

            // multiple chunks
            List<String> coordlist = new ArrayList<>();
            for (WorldCoord coord : coords)
                coordlist.add(coord.getWorldname() + "," + coord.getX() + "," + coord.getZ());
            config.set("coods", coordlist);

            config.set("owner", owner.getUuid().toString());
            config.set("realm", realm.getUuid().toString());
            config.set("uuid", uuid.toString());
            config.save(data);
        } catch (IOException e) {e.printStackTrace();}
    }

    public Land(List<WorldCoord> coords, Human owner, UUID uuid, String name) {
        this.coords = coords;
        this.owner = owner;
        this.realm = owner.getRealm();
        this.uuid = uuid;

        this.data = new File(RealmsMain.database + "/lands", uuid.toString());
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.set("name", name);
            //config.set("coord", coord.getWorldname() + "," + coord.getX() + "," + coord.getZ());

            // multiple chunks branch (Jena-bot)
            List<String> coordlist = new ArrayList<>();
            for (WorldCoord coord : coords)
                coordlist.add(coord.getWorldname() + "," + coord.getX() + "," + coord.getZ());
            config.set("coods", coordlist);

            config.set("owner", owner.getUuid().toString());
            config.set("realm", "");
            config.set("uuid", uuid.toString());
            config.save(data);
        } catch (IOException e) {e.printStackTrace();}
    }

    public Land(File data) throws RealmsException {
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.load(data);
            this.name = config.getString("name");
            this.uuid = UUID.fromString(config.getString("uuid"));
            this.data = data;

            // multiple chunks
            List<String> coords = config.getStringList("coords");
            for (String s : coords)
                this.coords.add(new WorldCoord(s.split(",")[0],
                        Integer.parseInt(s.split(",")[1]),
                        Integer.parseInt(s.split(",")[2])));

            this.owner = RealmsAPI.getHuman(UUID.fromString(config.getString("uuid")));
            //todo realm
            this.realm = null;

        } catch (InvalidConfigurationException | IOException | RealmsException e) {
            throw new RealmsException("201");
        }
    }

    // getters and setters

    public void setRealm(Realm realm) {
        this.realm = realm;
    }

    public Realm getRealm() {
        return realm;
    }

    public Human getOwner() {
        return owner;
    }

    public void setOwner(Human owner) {
        this.owner = owner;
    }

    public List<WorldCoord> getCoords() {
        return coords;
    }

    public void setCoords(List<WorldCoord> coords) {
        this.coords = coords;
    }

    public void addCoord(WorldCoord coord) {
        coords.add(coord);
    }

    public void removeCoord(WorldCoord coord) {
        coords.remove(coord);
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
