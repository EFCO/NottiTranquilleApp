package com.efcompany.nottitranquille.sortingtools;

import java.util.HashMap;

/**
 * Created by Shalby Omar on 29/01/16.
 */
public class ToSort implements Comparable<ToSort> {

    private String val;
    private HashMap id;

    //Constructor
    public ToSort(String val, HashMap id){
        this.val = val;
        this.id = id;
    }

    @Override
    public int compareTo(ToSort f) {

        if (val.compareTo(f.val)>0) {
            return 1;
        }
        else if (val.compareTo(f.val)<0) {
            return -1;
        }
        else {
            return 0;
        }

    }

    public HashMap getId() {
        return id;
    }

}
