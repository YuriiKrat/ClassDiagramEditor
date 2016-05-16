package main.listeners;

import main.Editor;
import main.EditorState;
import main.figure.Figure;
import main.lines.Line;

import java.awt.*;
import java.awt.event.*;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 11.05.16.
 */
public class EditorListener implements MouseListener, KeyListener {

    private Editor editor;

    private Figure fromFigure;
    private Figure toFigure;

    public EditorListener(Editor editor) {
        this.editor = editor;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        editor.requestFocus();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (editor.getEditorState() == EditorState.ADD_FIGURE
                || editor.getEditorState() == EditorState.ADD_INTERFACE) {
            Figure newFigure;
            if (editor.getEditorState() == EditorState.ADD_FIGURE) {
                newFigure = new Figure(1);
            }
            else {
                newFigure = new Figure(2);
            }
            newFigure.setBounds(e.getX(), e.getY(),
                    newFigure.getPreferredSize().width, newFigure.getPreferredSize().height);
            editor.getFigures().add(newFigure);
            editor.add(newFigure);
            editor.setEditorState(EditorState.DEFAULT);
            editor.validate();
            editor.repaint();
        }
        else if (editor.getEditorState() == EditorState.ADD_LINE) {
            Component figure = editor.getComponentAt(e.getX(), e.getY());
            if (figure instanceof Figure) {
                fromFigure = (Figure) figure;
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (editor.getEditorState() == EditorState.ADD_LINE) {
            Component figure = editor.getComponentAt(e.getX(), e.getY());
            if (figure instanceof Figure) {
                toFigure = (Figure) figure;
            }
            if (fromFigure != null && toFigure != null) {
                Line newLine = editor.getLineConstructor().constructor(fromFigure, toFigure);
                editor.getLines().add(newLine);
                editor.validate();
                editor.repaint();
            }
            editor.setEditorState(EditorState.DEFAULT);
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V) {
            if (editor.getBuffer() != null) {
                Figure figure = editor.getBuffer();
                figure.setBounds(10,10,
                        figure.getPreferredSize().width, figure.getPreferredSize().height);
                editor.add(figure);
                editor.getFigures().add(figure);
                editor.setBuffer((Figure)editor.getBuffer().cloneFigure());
                editor.revalidate();
                editor.repaint();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
