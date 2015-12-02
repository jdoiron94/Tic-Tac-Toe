package com.tictactoe.core;

/**
 * @author Jacob
 * @since 9/8/2015
 */
public class Player {

    private final char symbol;
    private boolean turn;
    private int score;
    private int uid;

    private String name;

    /**
     * Constructs a Player with a name, uid, and a playing symbol.
     *
     * @param name The name.
     * @param uid The unique identifier.
     * @param symbol The playing symbol.
     */
    public Player(String name, int uid, char symbol) {
        this.name = name;
        this.uid = uid;
        this.symbol = symbol;
    }

    /**
     * @return The player's name.
     */
    public String name() {
        return name;
    }

    /**
     * Sets the player's name.
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The player's game symbol.
     */
    public char symbol() {
        return symbol;
    }

    /**
     * @return <t>true</t> if it is the player's turn; otherwise, <t>false</t>.
     */
    public boolean isTurn() {
        return turn;
    }

    /**
     * Sets the player's turn status.
     *
     * @param turn The status to set.
     */
    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    /**
     * @return The player's current score.
     */
    public int score() {
        return score;
    }

    /**
     * Sets the player's score.
     *
     * @param score The score to set.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * @return The player's unique identification number.
     */
    public int uid() {
        return uid;
    }
}