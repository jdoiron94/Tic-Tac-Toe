package tictactoe.core;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * @author Jacob
 * @since 9/8/2015
 */
public class GameSpot {

    private final char key;
    private Rectangle rectangle;
    private Player owner;

    public GameSpot(char key, Rectangle rectangle) {
        this.key = key;
        this.rectangle = rectangle;
    }

    public char key() {
        return key;
    }

    public Rectangle rectangle() {
        return rectangle;
    }

    public int x() {
        return rectangle.x;
    }

    public int y() {
        return rectangle.y;
    }

    public GameSpot copyAndDerive(char key, int x, int y) {
        GameSpot spot = new GameSpot(key, new Rectangle(rectangle.x + x, rectangle.y + y, rectangle.width,
                rectangle.height));
        spot.setOwner(owner);
        return spot;
    }

    public boolean contains(Point p) {
        return rectangle.contains(p);
    }

    public Player owner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}