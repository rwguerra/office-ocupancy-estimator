/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import data.DataContainerWithProcessing;
import java.io.IOException;
import javax.swing.JFrame;

/**
 * Project to estimate occupancy of a room based on given data
 * @author correiar birklol
 */
public class MainFrame {

    /**
     * Main class that run the procjet
     * @param args args
     */
    public static void main(String[] args) {
        try {
            DataContainerWithProcessing data = new DataContainerWithProcessing("office.csv");
            JFrame plotFactory = new PlotFactory(data);
            plotFactory.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
