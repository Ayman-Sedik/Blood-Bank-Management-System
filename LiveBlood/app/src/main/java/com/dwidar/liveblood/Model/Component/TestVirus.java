package com.dwidar.liveblood.Model.Component;

public class TestVirus
{
    private String name;
    private String bloodType;

    public TestVirus(){}
    public TestVirus(String name, String bloodType) {
        this.name = name;
        this.bloodType = bloodType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }
}
