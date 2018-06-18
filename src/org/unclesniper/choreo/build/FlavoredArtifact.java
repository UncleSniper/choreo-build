package org.unclesniper.choreo.build;

public final class FlavoredArtifact {

	private final Artifact artifact;

	private final Flavor flavor;

	private final Language language;

	public FlavoredArtifact(Artifact artifact, Flavor flavor, Language language) {
		this.artifact = artifact;
		this.flavor = flavor;
		this.language = language;
	}

	public Artifact getArtifact() {
		return artifact;
	}

	public Flavor getFlavor() {
		return flavor;
	}

	public Language getLanguage() {
		return language;
	}

	public boolean equals(Object other) {
		return other instanceof FlavoredArtifact && equalsFlavoredArtifact((FlavoredArtifact)other);
	}

	public boolean equalsFlavoredArtifact(FlavoredArtifact other) {
		if(artifact == null) {
			if(other.artifact != null)
				return false;
		}
		else {
			if(other.artifact == null)
				return false;
			if(!artifact.equals(other.artifact))
				return false;
		}
		Flavor mf = flavor == null ? Flavor.GENERIC : flavor;
		Flavor tf = other.flavor == null ? Flavor.GENERIC : other.flavor;
		if(mf.getSymbol() != tf.getSymbol())
			return false;
		if(language == null)
			return other.language == null;
		return other.language != null && language.equals(other.language);
	}

}
