package team.keephealth.common.constants;

import java.util.Map;

public class SystemConstants {
    public static final String STATUS_NORMAL = "0";
    //是普通用户
    public static final String IS_USER = "0";
    //是第三方工作人员
    public static final String IS_WORKER = "1";
    //是管理员
    public static final String IS_ADMIN = "2";
    //降序
    public static final String DESC = "1";
    //升序
    public static final String ASC = "0";

    public static final Map<String, String> TYPE_MAP = Map.of(IS_USER, "用户", IS_WORKER, "养生大师", IS_ADMIN, "管理员");
    public static final Map<String, Long> ROLE_MAP = Map.of(IS_USER, 2L, IS_WORKER, 3L, IS_ADMIN, 1L);
    public static final String STATUS_BLOCK = "1";
    public static final String EMAIL_NOT_BIND = "未绑定";
    public static final Long ROOT_ID = -1L;

}