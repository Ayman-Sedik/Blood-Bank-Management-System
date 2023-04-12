package com.dwidar.liveblood.Model.Component;

import com.dwidar.liveblood.Model.Component.HospitalComponents.gHospital;

import java.util.List;

public class DataSettings
{
    private static gHospital myHospital;
    private static String hospitalPwd;
    private static gUser myUser;
    private static List<iBlood> bloods;

    public static List<iBlood> getBloods() {
        return bloods;
    }

    public static void setBloods(List<iBlood> bloods) {
        DataSettings.bloods = bloods;
    }

    public static gHospital getMyHospital() {
        return myHospital;
    }

    public static void setMyHospital(gHospital myHospital) {
        DataSettings.myHospital = myHospital;
    }

    public static gUser getMyUser() {
        return myUser;
    }

    public static void setMyUser(gUser myUser) {
        DataSettings.myUser = myUser;
    }

    public static String getHospitalPwd() {
        return hospitalPwd;
    }

    public static void setHospitalPwd(String hospitalPwd) {
        DataSettings.hospitalPwd = hospitalPwd;
    }
}
