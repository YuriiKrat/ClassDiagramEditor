package main.listeners;

import main.figure.Figure;
import main.lines.Line;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.LinkedList;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 09.05.16.
 */
public class NewAddPropertyListener implements ActionListener, Serializable{

    private String property;
    private Figure figure;

    public NewAddPropertyListener(String property, Figure figure) {
        this.property = property;
        this.figure = figure;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (property.equals("field"))
            figure.addField();
        else
            figure.addMethod();
        Dimension size = figure.getPreferredSize();
        if(size.height > figure.getHeight()) {
            figure.setSize(figure.getWidth(), size.height);
        }
        figure.revalidate();
        figure.repaint();
        LinkedList<Line> lines = figure.getLines();
        for (Line line: lines) {
            line.classMoved(figure, null);
        }
    }
}
