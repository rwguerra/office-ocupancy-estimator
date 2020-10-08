/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fitters;

import data.DataContainerWithProcessing;
import data.SimulatedAnnealing;

/**
 * Class that implements the CO2 occupancy estimator
 * @author Ricardo Guerra
 */
public class CO2OfficeModel {

    double[] doorOpening, windowOpening, officeCO2Concentration, corridorCO2Concentration, estimatedOccupation;
    double roomVolume = 55;
    double co2BreathProduction = 4;
    double samplePeriod = 3600;
    double outdoorCO2concentration = 395;

    /**
     * CO2 Occupancy constructor
     */
    public CO2OfficeModel() {

    }

    /**
     * Method that estimate the CO2 occupancy
     * @param data data
     */
    public void CO2OfficeEstimation(DataContainerWithProcessing data) {
        
        
        
        Double[] X0 = {25 / samplePeriod, 25 / samplePeriod, 150 / samplePeriod, 150 / samplePeriod};
        int maxIterations = 1000;
        Double[] windowData = data.getData("window_opening");
        Double[] doorData = data.getData("door_opening");
        Double[] referenceData = data.getData("occupancy_from_laptops");
        Double[] tOffice = data.getData("Toffice");
        Double[] tOut = data.getData("Tcorridor");
        Double[] tCorridor = data.getData("Tout");
        Double[] occFromCO2 = new Double[referenceData.length];
        Double[] QOut = new Double[windowData.length];
        Double[] QCorridor = new Double[doorData.length];
        SimulatedAnnealing sA = new SimulatedAnnealing(referenceData, windowData, doorData, tOut, tOffice, tCorridor, X0, maxIterations);
        Double[] Xb = sA.bestValues();
        for (int i = 0; i < QOut.length; i++) {
            QOut[i] = Xb[0] + windowData[i] * Xb[2];
            QCorridor[i] = Xb[1] + doorData[i] * Xb[3];
        }
        for (int i = 0; i < referenceData.length - 1; i++) {
            Double ak = Math.exp(-(QOut[i] + QCorridor[i]) * samplePeriod / roomVolume);
            Double bOut = ((1 - ak) * QOut[i]) / (QOut[i] + QCorridor[i]);
            Double bCorridor = ((1 - ak) * QCorridor[i]) / (QOut[i] + QCorridor[i]);
            Double bn = ((1 - ak) * co2BreathProduction) / (QOut[i] + QCorridor[i]);
            occFromCO2[i] = (tOffice[i + 1] - ak * tOffice[i] - bOut * tOut[i] - bCorridor * tCorridor[i]) / bn;

        }

        data.addData("occupancy_from_CO2", occFromCO2);
    }
}
