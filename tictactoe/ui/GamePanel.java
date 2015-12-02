package tictactoe.ui;

import tictactoe.util.Renderable;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jacob
 * @since 9/8/2015
 */
public class GamePanel extends JPanel {

    private final JFrame owner;

    private final List<Renderable> renderables = new ArrayList<>();

    public void addRenderable(Renderable renderable) {
        renderables.add(renderable);
    }

    @Override
    public final void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (Renderable renderable : renderables) {
            renderable.render(g);
        }
    }

    public GamePanel(JFrame owner) {
        this.owner = owner;
        new Timer(125, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        }).start();
    }

    public JFrame owner() {
        return owner;
    }
}