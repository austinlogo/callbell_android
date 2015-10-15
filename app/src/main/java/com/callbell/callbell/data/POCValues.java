package com.callbell.callbell.data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class POCValues {

    public static Map<String, List<String>> pocMap;

    public static String DEFAULT_CHOICE = "Select Chief Complaint...";

    public static String OTHER_CHOICE = "Other";

    //TODO: array values should be constants defined staticly that way we can reference better track these things
    @Inject
    public POCValues() {
        pocMap = new LinkedHashMap<>();

        pocMap.put(DEFAULT_CHOICE, new ArrayList<String>());

        final List<String> abdPain = new ArrayList<>();
        abdPain.add("Choice One");
        abdPain.add("Choice Two");
        abdPain.add("Choice Three");
        pocMap.put("abd pain", abdPain);

        final List<String> eyePain = new ArrayList<>();
        eyePain.add("Choice A");
        eyePain.add("Choice B");
        eyePain.add("Choice C");
        pocMap.put("Eye Pain", eyePain);
    }

}
