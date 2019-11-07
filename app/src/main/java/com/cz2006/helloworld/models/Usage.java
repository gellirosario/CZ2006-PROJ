package com.cz2006.helloworld.models;

public class Usage {

    private int userId;
    private int usageYear;
    private int usageMonth;
    private int usageAmount;
    private String usageType;

    public Usage(){

    }

    public Usage(int userId, int usageYear, int usageMonth, int usageAmount, String usageType){
        this.userId = userId;
        this.usageYear = usageYear;
        this.usageMonth = usageMonth;
        this.usageAmount = usageAmount;
        this.usageType = usageType;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUsageYear() {
        return usageYear;
    }

    public void setUsageYear(int usageYear) {
        this.usageYear = usageYear;
    }

    public int getUsageMonth() {
        return usageMonth;
    }

    public void setUsageMonth(int usageMonth) {
        this.usageMonth = usageMonth;
    }

    public int getUsageAmount() {
        return usageAmount;
    }

    public void setUsageAmount(int usageAmount) {
        this.usageAmount = usageAmount;
    }

    public String getUsageType() {
        return usageType;
    }

    public void setUsageType(String usageType) {
        this.usageType = usageType;
    }
}
