package main.lines;

import main.Editor;
import main.figure.Figure;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 20.04.16.
 */
public abstract class Line implements Serializable {

    private Figure fromFigure;
    private Figure toFigure;

    private Point fromPoint;
    private Point toPoint;

    private Editor editor;
    protected List<LinePoint> pointsOnLine;

    private boolean isActive;

    public Line() {
        super();
        fromPoint = new Point();
        toPoint = new Point();
        pointsOnLine = new LinkedList<>();
    }

    public Line(Figure fromFigure, Figure toFigure) {
        this();
        this.fromFigure = fromFigure;
        this.toFigure = toFigure;
        fromFigure.addLine(this);
        toFigure.addLine(this);
        defineMainPoints();
        this.editor = Editor.getInstance();
        addPoints();
    }

    private void addPoints() {
        for (LinePoint linePoint : pointsOnLine) {
            editor.add(linePoint);
        }
    }

    public void defineMainPoints() {
        if (fromFigure == toFigure) {
            fromPoint.x = (int) (Math.random() * (fromFigure.getWidth() + 1) + fromFigure.getX());
            fromPoint.y = fromFigure.getY() + fromFigure.getHeight();
            toPoint.x = toFigure.getX() + toFigure.getWidth();
            toPoint.y = (int) (Math.random() * (toFigure.getHeight() + 1) + toFigure.getY());
            pointsOnLine.add(new LinePoint(fromPoint.x, fromPoint.y, this, fromFigure));
            pointsOnLine.add(new LinePoint(fromPoint.x, fromPoint.y + 20, this, null));
            pointsOnLine.add(new LinePoint(toPoint.x + 20, fromPoint.y + 20, this, null));
            pointsOnLine.add(new LinePoint(toPoint.x + 20, toPoint.y, this, null));
            pointsOnLine.add(new LinePoint(toPoint.x, toPoint.y, this, toFigure));
        }
        else {
            boolean intersectX = (fromFigure.getX() <= toFigure.getX() &&
                    toFigure.getX() <= fromFigure.getX() + fromFigure.getWidth()) ||
                    (fromFigure.getX() <= toFigure.getX() + toFigure.getWidth() &&
                            toFigure.getX() + toFigure.getWidth() <= fromFigure.getX() + fromFigure.getWidth());
            boolean intersectY = (fromFigure.getY() <= toFigure.getY() &&
                    toFigure.getY() <= fromFigure.getY() + fromFigure.getHeight()) ||
                    (fromFigure.getY() <= toFigure.getY() + toFigure.getHeight() &&
                            toFigure.getY() + toFigure.getHeight() <= fromFigure.getY() + fromFigure.getHeight());
            if (intersectX && intersectY) {
                fromPoint.setLocation(-5, -5);
                toPoint.setLocation(-5, -5);
            } else {
                Figure rightest = (fromFigure.getX() + fromFigure.getWidth() > toFigure.getX() + toFigure.getWidth()) ? fromFigure : toFigure;
                Figure highest = fromFigure.getY() < toFigure.getY() ? fromFigure : toFigure;
                if (intersectX || (!intersectX && !intersectY)) {
                    fromPoint.x = (int) (Math.random() * (fromFigure.getWidth() + 1) + fromFigure.getX());
                    toPoint.x = (int) (Math.random() * (toFigure.getWidth() + 1) + toFigure.getX());
                    if (fromFigure == highest) {
                        fromPoint.y = fromFigure.getY() + fromFigure.getHeight();
                        toPoint.y = toFigure.getY();
                    } else {
                        fromPoint.y = fromFigure.getY();
                        toPoint.y = toFigure.getY() + toFigure.getHeight();
                    }
                } else if (intersectY) {
                    fromPoint.y = (int) (Math.random() * (fromFigure.getHeight() + 1) + fromFigure.getY());
                    toPoint.y = (int) (Math.random() * (toFigure.getHeight() + 1) + toFigure.getY());
                    if (fromFigure == rightest) {
                        fromPoint.x = fromFigure.getX();
                        toPoint.x = toFigure.getX() + toFigure.getWidth();
                    } else {
                        fromPoint.x = fromFigure.getX() + fromFigure.getWidth();
                        toPoint.x = toFigure.getX();
                    }
                }
                pointsOnLine.add(new LinePoint(fromPoint.x, fromPoint.y, this, fromFigure));
                pointsOnLine.add(new LinePoint(toPoint.x, toPoint.y, this, toFigure));
            }
        }

    }

