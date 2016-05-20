package com.efcompany.nottitranquille.model;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 4/1/16.
 */
public class AdvSearchData {
    private String nation;
    private String city;
    private String checkin;
    private String checkout;
    private String pricerange;
    private String locationtype;
    private int maxtenant;
    private ArrayList<String> commodities = new ArrayList<>();

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

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public ArrayList<String> getCommodities() {
        return commodities;
    }

    public void setCommodities(ArrayList<String> commodities) {
        this.commodities = commodities;
    }

    public String getPricerange() {return pricerange;}

    public void setPricerange(String pricerange) {this.pricerange = pricerange;}

    public String getLocationtype() {return locationtype;}

    public void setLocationtype(String locationtype) {this.locationtype = locationtype;}

    @Override
    public String toString() {
        //return super.toString();
        return new StringBuffer()
                .append(nation).append(city).append(checkin).append(checkout).append(pricerange)
                .append(locationtype).append(maxtenant).toString();
    }
}
