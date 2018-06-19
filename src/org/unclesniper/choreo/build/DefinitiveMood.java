package org.unclesniper.choreo.build;

import java.io.IOException;

public final class DefinitiveMood implements Mood {

	public static final Mood instance = new DefinitiveMood();

	public DefinitiveMood() {}

	public boolean present(Artifact artifact) {
		return artifact.isPresent();
	}

	public void modificationTimestamp(Artifact artifact, BuildSink<Long> sink) {
		artifact.getModificationTimestamp(sink);
	}

	public void virtualClock(Artifact artifact, BuildSink<Long> sink) {
		artifact.getVirtualClock(sink);
	}

	public void modified(Artifact artifact, BuildContext context) {
		artifact.notifyModified(context);
	}

	public void remove(Artifact artifact) throws IOException {
		artifact.remove();
	}

	public void require(Artifact artifact, BuildContext context) {
		artifact.require(context);
	}

	public void perform(Transform transform, BuildContext context, Artifact artifact) {
		//TODO
	}

	public boolean isStaticallyUpToDate(Artifact artifact) {
		return artifact.hasArtifactFlags(Artifact.AFL_DEFINITIVE_STATICALLY_UP_TO_DATE);
	}

	public void markAsStaticallyUpToDate(Artifact artifact) {
		artifact.addArtifactFlags(Artifact.AFL_DEFINITIVE_STATICALLY_UP_TO_DATE);
	}

}
