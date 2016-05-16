package main.listeners;

import main.Editor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 13.05.16.
 */
public class OpenListener implements ActionListener {

    private Editor editor;

    public OpenListener() {
        this.editor = Editor.getInstance();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser jFileChooser = new JFileChooser();
        if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            ObjectInputStream objectInputStream = null;
            try {
                objectInputStream = new ObjectInputStream(new FileInputStream(jFileChooser.getSelectedFile().getPath()));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            if (objectInputStream == null)
                return;
            try {
                editor.unserialize((Editor) objectInputStream.readObject());
                objectInputStream.close();
            } catch (ClassNotFoundException | IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
