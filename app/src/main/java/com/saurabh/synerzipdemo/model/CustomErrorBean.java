package com.saurabh.synerzipdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by SaurabhA on 03,October,2020
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomErrorBean extends Throwable {

    @JsonProperty("ErrorMessage")
    private String errorMessage;
    @JsonProperty("ErrorCode")
    private int errorCode;

    public CustomErrorBean() {

    }

    public CustomErrorBean(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
