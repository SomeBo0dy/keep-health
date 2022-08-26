package team.keephealth.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum Inform {

    OTHER(0, "其他"),
    LAW(1, "违法违规"),
    SUS(2, "色情低俗"),
    FRAUD(3, "赌博诈骗"),
    ATTACK(4, "人身攻击"),
    PRIVACY(5, "侵犯隐私"),
    AD(6, "广告"),
    WAR(7, "引战"),
    MSG(8, "不良信息"),
    IRRELEVANT(9, "内容不相关"),
    SPAM(10, "刷屏")
    ;

    int code;
    String inform;

    Inform(int code, String inform){
        this.code = code;
        this.inform = inform;
    }

    public static String getName(int code){
        for (Inform inform: Inform.values()) {
            if (inform.code == code) return inform.inform;
        }
        return null;
    }

    public static Map<Integer, String> getInforms(){
        Map<Integer, String> map = new HashMap<>();
        for (Inform inform: Inform.values()) map.put(inform.code, inform.inform);
        return map;
    }



}
