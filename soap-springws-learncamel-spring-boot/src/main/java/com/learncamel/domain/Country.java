package com.learncamel.domain;

public class Country {

    protected String countrycode;


    protected String name;

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Country{" +
                "countrycode='" + countrycode + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}


