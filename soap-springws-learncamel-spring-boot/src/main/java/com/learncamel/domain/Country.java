package com.learncamel.domain;

public class Country {

    protected String sISOCode;
    protected String sName;

    public String getsISOCode() {
        return sISOCode;
    }

    public void setsISOCode(String sISOCode) {
        this.sISOCode = sISOCode;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    @Override
    public String toString() {
        return "Country{" +
                "sISOCode='" + sISOCode + '\'' +
                ", sName='" + sName + '\'' +
                '}';
    }
}


