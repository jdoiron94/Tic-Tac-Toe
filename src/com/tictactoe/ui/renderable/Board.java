package com.tictactoe.ui.renderable;

import com.tictactoe.TicTacToe;
import com.tictactoe.core.GameSpot;
import com.tictactoe.core.Player;
import com.tictactoe.ui.GamePanel;
import com.tictactoe.util.Renderable;

import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Jacob
 * @since 9/8/2015
 */
public class Board implements Renderable {

    private static final BasicStroke DEFAULT_STROKE = new BasicStroke(15);

    private final TicTacToe frame;

    private final GamePanel panel;
    private final List<GameSpot[]> connectors = new ArrayList<>();

    private GameSpot leftTop, leftMid, leftBottom;
    private GameSpot midTop, midMid, midBottom;
    private GameSpot rightTop, rightMid, rightBottom;
    private GameSpot lastTurn;
    private GameSpot[] spots;

    private Player one, two, initial, winner;

    private Font symbolFont;

    private boolean canUndo;

    public Board(TicTacToe frame, GamePanel panel, Player one, Player two) {
        this.frame = frame;
        this.panel = panel;
        this.one = one;
        this.two = two;
        this.initial = one;
        panel.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                char key = e.getKeyChar();
                for (GameSpot spot : spots) {
                    if (spot.key() == key && spot.owner() == null) {
                        handleTurn(spot);
                        break;
                    }
                }
            }
        });
        panel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Point mouse = e.getPoint();
                for (GameSpot spot : spots) {
                    if (spot.contains(mouse) && spot.owner() == null) {
                        handleTurn(spot);
                        break;
                    }
                }
            }
        });
    }

    public void undo() {
        lastTurn.setOwner(null);
        if (one.isTurn()) {
            two.setTurn(true);
            one.setTurn(false);
        } else {
            one.setTurn(true);
            two.setTurn(false);
        }
        frame.setUndo(false);
    }

    public void reset(boolean tie) {
        for (GameSpot spot : spots) {
            spot.setOwner(null);
        }
        canUndo = false;
        if (tie) {
            if (initial.equals(one)) {
                two.setTurn(true);
                one.setTurn(false);
                initial = two;
            } else {
                one.setTurn(true);
                two.setTurn(false);
                initial = one;
            }
        } else {
            if (winner == null || winner.equals(one)) {
                one.setTurn(false);
                two.setTurn(true);
            } else {
                two.setTurn(false);
                one.setTurn(true);
            }
        }
    }

    private void handleTurn(GameSpot spot) {
        lastTurn = spot;
        canUndo = true;
        frame.setUndo(true);
        spot.setOwner(one.isTurn() ? one : two);
        if (one.isTurn()) {
            one.setTurn(false);
            two.setTurn(true);
        } else {
            two.setTurn(false);
            one.setTurn(true);
        }
        handleWinner();
    }

    private void handleWinner() {
        int count = 0;
        for (GameSpot s : spots) {
            if (s.owner() != null) {
                count++;
            }
        }
        winner = winner();
        if (winner != null) {
            frame.setUndo(false);
            JOptionPane.showMessageDialog(panel.owner(), winner.name() + " has won the game!", "Winner!",
                    JOptionPane.INFORMATION_MESSAGE);
            winner.setScore(winner.score() + 1);
            handleRestart(false);
        } else if (count == 9) {
            frame.setUndo(false);
            JOptionPane.showMessageDialog(panel.owner(), "The game resulted in a tie.", "Game over",
                    JOptionPane.INFORMATION_MESSAGE);
            handleRestart(true);
        }
    }

    private void handleRestart(boolean tie) {
        int selection = JOptionPane.showConfirmDialog(panel.owner(), "Would you like to play again?",
                "Round commencement", JOptionPane.YES_NO_OPTION);
        if (selection == 0) {
            handleChangeNames();
            reset(tie);
        } else {
            System.exit(0);
        }
    }

    private void handleChangeNames() {
        int selection = JOptionPane.showConfirmDialog(panel.owner(), "Would you like to change names?", "Name changes",
                JOptionPane.YES_NO_OPTION);
        if (selection == 0) {
            String p1 = JOptionPane.showInputDialog(panel.owner(), "Player one's new name:", "Player one",
                    JOptionPane.PLAIN_MESSAGE);
            String p2 = JOptionPane.showInputDialog(panel.owner(), "Player two's new name:", "Player two",
                    JOptionPane.PLAIN_MESSAGE);
            if (p1 != null && !p1.replaceAll("\\s+", "").isEmpty() && !p1.equals(one.name()) &&
                    p2 != null && !p2.replaceAll("\\s+", "").isEmpty() && !p2.equals(two.name())) {
                if (p1.length() <= 15 && p2.length() <= 15) {
                    one.setName(p1);
                    one.setScore(0);
                    two.setName(p2);
                    two.setScore(0);
                    initial = one;
                } else {
                    JOptionPane.showMessageDialog(panel.owner(), "Names must not exceed 15 characters.", "Name length",
                            JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(panel.owner(), "Names cannot be empty or the same as they are presently. " +
                        "Both names must change for the changes to take effect.", "Name conflict", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private Player winner() {
        main:
        for (GameSpot[] spots : connectors) {
            Player owner = null;
            Set<Integer> uids = new HashSet<>();
            for (GameSpot spot : spots) {
                owner = spot.owner();
                if (owner == null) {
                    continue main;
                }
                uids.add(owner.uid());
            }
            if (owner != null && uids.size() == 1) {
                return owner;
            }
        }
        return null;
    }

    private GameSpot obtainParent(GameSpot original, GameSpot current) {
        if (original != null) {
            current.setOwner(original.owner());
        }
        return current;
    }

    public boolean canUndo() {
        return canUndo;
    }

    public void setCanUndo(boolean canUndo) {
        this.canUndo = canUndo;
    }

    @Override
    public void render(Graphics2D g) {
        int panelMidX = panel.getWidth() / 2;
        int panelMidY = panel.getHeight() / 2;
        int topY = panelMidY - (panel.getHeight() / 3);
        int topLeftX = panelMidX - (int) (panel.getWidth() / 8.5D);
        int boxWidth = (int) (panel.getWidth() / 4.28D);
        int boxHeight = (int) (panel.getHeight() / 4.28D);
        if (leftTop == null || leftTop.rectangle().x != topLeftX - boxWidth || leftTop.rectangle().y != topY) {
            leftTop = obtainParent(leftTop, new GameSpot('7', new Rectangle(topLeftX - boxWidth, topY, boxWidth, boxHeight)));
            leftMid = obtainParent(leftMid, leftTop.copyAndDerive('4', 0, boxHeight));
            leftBottom = obtainParent(leftBottom, leftMid.copyAndDerive('1', 0, boxHeight));
            midTop = obtainParent(midTop, leftTop.copyAndDerive('8', boxWidth, 0));
            midMid = obtainParent(midMid, midTop.copyAndDerive('5', 0, boxHeight));
            midBottom = obtainParent(midBottom, midMid.copyAndDerive('2', 0, boxHeight));
            rightTop = obtainParent(rightTop, midTop.copyAndDerive('9', boxWidth, 0));
            rightMid = obtainParent(rightMid, rightTop.copyAndDerive('6', 0, boxHeight));
            rightBottom = obtainParent(rightBottom, rightMid.copyAndDerive('3', 0, boxHeight));
            spots = new GameSpot[]{leftTop, leftMid, leftBottom, midTop, midMid, midBottom, rightTop, rightMid, rightBottom};
            symbolFont = new Font("Tahoma", Font.BOLD, boxWidth / 2);
            connectors.clear();
            connectors.add(new GameSpot[]{leftTop, midMid, rightBottom}); // top left to bottom right
            connectors.add(new GameSpot[]{rightTop, midMid, leftBottom}); // top right to bottom left
            connectors.add(new GameSpot[]{leftTop, midTop, rightTop}); // left to right
            connectors.add(new GameSpot[]{leftMid, midMid, rightMid});
            connectors.add(new GameSpot[]{leftBottom, midBottom, rightBottom});
            connectors.add(new GameSpot[]{leftTop, leftMid, leftBottom}); // top to bottom
            connectors.add(new GameSpot[]{midTop, midMid, midBottom});
            connectors.add(new GameSpot[]{rightTop, rightMid, rightBottom});
        }
        g.setStroke(DEFAULT_STROKE);
        g.setColor(Color.BLACK);
        g.drawLine(midTop.x(), midTop.y(), midTop.x(), midBottom.y() + boxHeight);
        g.drawLine(rightTop.x(), rightTop.y(), rightTop.x(), rightBottom.y() + boxHeight);
        g.drawLine(midMid.x() - boxWidth, midMid.y(), rightMid.x() + boxWidth, midMid.y());
        g.drawLine(midMid.x() - boxWidth, midMid.y() + boxHeight, rightMid.x() + boxWidth, midMid.y() + boxHeight);
        g.setFont(symbolFont);
        FontMetrics metrics = g.getFontMetrics();
        for (GameSpot spot : spots) {
            if (spot.owner() != null) {
                String symbol = Character.toString(spot.owner().symbol());
                Rectangle2D symbolBounds = metrics.getStringBounds(symbol, g);
                int midX = spot.x() + (spot.rectangle().width / 2) - ((int) symbolBounds.getWidth() / 2);
                int midY = spot.y() + (spot.rectangle().height / 2) - (int) ((int) symbolBounds.getHeight() / 1.425D);
                g.drawString(symbol, midX, midY + (int) symbolBounds.getHeight());
            }
        }
    }
}