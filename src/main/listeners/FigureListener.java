package main.listeners;

import main.Editor;
import main.EditorState;
import main.figure.Figure;
import main.lines.Line;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 11.05.16.
 */
public class FigureListener implements MouseListener, MouseMotionListener, FocusListener, KeyListener, Serializable {

    private Figure figure;

    private int x;
    private int y;

    private int xForMouse;
    private int yForMouse;

    private boolean isMoving;

    private boolean isActive;

    public FigureListener(Figure figure) {
        this.figure = figure;
        isMoving = false;
        isActive = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        figure.requestFocus();
        if (Editor.getInstance().getEditorState() == EditorState.DEFAULT) {
            isMoving = true;
            x = figure.getX();
            y = figure.getY();
            xForMouse = e.getXOnScreen();
            yForMouse = e.getYOnScreen();
        } else {
            e.translatePoint((int) figure.getBounds().getX(), (int) figure.getBounds().getY());
            MouseListener[] mouseListeners = Editor.getInstance().getMouseListeners();
            if (mouseListeners.length == 1) {
                mouseListeners[0].mousePressed(e);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (Editor.getInstance().getEditorState() == EditorState.DEFAULT) {
            isMoving = false;
        } else {
            e.translatePoint((int) figure.getBounds().getX(), (int) figure.getBounds().getY());
            MouseListener[] mouseListeners = Editor.getInstance().getMouseListeners();
            if (mouseListeners.length == 1) {
                mouseListeners[0].mouseReleased(e);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (isMoving) {
            if (x + e.getXOnScreen() >= xForMouse && y + e.getYOnScreen() >= yForMouse) {
                figure.setLocation(x - xForMouse + e.getXOnScreen(), y - yForMouse + e.getYOnScreen());
                for (Line line : figure.getLines()) {
                    line.classMoved(figure, e);
                }
                Editor.getInstance().repaint();
                Editor.getInstance().repaint();
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void focusGained(FocusEvent e) {
        figure.requestFocus();
        isActive = true;
        figure.setBorder(BorderFactory.createLineBorder(Color.red, 2));
        figure.revalidate();
        figure.repaint();
    }

    @Override
    public void focusLost(FocusEvent e) {
        isActive = false;
        figure.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        figure.revalidate();
        figure.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (isActive && e.isControlDown() && e.getKeyCode() == KeyEvent.VK_C) {
            Editor.getInstance().setBuffer((Figure)figure.cloneFigure());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(isActive && e.getKeyCode() == 127) {
            figure.removeFigure();
        }
    }
}
