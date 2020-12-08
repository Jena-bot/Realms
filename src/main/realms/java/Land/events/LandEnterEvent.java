package main.realms.java.Land.events;

import main.realms.java.Human.Human;
import main.realms.java.Human.events.HumanChunkChangeEvent;
import main.realms.java.Land.Land;
import main.realms.java.objects.WorldCoord;

public class LandEnterEvent extends HumanChunkChangeEvent {
    public Land land;

    public LandEnterEvent(WorldCoord from, Land land, Human human) {
        super(from, land.getCoord(), human);
        this.land = land;
    }

    public Land getLand() {
        return land;
    }
}
