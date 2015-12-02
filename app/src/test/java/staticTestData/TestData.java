package staticTestData;

import com.callbell.callbell.models.State.State;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by austin on 11/30/15.
 */
public class TestData {

    public static State state_bed() {
        return new State("TEST",
                "ER",
                "1",
                "STATION",
                "DOC",
                "NURSE",
                "RES",
                "CHIEF",
                Arrays.asList(0,1,2,3),
                Arrays.asList(4,5,6,7),
                Arrays.asList("one", "two", "three", "four"),
                Arrays.asList("five", "six", "seven", "eight"),
                1);
    }
    public static State state_station() {
        return new State("TEST",
                "ER",
                "STATION",
                "",
                "",
                "",
                "",
                "",
                new ArrayList<Integer>(),
                new ArrayList<Integer>(),
                new ArrayList<String>(),
                new ArrayList<String>(),
                1);
    }

}
