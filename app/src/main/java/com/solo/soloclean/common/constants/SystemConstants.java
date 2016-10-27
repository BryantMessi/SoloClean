package com.solo.soloclean.common.constants;

import java.io.File;

/**
 * Created by Messi on 16-10-25.
 */

public class SystemConstants {
    //系统日志及临时文件存放目录string 类型，因手机而异
    public static final String PATH_DATA_TMP = "/data/tmp";

    public static final String PATH_DATA_LOCAL_TMP = "/data/local/tmp";

    public static final String PATH_DATA_SYSTEM_USAGESTATS = "/data/system/usagestats/";

    public static final String PATH_DATA_SYSTEM_APPUSAGESTATES = "/data/system/appusagestates/";

    public static final String PATH_DATA_SYSTEM_DROPBOX = "/data/system/dropbox/";

    public static final String PATH_DATA_TOMBSTONES = "/data/tombstones/";

    public static final String PATH_DATA_ANR = "/data/anr/";

    public static final String PATH_DEV_LOG_MAIN = " /dev/log/main";

    //系统日志及临时文件存放目录file 类型，因手机而异
    public static final File FILE_DATA_TMP = new File(PATH_DATA_TMP);

    public static final File FILE_DATA_LOCAL_TMP = new File(PATH_DATA_LOCAL_TMP);

    public static final File FILE_DATA_SYSTEM_USAGESTATS = new File(PATH_DATA_SYSTEM_USAGESTATS);

    public static final File FILE_DATA_SYSTEM_APPUSAGESTATES = new File(PATH_DATA_SYSTEM_APPUSAGESTATES);

    public static final File FILE_DATA_SYSTEM_DROPBOX = new File(PATH_DATA_SYSTEM_DROPBOX);

    public static final File FILE_DATA_TOMBSTONES = new File(PATH_DATA_TOMBSTONES);

    public static final File FILE_DATA_ANR = new File(PATH_DATA_ANR);

    public static final File FILE_DEV_LOG_MAIN = new File(PATH_DEV_LOG_MAIN);

    //大文件阀值

    public static final long BIG_FILE_THRESHOLD = 10 * 1024 * 1024;
}
