package main.figure;

import main.Editor;
import main.domain.Field;
import main.domain.FigureName;
import main.domain.Method;
import main.domain.Signature;
import main.lines.Line;
import main.listeners.FigureListener;
import main.listeners.NewAddPropertyListener;
import main.listeners.TextFieldListener;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.LinkedList;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 20.04.16.
 */
public class Figure extends JPanel implements CloneFigure, Serializable {

    private Editor editor;

    private Signature name;
    private LinkedList<Signature> fields;
    private LinkedList<Signature> methods;
    private LinkedList<Line> lines;

    private JPanel namePanel;
    private JPanel fieldsPanel;
    private JPanel methodsPanel;

    private boolean isInterface;

    public Figure(int figure) {

        super();

        fields = new LinkedList<>();
        methods = new LinkedList<>();
        lines = new LinkedList<>();

        namePanel = createPanel("name");
        fieldsPanel = createPanel("field");
        methodsPanel = createPanel("method");

        isInterface = figure == 1 ? false : true;

        this.editor = Editor.getInstance();

        setBorder(BorderFactory.createLineBorder(Color.black, 1));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        namePanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        namePanel.setBackground(Color.yellow);
        fieldsPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        methodsPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));

        addName();

        add(namePanel);
        add(fieldsPanel);
        add(methodsPanel);

        FigureListener figureListener = new FigureListener(this);
        addMouseListener(figureListener);
        addMouseMotionListener(figureListener);
        addFocusListener(figureListener);
        addKeyListener(figureListener);

    }

    public void addName() {
        name = new FigureName();
        String str = "SomeClass";
        if (isInterface) {
            str = "<<interface>>SomeInterface";
        }
        namePanel.add(createTextField(name, str));
        revalidate();
        repaint();
    }

    public void addName(String str) {
        name = new FigureName();
        namePanel.removeAll();
        namePanel.add(createTextField(name, str));
        revalidate();
        repaint();
    }

    public void addField() {
        Field field = new Field();
        fields.add(field);
        fieldsPanel.add(createTextField(field), fieldsPanel.getComponentCount() - 1);
        revalidate();
        repaint();
    }

    public void addMethod() {
        Method method = new Method();
        methods.add(method);
        methodsPanel.add(createTextField(method), methodsPanel.getComponentCount() - 1);
        revalidate();
        repaint();
    }

    public void addField(String str) {
        Field field = new Field();
        fields.add(field);
        fieldsPanel.add(createTextField(field, str), fieldsPanel.getComponentCount() - 1);
        revalidate();
        repaint();
    }

    public void addMethod(String str) {
        Method method = new Method();
        methods.add(method);
        methodsPanel.add(createTextField(method, str), methodsPanel.getComponentCount() - 1);
        revalidate();
        repaint();
    }


    public void deleteField(Signature field, JTextField textField) {
        if (fields.size() > 0) {
            fields.remove(field);
            fieldsPanel.remove(textField);
        }
        revalidate();
        repaint();
    }

    public void deleteMethod(Signature method, JTextField textField) {
        if (methods.size() > 0) {
            methods.remove(method);
            methodsPanel.remove(textField);
        }
        revalidate();
        repaint();
    }

    public JTextField createTextField(Signature signature) {
        JTextField textField = new JTextField();
        textField.setEditable(false);
        TextFieldListener textFieldListener = new TextFieldListener(textField, signature, this);
        textField.addMouseListener(textFieldListener);
        textField.addKeyListener(textFieldListener);
        textField.addFocusListener(textFieldListener);
        return textField;
    }

    public JTextField createTextField(Signature signature, String str) {
        JTextField textField = new JTextField();
        textField.setEditable(false);
        textField.setText(str);
        TextFieldListener textFieldListener = new TextFieldListener(textField, signature, this);
        textField.addMouseListener(textFieldListener);
        textField.addKeyListener(textFieldListener);
        textField.addFocusListener(textFieldListener);
        return textField;
    }


    public JPanel createPanel(String property) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        if (!property.equals("name")) {
            JPanel buttonPanel = new JPanel();
            Font font = new Font("Dialog", Font.PLAIN, 9);
            buttonPanel.setLayout(new BorderLayout());
            JButton addButton = new JButton("+ " + property);
            addButton.setBackground(Color.green);
            addButton.setFont(font);
            addButton.setOpaque(true);
            Dimension d = new Dimension(85, 30);
            addButton.setPreferredSize(d);
            addButton.addActionListener(new NewAddPropertyListener(property, this));
            buttonPanel.add(addButton, BorderLayout.WEST);
            buttonPanel.setBackground(Color.yellow);
            panel.add(buttonPanel);
        }

        return panel;
    }

    public LinkedList<Line> getLines() {
        return lines;
    }

    public Signature getFigureName() {
        return name;
    }

    public LinkedList<Signature> getFields() {
        return fields;
    }

    public LinkedList<Signature> getMethods() {
        return methods;
    }

    public boolean isInterface() {
        return isInterface;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setSize(getPreferredSize());
        revalidate();
    }

    public void removeFigure() {
        while (lines.size() > 0) {
            lines.get(0).removeLine();
        }
        editor.getFigures().remove(this);
        editor.remove(this);
        editor.revalidate();
        editor.repaint();
    }

    public boolean addLine(Line line) {
        return lines.add(line);
    }


    @Override
    public CloneFigure cloneFigure() {
        Figure cloneFigure;
        if (isInterface) {
            cloneFigure = new Figure(2);
        }
        else {
            cloneFigure = new Figure(1);
        }
        JTextField jTextField;

        Component []nameComponent = namePanel.getComponents();
        if (nameComponent.length == 1) {
            if (nameComponent[0].getClass().equals(JTextField.class)) {
                jTextField = (JTextField)nameComponent[0];
                cloneFigure.addName(jTextField.getText());
            }
        }

        Component[] fieldComponents = fieldsPanel.getComponents();

        for (Component component : fieldComponents) {
            if (component.getClass().equals(JTextField.class)) {
                jTextField = (JTextField) component;
                cloneFigure.addField(jTextField.getText());
            }
        }

        Component[] methodComponents = methodsPanel.getComponents();

        for (Component component : methodComponents) {
            if (component.getClass().equals(JTextField.class)) {
                jTextField = (JTextField) component;
                cloneFigure.addMethod(jTextField.getText());
            }
        }

        return cloneFigure;
    }

}
