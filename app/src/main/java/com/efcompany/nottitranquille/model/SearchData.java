package com.efcompany.nottitranquille.model;

import com.efcompany.nottitranquille.R;

import org.joda.time.DateTime;

/**
 * Created by Administrator on 3/31/16.
 */
public class SearchData {
    private String nation;
    private String city;
    private String checkin;
    private String checkout;
    private String pricerange;

    private String search = "search";

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

    public String getPricerange() {return pricerange;}

    public void setPricerange(String pricerange) {this.pricerange = pricerange;}

    @Override
    public String toString() {
        //return super.toString();
        return new StringBuffer()
                .append(nation).append(city).append(checkin).append(checkout).append(pricerange).toString();
    }
}
