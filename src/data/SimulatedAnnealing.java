/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 * Class SimulatedAnnealing
 * @author its
 */
public class SimulatedAnnealing {

    private Double Ts = 3600.0;
    private Double V = 55.0;
    private Double Sb = 4.0;
    private Double r = 0.1;
    private Double outdoorC02Concentration = 395.0;
    private Double[] X0;
    private Double[] referenceData;
    private Double[] windowData;
    private Double[] doorData;
    private Double[] tOut;
    private Double[] tOffice;
    private Double[] tCorridor;
    private int maxIterations;

    /**
     * Constructor receive the parameters to simulatedAnnealing
     * @param referenceData referenceData
     * @param windowData windowData
     * @param doorData doorData
     * @param tOut tOut
     * @param tOffice tOffice
     * @param tCorridor tCorridor
     * @param X0 X0
     * @param maxIterations maxIterations
     */
    public SimulatedAnnealing(Double[] referenceData, Double[] windowData, Double[] doorData, Double[] tOut, Double[] tOffice, Double[] tCorridor, Double[] X0, int maxIterations) {
        this.referenceData = referenceData;
        this.windowData = windowData;
        this.doorData = doorData;
        this.X0 = X0;
        this.tCorridor = tCorridor;
        this.tOut = tOut;
        this.tOffice = tOffice;
        this.maxIterations = maxIterations;

    }

    /**
     * Method to estimate the error
     * @param X X
     * @return the error
     */
    public Double erreurEstimation(Double[] X) {
        Double erreur = 0.0;
        Double estimation = 0.0;
        Double[] QOut = new Double[this.windowData.length];
        Double[] QCorridor = new Double[this.doorData.length];
        for (int i = 0; i < QOut.length; i++) {
            QOut[i] = X[0] + this.windowData[i] * X[2];
            QCorridor[i] = X[1] + this.doorData[i] * X[3];
        }
        for (int i = 0; i < this.referenceData.length - 1; i++) {
            Double ak = Math.exp(-(QOut[i] + QCorridor[i]) * Ts / V);
            Double bOut = ((1 - ak) * QOut[i]) / (QOut[i] + QCorridor[i]);
            Double bCorridor = ((1 - ak) * QCorridor[i]) / (QOut[i] + QCorridor[i]);
            Double bn = ((1 - ak) * Sb) / (QOut[i] + QCorridor[i]);
            estimation = (tOffice[i + 1] - ak * tOffice[i] - bOut * tOut[i] - bCorridor * tCorridor[i]) / bn;
            erreur += (this.referenceData[i] - estimation) * (this.referenceData[i] - estimation);
        }
        erreur += (this.referenceData[this.referenceData.length - 1] - estimation) * (this.referenceData[this.referenceData.length - 1] - estimation);
        return erreur;

    }

    /**
     *  Method to create random jump
     * @param X X
     * @return XOut
     */
    public Double[] randomJumpFunction(Double[] X) {
        Double[] XOut = new Double[X.length];
        for (int i = 0; i < X.length; i++) {
            XOut[i] = X[i] * (1 + (2 * Math.random() - 1) * r);
        }
        return XOut;
    }

    /**
     *  Method to find the best values
     * @return Xb
     */
    public Double[] bestValues() {
        Double[] Xc = X0;
        Double[] Xr = X0;
        Double[] Xb = X0;
        for (int k = 0; k < maxIterations - 1; k++) {
            Xc = randomJumpFunction(Xr);
            if (erreurEstimation(Xc) < erreurEstimation(Xb)) {
                Xb = Xb;
            }
            if (Math.random() < Math.exp((erreurEstimation(Xr)) - erreurEstimation(Xc) / (k * erreurEstimation(Xr) * (maxIterations - k) / (maxIterations)))) {
                Xr = Xc;
            }

        }
        return Xb;
    }

}
