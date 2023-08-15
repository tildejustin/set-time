package dev.tildejustin.settime;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SetTime {
    public static final long nightTime = 12542;
    public static boolean levelExists = false;
    public static Logger logger = LogManager.getLogger("set-time");

    public static void log(Level level, String message) {
        logger.log(level, message);
    }
}
