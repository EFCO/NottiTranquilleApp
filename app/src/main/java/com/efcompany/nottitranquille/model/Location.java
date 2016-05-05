package com.efcompany.nottitranquille.model;

import com.efcompany.nottitranquille.model.enumeration.LocationType;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 3/23/16.
 */
public class Location {

    /**
     * Default constructor
     */
    public Location() {
    }

    public Location(List<Interval> booking, Structure structure, Integer maxGuestsNumber, LocationType type) {
        this.booking = booking;
        this.structure = structure;
        this.type = type;
        this.maxGuestsNumber = maxGuestsNumber;
        structure.addLocation(this);
    }

    /**
     *
     */
    private String description;

    /**
     *
     */
    private Integer numberOfRooms;

    /**
     *
     */
    private Integer numberOfBathrooms;

    public Integer getMaxGuestsNumber() {
        return maxGuestsNumber;
    }

    /**
     *
     */
    private Integer maxGuestsNumber;

    /**
     *
     */
    private Integer numberOfBeds;

    /**
     *
     */
    private List<String> photos;

    /**
     *
     */
    private Integer numberOfBedrooms;

    private Prices prices;

    public LocationType getType() {
        return type;
    }

    private LocationType type;


    private List<Service> services;

    private Structure structure;

    private List<Interval> booking = new ArrayList<Interval>();


    public Float getPrice(DateTime date) {
        // TODO implement here
        return null;
    }

    public Float getPrices(DateTime fromDate, DateTime toDate) {
        // TODO implement here
        return null;
    }


    public Float getTotalPrice(Prices basePrice, Set<Condition> conditions) {
        // TODO implement here
        return null;
    }


    public void reserve(DateTime fromDate, DateTime toDate, Set<Condition> conditions) {
        // TODO implement here
    }

    /**
     *
     */
    public boolean isAvailable(Interval interval) {
        for (Interval inter : this.booking) {
            if (interval.isEqual(inter) || (inter.getStart().isBefore(interval.getStart()) && inter.getEnd().isAfter(interval.getEnd()))) {
                return true;
            }
        }
        return false;
    }

    private Long id;

    public Long getId() {
        return id;
    }

    public Structure getStructure() {
        return structure;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public String getDescription() {
        return description;
    }

    public String getLocationAddress() {
        return this.structure.getStructureAddress().getAddress();
    }
}
