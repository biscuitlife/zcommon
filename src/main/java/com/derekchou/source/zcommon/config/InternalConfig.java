package com.derekchou.source.zcommon.config;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class InternalConfig implements Config {
	private Option root = new Option();
	
	InternalConfig() {
	}
	
	void put(String optionName, String value) {
		String[] optionLvls = optionName.split("\\.");
		Option currLvlObj = root;
		for (int i = 0; i < optionLvls.length; i++) {
			String lvl = optionLvls[i].trim();
			Option theLvlObj = currLvlObj.children.get(lvl);
			if (theLvlObj == null) {
				theLvlObj = new Option();
				StringBuilder nameBuf = new StringBuilder();
				for (int j = 0; j <= i; j++) {
					if (j > 0) {
						nameBuf.append(".");
					}
					nameBuf.append(optionLvls[j]);
				}
				theLvlObj.name = nameBuf.toString();
				currLvlObj.children.put(lvl, theLvlObj);
			}
			currLvlObj = theLvlObj;
		}
		currLvlObj.value = value;
	}
	
	void evalVars() {
		String[] names = getOptionNames();
		for (String name : names) {
			resolve(name, null);
		}
	}
	
	String resolve(String name, Set<String> varNames) {
		Set<String> names = new HashSet<String>();
		if (varNames != null) {
			names.addAll(varNames);
		}
		names.add(name);
		String value = get(name);
		if (value != null) {
			Pattern pattern = Pattern.compile("\\$\\(([^\\(\\)]+)\\)");		//match: $(var)
			Matcher matcher = pattern.matcher(value);
			String newValue = value;
			while (matcher.find()) {
				String varName = matcher.group(1);
				if (names.contains(varName)) {
					throw ConfigException.circularReference(varName);
				}
				String varValue = resolve(varName, names);
				if (varValue == null) {
					throw ConfigException.varNotExists(varName);
				}
				newValue = newValue.replaceAll("\\$\\(" + Pattern.quote(varName) + "\\)", varValue);
			}
			if (newValue != value) {
				put(name, newValue);
				value = newValue;
			}
		}
		return value;
	}

	@Override
	public String get() {
		return toString(root.value);
	}
	
	private String toString(Object o) {
		return o == null ? null : o.toString();
	}

	@Override
	public String get(String optionName) {
		Object val = getOptionValue(optionName);
		return toString(val);
	}
	
	private Object getOptionValue(String optionName) {
		Option opt = getOption(optionName);
		return opt.value;
	}
	
	private Option getOption(String optionName) {
		Option ret = new Option();
		String[] optionLvls = optionName.split("\\.");
		Option currLvlObj = root;
		for (int i = 0; i < optionLvls.length; i++) {
			String lvl = optionLvls[i].trim();
			currLvlObj = currLvlObj.children.get(lvl);
			if (currLvlObj == null) {
				ret.name = optionName;
				break;
			}
		}
		if (currLvlObj != null) {
			ret = currLvlObj;
		}
		return ret;
	}

	@Override
	public String get(String optionName, String defVal) {
		String val = get(optionName);
		if (val == null) {
			val = defVal;
		}
		return val;
	}

	@Override
	public boolean getBool() {
		return toBool(root.value);
	}
	
	private boolean toBool(Object val) {
		if (val == null) {
			return false;
		}
		if (val instanceof Boolean) {
			return (Boolean)val;
		}
		String strVal = toString(val).trim().toLowerCase();
		if ("true".equals(strVal) || "yes".equals(strVal) || "on".equals(strVal)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean getBool(String optionName) {
		Object val = getOptionValue(optionName);
		return toBool(val);
	}

	@Override
	public boolean getBool(String optionName, boolean defVal) {
		Object val = getOptionValue(optionName);
		if (val == null) {
			return defVal;
		}
		return toBool(val);
	}

	@Override
	public boolean[] getBools(String optionName) {
		return null;
	}

	@Override
	public Config getBranch(String branchName) {
		Option opt = getOption(branchName);
		if (opt.value == null && opt.children.size() == 0) {
			return null;
		}
		InternalConfig config = new InternalConfig();
		config.root = opt;
		return config;
	}

	@Override
	public double getDouble() throws ConfigException {
		return toDouble(root);
	}
	
	private double toDouble(Option option) throws ConfigException {
		String name = option.name;
		Object val = option.value;
		if (val == null) {
			throw ConfigException.noOption(name);
		}
		if (val instanceof Double) {
			return (Double) val;
		}
		String strVal = toString(val).trim();
		try {
			return Double.parseDouble(strVal);
		} catch (Exception e) {
			throw ConfigException.invalidType(name, strVal, "double");
		}
	}

	@Override
	public double getDouble(String optionName) throws ConfigException {
		Option option = getOption(optionName);
		return toDouble(option);
	}

	@Override
	public double getDouble(String optionName, double defVal) {
		try {
			return getDouble(optionName);
		} catch (ConfigException ce) {
			return defVal;
		}
	}

	@Override
	public double[] getDoubles(String optionName) throws ConfigException {
		return null;
	}

	@Override
	public float getFloat() throws ConfigException {
		return toFloat(root);
	}
	
	private float toFloat(Option option) throws ConfigException {
		String name = option.name;
		Object val = option.value;
		if (val == null) {
			throw ConfigException.noOption(name);
		}
		if (val instanceof Float) {
			return (Float) val;
		}
		String strVal = toString(val).trim();
		try {
			return Float.parseFloat(strVal);
		} catch (Exception e) {
			throw ConfigException.invalidType(name, strVal, "float");
		}
	}

	@Override
	public float getFloat(String optionName) throws ConfigException {
		Option option = getOption(optionName);
		return toFloat(option);
	}

	@Override
	public float getFloat(String optionName, float defVal) {
		try {
			return getFloat(optionName);
		} catch (ConfigException ce) {
			return defVal;
		}
	}

	@Override
	public float[] getFloats(String optionName) throws ConfigException {
		return null;
	}

	@Override
	public int getInt() {
		return toInt(root);
	}
	
	private int toInt(Option option) throws ConfigException {
		String name = option.name;
		Object val = option.value;
		if (val == null) {
			throw ConfigException.noOption(name);
		}
		if (val instanceof Integer) {
			return (Integer) val;
		}
		String strVal = toString(val).trim();
		try {
			return Integer.parseInt(strVal);
		} catch (Exception e) {
			throw ConfigException.invalidType(name, strVal, "int");
		}
	}

	@Override
	public int getInt(String optionName) throws ConfigException {
		Option option = getOption(optionName);
		return toInt(option);
	}

	@Override
	public int getInt(String optionName, int defVal) {
		try {
			return getInt(optionName);
		} catch (ConfigException ce) {
			return defVal;
		}
	}

	@Override
	public int[] getInts(String optionName) throws ConfigException {
		return getInts(optionName, DEFAULT_SEPARATOR);
	}

	@Override
	public long getLong() throws ConfigException {
		return toLong(root);
	}
	
	private long toLong(Option option) throws ConfigException {
		String name = option.name;
		Object val = option.value;
		if (val == null) {
			throw ConfigException.noOption(name);
		}
		if (val instanceof Long) {
			return (Long) val;
		}
		String strVal = toString(val).trim();
		try {
			return Long.parseLong(strVal);
		} catch (Exception e) {
			throw ConfigException.invalidType(name, strVal, "long");
		}
	}

	@Override
	public long getLong(String optionName) throws ConfigException {
		Option option = getOption(optionName);
		return toLong(option);
	}

	@Override
	public long getLong(String optionName, long defVal) {
		try {
			return getLong(optionName);
		} catch (ConfigException ce) {
			return defVal;
		}
	}

	@Override
	public long[] getLongs(String optionName) throws ConfigException {
		return null;
	}

	@Override
	public String[] gets(String optionName) {
		return gets(optionName, DEFAULT_SEPARATOR);
	}

	@Override
	public boolean[] getBools(String optionName, String separator)
			throws ConfigException {
		return null;
	}

	@Override
	public double[] getDoubles(String optionName, String separator)
			throws ConfigException {
		return null;
	}

	@Override
	public float[] getFloats(String optionName, String separator)
			throws ConfigException {
		return null;
	}

	@Override
	public int[] getInts(String optionName, String separator)
			throws ConfigException {
		String[] strs = gets(optionName, separator);
		if (strs == null) {
			throw ConfigException.noOption(optionName);
		}
		try {
			int[] ret = new int[strs.length];
			for (int i = 0; i < strs.length; i++) {
				ret[i] = Integer.parseInt(strs[i]);
			}
			return ret;
		} catch (Exception e) {
			throw ConfigException.badOption(optionName);
		}
	}

	@Override
	public long[] getLongs(String optionName, String separator)
			throws ConfigException {
		return null;
	}

	@Override
	public String[] gets(String optionName, String separator) {
		Object val = getOptionValue(optionName);
		if (val == null) {
			return null;
		}
		String strVal = val.toString().trim();
		if (strVal.length() == 0) {
			return new String[0];
		}
		String[] parts = strVal.split(separator);
		return parts;
	}

	@Override
	public Date getDate() throws ConfigException {
		return toDate(root);
	}
	
	private Date toDate(Option option) throws ConfigException {
		String name = option.name;
		Object val = option.value;
		if (val == null) {
			throw ConfigException.noOption(name);
		}
		if (val instanceof Date) {
			return (Date) val;
		}
		String strVal = toString(val).trim();
		if (!strVal.matches("[\\d:\\-]+")) {
			throw ConfigException.badOption(name);
		}
		String pattern = null;
		if (strVal.length() == 4) {
			pattern = "yyyy";
		} else if (strVal.length() == 7) {
			pattern = "yyyy-MM";
		} else if (strVal.length() == 10) {
			pattern = "yyyy-MM-dd";
		} else if (strVal.length() == 13) {
			pattern = "yyyy-MM-dd HH";
		} else if (strVal.length() == 17) {
			pattern = "yyyy-MM-dd HH:mm";
		} else if (strVal.length() == 20) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		
		try {
			return new SimpleDateFormat(pattern).parse(strVal);
		} catch (Exception e) {
			throw ConfigException.invalidType(name, strVal, "long");
		}
	}

	@Override
	public Date getDate(String optionName) throws ConfigException {
		Option option = getOption(optionName);
		return toDate(option);
	}

	@Override
	public Date getDate(String optionName, Date defVal) {
		try {
			return getDate(optionName);
		} catch (ConfigException ce) {
			return defVal;
		}
	}

	@Override
	public Date[] getDates(String optionName) throws ConfigException {
		return null;
	}

	@Override
	public Date[] getDates(String optionName, String separator)
			throws ConfigException {
		return null;
	}

	@Override
	public InetSocketAddress getInetSocketAddress() throws ConfigException {
		return toInetSocketAddress(root);
	}
	
	private InetSocketAddress toInetSocketAddress(Option option) throws ConfigException {
		String name = option.name;
		Object val = option.value;
		if (val == null) {
			throw ConfigException.noOption(name);
		}
		String strVal = toString(val).trim();
		InetSocketAddress addr = toInetSocketAddress(strVal);
		if (addr == null) {
			throw ConfigException.badOption(name);
		}
		return addr;
	}
	
	private static InetSocketAddress toInetSocketAddress(String str) {
		String strVal = str.trim();
		if (!strVal.matches("(\\d{1,3}\\.){3}\\d{1,3}:\\d{1,5}")) {
			return null;
		}
		String[] parts = strVal.split(":");
		String ip = parts[0];
		int port = Integer.parseInt(parts[1]);
		return new InetSocketAddress(ip, port);
	}

	@Override
	public InetSocketAddress getInetSocketAddress(String optionName) throws ConfigException {
		Option option = getOption(optionName);
		return toInetSocketAddress(option);
	}

	@Override
	public InetSocketAddress getInetSocketAddress(String optionName, String defVal) {
		try {
			return getInetSocketAddress(optionName);
		} catch (ConfigException ce) {
			return toInetSocketAddress(defVal);
		}
	}

	@Override
	public InetSocketAddress[] getInetSocketAddresses(String optionName)
			throws ConfigException {
		return getInetSocketAddresses(optionName, DEFAULT_SEPARATOR);
	}

	@Override
	public InetSocketAddress[] getInetSocketAddresses(String optionName, String separator)
			throws ConfigException {
		String[] strs = gets(optionName, separator);
		if (strs == null) {
			throw ConfigException.noOption(optionName);
		}
		try {
			InetSocketAddress[] ret = new InetSocketAddress[strs.length];
			for (int i = 0; i < strs.length; i++) {
				ret[i] = toInetSocketAddress(strs[i]);
			}
			return ret;
		} catch (Exception e) {
			throw ConfigException.badOption(optionName);
		}
	}

	@Override
	public String[] getOptionNames() {
		List<String> names = getOptNames(null, root);
		return names.toArray(new String[names.size()]);
	}
	
	private List<String> getOptNames(String optPrefix, Option opt) {
		List<String> names = new ArrayList<String>();
		if (opt.children == null || opt.children.size() == 0) {
			if (optPrefix != null && optPrefix.trim().length() > 0) {
				names.add(optPrefix.trim());
			}
		} else {
			for (String name : opt.children.keySet()) {
				String prefix = optPrefix != null ? optPrefix + "." + name : name;
				List<String> nms = getOptNames(prefix, opt.children.get(name));
				names.addAll(nms);
			}
		}
		return names;
	}

	@Override
	public Map<String, String> toMap() {
		return toMap(String.class);
	}

	@Override
	public <T> Map<String, T> toMap(Class<T> valueClazz) {
		Map<String, T> map = new HashMap<String, T>();
		String[] names = getOptionNames();
		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			T value = getType(name, valueClazz);
			if (value != null) {
				map.put(name, value);
			}
		}
		return map;
	}
	
	private <T> T getType(String name, Class<T> valueClazz) {
		Object obj = null;
		if (valueClazz == Integer.class) {
			obj = getInt(name);
		} else if (valueClazz == Long.class) {
			obj = getLong(name);
		} else if (valueClazz == Boolean.class) {
			obj = getBool(name);
		} else if (valueClazz == Float.class) {
			obj = getFloat(name);
		} else if (valueClazz == Double.class) {
			obj = getDouble(name);
		} else if (valueClazz == Date.class) {
			obj = getDate(name);
		} else if (valueClazz == InetSocketAddress.class) {
			obj = getInetSocketAddress(name);
		} else if (valueClazz == String.class) {
			obj = get(name);
		}
		return (T)obj;
	}

}
