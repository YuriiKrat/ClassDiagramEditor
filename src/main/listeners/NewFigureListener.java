package main.listeners;

import main.Editor;
import main.EditorState;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 20.04.16.
 */
public class NewFigureListener implements MouseListener {

    private boolean isInterface;

    public NewFigureListener(boolean isInterface) {
        this.isInterface = isInterface;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (isInterface) {
            Editor.getInstance().setEditorState(EditorState.ADD_INTERFACE);
        }
        else {
            Editor.getInstance().setEditorState(EditorState.ADD_FIGURE);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
