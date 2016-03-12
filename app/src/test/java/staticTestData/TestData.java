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
                0,
                Arrays.asList(0,1),
                Arrays.asList(5,6),
                Arrays.asList(4,7),
                Arrays.asList(6,7),
                Arrays.asList(0,1,2,3,4),
                Arrays.asList(5,6,7,8),
                1,
                10);
    }

    public static State state_bed_2() {
        return new State("TEST",
                "ER",
                "1",
                "BED",
                "DOC_2",
                "NURSE_2",
                "RES_2",
                0,
                Arrays.asList(0,1),
                Arrays.asList(5,6),
                Arrays.asList(4,7),
                Arrays.asList(6,7),
                Arrays.asList(0,1,2,3,4),
                Arrays.asList(5,6,7,8),
                1,
                10);
    }


    public static State state_station() {
        return new State("TEST",
                "ER",
                "STATION",
                "",
                "",
                "",
                "",
                0,
                new ArrayList<Integer>(),
                new ArrayList<Integer>(),
                new ArrayList<Integer>(),
                new ArrayList<Integer>(),
                new ArrayList<Integer>(),
                new ArrayList<Integer>(),
                1,
                10);
    }

}
