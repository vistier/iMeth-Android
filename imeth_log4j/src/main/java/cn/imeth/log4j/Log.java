package cn.imeth.log4j;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

public class Log {

    /**
     * @see #init(String, String, Level, boolean, boolean)
     */
    public static void initNotUseFile(Level level) {
        init("iMeth", "", level, false, true);
    }

    /**
     * @see #init(String, String, Level, boolean, boolean)
     */
    public static void initNotUseFile(String name, Level level) {
        init(name, "", level, false, true);
    }

    /**
     * @see #init(String, String, Level, boolean, boolean)
     */
    public static void init(String logFilePath) {
        init(logFilePath, Level.DEBUG);
    }

    /**
     * @see #init(String, String, Level, boolean, boolean)
     */
    public static void init(String logFilePath, Level rootLevel) {
        init("iMeht", logFilePath, rootLevel, true, true);
    }

    /**
     * 初始化日志文件控件
     *
     * @param loggerName          获取Logger的Name
     * @param logFilePath         日志文件保存位置
     * @param rootLevel           保存日志文件级别
     * @param isUseFileAppender   是否启动保存到外存文件中
     * @param isUseLogcatAppender 是否输出到logcat中
     */
    public static void init(String loggerName, String logFilePath, Level rootLevel, boolean isUseFileAppender, boolean isUseLogcatAppender) {
        LogConfigurator configurator = new LogConfigurator();
        Logger logger = Logger.getLogger(loggerName);

        configurator.setLogger(logger);
        configurator.setFileName(logFilePath);
        // Set the root log level
        configurator.setLevel(rootLevel);
        // Set log level of a specific logger
        configurator.setUseFileAppender(isUseFileAppender);
        configurator.setUseLogcatAppender(isUseLogcatAppender);
        configurator.configure();
    }


}
