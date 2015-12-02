package com.tictactoe.core;

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

    /**
     * Constructs a GameSpot object which handles who owns a particular spot in the game grid.
     *
     * @param key The numeric key used to mark the spot as one's own.
     * @param rectangle The bounding area clickable with the mouse.
     */
    public GameSpot(char key, Rectangle rectangle) {
        this.key = key;
        this.rectangle = rectangle;
    }

    /**
     * @return The numeric key pressed to claim the spot as one's own.
     */
    public char key() {
        return key;
    }

    /**
     * @return The mouse clickable area.
     */
    public Rectangle rectangle() {
        return rectangle;
    }

    /**
     * @return The beginning x coordinate from the mouse clickable area.
     */
    public int x() {
        return rectangle.x;
    }

    /**
     * @return The beginning y coordinate from the mouse clickable area.
     */
    public int y() {
        return rectangle.y;
    }

    /**
     * Copies another game spot and then derives to find its clickable area.
     *
     * @param key The numeric key to claim the spot as one's own.
     * @param x The x offset.
     * @param y The y offset.
     * @return The new GameSpot.
     */
    public GameSpot copyAndDerive(char key, int x, int y) {
        GameSpot spot = new GameSpot(key, new Rectangle(rectangle.x + x, rectangle.y + y, rectangle.width,
                rectangle.height));
        spot.setOwner(owner);
        return spot;
    }

    /**
     * Determines if a clickable spot contains the given Point.
     *
     * @param p The Point to check.
     * @return <t>true</t> if the clickable bounds contains the Point; otherwise, <t>false</t>.
     */
    public boolean contains(Point p) {
        return rectangle.contains(p);
    }

    /**
     * @return The owner of the spot.
     */
    public Player owner() {
        return owner;
    }

    /**
     * Sets the owner of the spot.
     *
     * @param owner The owner.
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }
}