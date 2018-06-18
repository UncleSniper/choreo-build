package org.unclesniper.choreo.build;

import org.unclesniper.choreo.TypeMap;

public abstract class Artifact {

	private final TypeMap properties = new TypeMap();

	public Artifact() {}

	public <T> void setProperty(Class<T> key, T value) {
		properties.put(key, value);
	}

	public <T> T getProperty(Class<T> key) {
		return properties.get(key);
	}

	//TODO

}
