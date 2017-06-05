package com.example.cesare.leagueoflegendscoaching;

import java.util.Map;

/**
 * Created by cesare on 05/06/2017.
 */

public class Miscellaneous {
    public static final Map<String, Integer> eloMap = createEloMap();

    private static Map<String, Integer> createEloMap(){
        Map<String, Integer> eloMap = null;
        eloMap.put("Bronze", 0);
        eloMap.put("Silver", 1);
        eloMap.put("Gold", 2);
        eloMap.put("Platinum", 3);
        eloMap.put("Diamond", 4);
        eloMap.put("Master", 5);
        eloMap.put("Challenger", 6);

        return eloMap;
    }
}
