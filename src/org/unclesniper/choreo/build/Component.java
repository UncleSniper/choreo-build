package org.unclesniper.choreo.build;

import java.io.File;
import java.util.Set;
import java.util.Map;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import org.unclesniper.choreo.UniqueList;
import org.unclesniper.choreo.EmptyIterable;

public class Component {

	public static final class Type extends AbstractSymbol {

		public static final Type LIBRARY = Type.intern("library");

		public static final Type EXECUTABLE = Type.intern("executable");

		public static final Type DATA = Type.intern("data");

		private static final Map<String, Type> TYPES = new HashMap<String, Type>();

		private Type(String name, int symbol) {
			super(name, symbol);
		}

		public boolean equals(Object other) {
			return other instanceof Type && symbol == ((Type)other).symbol;
		}

		public boolean equalsType(Type other) {
			return other != null && symbol == other.symbol;
		}

		public static Type intern(String name) {
			name.length();
			synchronized(Type.TYPES) {
				Type type = Type.TYPES.get(name);
				if(type == null) {
					type = new Type(name, Type.TYPES.size());
					Type.TYPES.put(name, type);
				}
				return type;
			}
		}

		public static Iterable<Type> knownTypes() {
			return Type.TYPES.values();
		}

	}

	public interface GoalNamePrefixMapper {

		String getGoalNamePrefix(Type componentType);

	}

	private final Type type;

	private String name;

	private final File baseDirectory;

	private final Set<File> sourceDirectories = new UniqueList<File>();

	private final Set<Language> languages = new HashSet<Language>();

	private final Set<Component> dependencies = new HashSet<Component>();

	private final Map<Language, Map<File, FileArtifact>> privateHeaders
			= new HashMap<Language, Map<File, FileArtifact>>();

	private final Map<Language, Map<File, FileArtifact>> exposedHeaders
			= new HashMap<Language, Map<File, FileArtifact>>();

	private final UniqueList<FlavoredArtifact> finalArtifacts = new UniqueList<FlavoredArtifact>();

	private final Map<Artifact, FileArtifact> unexposedHeaders = new HashMap<Artifact, FileArtifact>();

	private final Map<Artifact, FileArtifact> reverseUnexposedHeaders = new HashMap<Artifact, FileArtifact>();

	private final Map<Language, Set<File>> exposeDirectories = new HashMap<Language, Set<File>>();

	private GoalNamePrefixMapper goalNamePrefixMapper;

	public Component(Type type, String name, File baseDirectory) {
		this.type = type;
		this.name = name;
		this.baseDirectory = baseDirectory;
	}

	public Type getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	public File getBaseDirectory() {
		return baseDirectory;
	}

	public GoalNamePrefixMapper getGoalNamePrefixMapper() {
		return goalNamePrefixMapper;
	}

	public void setGoalNamePrefixMapper(GoalNamePrefixMapper goalNamePrefixMapper) {
		this.goalNamePrefixMapper = goalNamePrefixMapper;
	}

	public String getGoalName() {
		return (goalNamePrefixMapper == null ? DiscreteComponentGoalNamePrefixMapper.DEFAULT_MAPPER
				: goalNamePrefixMapper).getGoalNamePrefix(type == null ? Type.LIBRARY : type) + name;
	}

	public void addSourceDirectory(File directory) {
		if(directory != null)
			sourceDirectories.add(directory);
	}

	public void removeSourceDirectory(File directory) {
		sourceDirectories.remove(directory);
	}

	public void clearSourceDirectories() {
		sourceDirectories.clear();
	}

	public Iterable<File> getSourceDirectories() {
		return sourceDirectories;
	}

	public FileArtifact getSourceFile(String basename, BuildContext context) {
		//TODO
		return null;
	}

	public void addLanguage(Language language) {
		if(language != null)
			languages.add(language);
	}

	public void removeLanguage(Language language) {
		languages.remove(language);
	}

	public void clearLanguages() {
		languages.clear();
	}

	public Iterable<Language> getLanguages() {
		return languages;
	}

	public void addDependency(Component component) {
		if(component != null)
			dependencies.add(component);
	}

	public void removeDependency(Component component) {
		dependencies.remove(component);
	}

	public void clearDependencies() {
		dependencies.clear();
	}

	public Iterable<Component> getDependencies() {
		return dependencies;
	}

	public Deque<Component> getTransitiveDependencies() {
		Deque<Component> deplist = new LinkedList<Component>();
		Set<Component> depset = new HashSet<Component>();
		Component.getTransitiveDependencies(this, deplist, depset);
		return deplist;
	}

	private static void getTransitiveDependencies(Component component,
			Deque<Component> deplist, Set<Component> depset) {
		for(Component dependency : component.getDependencies()) {
			if(dependency != null)
				Component.getTransitiveDependencies(dependency, deplist, depset);
		}
		for(Component dependency : component.getDependencies()) {
			if(dependency != null && !depset.contains(dependency)) {
				deplist.addFirst(dependency);
				depset.add(dependency);
			}
		}
	}

	public void addPrivateHeader(Language language, File path, FileArtifact file) {
		path.getName();
		Map<File, FileArtifact> h1 = privateHeaders.get(language);
		if(h1 == null) {
			h1 = new HashMap<File, FileArtifact>();
			privateHeaders.put(language, h1);
		}
		if(file == null)
			h1.remove(path);
		else
			h1.put(path, file);
	}

