package cn.imeth.android.lang.log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import cn.imeth.android.lang.thread.RunTask;
import cn.imeth.android.lang.thread.ThreadPool;
import cn.imeth.android.lang.utils.Times;

/**
 * 日志包装工具类
 *
 * @author wangjie
 * @version 创建时间：2013-3-19 上午8:47:42
 */
public class Log {
    /**
     * 是否在客户端记录用户操作
     */
    public static boolean logFile = false;

    private static String logFilePath;

    public static void initLogger(LogConfig logConfig) {
        if (null == logConfig) {
            return;
        }
        logFile = logConfig.isLogFile();
        logFilePath = logConfig.getLogFilePath();
    }


    public static void v(String tag, String msg) {
        android.util.Log.v(tag, msg);
        if (logFile) {
            writeLog(tag, msg, null, "VERBOSE");
        }
    }

    public static void v(String tag, String msg, Throwable tr) {
        android.util.Log.v(tag, msg, tr);
        if (logFile) {
            writeLog(tag, msg, tr, "VERBOSE");
        }
    }

    public static void d(String tag, String msg) {
        android.util.Log.d(tag, msg);
        if (logFile) {
            writeLog(tag, msg, null, "debug");
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        android.util.Log.d(tag, msg, tr);
        if (logFile) {
            writeLog(tag, msg, tr, "debug");
        }
    }

    public static void i(String tag, String msg) {
        android.util.Log.i(tag, msg);
        if (logFile) {
            writeLog(tag, msg, null, "INFO");
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        android.util.Log.i(tag, msg, tr);
        if (logFile) {
            writeLog(tag, msg, tr, "INFO");
        }
    }

    public static void w(String tag, String msg) {
        android.util.Log.w(tag, msg);
        if (logFile) {
            writeLog(tag, msg, null, "WARN");
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        android.util.Log.w(tag, msg, tr);
        if (logFile) {
            writeLog(tag, msg, tr, "WARN");
        }
    }

    public static void w(String tag, Throwable tr) {
        android.util.Log.w(tag, tr);
        if (logFile) {
            writeLog(tag, "", tr, "WARN");
        }
    }

    public static void e(String tag, String msg) {
        android.util.Log.e(tag, msg);
        if (logFile) {
            writeLog(tag, msg, null, "ERROR");
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        android.util.Log.e(tag, msg, tr);
        if (logFile) {
            writeLog(tag, msg, tr, "ERROR");
        }
    }

    public static void e(String tag, Throwable tr) {
        android.util.Log.e(tag, "", tr);
        if (logFile) {
            writeLog(tag, "", tr, "ERROR");
        }
    }

    /**
     * 记录日志线程
     *
     * @param tag
     * @param msg
     * @param tr
     * @param priority
     */
    private static void writeLog(String tag, String msg, Throwable tr, String priority) {
        ThreadPool.go(new RunTask<Object, Object>(tag, msg, tr, priority) {
            @Override
            public Void runInBackground() {
                synchronized (Log.class) {
                    String tag = (String) objs[0];
                    String msg = (String) objs[1];
                    Throwable tr = (Throwable) objs[2];
                    String priority = (String) objs[3];

                    if (!logFilePath.endsWith(File.separator)) {
                        logFilePath = logFilePath + File.separator;
                    }


                    String filename = String.format("%s%s.log", logFilePath, Times.millisToStringFilename(System.currentTimeMillis(), "yyyy-MM-dd"));
                    File logFile = new File(filename);

                    OutputStream os = null;
                    try {
                        if (!logFile.exists()) {
                            logFile.createNewFile();
                        }

                        os = new FileOutputStream(logFile, true);

                        String formatMsg = String.format("%s [%s][%s]: %s %s",
                                Times.millisToStringDate(System.currentTimeMillis()),
                                priority,
                                tag,
                                msg,
                                null == tr ? "\r\n" : String.format("Throwable Message:%s\r\nThrowable StackTrace: %s", tr.getMessage(), transformStackTrace(tr.getStackTrace())
                                ));

                        os.write(formatMsg.getBytes("utf-8"));
                        os.flush();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        if (null != os) {
                            try {
                                os.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }


                }
                return null;
            }
        });

    }

    public static StringBuilder transformStackTrace(StackTraceElement[] elements) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : elements) {
            sb.append(element.toString()).append("\r\n");
        }
        return sb;
    }


}
