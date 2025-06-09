package com.ifsp.tavinho.smt_backend.shared.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LoggerUtil {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    private LoggerUtil() {
        super();
    }

    synchronized public static void info(final String message) {
        LOGGER.info(message);
    }

    synchronized public static void error(final String message) {
        LOGGER.error(message);
    }

}
