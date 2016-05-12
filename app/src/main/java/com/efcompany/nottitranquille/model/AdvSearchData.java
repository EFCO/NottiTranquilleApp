package com.efcompany.nottitranquille.model;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by Administrator on 4/1/16.
 */
public class AdvSearchData {
    private String nation;
    private String city;
    private DateTime checkin;
    private DateTime checkout;
    private String pricerange;
    private String locationtype;
    private int maxtenant;
    private List<String> commodities;

    private String search = "advsearch";

    public int getMaxtenant() {
        return maxtenant;
    }

    public void setMaxtenant(int maxtenant) {
        this.maxtenant = maxtenant+1;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public DateTime getCheckin() {
        return checkin;
    }

    public void setCheckin(DateTime checkin) {
        this.checkin = checkin;
    }

    public DateTime getCheckout() {
        return checkout;
    }

    public void setCheckout(DateTime checkout) {
        this.checkout = checkout;
    }

    public List<String> getCommodities() {
        return commodities;
    }

    public void setCommodities(List<String> commodities) {
        this.commodities = commodities;
    }

    public String getPricerange() {return pricerange;}

    public void setPricerange(String pricerange) {this.pricerange = pricerange;}

    public String getLocationtype() {return locationtype;}

    public void setLocationtype(String locationtype) {this.locationtype = locationtype;}
}
