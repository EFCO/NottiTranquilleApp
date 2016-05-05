package com.efcompany.nottitranquille.sortingtools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Shalby Omar on 29/01/16.
 */

public class Sorter {

    public ArrayList<HashMap<String, String>> sorter(ArrayList<HashMap<String, String>> list, String tag) {


        List<ToSort> sortList = new ArrayList<ToSort>();

        //Convert from Hashmap to ToSort
        for (HashMap item : list) {
            sortList.add(new ToSort(item.get(tag).toString(), item));
        }

        //Sort
        Collections.sort(sortList);

        //Convert back from ToSort to Sort
        ArrayList<HashMap<String, String>> sortedList = new ArrayList<HashMap<String, String>>();
        for (ToSort item : sortList) {
            sortedList.add(item.getId());
        }

        return sortedList;
    }
}

