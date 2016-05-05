package com.efcompany.nottitranquille.model;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 3/23/16.
 */
public class Structure {

    public Structure(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public Structure(List<Service> services) {
        this.services = services;
    }

    public String getName() {
        return name;
    }

    public Address getStructureAddress() {
        return address;
    }

    /**
     *
     */
    private String name;

    /**
     *
     */
    private String numberOfLocations;

    /**
     *
     */
    private Set<String> photos;

    /**
     *
     */
    private String termsOfService;

    /**
     *
     */
    private String termsOfCancellation;

    /**
     *
     */
    private DateTime checkIn;

    /**
     *
     */
    private DateTime checkOut;


    private Manager managedBy;

    private Owner owner;

    private Address address;

    private StructureType type;

    private List<Service> services;

    private List<Location> locations = new ArrayList<Location>();

    public void setRequest(Request request) {
        this.request = request;
    }

    private Request request;

    public Structure() {
    }

    @Override
    public String toString() {
        return "Structure{" +
                "name='" + name + '\'' +
                ", numberOfLocations='" + numberOfLocations + '\'' +
                ", photos=" + photos +
                ", termsOfService='" + termsOfService + '\'' +
                ", termsOfCancellation='" + termsOfCancellation + '\'' +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", managedBy=" + managedBy +
                ", owner=" + owner +
                ", address=" + address +
                ", type=" + type +
                ", services=" + services +
                ", id=" + id +
                '}';
    }

    public void addLocation(Location location) {
        this.locations.add(location);
    }


    private Long id;

    public Long getId() {
        return id;
    }

    public List<Location> getLocations() {
        return locations;
    }
}
