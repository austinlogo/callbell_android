package com.callbell.callbell.data;

import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class POCValues {

    public static HashMap<String, String[]> pocMap;

    public static String DEFAULT_CHOICE = "Select Chief Complaint...";

    //TODO: array values should be constants defined staticly that way we can reference better track these things
    @Inject
    public POCValues() {
        pocMap = new HashMap<>();

        pocMap.put(DEFAULT_CHOICE, new String[]{} );

        pocMap.put("abd pain", new String[]{
                "medicine One",
                "X-RAY",
                "Something else"
        });

        pocMap.put("Eye Pain", new String[] {
                "Eye Drops",
                "Acetaminaphin",
                "third thing"
        });

        pocMap.put("Blood in Urine", new String[] {
                "Amputation",
                "Something less Graphic",
                "Something more reasonable",
                "the correct solution"
        });
    }

}
