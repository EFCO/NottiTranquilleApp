package com.efcompany.nottitranquille.model;

import org.joda.time.DateTime;

/**
 * Created by Administrator on 3/31/16.
 */
public class SearchData {
    private String nation;
    private String city;
    private DateTime checkin;
    private DateTime checkout;
    private String pricerange;

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

    public String getPricerange() {return pricerange;}

    public void setPricerange(String pricerange) {this.pricerange = pricerange;}
}
