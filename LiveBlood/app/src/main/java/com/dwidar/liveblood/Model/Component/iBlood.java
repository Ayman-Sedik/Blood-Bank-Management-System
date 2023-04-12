package com.dwidar.liveblood.Model.Component;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class iBlood {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("need")
    @Expose
    private String need;
    @SerializedName("available")
    @Expose
    private String available;
    @SerializedName("hospitalId")
    @Expose
    private Integer hospitalId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNeed() {
        return need;
    }

    public void setNeed(String need) {
        this.need = need;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }

}