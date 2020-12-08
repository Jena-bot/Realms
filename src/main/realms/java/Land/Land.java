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
import java.util.UUID;

public class Land {
    public UUID uuid;
    public WorldCoord coord;
    public Realm realm;
    public Human owner;
    private File data;

    //todo contructors
    public Land(WorldCoord coord, Human owner) {
        this.coord = coord;
        this.owner = owner;
        this.realm = owner.getRealm();
        this.uuid = UUID.randomUUID();

        this.data = new File(RealmsMain.database + "/humans", uuid.toString());
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.set("coord", coord.getWorldname() + "," + coord.getX() + "," + coord.getZ());
            config.set("owner", owner.getUuid().toString());
            config.set("realm", realm.getUuid());
            config.set("uuid", uuid.toString());
            config.save(data);
        } catch (IOException e) {e.printStackTrace();}
    }

    public Land(WorldCoord coord, Human owner, UUID uuid) {
        this.coord = coord;
        this.owner = owner;
        this.realm = owner.getRealm();
        this.uuid = uuid;

        this.data = new File(RealmsMain.database + "/lands", uuid.toString());
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.set("coord", coord.getWorldname() + "," + coord.getX() + "," + coord.getZ());
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
            this.data = data;
            this.coord = new WorldCoord(config.getString("coord").split(",")[0],
                    Integer.parseInt(config.getString("coord").split(",")[1]),
                    Integer.parseInt(config.getString("coord").split(",")[2]));
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

    public void setCoord(WorldCoord coord) {
        this.coord = coord;
    }

    public WorldCoord getCoord() {
        return coord;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
