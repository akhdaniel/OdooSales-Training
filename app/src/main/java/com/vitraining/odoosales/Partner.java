package com.vitraining.odoosales;


import java.util.Map;

public class Partner {
    private Integer id;
    private String name;
    private String street;
    private String street2;
    private String city;
    private String state;
    private String country;
    private String email;
    private String mobile;

    private Integer stateId;
    private Integer countryId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public void setData(Map<String, Object> data){
        setId( (Integer) data.get("id") );
        setName( OdooUtility.getString(data, "name") );
        setStreet( OdooUtility.getString(data, "street") );
        setStreet2( OdooUtility.getString(data, "street2") );
        setCity( OdooUtility.getString(data, "city") );
        setMobile( OdooUtility.getString(data, "mobile") );
        setEmail( OdooUtility.getString(data, "email") );

        //country, state : Many2one [12, "Indonesia"]
        M2Ofield country_id = OdooUtility.getMany2One(data, "country_id");
        setCountry(country_id.value);
        setCountryId(country_id.id);

        M2Ofield state_id = OdooUtility.getMany2One(data, "state_id");
        setState(state_id.value);
        setStateId(state_id.id);

    }
}


