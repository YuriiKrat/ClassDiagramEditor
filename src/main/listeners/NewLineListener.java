package main.listeners;

import main.Editor;
import main.EditorState;
import main.lines.LineConstructor;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 29.04.16.
 */
public class NewLineListener implements MouseListener {

    private Editor editor;
    private LineConstructor lineConstructor;

    public NewLineListener(LineConstructor lineConstructor) {
        this.editor = Editor.getInstance();
        this.lineConstructor = lineConstructor;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        editor.setEditorState(EditorState.ADD_LINE);
        editor.setLineConstructor(lineConstructor);
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
