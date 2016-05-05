package com.efcompany.nottitranquille.model;

import com.efcompany.nottitranquille.model.enumeration.ReservationState;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by Administrator on 3/23/16.
 */
public class Reservation {

    /**
     * Default constructor
     */
    public Reservation() {
    }

    /**
     *
     */
    private DateTime date;

    /**
     *
     */

    private List<Service> services;

    /**
     *
     */
    private ReservationState state;

    /**
     *
     */

    private Request request;



    /**
     *
     */
    public void pay() {
        // TODO implement here
    }

    /**
     * @return
     */
    public Prices CalculatePrice() {
        // TODO implement here
        return null;
    }

    /**
     *
     */
    public void addService() {
        // TODO implement here
    }

    /**
     *
     */
    public void remoseService() {
        // TODO implement here
    }

    /**
     *
     */
    public void changeState() {
        // TODO implement here
    }

    /**
     *
     */
    public void reservationNotify() {
        // TODO implement here
    }


    private Long id;

    public Long getId() {
        return id;
    }
}
