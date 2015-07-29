package com.derekchou.source.zcommon.config;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Map;

public interface Config {
	
	static String DEFAULT_SEPARATOR = "[ ,]+";
	
	public String get();
	public int getInt() throws ConfigException;
	public long getLong() throws ConfigException;
	public boolean getBool() throws ConfigException;
	public float getFloat() throws ConfigException;
	public double getDouble() throws ConfigException;
	public Date getDate() throws ConfigException;
	public InetSocketAddress getInetSocketAddress() throws ConfigException;
	
	public String get(String optionName);
	public int getInt(String optionName) throws ConfigException;
	public long getLong(String optionName) throws ConfigException;
	public boolean getBool(String optionName) throws ConfigException;
	public float getFloat(String optionName) throws ConfigException;
	public double getDouble(String optionName) throws ConfigException;
	public Date getDate(String optionName) throws ConfigException;
	public InetSocketAddress getInetSocketAddress(String optionName) throws ConfigException;
	
	public String get(String optionName, String defVal);
	public int getInt(String optionName, int defVal);
	public long getLong(String optionName, long defVal);
	public boolean getBool(String optionName, boolean defVal);
	public float getFloat(String optionName, float defVal);
	public double getDouble(String optionName, double defVal);
	public Date getDate(String optionName, Date defVal);
	public InetSocketAddress getInetSocketAddress(String optionName, String defVal);
	
	public String[] gets(String optionName);
	public int[] getInts(String optionName) throws ConfigException;
	public long[] getLongs(String optionName) throws ConfigException;
	public boolean[] getBools(String optionName) throws ConfigException;
	public float[] getFloats(String optionName) throws ConfigException;
	public double[] getDoubles(String optionName) throws ConfigException;
	public Date[] getDates(String optionName) throws ConfigException;
	public InetSocketAddress[] getInetSocketAddresses(String optionName) throws ConfigException;
	
	public String[] gets(String optionName, String separator);
	public int[] getInts(String optionName, String separator) throws ConfigException;
	public long[] getLongs(String optionName, String separator) throws ConfigException;
	public boolean[] getBools(String optionName, String separator) throws ConfigException;
	public float[] getFloats(String optionName, String separator) throws ConfigException;
	public double[] getDoubles(String optionName, String separator) throws ConfigException;
	public Date[] getDates(String optionName, String separator) throws ConfigException;
	public InetSocketAddress[] getInetSocketAddresses(String optionName, String separator) throws ConfigException;
	
	public Config getBranch(String branchName);
 
	public String[] getOptionNames();
	
	public Map<String, String> toMap();
	public <T> Map<String, T> toMap(Class<T> valueClazz);
}