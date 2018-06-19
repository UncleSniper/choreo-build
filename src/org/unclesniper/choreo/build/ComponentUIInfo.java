package org.unclesniper.choreo.build;

public final class ComponentUIInfo {

	private String componentType;

	private String componentName;

	private String componentBase;

	public ComponentUIInfo() {}

	public String getComponentType() {
		return componentType;
	}

	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public String getComponentBaseDirectory() {
		return componentBase;
	}

	public void setComponentBaseDirectory(String componentBase) {
		this.componentBase = componentBase;
	}

}
