package com.ydys.qmb.util;

/**
 * Created by zhangdinghui on 2019/4/28.
 */
public class MyFileInfoUtils {

    /**
     * 判断图片是否为gif图片
     *
     * @param fileUrl
     * @return
     */
    public static boolean isGif(String fileUrl) {
        String prefix = fileUrl.substring(fileUrl.lastIndexOf(".") + 1);
        if (prefix.toLowerCase().equals("gif")) {
            return true;
        }
        return false;
    }
}
