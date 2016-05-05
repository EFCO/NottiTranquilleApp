package com.efcompany.nottitranquille.model;

/**
 * Created by Administrator on 3/23/16.
 */
public class Address {

    private String nation;

    private String city;

    private String address;

    private String postalcode;

    public Address(String nation, String city, String address, String postalcode) {
        this.nation = nation;
        this.city = city;
        this.address = address;
        this.postalcode = postalcode;
    }

    /**
     * Default constructor
     */
    public Address() {

    }

    private Long id;

    public Long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }
}
