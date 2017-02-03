package com.mtsmda.apache.activemq.domain;

import java.io.Serializable;

/**
 * Created by dminzat on 1/31/2017.
 */
public class FootballClub implements Serializable{

    private Integer footballClubId;
    private String name;
    private String countryName;
    private String cityName;

    public FootballClub() {

    }

    public FootballClub(Integer footballClubId, String name, String countryName, String cityName) {
        this.footballClubId = footballClubId;
        this.name = name;
        this.countryName = countryName;
        this.cityName = cityName;
    }

    public Integer getFootballClubId() {
        return footballClubId;
    }

    public void setFootballClubId(Integer footballClubId) {
        this.footballClubId = footballClubId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return "FootballClub{" +
                "footballClubId=" + footballClubId +
                ", name='" + name + '\'' +
                ", countryName='" + countryName + '\'' +
                ", cityName='" + cityName + '\'' +
                '}';
    }
}