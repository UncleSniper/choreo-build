package org.unclesniper.choreo.build;

public abstract class AbstractSymbol {

	protected final String name;

	protected final int symbol;

	public AbstractSymbol(String name, int symbol) {
		this.name = name;
		this.symbol = symbol;
	}

	public final String getName() {
		return name;
	}

	public final int getSymbol() {
		return symbol;
	}

	public final String toString() {
		return name;
	}

	public final int hashCode() {
		return symbol;
	}

}
