/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fitters;

import data.DataContainerWithProcessing;
import java.util.ArrayList;

/**
 * Class that estimate occupancy based in motion
 * @author Ricardo Guerra
 */
public class LaptopOfficeModel {

    Double[] laptop1Zone_1;
    Double[] laptop1Zone_2;
    Double[] laptop2Zone_2;
    Double[] laptop3Zone_2;
    ArrayList<Double[]> Laptops = new ArrayList<>();

    /**
     * Class to estimate the occupancy based on laptop consuption
     */
    public LaptopOfficeModel() {

    }

    /**
     * Constructor of the laptop estimation class
     * @param data data
     */
    public void LaptopEstimation(DataContainerWithProcessing data) {

        laptop1Zone_1 = data.getData("power_laptop1_zone1");
        laptop1Zone_2 = data.getData("power_laptop1_zone2");
        laptop2Zone_2 = data.getData("power_laptop2_zone2");
        laptop3Zone_2 = data.getData("power_laptop3_zone2");
        Double[] estimation = new Double[laptop1Zone_1.length];
        Laptops.add(laptop1Zone_1);
        Laptops.add(laptop1Zone_2);
        Laptops.add(laptop2Zone_2);
        Laptops.add(laptop3Zone_2);

        for (int k = 0; k < estimation.length; k++) {
            estimation[k] = 0.0;
        }
        for (int i = 0; i < Laptops.size(); i++) {
            for (int j = 0; j < Laptops.get(i).length; j++) {
                if (Laptops.get(i)[j] > 15) {
                    estimation[j] = estimation[j] + 1.0;
                }
            }
        }
        data.addData("occupancy_from_laptops", estimation);
    }
}
