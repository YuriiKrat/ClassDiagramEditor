package main;

import main.lines.*;
import main.listeners.NewFigureListener;
import main.listeners.NewLineListener;

import javax.swing.*;
import java.io.Serializable;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 20.04.16.
 */
public class Utilities extends JPanel {

    public Utilities() {

        super();

        JToolBar toolBar = new JToolBar(JToolBar.VERTICAL);

        ImageIcon classIcon = new ImageIcon("src/images/class.png");
        JButton newClass = new JButton(classIcon);
        newClass.setToolTipText("Class");
        toolBar.add(newClass);
        newClass.addMouseListener(new NewFigureListener(false));

        ImageIcon generalization = new ImageIcon("src/images/generalization.png");
        JButton newGeneralization = new JButton(generalization);
        newGeneralization.setToolTipText("Generalization");
        toolBar.add(newGeneralization);
        newGeneralization.addMouseListener(new NewLineListener(
                (LineConstructor & Serializable)GeneralizationLine::new));

        ImageIcon composition = new ImageIcon("src/images/composition.png");
        JButton newComposition = new JButton(composition);
        newComposition.setToolTipText("Composition");
        toolBar.add(newComposition);
        newComposition.addMouseListener(new NewLineListener(
                (LineConstructor & Serializable)CompositionLine::new));

        ImageIcon uniComposition = new ImageIcon("src/images/uni-composition.png");
        JButton newUniComposition = new JButton(uniComposition);
        newUniComposition.setToolTipText("Unicomposition");
        toolBar.add(newUniComposition);
        newUniComposition.addMouseListener(new NewLineListener(
                (LineConstructor & Serializable)UniCompositionLine::new));

        ImageIcon aggregation = new ImageIcon("src/images/aggregation.png");
        JButton newAggregation = new JButton(aggregation);
        newAggregation.setToolTipText("Aggregation");
        toolBar.add(newAggregation);
        newAggregation.addMouseListener(new NewLineListener(
                (LineConstructor & Serializable)AggregationLine::new));

        ImageIcon uniAggregation = new ImageIcon("src/images/uni-aggregation.png");
        JButton newUniAggregation = new JButton(uniAggregation);
        newUniAggregation.setToolTipText("Uniaggregation");
        toolBar.add(newUniAggregation);
        newUniAggregation.addMouseListener(new NewLineListener((LineConstructor & Serializable)
                UniAggregationLine::new));

        ImageIcon association = new ImageIcon("src/images/association.png");
        JButton newAssociation = new JButton(association);
        newAssociation.setToolTipText("Association");
        toolBar.add(newAssociation);
        newAssociation.addMouseListener(new NewLineListener(
                (LineConstructor & Serializable)AssociationLine::new));

        ImageIcon uniAssociation = new ImageIcon("src/images/uni-association.png");
        JButton newUniAssociation = new JButton(uniAssociation);
        newUniAssociation.setToolTipText("Uniassociation");
        toolBar.add(newUniAssociation);
        newUniAssociation.addMouseListener(new NewLineListener(
                (LineConstructor & Serializable)UniAssociationLine::new));

        ImageIcon interfaceIcon = new ImageIcon("src/images/interface.png");
        JButton newInterface = new JButton(interfaceIcon);
        newInterface.setToolTipText("Interface");
        toolBar.add(newInterface);
        newInterface.addMouseListener(new NewFigureListener(true));

        ImageIcon realization = new ImageIcon("src/images/realization.png");
        JButton newRealization = new JButton(realization);
        newRealization.setToolTipText("Realization");
        toolBar.add(newRealization);
        newRealization.addMouseListener(new NewLineListener(
                (LineConstructor & Serializable)RealizationLine::new));

        ImageIcon dependency = new ImageIcon("src/images/dependency.png");
        JButton newDependency = new JButton(dependency);
        newDependency.setToolTipText("Dependency");
        toolBar.add(newDependency);
        newDependency.addMouseListener(new NewLineListener(
                (LineConstructor & Serializable)DependencyLine::new));

        add(toolBar);

        validate();
        repaint();
    }
}
