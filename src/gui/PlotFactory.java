/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import data.DataContainer;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import javax.swing.*;

/**
 * Class to implement the gui
 * @author bircklol and correiar
 */
public class PlotFactory extends JFrame {

    private JCheckBox[] tab;
    private JButton plot;
    private JPanel checkbox, b;

    /**
     * Constructor receive the data to create checkboxes to each variable
     * @param data data
     */
    public PlotFactory(DataContainer data) {
        JPanel checkbox = new JPanel();
        JPanel b = new JPanel();
        setTitle("Options");
        setLayout(new BorderLayout());
        String[] orderedVariableNames = data.getAvailableVariables();
        JCheckBox[] tab = new JCheckBox[orderedVariableNames.length];
        for (int i = 0; i < tab.length; i++) {
            tab[i] = new JCheckBox(orderedVariableNames[i]);
            checkbox.add(tab[i]);
        }
        checkbox.setLayout(new GridLayout(tab.length, 1));
        plot = new JButton("plot");
        plot.addActionListener(new PlotButtonListener(tab, data));
        b.add(plot);
        add(checkbox, BorderLayout.CENTER);
        add(b, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }

}
