package net.sf.anathema.hero.thaumaturgy.model;

import net.sf.anathema.hero.traits.model.types.ITraitTypeVisitor;

import java.util.ArrayList;
import java.util.Collection;

public class NullRitualOption implements ThaumaturgyRitual {

	@Override
	public boolean isLegalValue(int value) {
		return false;
	}

	@Override
	public int getMinimumValue() {
		return 0;
	}

	@Override
	public int getMaximumValue() {
		return 0;
	}

	@Override
	public void accept(ITraitTypeVisitor visitor) {
		
	}

	@Override
	public String getId() {
		return null;
	}

	@Override
	public Collection<String> getSuggestions() {
		return new ArrayList<>();
	}


}