package com.efcompany.nottitranquille.model;

import com.efcompany.nottitranquille.model.enumeration.Gender;

import org.joda.time.DateTime;

/**
 * Created by Administrator on 4/4/16.
 */
public class Person {
    /**
     * Default constructor
     */
    public Person() {
    }

    /**
     *
     */
    private String firstName;

    /**
     *
     */
    private String lastName;

    /**
     *
     */
    private String email;

    /**
     *
     */
    private DateTime birthdate;

    /**
     *
     */
    private String phoneNumber;

    private Address address;

    private Gender gender;

    private Long id;

    public Long getId() {
        return id;
    }
}
