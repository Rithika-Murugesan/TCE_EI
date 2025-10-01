
package com.smarthome.util;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class LoggerUtil {
    static {
        Logger root = Logger.getLogger(""); 
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.ALL);
        ch.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                return String.format("%1$tF %1$tT [%2$-7s] %3$s %n", 
                    record.getMillis(), record.getLevel().getName(), record.getMessage());
            }
        });
        root.addHandler(ch);
    }

    public static Logger getLogger(String name) {
        Logger logger = Logger.getLogger(name);
        logger.setLevel(Level.INFO);
        return logger;
    }
}
