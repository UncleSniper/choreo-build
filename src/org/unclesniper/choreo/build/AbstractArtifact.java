package org.unclesniper.choreo.build;

public abstract class AbstractArtifact extends Artifact {

	private boolean predictedPresent;

	private long predictedTimestamp;

	private long predictedVirtualClock;

	private long virtualClock;

	private ArtifactStage stage;

	public AbstractArtifact() {}

	protected void setPredictedModificationTimestamp(long timestamp) {
		predictedPresent = true;
		predictedTimestamp = timestamp;
	}

	public ArtifactStage getStage() {
		return stage;
	}

	public void setStage(ArtifactStage stage) {
		this.stage = stage;
	}

	public ArtifactStage getEffectiveArtifactStage() {
		return stage == null ? ArtifactStage.DEFAULT_STAGE : stage;
	}

	public ArtifactStage getEffectiveArtifactStage(BuildContext context) {
		if(stage != null)
			return stage;
		if(context == null)
			return ArtifactStage.DEFAULT_STAGE;
		return context.getEffectiveArtifactStage();
	}

	public boolean wouldBePresent() {
		return predictedPresent;
	}

	public void getPredictedModificationTimestamp(BuildSink<Long> sink) {
		sink.sinkBuildObject(predictedTimestamp);
	}

	public void getPredictedVirtualClock(BuildSink<Long> sink) {
		sink.sinkBuildObject(predictedVirtualClock);
	}

	public void wouldModify(BuildContext context) {
		predictedTimestamp = System.currentTimeMillis();
		//predictedVirtualClock = context.tickVirtualTime();//TODO
		predictedPresent = true;
	}

	public void wouldRemove() {
		predictedPresent = false;
	}

	public void getVirtualClock(BuildSink<Long> sink) {
		sink.sinkBuildObject(virtualClock);
	}

	public void notifyModified(BuildContext context) {
		//virtualClock = context.tickVirtualTime();//TODO
	}

}
