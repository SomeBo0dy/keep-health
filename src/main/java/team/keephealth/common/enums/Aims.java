package team.keephealth.common.enums;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Aims {

    TOL(1, "全身减脂"),
    WAIST(2, "瘦腰"),
    ARM(3, "瘦胳膊"),
    LEG(4, "瘦腿")
    ;


    int code;
    String aim;

    Aims(int code, String aim){
        this.code = code;
        this.aim = aim;
    }

    public static boolean isExist(int code){
        for (Aims a: Aims.values()) {
            if (a.code == code) return true;
        }
        return false;
    }
    public static String getName(int code){
        for (Aims a: Aims.values()) {
            if (a.code == code) return a.aim;
        }
        return null;
    }

    public static Map<Integer, String> getAims(){
        Map<Integer, String> map = new HashMap<>();
        for (Aims a: Aims.values()) map.put(a.code, a.aim);
        return map;
    }
}
