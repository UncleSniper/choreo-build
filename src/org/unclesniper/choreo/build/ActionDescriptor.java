package org.unclesniper.choreo.build;

public final class ActionDescriptor {

	private String componentType;

	private String componentName;

	private String actionName;

	private String source;

	private String target;

	public ActionDescriptor() {}

	public ActionDescriptor(String componentType, String componentName,
			String actionName, String source, String target) {
		this.componentType = componentType;
		this.componentName = componentName;
		this.actionName = actionName;
		this.source = source;
		this.target = target;
	}

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

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

}
