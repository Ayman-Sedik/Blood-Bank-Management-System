package com.dwidar.liveblood.Model.Component;

public class Blood
{
    private String type;
    private int hospitalId;
    private int need;
    private int available;

    public Blood(){}

    public Blood(String type, int hospitalId, int need, int available) {
        this.type = type;
        this.hospitalId = hospitalId;
        this.need = need;
        this.available = available;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    public int getNeed() {
        return need;
    }

    public void setNeed(int need) {
        this.need = need;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }
}
