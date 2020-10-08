/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import data.DataContainer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.data.time.Hour;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

/**
 *  Class that implemente a button Listener (plot the graphs)
 * @author bircklol and correiar
 */
public class PlotButtonListener implements ActionListener {

    JCheckBox[] tab;
    DataContainer dataContainer;
    int counter = 0;

    PlotButtonListener(JCheckBox[] tab, DataContainer dataContainer) {
        this.tab = tab;
        this.dataContainer = dataContainer;
    }

    public void actionPerformed(ActionEvent e) {
        counter++;
        Date[] dates = null;
        try {
            dates = dataContainer.getDates();
        } catch (ParseException ex) {
            Logger.getLogger(PlotButtonListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();
        for (int i = 0; i < tab.length; i++) {
            if (tab[i].isSelected()) {
                String var = tab[i].getText();
                TimeSeries timesSerie = new TimeSeries(var);
                Double[] values = dataContainer.getData(var);
                for (int j = 0; j < dates.length; j++) {
                    timesSerie.add(new Hour(dates[j]), values[j]);
                }
                timeSeriesCollection.addSeries(timesSerie);
            }
        }

        JPanel chartPanel = new ChartPanel(ChartFactory.createTimeSeriesChart("Occupancy Estimator " + counter, "Times", "Data", timeSeriesCollection, true, true, false));
        JFrame frame = new JFrame("Plot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(chartPanel);
        frame.pack();
        frame.setVisible(true);

    }
}
