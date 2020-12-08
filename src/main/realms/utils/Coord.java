package main.realms.utils;

public class Coord {
    /**
     * A class to hold and calculate Coordinates in a grid according to the size
     * defined in
     * the static field size.
     * modified minorly by Jena-bot
     *
     * @author Shade
     */
    private static final int cellSize = 16;
    private int x;
    private int z;

    public Coord(int x, int z) {

        this.x = x;
        this.z = z;
    }

    public Coord(Coord Coord) {

        this.x = Coord.getX();
        this.z = Coord.getZ();
    }

    public int getX() {

        return x;
    }

    public int getZ() {

        return z;
    }

    public Coord add(int xOffset, int zOffset) {

        return new Coord(getX() + xOffset, getZ() + zOffset);
    }

}
