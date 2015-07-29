package com.derekchou.source.zcommon.config;

import org.apache.log4j.Level;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;

public class Logger {
	org.apache.log4j.Logger logger;
	private static final String FQCN = org.apache.log4j.Logger.class.getName();

	public Logger(org.apache.log4j.Logger logger) {
		this.logger = logger;
	}
	
	public static Logger getLogger(Class clazz) {
		org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(clazz);
		Logger log = new Logger(logger);
		return log;
	}
	
	public static Logger getLogger(String name) {
		org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(name);
		Logger log = new Logger(logger);
		return log;
	}
	
	public static Logger getRootLogger() {
		org.apache.log4j.Logger logger = org.apache.log4j.Logger.getRootLogger();
		Logger log = new Logger(logger);
		return log;
	}

	private void log(Level level, Object... message) {
		if (message.length > 0 && logger.isEnabledFor(level)) {
			Throwable t = null;
			int last = message.length;
			if (message.length > 1 && (message[message.length - 1] != null && message[message.length - 1] instanceof Throwable)) {
				t = (Throwable)message[message.length - 1];
				last = message.length - 1;
			}
			StringBuilder msg = new StringBuilder();
			for (int i = 0; i < last; i++) {
				msg.append(message[i]);
			}
			logger.callAppenders(new LoggingEvent(FQCN, logger, level, msg.toString(), t));
		}
	}
	
	private void logf(Level level, String format, Object... args) {
		if (logger.isEnabledFor(level)) {
			String msg = String.format(format, args);
			logger.callAppenders(new LoggingEvent(FQCN, logger, level, msg, null));
		}
	}
	
	public void debug(Object... message) {
		log(Level.DEBUG, message);
	}
	
	public void debugf(String format, Object... args) {
		logf(Level.DEBUG, format, args);
	}
	
	public void info(Object... message) {
		log(Level.INFO, message);
	}
	
	public void infof(String format, Object... args) {
		logf(Level.INFO, format, args);
	}
	
	public void error(Object... message) {
		log(Level.ERROR, message);
	}
	
	public void errorf(String format, Object... args) {
		logf(Level.ERROR, format, args);
	}
	
	public void fatal(Object... message) {
		log(Level.FATAL, message);
	}
	
	public void fatalf(String format, Object... args) {
		logf(Level.FATAL, format, args);
	}
	
	public void trace(Object... message) {
		log(Level.TRACE, message);
	}
	
	public void tracef(String format, Object... args) {
		logf(Level.TRACE, format, args);
	}
	
	public void warn(Object... message) {
		log(Level.WARN, message);
	}
	
	public void warnf(String format, Object... args) {
		logf(Level.WARN, format, args);
	}
	
	public void logIf(boolean condition, Object... message) {
		if (condition) {
			log(Level.FATAL, message);
		}
	}

	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	public boolean isEnabledFor(Priority level) {
		return logger.isEnabledFor(level);
	}

	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}
	

}
