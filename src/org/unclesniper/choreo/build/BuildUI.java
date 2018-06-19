package org.unclesniper.choreo.build;

public interface BuildUI {

	void willPerformAction(ActionDescriptor action, boolean isMinor);

	void wouldPerformAction(ActionDescriptor action, boolean isMinor);

	void startPredictiveRun();

	void endPredictiveRun();

	void startDefinitiveRun();

	void endDefinitiveRun();

}
