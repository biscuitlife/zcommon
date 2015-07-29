package com.derekchou.source.zcommon.config;

import java.util.HashMap;
import java.util.Map;

class Option {
	String name;
	Object value;
	Map<String, Option> children = new HashMap<String, Option>();

}
