package org.unclesniper.choreo.build;

public interface FollowupTransformPropertyInjector {

	void injectFollowupTransformProperties(Component component, Language language, Artifact artifact,
			Flavor sourceFlavor, Flavor transformFlavor, Transform transform);

}
