package org.unclesniper.choreo.build;

import java.util.Map;
import java.util.HashMap;

public final class Flavor extends AbstractSymbol {

	public static final Flavor SHIPPED = Flavor.intern("shipped");

	public static final Flavor CLEAN = Flavor.intern("clean");

	public static final Flavor STATIC = Flavor.intern("static");

	public static final Flavor DYNAMIC = Flavor.intern("dynamic");

	public static final Flavor HEADER = Flavor.intern("header");

	public static final Flavor SOURCE = Flavor.intern("source");

	public static final Flavor GENERIC = Flavor.intern("generic");

	private static final Map<String, Flavor> FLAVORS = new HashMap<String, Flavor>();

	public Flavor(String name, int symbol) {
		super(name, symbol);
	}

	public boolean equals(Object other) {
		return other instanceof Flavor && symbol == ((Flavor)other).symbol;
	}

	public boolean equalsFlavor(Flavor other) {
		return other != null && symbol == other.symbol;
	}

	public static Flavor intern(String name) {
		name.length();
		synchronized(Flavor.FLAVORS) {
			Flavor flavor = Flavor.FLAVORS.get(name);
			if(flavor == null) {
				flavor = new Flavor(name, Flavor.FLAVORS.size());
				Flavor.FLAVORS.put(name, flavor);
			}
			return flavor;
		}
	}

}
