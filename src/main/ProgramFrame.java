package main;

import main.listeners.OpenListener;
import main.listeners.SaveListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 10.04.16.
 */
public class ProgramFrame extends JFrame {

    public static void createGUI() {

        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Class Diagram Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Font font = new Font("Verdana", Font.PLAIN, 14);

        Editor editorPanel = Editor.getInstance();

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setFont(font);

        ImageIcon newFile = new ImageIcon("src/images/new-file.png");
        JMenuItem newProject = new JMenuItem("New project", newFile);
        newProject.setFont(font);
        fileMenu.add(newProject);
        newProject.addActionListener(e -> Editor.getInstance().clearAll());

        ImageIcon openFile = new ImageIcon("src/images/open-file.png");
        JMenuItem openFromFile = new JMenuItem("Open...", openFile);
        openFromFile.setFont(font);
        fileMenu.add(openFromFile);
        openFromFile.addActionListener(new OpenListener());

        ImageIcon saveFile = new ImageIcon("src/images/save-file.png");
        JMenuItem save = new JMenuItem("Save", saveFile);
        save.setFont(font);
        fileMenu.add(save);
        save.addActionListener(new SaveListener());

        ImageIcon exitImage = new ImageIcon("src/images/exit.png");
        JMenuItem exit = new JMenuItem("Exit", exitImage);
        exit.setFont(font);
        fileMenu.add(exit);
        exit.addActionListener(e -> System.exit(0));

        menuBar.add(fileMenu);

        JMenu codeMenu = new JMenu("Code");
        codeMenu.setFont(font);

        ImageIcon javaCode = new ImageIcon("src/images/java.png");
        JMenuItem generateCode = new JMenuItem("Generate Java code", javaCode);
        generateCode.setFont(font);
        codeMenu.add(generateCode);
        generateCode.addActionListener(e -> {
            try {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    Editor.getInstance().generateJavaCode(jFileChooser.getSelectedFile().getPath());
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        menuBar.add(codeMenu);

        JMenu helpMenu = new JMenu("Help");
        helpMenu.setFont(font);

        ImageIcon aboutUs = new ImageIcon("src/images/about.png");
        JMenuItem about = new JMenuItem("About", aboutUs);
        about.setFont(font);
        helpMenu.add(about);
        about.addActionListener(e -> JOptionPane.showMessageDialog(null,
                "ClassDiagramEditor\n" +
                        "Created by Yurii Krat\n\n" +
                        "\tClassDiagramEditor allows to create class diagrams and generate Java code from it.\n\n" +
                        "Hot Keys:\n" +
                        "DELETE - Removes figures, lines, fields and methods\n" +
                        "CTRL + C - Copy figure\n" +
                        "CTRL + V - Insert figure\n\n" +
                        "\u00A9 All rights reserved 2016\n", "Help",
                JOptionPane.INFORMATION_MESSAGE));

        menuBar.add(helpMenu);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        Dimension dimension = new Dimension(3000, 2000);
        editorPanel.setPreferredSize(dimension);

        Utilities utilitiesPanel = new Utilities();

        JScrollPane jScrollPane = new JScrollPane(editorPanel);

        panel.add(utilitiesPanel, BorderLayout.WEST);
        panel.add(jScrollPane, BorderLayout.CENTER);

        frame.getContentPane().add(panel);
        frame.setJMenuBar(menuBar);
        frame.setBounds(0, 0, 1366, 768);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createGUI());
    }
}
