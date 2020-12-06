package main.realms.java.Land;

import main.realms.java.Human.Human;

import java.awt.geom.Point2D;
import java.io.File;

public class Land {
    public Point2D coord1;
    public Point2D coord2;
    public Human owner;
    private File data;

    // to check if inside land
    public boolean Inside(Point2D coord) {
        return coord.getX() > coord1.getX() && coord.getX() < coord2.getX() &&
                coord.getY() > coord1.getY() && coord.getY() < coord2.getY();
    }

    // getters and setters

    public Point2D getCoord1() {
        return coord1;
    }

    public Point2D getCoord2() {
        return coord2;
    }

    public Human getOwner() {
        return owner;
    }

    public void setCoord1(Point2D coord1) {
        this.coord1 = coord1;
    }

    public void setCoord2(Point2D coord2) {
        this.coord2 = coord2;
    }

    public void setOwner(Human owner) {
        this.owner = owner;
    }

    private double getHypo() {
        double x1 = Math.abs(coord1.getY() - coord2.getY());
        double x2 = Math.abs(coord1.getX() - coord2.getX());
        return Math.hypot(x1, x2);
    }
}
