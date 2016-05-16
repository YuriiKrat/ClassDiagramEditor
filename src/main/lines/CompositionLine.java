package main.lines;

import main.figure.Figure;
import java.awt.*;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 11.05.16.
 */
public class CompositionLine extends Line {

    public CompositionLine(Figure fromFigure, Figure toFigure) {
        super(fromFigure, toFigure);
    }

    @Override
    public void paintBeginning(Graphics g) {
        if (pointsOnLine != null && pointsOnLine.size() > 1) {
            Graphics2D g2 = (Graphics2D) g;
            Point startPoint = pointsOnLine.get(1).getPoint();
            Point endPoint = pointsOnLine.get(0).getPoint();
            Point[] points = getArrow(startPoint, endPoint, 30, 15);
            Polygon polygon = new Polygon();
            polygon.addPoint(points[3].x, points[3].y);
            polygon.addPoint(points[1].x, points[1].y);
            polygon.addPoint(points[0].x, points[0].y);
            polygon.addPoint(points[2].x, points[2].y);
            g2.setColor(new Color(50, 50, 50));
            g2.fillPolygon(polygon);
            g2.setColor(Color.BLACK);
            g2.drawPolygon(polygon);
        }
    }

    @Override
    public void paintEnding(Graphics g) {

    }
}
