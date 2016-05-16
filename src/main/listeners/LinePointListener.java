package main.listeners;

import main.figure.Figure;
import main.lines.Line;
import main.lines.LinePoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 11.05.16.
 */
public class LinePointListener implements MouseMotionListener, MouseListener, FocusListener, KeyListener, Serializable {

    private int x;
    private int y;

    private int mouseX;
    private int mouseY;

    private LinePoint linePoint;

    public LinePointListener(LinePoint linePoint) {
        this.linePoint = linePoint;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (linePoint.isActive()) {
            if (SwingUtilities.isRightMouseButton(e)) {
                linePoint.getLine().removePoint(linePoint, false);
            } else {
                linePoint.getLine().addPointNear(linePoint);
            }
        }
        else {
            linePoint.requestFocus();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (linePoint.isActive()) {
            x = linePoint.getX();
            y = linePoint.getY();
            mouseX = e.getXOnScreen();
            mouseY = e.getYOnScreen();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (linePoint.getFigure() != null) {
            Component component = linePoint.getParent().getComponentAt(linePoint.getCenterX(), linePoint.getCenterY());
            if (component instanceof Figure) {
                linePoint.setFigure((Figure) component);
            }
            linePoint.putOnBorder();
            linePoint.getLine().repaint();
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
        if (linePoint.isActive()) {
            linePoint.setLocation(x + e.getXOnScreen() - mouseX, y + e.getYOnScreen() - mouseY);
            Point location = linePoint.getLocation();
            if (location.x < 0) {
                location.x = 0;
            }
            if (location.y < 0) {
                location.y = 0;
            }
            linePoint.setLocation(location.x, location.y);
            linePoint.getLine().repaint();
        }
    }


    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void focusGained(FocusEvent e) {
        linePoint.getLine().setActive(true);
    }

    @Override
    public void focusLost(FocusEvent e) {
        linePoint.getLine().setActive(false);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (linePoint.isActive() && e.getKeyCode() == 127) {
            Line line = linePoint.getLine();
            line.removeLine();
        }
    }
}
