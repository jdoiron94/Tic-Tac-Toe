package com.tictactoe.ui.renderable;

import com.tictactoe.core.Player;
import com.tictactoe.ui.GamePanel;
import com.tictactoe.util.Renderable;

import java.awt.*;

/**
 * @author Jacob Doiron
 * @since 9/8/2015
 */
public class Score implements Renderable {

    private static final Font DEFAULT_FONT = new Font("Tahoma", Font.PLAIN, 30);
    private static final Color GREEN = new Color(64, 122, 82);
    private static final Color RED = new Color(207, 0, 15);
    private static final BasicStroke DEFAULT_STROKE = new BasicStroke(3);

    private final GamePanel panel;
    private final Player one, two;

    /**
     * Constructs a Score object with an owning GamePanel and two players.
     *
     * @param panel The owning panel.
     * @param one The first player.
     * @param two The second player.
     */
    public Score(GamePanel panel, Player one, Player two) {
        this.panel = panel;
        this.one = one;
        this.two = two;
    }

    @Override
    public void render(Graphics2D g) {
        g.setFont(DEFAULT_FONT);
        FontMetrics metrics = g.getFontMetrics();
        String oneScore = one.name() + " - " + one.score();
        int oneWidth = metrics.stringWidth(oneScore);
        String twoScore = two.name() + " - " + two.score();
        int twoWidth = metrics.stringWidth(twoScore);
        int separatorWidth = 25;
        int fontHeight = metrics.getHeight();
        int midX = (panel.getWidth() / 2);
        int midY = (panel.getHeight() / 12);
        g.setColor(one.score() > two.score() ? GREEN : (one.score() == two.score() ? Color.ORANGE : RED));
        g.drawString(oneScore, midX - oneWidth - (separatorWidth / 2), midY);
        g.setColor(two.score() > one.score() ? GREEN : (one.score() == two.score() ? Color.ORANGE : RED));
        g.drawString(twoScore, midX + (separatorWidth / 2), midY);
        Rectangle rectangle;
        if (one.isTurn()) {
            rectangle = new Rectangle(midX - oneWidth - (separatorWidth / 2) - 5, midY - (int) (fontHeight / 1.3D),
                    oneWidth + 10, fontHeight);
        } else {
            rectangle = new Rectangle(midX + (separatorWidth / 2) - 5, midY - (int) (fontHeight / 1.3D),
                    twoWidth + 10, fontHeight);
        }
        g.setColor(Color.WHITE);
        g.setStroke(DEFAULT_STROKE);
        g.draw(rectangle);
    }
}