	public void removePrivateHeader(Language language, File path) {
		Map<File, FileArtifact> h1 = privateHeaders.get(language);
		if(h1 != null)
			h1.remove(path);
	}

	public void clearPrivateHeaders(Language language) {
		Map<File, FileArtifact> h1 = privateHeaders.get(language);
		if(h1 != null)
			h1.clear();
	}

	public void clearPrivateHeaders() {
		privateHeaders.clear();
	}

	public FileArtifact getPrivateHeader(Language language, File path) {
		Map<File, FileArtifact> h1 = privateHeaders.get(language);
		return h1 == null ? null : h1.get(path);
	}

	public void addExposedHeader(Language language, File path, FileArtifact file) {
		path.getName();
		Map<File, FileArtifact> h1 = exposedHeaders.get(language);
		if(h1 == null) {
			h1 = new HashMap<File, FileArtifact>();
			exposedHeaders.put(language, h1);
		}
		if(file == null)
			h1.remove(path);
		else
			h1.put(path, file);
	}

	public void removeExposedHeader(Language language, File path) {
		Map<File, FileArtifact> h1 = exposedHeaders.get(language);
		if(h1 != null)
			h1.remove(path);
	}

	public void clearExposedHeaders(Language language) {
		Map<File, FileArtifact> h1 = exposedHeaders.get(language);
		if(h1 != null)
			h1.clear();
	}

	public void clearExposedHeaders() {
		exposedHeaders.clear();
	}

	public FileArtifact getExposedHeader(Language language, File path) {
		Map<File, FileArtifact> h1 = exposedHeaders.get(language);
		return h1 == null ? null : h1.get(path);
	}

	public Iterable<Map.Entry<File, FileArtifact>> getExposedHeaders(Language language) {
		Map<File, FileArtifact> h1 = exposedHeaders.get(language);
		return h1 == null ? new EmptyIterable<Map.Entry<File, FileArtifact>>() : h1.entrySet();
	}

	public void addFinalArtifact(Artifact artifact, Flavor flavor, Language language) {
		if(artifact == null)
			return;
		if(flavor == null) {
			flavor = artifact.getProperty(Flavor.class);
			if(flavor == null)
				flavor = Flavor.GENERIC;
		}
		finalArtifacts.add(new FlavoredArtifact(artifact, flavor, language));
	}

	public void removeFinalArtifact(Artifact artifact, Flavor flavor, Language language) {
		if(artifact == null)
			return;
		if(flavor == null) {
			flavor = artifact.getProperty(Flavor.class);
			if(flavor == null)
				flavor = Flavor.GENERIC;
		}
		finalArtifacts.remove(new FlavoredArtifact(artifact, flavor, language));
	}

	public void clearFinalArtifacts() {
		finalArtifacts.clear();
	}

	public Iterable<FlavoredArtifact> getFinalArtifacts() {
		return finalArtifacts;
	}

	public void addUnexposedHeader(Artifact exposed, FileArtifact unexposed) {
		if(exposed == null)
			return;
		if(unexposed == null)
			unexposedHeaders.remove(exposed);
		else
			unexposedHeaders.put(exposed, unexposed);
	}

	public void removeUnexposedHeader(Artifact exposed) {
		unexposedHeaders.remove(exposed);
	}

	public void clearUnexposedHeaders() {
		unexposedHeaders.clear();
	}

	public FileArtifact getUnexposedHeader(Artifact exposed) {
		return unexposedHeaders.get(exposed);
	}

	public void addReverseUnexposedHeader(Artifact unexposed, FileArtifact exposed) {
		if(unexposed == null)
			return;
		if(exposed == null)
			reverseUnexposedHeaders.remove(unexposed);
		else
			reverseUnexposedHeaders.put(unexposed, exposed);
	}

	public void removeReverseUnexposedHeader(Artifact unexposed) {
		reverseUnexposedHeaders.remove(unexposed);
	}

	public void clearReverseUnexposedHeaders() {
		reverseUnexposedHeaders.clear();
	}

	public FileArtifact getReverseUnexposedHeader(Artifact unexposed) {
		return reverseUnexposedHeaders.get(unexposed);
	}

	public void addHeaderExposeDirectory(Language language, File directory) {
		if(directory == null)
			return;
		Set<File> directories = exposeDirectories.get(language);
		if(directories == null) {
			directories = new HashSet<File>();
			exposeDirectories.put(language, directories);
		}
		directories.add(directory);
	}

	public void removeHeaderExposeDirectory(Language language, File directory) {
		if(directory == null)
			return;
		Set<File> directories = exposeDirectories.get(language);
		if(directories != null)
			directories.remove(directory);
	}

	public void clearHeaderExposeDirectories(Language language) {
		Set<File> directories = exposeDirectories.get(language);
		if(directories != null)
			directories.clear();
	}

	public void clearHeaderExposeDirectories() {
		exposeDirectories.clear();
	}

	public Iterable<File> getHeaderExposeDirectories(Language language) {
		Set<File> directories = exposeDirectories.get(language);
		return directories == null ? new EmptyIterable<File>() : directories;
	}

	public static int getMaximalComponentTypeWidth(Iterable<Type> types, ComponentTypeStringifier stringifier) {
		if(types == null)
			types = Type.knownTypes();
		if(stringifier == null)
			stringifier = ToStringComponentTypeStringifier.instance;
		int max = 0;
		for(Type type : types) {
			String s = stringifier.stringifyComponentType(type);
			if(s != null) {
				int length = s.length();
				if(length > max)
					max = length;
			}
		}
		return max;
	}

}
