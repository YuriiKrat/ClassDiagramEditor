package main.code;

import main.domain.Signature;
import main.figure.Figure;
import main.lines.GeneralizationLine;
import main.lines.Line;
import main.lines.RealizationLine;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 12.05.16.
 */
public class CreateJavaFile {

    private Figure figure;
    private String path;
    private Signature name;
    private LinkedList<Signature> fields;
    private LinkedList<Signature> methods;
    private LinkedList<Signature> extendingClasses;
    private LinkedList<Signature> implementingInterfaces;

    public CreateJavaFile(Figure figure, String path) {

        this.figure = figure;
        this.path = path;
        name = figure.getFigureName();
        fields = figure.getFields();
        methods = figure.getMethods();

        extendingClasses = new LinkedList<>();
        implementingInterfaces = new LinkedList<>();

        for (Line line: figure.getLines()) {
            if (line instanceof GeneralizationLine && figure == line.getFromFigure()) {
                extendingClasses.add(line.getToFigure().getFigureName());
            }
            else if (line instanceof RealizationLine && figure == line.getFromFigure()) {
                implementingInterfaces.add(line.getToFigure().getFigureName());
                for (Signature method: line.getToFigure().getMethods()) {
                    if (methods.indexOf(method) == -1)
                        methods.add(method);
                }
            }
        }

    }

    public void writeInFile() throws IOException{

        File file = new File(path + "/" + name.toString() + ".java");
        try (FileWriter writer = new FileWriter(file, false))
        {
            if (!figure.isInterface()) {
                writer.write("class " + name);
            }
            else {
                writer.write("interface " + name);
            }
            for (int i = 0; i < extendingClasses.size(); i++) {
                if (i == 0) {
                    writer.write(" extends ");
                }
                System.out.println(extendingClasses.get(i).toString());
                writer.write(extendingClasses.get(i).toString());
                if (i != extendingClasses.size() - 1) {
                    writer.write(", ");
                }
            }

            if (!figure.isInterface()) {
                for (int i = 0; i < implementingInterfaces.size(); i++) {
                    if (i == 0) {
                        writer.write(" implements ");
                    }
                    writer.write(implementingInterfaces.get(i).toString());
                    if (i != implementingInterfaces.size() - 1) {
                        writer.write(", ");
                    }
                }
            }
            writer.write(" {\n");
            for (Signature field: fields) {
                writer.write("\t" + field.toString() + "\n");
            }
            for (Signature method: methods) {
                writer.write("\t" + method.toString());
                if (figure.isInterface()) {
                    writer.append(';');
                }
                else {
                    writer.write("() {\n\n\t}" );
                }
                writer.write("\n");
            }
            writer.write("}");
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

}
