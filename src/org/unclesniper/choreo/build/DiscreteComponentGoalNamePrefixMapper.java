package org.unclesniper.choreo.build;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public class DiscreteComponentGoalNamePrefixMapper implements Component.GoalNamePrefixMapper {

	public static final String DEFAULT_PREFIX = "cmp:";

	public static final Component.GoalNamePrefixMapper DEFAULT_MAPPER;

	static {
		DiscreteComponentGoalNamePrefixMapper mapper = new DiscreteComponentGoalNamePrefixMapper();
		mapper.setDefaultPrefix("lib");
		mapper.mapType(Component.Type.EXECUTABLE, "exe:");
		DEFAULT_MAPPER = mapper;
	}

	private final Map<Component.Type, String> prefixes = new HashMap<Component.Type, String>();

	private String defaultPrefix;

	public DiscreteComponentGoalNamePrefixMapper() {}

	public Set<Component.Type> getMappedTypes() {
		return prefixes.keySet();
	}

	public void mapType(Component.Type type, String prefix) {
		if(prefix == null)
			prefixes.remove(type);
		else
			prefixes.put(type, prefix);
	}

	public void clear() {
		prefixes.clear();
	}

	public String getPrefix(Component.Type type) {
		return prefixes.get(type);
	}

	public String getDefaultPrefix() {
		return defaultPrefix;
	}

	public void setDefaultPrefix(String defaultPrefix) {
		this.defaultPrefix = defaultPrefix;
	}

	public String getGoalNamePrefix(Component.Type componentType) {
		String prefix = prefixes.get(componentType);
		if(prefix != null)
			return prefix;
		if(defaultPrefix != null)
			return defaultPrefix;
		return DiscreteComponentGoalNamePrefixMapper.DEFAULT_PREFIX;
	}

}
