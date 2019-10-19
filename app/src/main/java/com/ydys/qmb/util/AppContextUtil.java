package com.ydys.qmb.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class AppContextUtil {
    private static Context sContext;

    private AppContextUtil() {

    }

    public static void init(Context context) {
        sContext = context;
    }

    public static Context getInstance() {
        if (sContext == null) {
            throw new NullPointerException("the context is null,please init AppContextUtil in Application first.");
        }
        return sContext;
    }

    //{"author":"admin","agent_id":5}
    // 渠道获取 从配置文件中 找到文件
    public static String getChannel(Context context) {
        String result1 = null;
        ApplicationInfo appinfo = context.getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        ZipFile zf = null;
        try {
            zf = new ZipFile(sourceDir);
            ZipEntry ze1 = zf.getEntry("META-INF/gamechannel.json");
            InputStream in1 = zf.getInputStream(ze1);
            result1 = readString(in1);
            Logger.i("get channel info--->" + result1);
        } catch (Exception e) {
            if (zf != null) {
                try {
                    zf.close();
                } catch (Exception e2) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            Logger.i("文件不存在");
        } finally {
            if (zf != null) {
                try {
                    zf.close();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return result1;
    }

    public static String readString(InputStream in) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;
        StringBuffer result = new StringBuffer();
        while ((line = br.readLine()) != null) {
            result.append(line + "\n");
        }
        return result.toString();
    }
}
