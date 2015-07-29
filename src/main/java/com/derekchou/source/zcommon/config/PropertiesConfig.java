package com.derekchou.source.zcommon.config;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.ResourceBundle;

public class PropertiesConfig implements Config {
	
	private InternalConfig internalConfig;
	
	public PropertiesConfig(String configFile) {
		internalConfig = new InternalConfig();
		ResourceBundle res = null;
		try {
			res = ResourceBundle.getBundle(configFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res != null) {
			Enumeration<String> keys = res.getKeys();
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				internalConfig.put(key, res.getString(key));
			}
		}
		internalConfig.evalVars();
	}

	@Override
	public String get() {
		return null;
	}

	@Override
	public int getInt() throws ConfigException {
		throw new ConfigException();
	}

	@Override
	public long getLong() throws ConfigException {
		throw new ConfigException();
	}

	@Override
	public boolean getBool() throws ConfigException {
		throw new ConfigException();
	}

	@Override
	public float getFloat() throws ConfigException {
		throw new ConfigException();
	}

	@Override
	public double getDouble() throws ConfigException {
		throw new ConfigException();
	}

	@Override
	public Date getDate() throws ConfigException {
		throw new ConfigException();
	}

	@Override
	public InetSocketAddress getInetSocketAddress() throws ConfigException {
		throw new ConfigException();
	}

	@Override
	public String get(String optionName) {
		return internalConfig.get(optionName);
	}

	@Override
	public int getInt(String optionName) throws ConfigException {
		return internalConfig.getInt(optionName);
	}

	@Override
	public long getLong(String optionName) throws ConfigException {
		return internalConfig.getLong(optionName);
	}

	@Override
	public boolean getBool(String optionName) throws ConfigException {
		return internalConfig.getBool(optionName);
	}

	@Override
	public float getFloat(String optionName) throws ConfigException {
		return internalConfig.getFloat(optionName);
	}

	@Override
	public double getDouble(String optionName) throws ConfigException {
		return internalConfig.getDouble(optionName);
	}

	@Override
	public Date getDate(String optionName) throws ConfigException {
		return internalConfig.getDate(optionName);
	}

	@Override
	public InetSocketAddress getInetSocketAddress(String optionName) throws ConfigException {
		return internalConfig.getInetSocketAddress(optionName);
	}

	@Override
	public String get(String optionName, String defVal) {
		return internalConfig.get(optionName, defVal);
	}

	@Override
	public int getInt(String optionName, int defVal) {
		return internalConfig.getInt(optionName, defVal);
	}

	@Override
	public long getLong(String optionName, long defVal) {
		return internalConfig.getLong(optionName, defVal);
	}

	@Override
	public boolean getBool(String optionName, boolean defVal) {
		return internalConfig.getBool(optionName, defVal);
	}

	@Override
	public float getFloat(String optionName, float defVal) {
		return internalConfig.getFloat(optionName, defVal);
	}

	@Override
	public double getDouble(String optionName, double defVal) {
		return internalConfig.getDouble(optionName, defVal);
	}

	@Override
	public Date getDate(String optionName, Date defVal) {
		return internalConfig.getDate(optionName, defVal);
	}

	@Override
	public InetSocketAddress getInetSocketAddress(String optionName, String defVal) throws ConfigException {
		return internalConfig.getInetSocketAddress(optionName, defVal);
	}

	@Override
	public String[] gets(String optionName) {
		return internalConfig.gets(optionName);
	}

	@Override
	public int[] getInts(String optionName) throws ConfigException {
		return internalConfig.getInts(optionName);
	}

	@Override
	public long[] getLongs(String optionName) throws ConfigException {
		return internalConfig.getLongs(optionName);
	}

	@Override
	public boolean[] getBools(String optionName) throws ConfigException {
		return internalConfig.getBools(optionName);
	}

	@Override
	public float[] getFloats(String optionName) throws ConfigException {
		return internalConfig.getFloats(optionName);
	}

	@Override
	public double[] getDoubles(String optionName) throws ConfigException {
		return internalConfig.getDoubles(optionName);
	}

	@Override
	public Date[] getDates(String optionName) throws ConfigException {
		return internalConfig.getDates(optionName);
	}

	@Override
	public InetSocketAddress[] getInetSocketAddresses(String optionName) throws ConfigException {
		return internalConfig.getInetSocketAddresses(optionName);
	}

	@Override
	public String[] gets(String optionName, String separator) {
		return internalConfig.gets(optionName, separator);
	}

	@Override
	public int[] getInts(String optionName, String separator)
			throws ConfigException {
		return internalConfig.getInts(optionName, separator);
	}

	@Override
	public long[] getLongs(String optionName, String separator)
			throws ConfigException {
		return internalConfig.getLongs(optionName, separator);
	}

	@Override
	public boolean[] getBools(String optionName, String separator)
			throws ConfigException {
		return internalConfig.getBools(optionName, separator);
	}

	@Override
	public float[] getFloats(String optionName, String separator)
			throws ConfigException {
		return internalConfig.getFloats(optionName, separator);
	}

	@Override
	public double[] getDoubles(String optionName, String separator)
			throws ConfigException {
		return internalConfig.getDoubles(optionName, separator);
	}

	@Override
	public Date[] getDates(String optionName, String separator)
			throws ConfigException {
		return internalConfig.getDates(optionName, separator);
	}

	@Override
	public InetSocketAddress[] getInetSocketAddresses(String optionName, String separator) throws ConfigException {
		return internalConfig.getInetSocketAddresses(optionName, separator);
	}

	@Override
	public Config getBranch(String branchName) {
		return internalConfig.getBranch(branchName);
	}

	@Override
	public String[] getOptionNames() {
		return internalConfig.getOptionNames();
	}

	@Override
	public Map<String, String> toMap() {
		return internalConfig.toMap();
	}

	@Override
	public <T> Map<String, T> toMap(Class<T> valueClazz) {
		return internalConfig.toMap(valueClazz);
	}

}
