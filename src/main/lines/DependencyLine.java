package main.lines;

import main.figure.Figure;
import java.awt.*;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 11.05.16.
 */
public class DependencyLine extends Line {

    public DependencyLine(Figure fromFigure, Figure toFigure) {
        super(fromFigure, toFigure);
    }

    @Override
    public Stroke getStroke(Graphics g) {
        return new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0);
    }

    @Override
    public void paintBeginning(Graphics g) {

    }

    @Override
    public void paintEnding(Graphics g) {
        if (pointsOnLine != null && pointsOnLine.size() > 1) {
            Graphics2D g2 = (Graphics2D) g;
            Point startPoint = pointsOnLine.get(pointsOnLine.size() - 2).getPoint();
            Point endPoint = pointsOnLine.get(pointsOnLine.size() - 1).getPoint();
            Point[] points = getArrow(startPoint, endPoint, 40, 10);
            g2.drawLine(points[1].x, points[1].y, points[0].x, points[0].y);
            g2.drawLine(points[2].x, points[2].y, points[0].x, points[0].y);
        }
    }

}
