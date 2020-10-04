package com.saurabh.synerzipdemo.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by SaurabhA on 03,October,2020
 *
 */
@Entity(tableName = "tbl_weather_report")
public class SynerzipDataEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "city_id")
    private int cityId;
    @ColumnInfo(name = "city_name")
    private String cityName;
    @ColumnInfo(name = "city_temp")
    private int cityTemp;
    @ColumnInfo(name = "isActive")
    private Boolean isActive;
    @ColumnInfo(name = "created_date", defaultValue = "current_date")
    private String createdDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityTemp() {
        return cityTemp;
    }

    public void setCityTemp(int cityTemp) {
        this.cityTemp = cityTemp;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
