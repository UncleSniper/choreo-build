package org.unclesniper.choreo.build;

import java.io.IOException;

public interface Mood {

	boolean present(Artifact artifact);

	void modificationTimestamp(Artifact artifact, BuildSink<Long> sink);

	void virtualClock(Artifact artifact, BuildSink<Long> sink);

	void modified(Artifact artifact, BuildContext context);

	void remove(Artifact artifact) throws IOException;

	void require(Artifact artifact, BuildContext context);

	void perform(Transform transform, BuildContext context, Artifact artifact);

	boolean isStaticallyUpToDate(Artifact artifact);

	void markAsStaticallyUpToDate(Artifact artifact);

}
