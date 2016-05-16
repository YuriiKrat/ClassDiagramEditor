package main;

import main.code.CreateJavaFile;
import main.figure.Figure;
import main.lines.Line;
import main.lines.LineConstructor;
import main.listeners.EditorListener;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 20.04.16.
 */
public class Editor extends JPanel implements Serializable{

    private EditorState editorState;

    private static final long serialVersionUID = 1L;

    private static Editor editor;

    private Figure buffer;

    private LinkedList<Line> lines;
    private LinkedList<Figure> figures;
    private LineConstructor lineConstructor;

    private Editor() {
        super();
        editorState = EditorState.DEFAULT;

        lines = new LinkedList<>();
        figures = new LinkedList<>();

        setLayout(null);
        setBackground(Color.lightGray);

        validate();
        repaint();
        EditorListener editorListener = new EditorListener(this);
        addMouseListener(editorListener);
        addKeyListener(editorListener);

    }

    public static Editor getInstance() {
        if (editor == null) {
            editor = new Editor();
        }
        return editor;
    }

    public EditorState getEditorState() {
        return editorState;
    }

    public void setEditorState(EditorState editorState) {
        this.editorState = editorState;
    }

    public LinkedList<Line> getLines() {
        return lines;
    }

    public LinkedList<Figure> getFigures() {
        return figures;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(Line line: lines) {
            line.paintComponent(g);
        }
    }

    public void setLineConstructor(LineConstructor lineConstructor) {
        this.lineConstructor = lineConstructor;
    }

    public LineConstructor getLineConstructor() {
        return lineConstructor;
    }

    public Figure getBuffer() {
        return buffer;
    }

    public void setBuffer(Figure buffer) {
        this.buffer = buffer;
    }

    public void generateJavaCode(String path) throws IOException {
        if (figures.size() > 0) {
            CreateJavaFile file;
            for (Figure figure : figures) {
                file = new CreateJavaFile(figure, path);
                file.writeInFile();
            }
        }
    }

    public void clearAll() {
        while (figures.size() > 0) {
            figures.get(0).removeFigure();
        }
        editor.setEditorState(EditorState.DEFAULT);
    }

    public void unserialize(Editor serializedEditor) {
        Editor newEditor = Editor.getInstance();
        for (Figure figure: serializedEditor.getFigures()) {
            newEditor.add(figure);
            newEditor.getFigures().add(figure);
        }
        for (Line line: serializedEditor.getLines()) {
            newEditor.getLines().add(line);
        }

        newEditor.revalidate();
        newEditor.repaint();

    }
}
