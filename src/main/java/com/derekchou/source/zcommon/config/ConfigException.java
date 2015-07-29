package com.derekchou.source.zcommon.config;

public class ConfigException extends RuntimeException {

	private static final long serialVersionUID = -5726530678806985148L;

	public ConfigException() {
		super();
	}
	
	public ConfigException(String message) {
		super(message);
	}
	
	public ConfigException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ConfigException(Throwable cause) {
		super(cause);
	}
	
	public static ConfigException noOption(String optionName) {
		return new ConfigException("no such option:" + optionName);
	}
	
	public static ConfigException badOption(String optionName) {
		return new ConfigException("option:" + optionName);
	}
	
	public static ConfigException invalidType(String optionName, String strValue, String type) {
		return new ConfigException("can not parse " + strValue + " to " + type + " for option:" + optionName);
	}
	
	public static ConfigException circularReference(String optionName) {
		return new ConfigException("circular reference, option:" + optionName);
	}
	
	public static ConfigException varNotExists(String varName) {
		return new ConfigException("variable not exists, variable name:" + varName);
	}
	
	public static ConfigException fromMessage(String message) {
		return new ConfigException(message);
	}
}
