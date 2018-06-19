package org.unclesniper.choreo.build;

import java.io.File;
import java.util.Map;
import java.util.HashMap;

public final class BuildContext {

	private ArtifactStage defaultStage;

	private final Map<File, FileArtifact> fileArtifacts = new HashMap<File, FileArtifact>();

	private BuildUI ui;

	public BuildContext(BuildUI ui) {
		this.ui = ui;
	}

	public ArtifactStage getDefaultStage() {
		return defaultStage;
	}

	public void setDefaultStage(ArtifactStage defaultStage) {
		this.defaultStage = defaultStage;
	}

	public ArtifactStage getEffectiveArtifactStage() {
		return defaultStage == null ? ArtifactStage.DEFAULT_STAGE : defaultStage;
	}

	public BuildUI getUI() {
		return ui;
	}

	public void setUI(BuildUI ui) {
		this.ui = ui;
	}

	public FileArtifact internFileArtifact(File path, String label) {
		FileArtifact artifact = fileArtifacts.get(path);
		if(artifact == null) {
			artifact = new FileArtifact(path, label);
			fileArtifacts.put(path, artifact);
		}
		return artifact;
	}

}
