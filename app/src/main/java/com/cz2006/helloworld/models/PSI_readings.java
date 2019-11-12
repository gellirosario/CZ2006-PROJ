package com.cz2006.helloworld.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Represents PSI Readings
 *
 * @author Edmund
 *
 */
public class PSI_readings {

    @SerializedName("psi_twenty_four_hourly")
    @Expose
    private PSI_twentyfourhour psiTwentyFourHourly;


    public PSI_twentyfourhour getPsiTwentyFourHourly() {
        return psiTwentyFourHourly;
    }

    public void setPsiTwentyFourHourly(PSI_twentyfourhour psiTwentyFourHourly) {
        this.psiTwentyFourHourly = psiTwentyFourHourly;
    }


}