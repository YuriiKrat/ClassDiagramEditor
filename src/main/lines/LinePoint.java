package main.lines;

import main.figure.Figure;
import main.listeners.LinePointListener;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 20.04.16.
 */
public class LinePoint extends JLabel implements Serializable{

    private boolean isActive;

    private int centerX;
    private int centerY;

    private Figure figure;
    private Line line;

    public LinePoint(int x, int y, Line line) {
        super();
        figure = null;
        this.line = line;
        centerX = x;
        centerY = y;
        setBounds(x - 4, y - 4, 9, 9);
        setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));
        setOpaque(true);
        LinePointListener linePointListener = new LinePointListener(this);
        addMouseListener(linePointListener);
        addMouseMotionListener(linePointListener);
        addFocusListener(linePointListener);
        addKeyListener(linePointListener);
    }

    public LinePoint(int x, int y, Line line, Figure figure) {
        this(x, y, line);
        this.figure = figure;
    }

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public Point getPoint() {
        return new Point(centerX, centerY);
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public Figure getFigure() {
        return figure;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }

    public boolean isActive() {
        return isActive;
    }

    public Line getLine() {
        return line;
    }

    @Override
    public void setLocation(int x, int y) {
        centerX = x + 4;
        centerY = y + 4;
        super.setLocation(x, y);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isActive) {
            g.setColor(Color.RED);
            g.drawOval(0, 0, 8, 8);
        }
    }

    public void putOnBorder() {

        StraightLine[] lines = new StraightLine[]{
                new StraightLine(1, 0, figure.getX()),
                new StraightLine(1, 0, figure.getX() + figure.getWidth()),
                new StraightLine(0, 1, figure.getY()),
                new StraightLine(0, 1, figure.getY() + figure.getHeight())
        };

        int centerX = (2 * figure.getX() + figure.getWidth()) / 2;
        int centerY = (2 * figure.getY() + figure.getHeight()) / 2;
        if (centerX == getX() && centerY == getY()) {
            setLocation(figure.getX() + figure.getWidth() - 5, centerY - 5);
            return;
        }
        StraightLine fromCenter = new StraightLine(centerX, centerY, getX() + 5, getY() + 5);
        Point[] intersectPoints = new Point[4];
        for (int i = 0; i < 4; i++) {
            intersectPoints[i] = fromCenter.intersect(lines[i]);
        }
        Point closestPoint = null;
        double closestDistance = Double.MAX_VALUE;
        for (int i = 0; i < 4; i++) {
            double distance = Math.hypot(intersectPoints[i].getX() - getX(), intersectPoints[i].getY() - getY());
            if (intersectPoints[i].x >= figure.getX()
                    && intersectPoints[i].x <= figure.getX() + figure.getWidth()
                    && intersectPoints[i].y >= figure.getY()
                    && intersectPoints[i].y <= figure.getY() + figure.getHeight()
                    && distance < closestDistance
                    ) {
                closestDistance = distance;
                closestPoint = intersectPoints[i];
            }
        }
        setLocation(closestPoint.x - 5, closestPoint.y - 5);
    }

}
