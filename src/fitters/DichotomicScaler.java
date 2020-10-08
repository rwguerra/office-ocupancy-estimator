/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fitters;

import java.util.Arrays;

/**
 * Class to scale rightly the occupancy based in motion
 * @author Ricardo Guerra
 */
public class DichotomicScaler {

    Double[] referenceData;
    Double[] scaledData;
    Double bestScale;
    int numberOfiterations;
    double precision;

    /**
     * Constructor
     * @param referenceData referenceData
     * @param scaledData scaledData
     * @param minScale minScale
     * @param maxScale maxScale
     * @param maxiterations maxiterations
     */
    public DichotomicScaler(Double[] referenceData, Double[] scaledData, Double minScale, Double maxScale, int maxiterations) {
        Double center;
        this.referenceData = referenceData;
        this.scaledData = scaledData;
        Double fLower;
        Double fUpper;
        Double fCenter;
        for (int i = 0; i < maxiterations; i++) {
            center = (minScale + maxScale) / 2;
            fLower = func(minScale);
            fUpper = func(maxScale);
            fCenter = func(center);
            if (fLower < fUpper && fCenter < fUpper) {
                maxScale = center;
            } else if (fCenter < fLower && fUpper < fLower) {
                minScale = center;
            } else {
                maxiterations = i;
                break;
            }
            bestScale = center;
            precision = (maxScale - minScale) / 2;
            numberOfiterations = maxiterations;

        }
    }

    /**
     * Method to return the number of iterations
     * @return numberOfiterations
     */
    public int getNumberOfiterations() {
        return numberOfiterations;
    }

    /**
     * Method to return the best scale
     * @return bestScale
     */
    public double getBestScale() {
        return bestScale;
    }

    /**
     * Method to return the precision
     * @return precision
     */
    public double getPrecision() {
        return precision;
    }

    @Override
    public String toString() {
        return "DischotomicScaler{" + "referenceData=" + Arrays.toString(referenceData) + ", scaledData=" + Arrays.toString(scaledData) + ", bestScale=" + bestScale + ", numberOfiterations=" + numberOfiterations + ", precision=" + precision + '}';
    }

    /**
     * Function
     * @param x x
     * @return fValue
     */
    public Double func(double x) {
        Double fValue = 0.0;
        for (int i = 0; i < referenceData.length; i++) {
            fValue += (referenceData[i] - x * scaledData[i]) * (referenceData[i] - x * scaledData[i]);
        }
        return fValue;
    }
}
