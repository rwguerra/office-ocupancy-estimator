/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import fitters.CO2OfficeModel;
import fitters.LaptopOfficeModel;
import fitters.MotionOfficeModel;
import java.io.IOException;

/**
 * Class to process de data and estimators
 * @author correiar and birklol
 */
public class DataContainerWithProcessing extends DataContainer {

    LaptopOfficeModel laptop = new LaptopOfficeModel();
    MotionOfficeModel motion = new MotionOfficeModel();
    CO2OfficeModel co2 = new CO2OfficeModel();

    /**
     * Constructor receive the csvfilename and execute the estimators
     * @param csvFileName csvFileName
     * @throws IOException
     */
    public DataContainerWithProcessing(String csvFileName) throws IOException {
        super(csvFileName);

        laptop.LaptopEstimation(this);
        motion.MotionEstimation(this);
        //co2.CO2OfficeEstimation(this); //NOT WORKING

    }
}
