package main.lines;

import main.figure.Figure;

import java.io.Serializable;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 11.05.16.
 */

@FunctionalInterface
public interface LineConstructor extends Serializable{

    Line constructor(Figure fromFigure, Figure toFigure);

}
