package net.sf.anathema.hero.combos.model.rules;

import net.sf.anathema.hero.combos.model.ComboRules;
import net.sf.anathema.hero.magic.charm.Charm;
import net.sf.anathema.hero.traits.model.TraitType;
import net.sf.anathema.hero.traits.model.types.AbilityType;
import net.sf.anathema.hero.traits.model.types.AttributeType;

public abstract class AbstractComboRules implements ComboRules {

  protected final boolean haveSamePrerequisite(Charm charm1, Charm charm2) {
    TraitType primaryTraitType1 = charm1.getPrerequisites().getPrimaryTraitType();
    TraitType primaryTraitType2 = charm2.getPrerequisites().getPrimaryTraitType();
    return primaryTraitType1.equals(primaryTraitType2);
  }

  protected final boolean haveAttributePrerequisites(Charm charm1, Charm charm2) {
    return hasAttributePrerequisite(charm1) && hasAttributePrerequisite(charm2);
  }

  protected final boolean haveAbilityPrerequisites(Charm charm1, Charm charm2) {
    return hasAbilityPrerequisite(charm1) && hasAbilityPrerequisite(charm2);
  }

  protected final boolean hasNoTraitPrerequisites(Charm charm) {
    return !hasAttributePrerequisite(charm) && !hasAbilityPrerequisite(charm);
  }

  protected final boolean isAbilityAttributeCombo(Charm charm1, Charm charm2) {
    return isAbilityAttributeMix(charm1, charm2) || isAbilityAttributeMix(charm2, charm1);
  }

  private boolean isAbilityAttributeMix(Charm charm1, Charm charm2) {
    return hasAbilityPrerequisite(charm1) && hasAttributePrerequisite(charm2);
  }

  private boolean hasAbilityPrerequisite(Charm charm) {
    return charm.getPrerequisites().getPrimaryTraitType() instanceof AbilityType;
  }

  private boolean hasAttributePrerequisite(Charm charm) {
    return charm.getPrerequisites().getPrimaryTraitType() instanceof AttributeType;
  }
}