package org.unclesniper.choreo.build;

import java.io.File;
import java.util.Set;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.unclesniper.choreo.TypeMap;
import org.unclesniper.choreo.UniqueList;

public abstract class Artifact {

	public static final int AFL_DEFINITIVE_STATICALLY_UP_TO_DATE = 001;

	public static final int AFL_PREDICTIVE_STATICALLY_UP_TO_DATE = 002;

	private final TypeMap properties = new TypeMap();

	private Transform generatingTransform;

	private Set<FollowupTransformPropertyInjector> followupTransformPropertyInjectors
			= new UniqueList<FollowupTransformPropertyInjector>();

	private int artifactFlags;

	public Artifact() {}

	public <T> void setProperty(Class<T> key, T value) {
		properties.put(key, value);
	}

	public <T> T getProperty(Class<T> key) {
		return properties.get(key);
	}

	public Transform getGeneratingTransform() {
		return generatingTransform;
	}

	public int getArtifactFlags() {
		return artifactFlags;
	}

	public void setArtifactFlags(int artifactFlags) {
		this.artifactFlags = artifactFlags;
	}

	public boolean hasArtifactFlags(int mask) {
		return (artifactFlags & mask) == mask;
	}

	public void addArtifactFlags(int mask) {
		artifactFlags |= mask;
	}

	public void removeArtifactFlags(int mask) {
		artifactFlags &= ~mask;
	}

	public void setGeneratingTransform(Transform generatingTransform) {
		this.generatingTransform = generatingTransform;
	}

	public void addFollowupTransformPropertyInjector(FollowupTransformPropertyInjector injector) {
		if(injector != null)
			followupTransformPropertyInjectors.add(injector);
	}

	public void removeFollowupTransformPropertyInjector(FollowupTransformPropertyInjector injector) {
		followupTransformPropertyInjectors.remove(injector);
	}

	public void clearFollowupTransformPropertyInjectors() {
		followupTransformPropertyInjectors.clear();
	}

	public Iterable<FollowupTransformPropertyInjector> getFollowupTransformPropertyInjectors() {
		return followupTransformPropertyInjectors;
	}

	public void injectFollowupTransformProperties(Component component, Language language,
			Flavor sourceFlavor, Flavor transformFlavor, Transform transform) {
		for(FollowupTransformPropertyInjector injector : followupTransformPropertyInjectors)
			injector.injectFollowupTransformProperties(component, language, this,
					sourceFlavor, transformFlavor, transform);
	}

	public void require(BuildContext context) {
		require(DefinitiveMood.instance, context);
	}

	public void wouldRequire(BuildContext context) {
		require(PredictiveMood.instance, context);
	}

	private void require(Mood mood, BuildContext context) {
		//TODO
	}

	private void rebuild(Mood mood, BuildContext context) {
		//TODO
	}

	public abstract boolean wouldBePresent();

	public abstract void getPredictedModificationTimestamp(BuildSink<Long> sink);

	public abstract void getPredictedVirtualClock(BuildSink<Long> sink);

	public abstract void wouldModify(BuildContext context);

	public abstract void wouldRemove();

	public abstract boolean isPresent();

	public abstract void getModificationTimestamp(BuildSink<Long> sink);

	public abstract void getVirtualClock(BuildSink<Long> sink);

	public abstract void notifyModified(BuildContext context);

	public abstract void remove() throws IOException;

	public abstract InputStream getInputStream(BuildContext context) throws IOException;

	public abstract OutputStream getOutputStream(BuildContext context, ReferenceMood mood, ComponentUIInfo info)
			throws IOException;

	public abstract void getFileReference(File suffix, BuildSink<File> sink, ReferenceDirection direction,
			ReferenceMood mood, BuildContext context, ComponentUIInfo info) throws IOException;

	public abstract boolean isTransformable();

	public abstract String getName();

	public abstract String getLabel();

}
