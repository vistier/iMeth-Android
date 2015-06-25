package cn.imeth.log4j;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Log {

    public static void initNotUseFile(Level level) {
        init("",level,false);
    }

    public static void init(String logFilePath) {
        init(logFilePath, Level.DEBUG, true);
    }

    public static void init(String logFilePath, Level level) {
        init(logFilePath, level, true);
    }

    public static void init(String logFilePath, Level rootLevel, boolean isUseFileAppender) {
        final LogConfigurator logConfigurator = new LogConfigurator();

        logConfigurator.setFileName(logFilePath);
        // Set the root log level
        logConfigurator.setLevel(rootLevel);
        // Set log level of a specific logger
        logConfigurator.setUseFileAppender(isUseFileAppender);
        logConfigurator.configure();
    }

    public static void init(String loggerName, String logFilePath, Level rootLevel, boolean isUseFileAppender) {
        LogConfigurator logConfigurator = new LogConfigurator();
        Logger logger = Logger.getLogger(loggerName);

        logConfigurator.setLogger(logger);
        logConfigurator.setFileName(logFilePath);
        // Set the root log level
        logConfigurator.setLevel(rootLevel);
        // Set log level of a specific logger
        logConfigurator.setUseFileAppender(isUseFileAppender);
        logConfigurator.configure();
    }


}
