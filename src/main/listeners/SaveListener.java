package main.listeners;

import main.Editor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 13.05.16.
 */
public class SaveListener implements ActionListener {

    private Editor editor;

    public SaveListener() {
        this.editor = Editor.getInstance();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser jFileChooser = new JFileChooser();
        if (jFileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            ObjectOutputStream objectOutputStream = null;
            try {
                objectOutputStream = new ObjectOutputStream(new FileOutputStream(jFileChooser.getSelectedFile().getPath()));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            if (objectOutputStream == null)
                return;
            try {
                objectOutputStream.writeObject(editor);
                objectOutputStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
