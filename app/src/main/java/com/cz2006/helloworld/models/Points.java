package com.cz2006.helloworld.models;

/**
 * Represents User's Points
 *
 * @author Rosario Gelli Ann
 *
 */
public class Points {

    private int pointID;
    private int points;
    private String pointType;
    private String pointDate;

    public Points(){

    }

    public Points(int pointID, int points, String pointType, String pointDate){
        this.pointID = pointID;
        this.points = points;
        this.pointDate = pointDate;
        this.pointType = pointType;
    }

    public int getPointID() {
        return pointID;
    }

    public void setPointID(int pointID) {
        this.pointID = pointID;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getPointType() {
        return pointType;
    }

    public void setPointType(String pointType) {
        this.pointType = pointType;
    }

    public String getPointDate() {
        return pointDate;
    }

    public void setPointDate(String pointDate) {
        this.pointDate = pointDate;
    }
}
