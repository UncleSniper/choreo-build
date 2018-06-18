package org.unclesniper.choreo.build;

public interface BuildSink<ElementT> {

	void sinkBuildObject(ElementT element);

}
