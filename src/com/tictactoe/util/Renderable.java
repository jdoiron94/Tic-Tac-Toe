package com.tictactoe.util;

import java.awt.Graphics2D;

/**
 * @author Jacob Doiron
 * @since 9/8/2015
 */
public interface Renderable {

    /**
     * Handles the rendering of the renderable.
     *
     * @param g The Graphics2D object to draw with.
     */
    void render(Graphics2D g);
}