    protected Point[] getArrow(Point startPoint, Point endPoint, int angle, int size) {
        double phi = Math.toRadians(angle);
        double dy = endPoint.y - startPoint.y;
        double dx = endPoint.x - startPoint.x;
        double theta = Math.atan2(dy, dx);
        Point[] res = new Point[4];
        res[0] = new Point(endPoint.x, endPoint.y);
        res[1] = new Point(
                (int) (endPoint.x - size * Math.cos(theta + phi)),
                (int) (endPoint.y - size * Math.sin(theta + phi))
        );
        res[2] = new Point(
                (int) (endPoint.x - size * Math.cos(theta - phi)),
                (int) (endPoint.y - size * Math.sin(theta - phi))
        );
        res[3] = new Point(
                (int)(endPoint.x - 2 * size * Math.sin(phi) * Math.cos(theta)),
                (int)(endPoint.y - 2 * size * Math.cos(phi) * Math.sin(theta))
        );
        return res;
    }

    public abstract void paintBeginning(Graphics g);

    public abstract void paintEnding(Graphics g);

    protected Stroke getStroke(Graphics g) {
        return ((Graphics2D) g).getStroke();
    }

    protected  void drawLine(Graphics2D g) {
        for (int i = 1; i < pointsOnLine.size(); i++) {
            Line2D line2D = new Line2D.Double(pointsOnLine.get(i - 1).getPoint(), pointsOnLine.get(i).getPoint());
            g.draw(line2D);
        }
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(isActive ? Color.RED : Color.BLACK);
        Stroke defaultStroke = g2.getStroke();
        g2.setStroke(getStroke(g));
        drawLine(g2);
        g2.setStroke(defaultStroke);
        paintBeginning(g);
        paintEnding(g);
        g2.dispose();
    }

    public void classMoved(Figure figure, MouseEvent e) {
        if (pointsOnLine != null && pointsOnLine.size() >= 2) {
            MouseListener[] mouseListeners;
            if (figure == fromFigure) {
                mouseListeners = pointsOnLine.get(0).getMouseListeners();
                if (mouseListeners.length == 1) {
                    mouseListeners[0].mouseReleased(e);
                }
            } else if (figure == toFigure) {
                mouseListeners = pointsOnLine.get(pointsOnLine.size() - 1).getMouseListeners();
                if (mouseListeners.length == 1) {
                    mouseListeners[0].mouseReleased(e);
                }
            }
        }
    }

    public void repaint() {
        editor.repaint();
    }

    public void removePoint(LinePoint linePoint, boolean flag) {
        int index = pointsOnLine.indexOf(linePoint);
        if (flag) {
            if (index >= 0 && index != pointsOnLine.size()) {
                pointsOnLine.remove(index);
                editor.remove(linePoint);
                editor.revalidate();
                editor.repaint();
            }
        }
        else {
            if (index > 0 && index != pointsOnLine.size()) {
                pointsOnLine.remove(index);
                editor.remove(linePoint);
                pointsOnLine.get(0).requestFocus();
                editor.revalidate();
                editor.repaint();
            }
        }
    }

    public void removeLine() {
        while (pointsOnLine.size() > 0){
            removePoint(pointsOnLine.get(0), true);
        }
        fromFigure.getLines().remove(this);
        if (fromFigure != toFigure) {
            toFigure.getLines().remove(this);
        }
        repaint();
    }

    public void addPointNear(LinePoint linePoint) {
        int index = pointsOnLine.indexOf(linePoint);
        if (index == -1) {
            return;
        }
        if (index == 0) {
            addPointBetween(linePoint, pointsOnLine.get(index + 1));
        }
        else if (index == pointsOnLine.size() - 1) {
            addPointBetween(pointsOnLine.get(index - 1), linePoint);
        }
        else {
            LinePoint left = pointsOnLine.get(index - 1);
            LinePoint right = pointsOnLine.get(index + 1);
            double leftDistance = Math.hypot(
                    left.getCenterX() - linePoint.getCenterX(),
                    left.getCenterY() - linePoint.getCenterY()
            );
            double rightDistance = Math.hypot(
                    right.getCenterX() - linePoint.getCenterX(),
                    right.getCenterY() - linePoint.getCenterY()
            );
            if (rightDistance > leftDistance) {
                addPointBetween(linePoint, right);
            }
            else {
                addPointBetween(left, linePoint);
            }
        }
    }

    private void addPointBetween(LinePoint left, LinePoint right) {
        LinePoint newPoint = new LinePoint(
                (left.getCenterX() + right.getCenterX()) / 2,
                (left.getCenterY() + right.getCenterY()) / 2,
                this, null
        );
        newPoint.setActive(true);
        pointsOnLine.add(
                pointsOnLine.indexOf(left) + 1,
                newPoint
        );
        editor.add(newPoint);
        editor.revalidate();
        editor.repaint();
    }

    public void setActive(boolean active) {
        this.isActive = active;
        for (LinePoint linePoint : pointsOnLine) {
            linePoint.setActive(active);
        }
        editor.repaint();
    }

    public Figure getFromFigure() {
        return fromFigure;
    }

    public Figure getToFigure() {
        return toFigure;
    }
}
