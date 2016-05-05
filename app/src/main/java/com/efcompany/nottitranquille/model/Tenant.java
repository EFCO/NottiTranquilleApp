package com.efcompany.nottitranquille.model;

import java.util.List;

/**
 * Created by Administrator on 4/4/16.
 */
public class Tenant {
    /**
     * Default constructor
     * @param reservations
     */
    public Tenant(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     *
     */
    private List<Reservation> reservations;

    public Tenant() {
    }

    /**
     * @param location
     */
    public void reserve(Location location) {
        // TODO implement here
    }
}
