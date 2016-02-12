package com.callbell.callbell.util;

import com.callbell.callbell.data.MedicationValues;
import com.callbell.callbell.data.POCValues;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by austin on 1/23/16.
 */
public class MixedListUtil {

    private static String decodeTest(Integer i) {
        return POCValues.masterMap.getValue(i);
    }

    public static String decodeMedication(String str) {
        return str;
    }

    public static String decodeMedication(Integer i) {
        return MedicationValues.masterMap.getValue(i);
    }

    public static List<String> decodeTestList(List<Integer> list) {
        List<String> result = new ArrayList<>();

        for (Integer i : list) {
            result.add(decodeTest(i));
        }

        return result;
    }
}
