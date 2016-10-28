package com.solo.soloclean.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.io.File;
import java.security.MessageDigest;

public class DeviceUtils {

    /**
     * 获取uuid
     */
    @SuppressLint("DefaultLocale")
    public static String getDeviceUUID(Context mContext) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            String uuid = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] bytes = uuid.getBytes("UTF-8");
            digest.update(bytes, 0, bytes.length);
            bytes = digest.digest();

            for (final byte b : bytes) {
                stringBuilder.append(String.format("%02X", b));
            }

            return stringBuilder.toString().toLowerCase();
        } catch (Exception e) {
            return "";
        }
    }

    @SuppressLint("DefaultLocale")
    public static String getAndroidId(Context context) {
        try {
            String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            return androidId.toLowerCase();
        } catch (Exception e) {
            return "";
        }
    }

    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    public static int isRoot() {
        int bool = 0;
        try {
            if ((!new File("/system/bin/su").exists()) && (!new File("/system/xbin/su").exists())) {
                bool = 0;
            } else {
                bool = 1;
            }
        } catch (Exception e) {
        }
        return bool;
    }

}
