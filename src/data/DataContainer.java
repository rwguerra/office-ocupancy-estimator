/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

/**
 * Class that handle datacontainers
 * @author bircklol
 */
public class DataContainer {

    /**
     * Times list
     */
    ArrayList<String> timeStrings;

    /**
     * Sensors list (Toffice, Tcorridor,...)
     */
    ArrayList<String> orderedVariableNames;

    /**
     * Data in the form of key -> values list
     */
    Hashtable<String, ArrayList<Double>> data;

    /**
     * number of the samples
     */
    int numberOfSamples = 0;

    /**
     * Class allowing to read a "csv" file and stock the data in the form of
     * lists.
     *
     * @param csvFileName file name of the data
     * @throws IOException
     */
    public DataContainer(String csvFileName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFileName));
        orderedVariableNames = new ArrayList<>();
        timeStrings = new ArrayList<>();
        data = new Hashtable<>();
        String line;
        line = bufferedReader.readLine();
        String[] tokens = line.split(",");
        int numberOfVariables = 0;
        for (int i = 1; i < tokens.length; i++) {
            orderedVariableNames.add(tokens[i]);
            data.put(tokens[i], new ArrayList<>());
            numberOfVariables++;
        }
        while ((line = bufferedReader.readLine()) != null) {
            String[] values = line.split(",");
            for (int i = 0; i < numberOfVariables + 1; i++) {
                if (i == 0) {
                    timeStrings.add(values[i]);
                } else {
                    data.get(orderedVariableNames.get(i - 1)).add(Double.parseDouble(values[i]));
                }
            }
        }
        bufferedReader.close();
        numberOfSamples = timeStrings.size();
    }

    /**
     *
     * @return the number of samples
     */
    public int getNumberOfSamples() {
        return numberOfSamples;
    }

    /**
     *
     * @return the number of sensors
     */
    public int getNumberOfVariables() {
        return data.size();
    }

    /**
     *
     * @return the table containing the time value of each sensor reading
     */
    public String[] getTimeStrings() {
        return timeStrings.toArray(new String[numberOfSamples]);
    }

    /**
     *
     * @return table of date values
     * @throws ParseException ParseException
     */
    public Date[] getDates() throws ParseException {
        Date[] dates = new Date[numberOfSamples];
        DateFormat format = new SimpleDateFormat("yyyy-MM-d HH:mm:ss");
        for (int i = 0; i < numberOfSamples; i++) {
            dates[i] = format.parse(timeStrings.get(i));
        }
        return dates;
    }

    /**
     *
     * @return Sensors table (Toffice, Tcorridor,...)
     */
    public String[] getAvailableVariables() {
        return orderedVariableNames.toArray(new String[getNumberOfVariables()]);
    }

    /**
     *
     * @param variableName : the sensor name
     * @return data table of the sensor
     */
    public Double[] getData(String variableName) {
        return data.get(variableName).toArray(new Double[numberOfSamples]);
    }

    /**
     * Method to add the sensor values to the data in the form of key  values
     * list
     *
     * @param variableName: the sensor name
     * @param values : values table of the sensor
     */
    public void addData(String variableName, double[] values) {
        if (values.length != getNumberOfSamples()) {
            throw new RuntimeException(variableName + " has " + values.length + " samples instead of " + getNumberOfSamples());
        }
        if (data.containsKey(variableName)) {
            throw new RuntimeException(variableName + " already exists");
        }
        orderedVariableNames.add(variableName);
        ArrayList<Double> newValues = new ArrayList<Double>();
        for (double value : values) {
            newValues.add(value);
        }
        data.put(variableName, newValues);
    }

    /**
     * Method to add the sensor values to the data in the form of key values
     * list
     *
     * @param variableName: the sensor name
     * @param values: values table of the sensor
     */
    public void addData(String variableName, Double[] values) {
        double[] primitiveValues = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            primitiveValues[i] = values[i];
        }
        addData(variableName, primitiveValues);
    }

    /**
     *
     * @return string containing the information about the csv file.
     */
    public String toString() {
        String string = getNumberOfVariables() + " variables: ";
        String firstRow = "[";
        String lastRow = "[";
        for (String variableName : getAvailableVariables()) {
            string += variableName + ", ";
            Double[] values = getData(variableName);
            firstRow += values[0] + ", ";
            lastRow += values[numberOfSamples - 1] + ", ";
        }
        string += "\nnumber of data: " + numberOfSamples + "\n";
        string += getTimeStrings()[0] + ": " + firstRow + "]\n...\n" + getTimeStrings()[numberOfSamples - 1] + ": " + lastRow + "]\n";
        return string;
    }
}
