package main.lines;

import main.figure.Figure;
import java.awt.*;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 11.05.16.
 */
public class AssociationLine extends Line {
    public AssociationLine(Figure fromFigure, Figure toFigure) {
        super(fromFigure, toFigure);
    }

    @Override
    public void paintBeginning(Graphics g) {

    }

    @Override
    public void paintEnding(Graphics g) {

    }
}
