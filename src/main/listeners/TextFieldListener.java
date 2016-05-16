package main.listeners;

import main.domain.Field;
import main.domain.FigureName;
import main.domain.Method;
import main.domain.Signature;
import main.figure.Figure;
import main.lines.Line;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.LinkedList;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 20.04.16.
 */
public class TextFieldListener implements MouseListener, KeyListener, FocusListener, Serializable {

    public JTextField textField;

    private Figure figure;

    private Signature signature;

    public TextFieldListener(JTextField textField, Signature signature, Figure figure) {
        this.textField = textField;
        this.figure = figure;
        this.signature = signature;
        textField.setBackground(Color.YELLOW);
    }

    private void writeProperty() {
        String interfaceSign = "<<interface>>";
        String str = textField.getText().replaceAll("[\\s]", "");
        if (figure.isInterface() && signature instanceof FigureName) {
            str = str.replaceAll(interfaceSign, "");
        }
        if (str.length() > 0) {
            if (!signature.rewriteProperty(str)) {
                textField.requestFocus();
            }
            else {
                textField.setEditable(false);
                textField.setBackground(Color.yellow);
            }
            if (figure.isInterface() && signature instanceof FigureName) {
                String newStr = interfaceSign + str;
                textField.setText(newStr);
            }
            else {
                textField.setText(str);
            }
        }
        else {
            textField.setEditable(false);
            textField.setBackground(Color.yellow);
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Dimension size;
        LinkedList<Line> lines = figure.getLines();
        if (e.getKeyCode() == 10) {
            writeProperty();
        }
        else if (e.getKeyCode() == 127) {
            if (signature instanceof Method) {
                figure.deleteMethod(signature, textField);

            }
            else if (signature instanceof Field) {
                figure.deleteField(signature, textField);
            }
            size = figure.getPreferredSize();
            if(size.height < figure.getHeight()) {
                figure.setSize(figure.getWidth(), size.height);
            }
            figure.revalidate();
            figure.repaint();
        }
        size = figure.getPreferredSize();
        figure.setSize(size);
        figure.revalidate();
        figure.repaint();
        for (Line line: lines) {
            line.classMoved(figure, null);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        textField.requestFocus();
        textField.setEditable(true);
        textField.setBackground(Color.white);
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

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {
        writeProperty();
    }

}
