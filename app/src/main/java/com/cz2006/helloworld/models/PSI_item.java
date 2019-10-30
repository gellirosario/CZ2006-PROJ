package com.cz2006.helloworld.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PSI_item {

    @SerializedName("timestamp")
    @Expose
    private String timeStamp;

    @SerializedName("update_timestamp")
    @Expose
    private String updateTimeStamp;

    @SerializedName("readings")
    @Expose
    private PSI_readings readings;

    public PSI_readings getReadings(){

        return readings;

    }

    public void setReadings(PSI_readings readings) {
        this.readings = readings;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUpdateTimeStamp() {
        return updateTimeStamp;
    }

    public void setUpdateTimeStamp(String updateTimeStamp) {
        this.updateTimeStamp = updateTimeStamp;
    }
}
