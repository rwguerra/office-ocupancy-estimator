/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fitters;

import data.DataContainerWithProcessing;

/**
 * Class that estimate occupancy based in motion
 * @author Ricardo Guerra
 */
public class MotionOfficeModel{
    
        Double[] estimator_motions;
        Double[] estimator_Lap;
        Double[] Motion;
        Double minScale = 0.0;
        Double maxScale = 0.3;
        int maxIterations = 50;
        
        
    public MotionOfficeModel() {
    }
    public void MotionEstimation(DataContainerWithProcessing data){
        estimator_motions = data.getData("detected_motions");
        estimator_Lap = data.getData("occupancy_from_laptops");
        Motion = new Double[estimator_Lap.length];
        
        
        // Build of dichotomic scaler
        DichotomicScaler dic = new DichotomicScaler(estimator_Lap,estimator_motions,minScale,maxScale,maxIterations);
        Double scale = dic.getBestScale();
        for(int i = 0; i < estimator_motions.length; i++){
            Motion[i] = estimator_motions[i] * scale;
        }
        data.addData("occupancy_from_motions", Motion);
    }
}
