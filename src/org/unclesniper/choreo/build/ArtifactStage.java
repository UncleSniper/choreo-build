package org.unclesniper.choreo.build;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.nio.file.FileSystems;
import org.unclesniper.choreo.FileUtils;
import org.unclesniper.choreo.StreamUtils;

public final class ArtifactStage {

	public static final ArtifactStage DEFAULT_STAGE
			= new ArtifactStage(new File(System.getProperty("java.io.tmpdir")));

	private File directory;

	public ArtifactStage(File directory) {
		this.directory = directory;
	}

	public File getDirectory() {
		return directory;
	}

	public void setDirectory(File directory) {
		this.directory = directory;
	}

	public File stage(Artifact artifact, File suffix, boolean withContents, BuildContext context,
			File labelBase, ComponentUIInfo componentInfo) throws IOException {
		File lbase = labelBase == null || labelBase.getPath().isEmpty()
				? new File("foo").getAbsoluteFile().getParentFile() : labelBase;
		File fullPath = new File(directory, suffix.getPath());
		File dirPath = fullPath.getAbsoluteFile().getParentFile();
		if(dirPath != null & !dirPath.exists()) {
			context.getUI().willPerformAction(new ActionDescriptor(componentInfo == null ? ""
					: componentInfo.getComponentType(), componentInfo == null ? ""
					: componentInfo.getComponentName(), "creating directory", null,
					FileUtils.strip(dirPath, lbase).getPath()), true);
			// Yeeeeees, java.nio.file is... a nice try. It's still
			// mostly garbage, but we'll use it for this one case,
			// since File.mkdirs() is just completely moronic.
			Files.createDirectories(FileSystems.getDefault().getPath(dirPath.getPath()));
		}
		if(withContents) {
			try (
				InputStream is = artifact.getInputStream(context);
				FileOutputStream os = new FileOutputStream(fullPath)
			) {
				StreamUtils.copy(is, os);
			}
		}
		return fullPath;
	}

}
