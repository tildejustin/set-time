package xyz.tildejustin.settime;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SetTime implements ModInitializer {
    public static boolean levelExists;
    public static long nightTime;
    public static Logger logger;

    public static void log(Level level, String message) {
        logger.log(level, message);
    }

    @Override
    public void onInitialize() {
        levelExists = false;
        nightTime = 12542;
        logger = LogManager.getLogger("set-time");
        log(Level.INFO, "initializing set-time");
    }
}
