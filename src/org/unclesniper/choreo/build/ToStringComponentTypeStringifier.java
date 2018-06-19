package org.unclesniper.choreo.build;

public class ToStringComponentTypeStringifier implements ComponentTypeStringifier {

	public static final ComponentTypeStringifier instance = new ToStringComponentTypeStringifier();

	public ToStringComponentTypeStringifier() {}

	public String stringifyComponentType(Component.Type componentType) {
		return (componentType == null ? Component.Type.LIBRARY : componentType).toString();
	}

}
