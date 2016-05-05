package com.efcompany.nottitranquille.sortingtools;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Shalby Omar on 29/01/16.
 */
public class SortInverter {

    public static ArrayList<HashMap<String, String>> inverter (ArrayList<HashMap<String, String>> list){

        ArrayList<HashMap<String, String>> invertedList = new ArrayList<HashMap<String, String>>();
        for (int i = list.size()-1; i >= 0; i--) {
            invertedList.add(list.get(i));
        }

        return invertedList;
    }
}
