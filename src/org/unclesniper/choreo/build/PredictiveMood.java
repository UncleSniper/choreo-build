package org.unclesniper.choreo.build;

public final class PredictiveMood implements Mood {

	public static final Mood instance = new PredictiveMood();

	public PredictiveMood() {}

	public boolean present(Artifact artifact) {
		return artifact.wouldBePresent();
	}

	public void modificationTimestamp(Artifact artifact, BuildSink<Long> sink) {
		artifact.getPredictedModificationTimestamp(sink);
	}

	public void virtualClock(Artifact artifact, BuildSink<Long> sink) {
		artifact.getPredictedVirtualClock(sink);
	}

	public void modified(Artifact artifact, BuildContext context) {
		artifact.wouldModify(context);
	}

	public void remove(Artifact artifact) {
		artifact.wouldRemove();
	}

	public void require(Artifact artifact, BuildContext context) {
		artifact.wouldRequire(context);
	}

	public void perform(Transform transform, BuildContext context, Artifact artifact) {
		//TODO
	}

	public boolean isStaticallyUpToDate(Artifact artifact) {
		return artifact.hasArtifactFlags(Artifact.AFL_PREDICTIVE_STATICALLY_UP_TO_DATE);
	}

	public void markAsStaticallyUpToDate(Artifact artifact) {
		artifact.addArtifactFlags(Artifact.AFL_PREDICTIVE_STATICALLY_UP_TO_DATE);
	}

}
