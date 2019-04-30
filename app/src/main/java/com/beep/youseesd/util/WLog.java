package com.beep.youseesd.util;

import android.util.Log;

public class WLog {

    static int lineNumber;
    static String className;
    static String methodName;

    public static boolean isDebuggable() {
//        return BuildConfig.DEBUG;
        return true;
    }

    private static void log(int mode, String className, String msg) {
        if (isDebuggable()) return;
//        CrashlyticsHelper.log(mode, className, msg);
    }

    /**
     * Exceptions
     */
    public static void logException(Throwable e) {
        e.printStackTrace();

        if (!isDebuggable()) return;

//        Crashlytics.logException(e);
    }

    private static String createLog(String log) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append(methodName);
        buffer.append("():");
        buffer.append(lineNumber);
        buffer.append("]");
        buffer.append(log);

        return buffer.toString();
    }

    private static void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[1].getFileName().replaceAll(".java", "");
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }

    public static void e(String message) {
        if (!isDebuggable()) return;

        // Throwable instance must be created before any methods
        getMethodNames(new Throwable().getStackTrace());

        Log.e(className, createLog(message));
        log(Log.ERROR, className, createLog(message));
    }

    public static void i(String message) {
        if (!isDebuggable()) return;

        getMethodNames(new Throwable().getStackTrace());

        Log.i(className, createLog(message));
        log(Log.INFO, className, createLog(message));
    }

    public static void d(String message) {
        if (!isDebuggable()) return;

        getMethodNames(new Throwable().getStackTrace());

        Log.d(className, createLog(message));
        log(Log.DEBUG, className, createLog(message));
    }

    public static void v(String message) {
        if (!isDebuggable()) return;

        getMethodNames(new Throwable().getStackTrace());

        Log.v(className, createLog(message));
        log(Log.VERBOSE, className, createLog(message));
    }

    public static void w(String message) {
        if (!isDebuggable()) return;

        getMethodNames(new Throwable().getStackTrace());

        Log.w(className, createLog(message));
        log(Log.WARN, className, createLog(message));
    }

    public static void wtf(String message) {
        if (!isDebuggable()) return;

        getMethodNames(new Throwable().getStackTrace());

        Log.wtf(className, createLog(message));
        log(Log.ERROR, className, createLog("WatTheFuk: " + message));
    }
}

