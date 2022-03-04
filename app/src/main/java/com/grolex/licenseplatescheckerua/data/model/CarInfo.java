package com.grolex.licenseplatescheckerua.data.model;

import lombok.Data;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
@Data
public class CarInfo {
    private String id;
    private String number;
    private String model;
    private String year;
    private String date;
    private String registration;
    private String capacity;
    private String color;
    private String body;
    private String kind;
    private String regAddrKoatuu;
    private String ownWeight;
    private String dep;
}