package com.efcompany.nottitranquille.model;

import com.efcompany.nottitranquille.model.enumeration.RequestStatus;

import org.joda.time.DateTime;

/**
 * Created by Administrator on 4/4/16.
 */
public class Request {
    /**
     * Default constructor
     * @param requestDate
     * @param acceptedDate
     * @param lastModified
     * @param requestedBy
     * @param reviewedBy
     * @param structure
     * @param status
     */
    public Request(DateTime requestDate, DateTime acceptedDate, DateTime lastModified, Person requestedBy, Scout reviewedBy, Structure structure, RequestStatus status) {
        this.requestDate = requestDate;
        this.acceptedDate = acceptedDate;
        this.lastModified = lastModified;
        this.requestedBy = requestedBy;
        this.reviewedBy = reviewedBy;
        this.structure = structure;
        structure.setRequest(this);
        this.status = status;
    }

    public Request(Structure structure) {
        this.structure = structure;
        structure.setRequest(this);
        this.status = RequestStatus.Accepted;
    }

    public Request() {
    }

    /**
     *
     */
    private DateTime requestDate;

    /**
     *
     */
    private DateTime acceptedDate;

    /**
     *
     */
    private DateTime lastModified;

    private Person requestedBy;

    private Scout reviewedBy;

    private Structure structure;

    private RequestStatus status;


    private Long id;

    public Long getId() {
        return id;
    }


    public Structure getStructure() {
        return structure;
    }
}
