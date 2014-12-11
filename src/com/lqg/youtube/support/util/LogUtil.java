package com.lqg.youtube.support.util;


public class LogUtil {

    private static final String DefaultTag = "YouTuBe";
    private static final boolean DEBUG = true;

    public static void v(String msg) {
        if (DEBUG)
            android.util.Log.v(DefaultTag, buildMessage(msg));
    }

    public static void v(String msg, Throwable thr) {
        if (DEBUG)
            android.util.Log.v(DefaultTag, buildMessage(msg), thr);
    }

    public static void d(Object msg) {
        if (DEBUG)
            android.util.Log.d(DefaultTag, buildMessage(msg + ""));
    }

    public static void d(String msg) {
        if (DEBUG)
            android.util.Log.d(DefaultTag, buildMessage(msg));
    }

    public static void d(String msg, Throwable thr) {
        if (DEBUG)
            android.util.Log.d(DefaultTag, buildMessage(msg), thr);
    }

    public static void d(String tag, String msg) {
        if (DEBUG)
            android.util.Log.d(tag, buildMessage(msg));
    }

    public static void i(String msg) {
        if (DEBUG)
            android.util.Log.i(DefaultTag, buildMessage(msg));
    }

    public static void i(String msg, Throwable thr) {
        if (DEBUG)
            android.util.Log.i(DefaultTag, buildMessage(msg), thr);
    }

    public static void e(String msg) {
        if (DEBUG)
            android.util.Log.e(DefaultTag, buildMessage(msg));
    }

    public static void e(String msg, Throwable thr) {
        if (DEBUG)
            android.util.Log.e(DefaultTag, buildMessage(msg), thr);
    }

    public static void w(String msg) {
        if (DEBUG)
            android.util.Log.w(DefaultTag, buildMessage(msg));
    }

    public static void w(String msg, Throwable thr) {
        if (DEBUG)
            android.util.Log.w(DefaultTag, buildMessage(msg), thr);
    }

    public static void w(Throwable thr) {
        if (DEBUG)
            android.util.Log.w(DefaultTag, buildMessage(""), thr);
    }

    protected static String buildMessage(String msg) {
        StackTraceElement caller = new Throwable().fillInStackTrace().getStackTrace()[2];

        return new StringBuilder()
                .append(caller.getClassName())
                .append(".")
                .append(caller.getMethodName())
                .append("(): ")
                .append(msg).toString();
    }
}
