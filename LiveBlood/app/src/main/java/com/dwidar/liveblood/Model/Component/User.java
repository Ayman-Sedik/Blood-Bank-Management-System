package com.dwidar.liveblood.Model.Component;

import com.google.gson.annotations.SerializedName;

public class User
{
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String Name;

    @SerializedName("email")
    private String Email;

    @SerializedName("password")
    private String Pwd;

    @SerializedName("phone")
    private String Phone;

    @SerializedName("address")
    private String Address;

    @SerializedName("bloodType")
    private String BloodType;

    @SerializedName("latitude")
    private String Latitude;

    @SerializedName("longitude")
    private String Longitude;

    public User(){}

    public User(String name, String email, String pwd, String phone, String address, String bloodType, String lat, String lng) {
        Name = name;
        Email = email;
        Pwd = pwd;
        Phone = phone;
        Address = address;
        BloodType = bloodType;
        Latitude = lat;
        Longitude = lng;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPwd() {
        return Pwd;
    }

    public void setPwd(String pwd) {
        Pwd = pwd;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone)
    {
        Phone = phone;
    }

    public String getAddress()
    {
        return Address;
    }

    public void setAddress(String address)
    {
        Address = address;
    }

    public String getLatitude()
    {
        return Latitude;
    }

    public void setLatitude(String latitude)
    {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude)
    {
        Longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public String getBloodType() {
        return BloodType;
    }

    public void setBloodType(String bloodType) {
        BloodType = bloodType;
    }

    public void setId(int id) {
        this.id = id;
    }

}
