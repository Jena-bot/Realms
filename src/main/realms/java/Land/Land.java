package main.realms.java.Land;

import main.realms.java.Human.Human;

import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Land {
    public static Point2D coord1;
    public static Point2D coord2;
    public static Human owner;
    private static File data;

    // to check if inside land
    public boolean Inside(Point2D coord) {
        List<Point2D> points = new ArrayList<>();
        points.add(coord1);
        points.add(coord2);

        for (Point2D point : points) {
            double x1 = Math.abs(point.getY() - coord.getY());
            double x2 = Math.abs(point.getX() - coord.getX());
            if (Math.hypot(x1, x2) < getHypo()) {
                return true;
            }
        }
        return false;
    }

    // getters and setters

    public static Point2D getCoord1() {
        return coord1;
    }

    public static Point2D getCoord2() {
        return coord2;
    }

    public static Human getOwner() {
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
