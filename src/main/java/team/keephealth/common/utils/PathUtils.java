package team.keephealth.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class PathUtils {
    static final String IMG = "img/";
    static final String TEXT = "text/";

    public static String generateImgFilePath(String fileName) {
        //根据日期生成路径   2022/1/15/
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        String datePath = sdf.format(new Date());
        //uuid作为文件名
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //后缀和文件后缀一致
        int index = fileName.lastIndexOf(".");
        String fileType = fileName.substring(index);
        return new StringBuilder().append(IMG + "/").append(datePath).append(uuid).append(fileType).toString();
    }

    public static String generateTxtFilePath(String fileName) {
        //根据日期生成路径   2022/1/15/
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        String datePath = sdf.format(new Date());
        //uuid作为文件名
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //后缀和文件后缀一致
        int index = fileName.lastIndexOf(".");
        String fileType = fileName.substring(index);
        return new StringBuilder().append(TEXT + "/").append(datePath).append(uuid).append(fileType).toString();
    }
}