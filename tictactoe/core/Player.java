package tictactoe.core;

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

    public Player(String name, int uid, char symbol) {
        this.name = name;
        this.uid = uid;
        this.symbol = symbol;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char symbol() {
        return symbol;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public int score() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int uid() {
        return uid;
    }
}