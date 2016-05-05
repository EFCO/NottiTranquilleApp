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
    private int price;
    private String locationType;
    private List<String> commodities;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public List<String> getCommodities() {
        return commodities;
    }

    public void setCommodities(List<String> commodities) {
        this.commodities = commodities;
    }
}
