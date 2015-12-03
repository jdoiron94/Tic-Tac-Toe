package com.tictactoe;

import com.tictactoe.core.Player;
import com.tictactoe.ui.GamePanel;
import com.tictactoe.ui.renderable.Board;
import com.tictactoe.ui.renderable.Score;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Jacob Doiron
 * @since 9/8/2015
 */
public class TicTacToe extends JFrame {

    private static final String VERSION_NUMBER = "1.0.0";

    private final JMenuItem undo;

    private final GamePanel panel;

    /**
     * Constructs the TicTacToe frame.
     */
    public TicTacToe() {
        super("Tic Tac Toe");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        final Player playerOne = new Player(name(true), 0, 'X');
        final Player playerTwo = new Player(name(false), 1, 'O');
        playerOne.setTurn(true);
        this.panel = new GamePanel(this);
        final Board board = new Board(this, panel, playerOne, playerTwo);
        panel.addRenderable(new Score(panel, playerOne, playerTwo));
        panel.addRenderable(board);
        panel.setFocusable(true);
        panel.setBackground(Color.BLACK);
        add(panel);
        JMenuBar bar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem restart = new JMenuItem("Restart game");
        restart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playerOne.setName(name(true));
                playerOne.setScore(0);
                playerTwo.setName(name(false));
                playerTwo.setScore(0);
                board.reset(false);
            }
        });
        file.add(restart);
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        file.add(exit);
        JMenu edit = new JMenu("Edit");
        undo = new JMenuItem("Undo");
        undo.setEnabled(false);
        undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (undo.isEnabled() && board.canUndo()) {
                    board.undo();
                    board.setCanUndo(false);
                }
            }
        });
        edit.add(undo);
        JMenu help = new JMenu("Help");
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showConfirmDialog(getContentPane(), "Tic-Tac-Toe version " + VERSION_NUMBER +
                        " by Jacob Doiron, Andrew Riehl, Dylan Colyer, and Cody Forrest.", "About", JOptionPane.OK_CANCEL_OPTION);
            }
        });
        help.add(about);
        bar.add(file);
        bar.add(edit);
        bar.add(help);
        setJMenuBar(bar);
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Handles the initiation of the frame.
     */
    private void init() {
        panel.requestFocusInWindow();
        setVisible(true);
    }

    /**
     * Handles the undo function availability within the menu.
     *
     * @param enabled The undo availability status to set.
     */
    public void setUndo(boolean enabled) {
        undo.setEnabled(enabled);
    }

    /**
     * Handles the setting of player one and player two's names.
     *
     * @param first <t>true</t> if setting player one's name; otherwise, <t>false</t>.
     * @return The selected player name.
     */
    private String name(boolean first) {
        String message = "Player " + (first ? "one's" : "two's") + " name:";
        String title = "Player " + (first ? "one" : "two");
        String name;
        do {
            JOptionPane pane = new JOptionPane(message, JOptionPane.QUESTION_MESSAGE);
            pane.setWantsInput(true);
            JDialog dialog = pane.createDialog(this, title);
            dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            dialog.setVisible(true);
            name = (String) pane.getInputValue();
            if (name == null) {
                System.exit(1);
            } else if (name.replaceAll("\\s+", "").isEmpty()) {
                name = null;
                JOptionPane.showMessageDialog(this, "Names cannot be empty or the same as they are presently. ",
                        "Name conflict", JOptionPane.WARNING_MESSAGE);
            } else if (name.length() > 15) {
                name = null;
                JOptionPane.showMessageDialog(this, "Names must not exceed 15 characters.", "Name length",
                        JOptionPane.WARNING_MESSAGE);
            }
        } while (name == null);
        return name;
    }

    /**
     * Driver for the entire game.
     *
     * @param args The list of command-line arguments.
     */
    public static void main(String... args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
                    System.err.println("Could not set system default look and feel.");
                    e.printStackTrace();
                }
                new TicTacToe().init();
            }
        });
    }
}