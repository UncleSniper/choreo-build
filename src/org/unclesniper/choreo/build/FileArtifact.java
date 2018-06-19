package org.unclesniper.choreo.build;

import java.io.File;
import java.util.Set;
import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.IdentityHashMap;
import java.nio.file.FileSystems;
import org.unclesniper.choreo.Doom;
import org.unclesniper.choreo.FileUtils;
import org.unclesniper.choreo.UniqueList;

public class FileArtifact extends AbstractArtifact {

	private final File path;

	private final String label;

	private final Set<FileArtifact> intermediateDirectories
			= new UniqueList<FileArtifact>(new IdentityHashMap<FileArtifact, UniqueList.Node<FileArtifact>>());

	public FileArtifact(File path, String label) {
		this.path = path;
		this.label = label;
	}

	public File getPath() {
		return path;
	}

	private void notifyIntermediateDirectoriesCreated(Mood mood, BuildContext context) {
		for(FileArtifact directory : intermediateDirectories)
			mood.modified(directory, context);
	}

	public void addIntermediateDirectories(BuildContext context, File base) {
		if(base == null)
			base = new File("foo").getAbsoluteFile().getParentFile();
		else
			base = base.getParentFile();
		File current = path.getAbsoluteFile().getParentFile();
		while(current != null && !current.equals(base)) {
			FileArtifact idir = context.internFileArtifact(current, FileUtils.strip(current, base).getPath());
			intermediateDirectories.add(idir);
			current = current.getParentFile();
		}
	}

	public void wouldModify(BuildContext context) {
		super.wouldModify(context);
		notifyIntermediateDirectoriesCreated(PredictiveMood.instance, context);
	}

	public boolean isPresent() {
		return path.exists();
	}

	public void getModificationTimestamp(BuildSink<Long> sink) {
		sink.sinkBuildObject(path.lastModified());
	}

	public void notifyModified(BuildContext context) {
		super.notifyModified(context);
		notifyIntermediateDirectoriesCreated(DefinitiveMood.instance, context);
	}

	public void remove() throws IOException {
		FileUtils.deleteRecursively(path, true);
	}

	public InputStream getInputStream(BuildContext context) throws IOException {
		return new FileInputStream(path);
	}

	private void getFileReferenceForOutput(ReferenceMood mood, BuildContext context, ComponentUIInfo component)
			throws IOException {
		String componentBase = component == null ? null : component.getComponentBaseDirectory();
		File lbase = componentBase == null ? null : new File(componentBase);
		if(lbase == null || lbase.getPath().isEmpty())
			lbase = new File("foo").getAbsoluteFile().getParentFile();
		File dirPath = path.getAbsoluteFile().getParentFile();
		switch(mood) {
			case FOR_USE:
				if(!dirPath.exists()) {
					context.getUI().willPerformAction(new ActionDescriptor(component == null ? ""
							: component.getComponentType(), component == null ? ""
							: component.getComponentName(), "creating directory", null,
							FileUtils.strip(dirPath, lbase).getPath()), true);
					Files.createDirectories(FileSystems.getDefault().getPath(dirPath.getPath()));
					notifyIntermediateDirectoriesCreated(DefinitiveMood.instance, context);
				}
				break;
			case FOR_PREDICTION:
				{
					String plabel = FileUtils.strip(dirPath, lbase).getPath();
					if(!context.internFileArtifact(dirPath, plabel).wouldBePresent()) {
						context.getUI().wouldPerformAction(new ActionDescriptor(component == null ? ""
								: component.getComponentType(), component == null ? ""
								: component.getComponentName(), "would create directory", null,
								plabel), true);
						notifyIntermediateDirectoriesCreated(PredictiveMood.instance, context);
					}
				}
				break;
			default:
				throw new Doom("Unrecognized ReferenceMood: " + mood.name());
		}
	}

	public OutputStream getOutputStream(BuildContext context, ReferenceMood mood, ComponentUIInfo info)
			throws IOException {
		getFileReferenceForOutput(mood, context, info);
		return mood == ReferenceMood.FOR_USE ? new FileOutputStream(path) : null;
	}

	public void getFileReference(File suffix, BuildSink<File> sink, ReferenceDirection direction,
			ReferenceMood mood, BuildContext context, ComponentUIInfo info) throws IOException {
		if(suffix == null || suffix.getPath().isEmpty()) {
			if(direction == ReferenceDirection.FOR_OUTPUT)
				getFileReferenceForOutput(mood, context, info);
			sink.sinkBuildObject(path);
		}
		else {
			File prefix = FileUtils.endsWithStrip(path.getAbsoluteFile(), suffix);
			if(prefix != null) {
				if(direction == ReferenceDirection.FOR_OUTPUT)
					getFileReferenceForOutput(mood, context, info);
				sink.sinkBuildObject(prefix);
			}
			else {
				String componentBase = info == null ? null : info.getComponentBaseDirectory();
				File lbase = componentBase == null ? null : new File(componentBase);
				if(lbase == null || lbase.getPath().isEmpty())
					lbase = new File("foo").getAbsoluteFile().getParentFile();
				ArtifactStage stage = getEffectiveArtifactStage();
				switch(direction) {
					case FOR_INPUT:
						if(path.exists())
							stage.stage(this, suffix, true, context, lbase, info);
						break;
					case FOR_OUTPUT:
						stage.stage(this, suffix, false, context, lbase, info);
						notifyIntermediateDirectoriesCreated(DefinitiveMood.instance, context);
						break;
					default:
						throw new Doom("Unrecognized ReferenceDirection: " + direction.name());
				}
				sink.sinkBuildObject(stage.getDirectory());
			}
		}
	}

	public boolean isTransformable() {
		return true;
	}

	public String getName() {
		return path.getPath();
	}

	public String getLabel() {
		return label;
	}

}
