package team.keephealth.common.enums;

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200, "操作成功"),
    // 失败
    ERROR(404, "操作失败"),

    // 登录
    NEED_LOGIN(401, "需要登录后操作"),
    NO_OPERATOR_AUTH(403, "无权限操作"),
    ACCOUNT_NOT_NULL(400100, "账号不得为空"),
    EMAIL_NOT_NULL(400101, "邮箱不得为空"),
    CODE_NOT_NULL(400102, "验证码不得为空"),
    PASSWORD_NOT_NULL(400103, "密码不得为空"),
    TYPE_NOT_NULL(400104, "类型不得为空"),
    PAGE_NUM_NOT_NULL(400105, "页码不得为空"),
    PAGE_SIZE_NOT_NULL(400106, "单页数据量不得为空"),
    USER_ID_NOT_NULL(400107, "用户ID不得为空"),
    TEXT_NOT_NULL(400108,"文件url不得为空" ),
    KSLID_NOT_NULL(400109,"动态的id不得为空" ),
    TIME_ORDER_NOT_NULL(400110,"时间顺序不得为空" ),
    SHOW_USER_ID_NOT_NULL(400111,"动态所属对象id不得为空" ),
    STATE_TYPE_NOT_NULL(400112,"状态参数不得为空" ),
    USER_HEIGHT_NOT_NULL(400113,"用户身高不得为空" ),
    USER_WEIGHT_NOT_NULL(400114,"用户体重不得为空" ),
    USER_TARGET_WEIGHT_NOT_NULL(400115,"用户目标体重不得为空" ),
    USER_BIRTHDAY_NOT_NULL(400116,"用户出生日期不得为空" ),
    COMMENT_ID_NOT_NULL(400117,"评论id不得为空" ),
    CONTENT_NOT_NULL(400118,"内容不得为空" ),
    ROOT_ID_NOT_NULL(400119,"根评论id不得为空" ),
    TIME_NOT_NULL(400200,"时间不得为空" ),
    SYSTEM_ERROR(500, "出现错误"),
    FILE_TYPE_ERROR(500100, "文件格式错误"),
    LOGIN_ERROR(500101, "用户名或密码错误"),
    CODE_OVERDUE(500102, "验证码已过期"),
    CODE_INCORRECT(500103, "验证码错误"),

    EMAIL_NOT_RECORD(500104, "邮箱尚未绑定账号,请使用账号密码登录"),
    EMAIL_LOGIN_FAIL(500105, "邮箱登录失败"),
    PASSWORD_LOGIN_FAIL(500106, "账号密码登录失败"),
    ACCOUNT_EXIST(500107, "账号已存在"),
    EMAIL_BINDED(500108, "该邮箱已被其他用户绑定"),
    USER_EMAIL_BINDED(500109, "该用户已绑定过邮箱"),
    STATE_TYPE_ERROR(500110, "状态参数有误（0正常 1停用）"),
    ALREADY_FOLLOWED(500111, "不可重复关注"),
    WATCHED_USER_NOT_EXIST(500112, "该用户不存在"),
    NEVER_WATCHED_USER(500113, "并未关注此人"),
    KSLID_ERROR(500114,"该动态不存在" ),
    TIME_ORDER_ERROR(500115,"时间排序参数错误" ),
    KSTID_ERROR(500116,"举报信息不存在" ),
    TYPE_ERROR(500117,"类型参数错误" ),
    USER_PROFILE_EXIST(500118,"用户档案已存在" ),
    USER_PROFILE_NOT_EXIST(500119,"用户档案不存在" ),
    CUR_PROFILE_NULL(500120,"请先登记当前档案" ),
    USER_TAR_PROFILE_NOT_EXIST(500121,"用户目标档案不存在" ),
    LIKE_EXIST(500122, "不可重复点赞"),
    LIKE_NOT_EXIST(500123, "未点赞"),
    KSLID_TIP_ERROR(500124,"该动态并未被举报" ),
    FOOD_NOT_FOUND(500125,"很抱歉无法识别该食物" ),

    DATA_WRONG(100, "数据获取错误"),
    DATA_KIND_ERROR(101, "类型参数有误"),
    DATA_DIRECTION_ERROR(102, "排序参数有误"),
    DATA_STATE_ERROR(103, "状态参数有误"),
    DATA_ARTICLE_ID_ERROR(104, "获取文章编号有误"),
    DATA_PAGESIZE_ERROR(105, "每页数据量大小错误"),
    DATA_REPLY_ID_ERROR(106, "回复的评论id错误"),
    DATA_COMMENT_ID_ERROR(107, "评论id错误"),
    DATA_CHECK_ERROR(108, "审核信息错误"),
    DATA_KCAL_ERROR(109, "总热量参数错误/不可为零"),
    DATA_BF_KCAL_ERROR(110, "早餐热量参数错误/不可为零"),
    DATA_LU_KCAL_ERROR(111, "午餐热量参数错误/不可为零"),
    DATA_DN_KCAL_ERROR(112, "晚餐热量参数错误/不可为零"),
    DATA_RECIPE_ID_ERROR(113, "食谱id错误"),
    DATA_SPORT_ID_ERROR(114, "运动id错误"),
    DATA_WWE_ERROR(115, "运动项目编号错误"),
    DATA_TIME_ERROR(116, "时间长度不合理"),
    DATA_END_TIME_ERROR(117, "起止时间不合理"),
    DATA_AIM_ERROR(118, "目标编号错误"),
    DATA_PLAN_ID_ERROR(119, "计划id错误"),
    DATA_KCAL_RANGE_ERROR(120, "kcal范围不合理"),

    EMPTY_TITLE(130, "标题参数为空"),
    EMPTY_WORDS(131, "正文参数为空"),
    EMPTY_KEYWORD(132, "关键词参数为空"),
    EMPTY_COMMENT(133, "评论内容错误/为空"),
    EMPTY_MESSAGE(134, "详细信息为空"),
    EMPTY_BREAKFAST(135, "早餐内容为空"),
    EMPTY_LUNCH(136, "午餐内容为空"),
    EMPTY_DINNER(137, "晚餐内容为空"),
    EMPTY_DETAIL_LIST(138, "阶段信息为空"),
    EMPTY_PUNCH_MSG(139, "无打卡信息"),

    ARTICLE_EXIST(150, "该文章已存在"),
    KUDOS_EXIST(154, "已赞"),
    SET_ERROR(151, "数据更新失败"),
    SET_ARTICLE_ERROR(152, "文章数据更新失败"),
    SET_CONTENT_ERROR(153, "content数据更新失败"),
    GET_REPLY_ERROR(155, "该评论为回复的评论，不可回复"),
    SET_FOOD_ERROR(156, "餐品未添加/更改成功"),
    PLAN_PUNCH_EXIST(157, "今日计划已打卡"),
    BREAKFAST_EXIST(158, "早餐已打卡"),
    LUNCH_EXIST(159, "午餐已打卡"),
    DINNER_EXIST(160, "晚餐已打卡"),

    GET_PAGE_ERROR(180, "当前页码错误"),
    GET_CONTENT_ERROR(181, "该文章内容不存在"),
    GET_KUDOS_ERROR(182, "未点赞"),
    GET_CHECK_ERROR(183, "审核状态未更新"),
    GET_PUNCH_ERROR(184, "未记录"),
    GET_CHOOSE_ERROR(185, "不可同时选择多个套餐/运动计划"),
    GET_CHOOSE_NOR(186, "未选择计划");

    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage) {
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
