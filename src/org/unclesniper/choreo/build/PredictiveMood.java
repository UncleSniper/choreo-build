package org.unclesniper.choreo.build;

public final class PredictiveMood implements Mood {

	public static final Mood instance = new PredictiveMood();

	public PredictiveMood() {}

	public boolean present(Artifact artifact) {
		//TODO
		return false;
	}

	public void modificationTimestamp(Artifact artifact, BuildSink<Long> sink) {
		//TODO
	}

	public void virtualClock(Artifact artifact, BuildSink<Long> sink) {
		//TODO
	}

	public void modified(Artifact artifact, BuildContext context) {
		//TODO
	}

	public void remove(Artifact artifact) {
		//TODO
	}

	public void require(Artifact artifact, BuildContext context) {
		//TODO
	}

	public void perform(Transform transform, BuildContext context, Artifact artifact) {
		//TODO
	}

	public boolean isStaticallyUpToDate(Artifact artifact) {
		//TODO
		return false;
	}

	public void markAsStaticallyUpToDate(Artifact artifact) {
		//TODO
	}

}
