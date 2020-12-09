package main.realms.java.Land.events;

import main.realms.java.Human.Human;
import main.realms.java.Human.events.HumanChunkChangeEvent;
import main.realms.java.Land.Land;
import main.realms.java.objects.WorldCoord;

public class LandExitEvent extends HumanChunkChangeEvent {
    public Land land;

    public LandExitEvent(Land land, WorldCoord coord, Human human) {
        super(land.getCoord(), coord, human);
        this.land = land;
    }

    public Land getLand() {
        return land;
    }
}
