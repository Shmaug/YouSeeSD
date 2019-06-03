package com.beep.youseesd.util;

import android.util.Log;

/**
 * Utility class that wraps the Android Logger for convenience
 */
public class WLog {

  private static int lineNumber;
  private static String className;
  private static String methodName;

  /**
   * Always returns true (for now)
   *
   * @return true if the app is debuggable
   */
  public static boolean isDebuggable() {
    return true;
  }

  /**
   * Returns a buffer of the string with some formatting
   *
   * @param log the log
   * @return the formatted log
   */
  private static String createLog(String log) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("[");
    buffer.append(getMethodName());
    buffer.append("():");
    buffer.append(getLineNumber());
    buffer.append("]");
    buffer.append(log);

    return buffer.toString();
  }

  /**
   * Gets the method names in question
   *
   * @param sElements the array of elements in the StackTrace
   */
  private static void getMethodNames(StackTraceElement[] sElements) {
    setClassName(sElements[1].getFileName().replaceAll(".java", ""));
    setMethodName(sElements[1].getMethodName());
    setLineNumber(sElements[1].getLineNumber());
  }

  /**
   * Logs using level e
   *
   * @param message the message to log
   */
  public static void e(String message) {
    if (!isDebuggable()) return;

    // Throwable instance must be created before any methods
    getMethodNames(new Throwable().getStackTrace());

    Log.e(getClassName(), createLog(message));
  }

  /**
   * Logs using level i
   *
   * @param message the message to log
   */
  public static void i(String message) {
    if (!isDebuggable()) return;

    getMethodNames(new Throwable().getStackTrace());

    Log.i(getClassName(), createLog(message));
  }

  /**
   * Logs using level d
   *
   * @param message the message to log
   */
  public static void d(String message) {
    if (!isDebuggable()) return;

    getMethodNames(new Throwable().getStackTrace());

    Log.d(getClassName(), createLog(message));
  }

  /**
   * Logs using level v
   *
   * @param message the message to log
   */
  public static void v(String message) {
    if (!isDebuggable()) return;

    getMethodNames(new Throwable().getStackTrace());

    Log.v(getClassName(), createLog(message));
  }

  /**
   * Logs using level w
   *
   * @param message the message to log
   */
  public static void w(String message) {
    if (!isDebuggable()) return;

    getMethodNames(new Throwable().getStackTrace());

    Log.w(getClassName(), createLog(message));
  }

  /**
   * Logs using level wtf
   *
   * @param message the message to log
   */
  public static void wtf(String message) {
    if (!isDebuggable()) return;

    getMethodNames(new Throwable().getStackTrace());

    Log.wtf(getClassName(), createLog(message));
  }

  /**
   * Getter for lineNumber
   *
   * @return lineNumber
   */
  public static int getLineNumber() {
    return lineNumber;
  }

  /**
   * Setter for lineNumber
   *
   * @param lineNumber the lineNumber to be set
   */
  public static void setLineNumber(int lineNumber) {
    WLog.lineNumber = lineNumber;
  }

  /**
   * Getter for className
   *
   * @return className
   */
  public static String getClassName() {
    return className;
  }

  /**
   * Setter for className
   *
   * @param className the className to be set
   */
  public static void setClassName(String className) {
    WLog.className = className;
  }

  /**
   * Getter for methodName
   *
   * @return methodName
   */
  public static String getMethodName() {
    return methodName;
  }

  /**
   * Setter for methodName
   *
   * @param methodName the methodName to be set
   */
  public static void setMethodName(String methodName) {
    WLog.methodName = methodName;
  }
}

