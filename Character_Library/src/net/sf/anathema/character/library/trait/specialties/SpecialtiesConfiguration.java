package net.sf.anathema.character.library.trait.specialties;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.disy.commons.core.util.StringUtilities;
import net.sf.anathema.character.generic.framework.additionaltemplate.listening.ICharacterChangeListener;
import net.sf.anathema.character.generic.framework.additionaltemplate.model.ICharacterModelContext;
import net.sf.anathema.character.generic.traits.ITraitType;
import net.sf.anathema.character.generic.traits.groups.ITraitTypeGroup;
import net.sf.anathema.character.generic.traits.groups.TraitTypeGroup;
import net.sf.anathema.character.library.trait.ITrait;
import net.sf.anathema.character.library.trait.ITraitCollection;
import net.sf.anathema.character.library.trait.subtrait.ISubTrait;
import net.sf.anathema.character.library.trait.subtrait.ISubTraitContainer;
import net.sf.anathema.character.library.trait.subtrait.ISubTraitListener;
import net.sf.anathema.character.library.trait.visitor.IAggregatedTrait;
import net.sf.anathema.character.library.trait.visitor.IDefaultTrait;
import net.sf.anathema.character.library.trait.visitor.ITraitVisitor;
import net.sf.anathema.lib.control.change.ChangeControl;
import net.sf.anathema.lib.control.change.IChangeListener;

public class SpecialtiesConfiguration implements ISpecialtiesConfiguration {

  private final Map<ITraitType, ISubTraitContainer> specialtiesByType = new HashMap<ITraitType, ISubTraitContainer>();
  private final Map<ITraitReference, ISubTraitContainer> specialtiesByTrait = new HashMap<ITraitReference, ISubTraitContainer>();
  private final ChangeControl control = new ChangeControl();
  private final ChangeControl traitControl = new ChangeControl();
  private final ICharacterModelContext context;
  private String currentName;
  private ITraitReference currentType;

  public SpecialtiesConfiguration(
      ITraitCollection traitCollection,
      ITraitTypeGroup[] groups,
      final ICharacterModelContext context) {
    this.context = context;
    ITraitType[] traitTypes = TraitTypeGroup.getAllTraitTypes(groups);
    for (ITrait trait : traitCollection.getTraits(traitTypes)) {
      trait.accept(new ITraitVisitor() {
        public void visitAggregatedTrait(IAggregatedTrait visitedTrait) {
          initializeAggregatedTrait(visitedTrait);
        }

        public void visitDefaultTrait(IDefaultTrait visitedTrait) {
          SpecialtiesContainer container = addSpecialtiesContainer(visitedTrait);
          specialtiesByType.put(visitedTrait.getType(), container);
        }
      });
    }
  }

  private void initializeAggregatedTrait(final IAggregatedTrait visitedTrait) {
    visitedTrait.getSubTraits().addSubTraitListener(new ISubTraitListener() {
      public void subTraitAdded(ISubTrait subTrait) {
        ISubTraitContainer container = specialtiesByType.get(visitedTrait.getType());
        addSubTraitSpecialtiesContainer(subTrait, (AggregatedSpecialtiesContainer) container);
        traitControl.fireChangedEvent();
      }

      public void subTraitRemoved(ISubTrait subTrait) {
        ITraitReference reference = new TraitReference(subTrait);
        ISubTraitContainer subContainer = specialtiesByTrait.remove(reference);
        ISubTraitContainer container = specialtiesByType.get(visitedTrait.getType());
        ((AggregatedSpecialtiesContainer) container).removeContainer(subContainer);
        traitControl.fireChangedEvent();
      }

      public void subTraitValueChanged() {
        // nothing to do
      }
    });
    AggregatedSpecialtiesContainer container = new AggregatedSpecialtiesContainer();
    for (ISubTrait subTrait : visitedTrait.getSubTraits().getSubTraits()) {
      addSubTraitSpecialtiesContainer(subTrait, container);
    }
    specialtiesByType.put(visitedTrait.getType(), container);
  }

  private void addSubTraitSpecialtiesContainer(ISubTrait subTrait, AggregatedSpecialtiesContainer container) {
    SpecialtiesContainer subContainer = addSpecialtiesContainer(subTrait);
    container.addContainer(subContainer);
  }

  private SpecialtiesContainer addSpecialtiesContainer(ITrait trait) {
    ITraitReference reference = new TraitReference(trait);
    SpecialtiesContainer specialtiesContainer = new SpecialtiesContainer(reference, context.getTraitContext());
    specialtiesByTrait.put(reference, specialtiesContainer);
    return specialtiesContainer;
  }

  public ISubTraitContainer getSpecialtiesContainer(ITraitReference trait) {
    return specialtiesByTrait.get(trait);
  }

  public ISubTraitContainer getSpecialtiesContainer(ITraitType traitType) {
    return specialtiesByType.get(traitType);
  }

  public ITraitReference[] getAllTraits() {
    Set<ITraitReference> keySet = specialtiesByTrait.keySet();
    return keySet.toArray(new ITraitReference[keySet.size()]);
  }

  public void setCurrentSpecialtyName(String newSpecialtyName) {
    this.currentName = newSpecialtyName;
    control.fireChangedEvent();
  }

  public void setCurrentTrait(ITraitReference newValue) {
    this.currentType = newValue;
    control.fireChangedEvent();
  }

  public void commitSelection() {
    specialtiesByTrait.get(currentType).addSubTrait(currentName);
  }

  public void clear() {
    currentName = null;
    currentType = null;
    control.fireChangedEvent();
  }

  public void addSelectionChangeListener(IChangeListener listener) {
    control.addChangeListener(listener);
  }

  public boolean isEntryComplete() {
    return !StringUtilities.isNullOrEmpty(currentName) && currentType != null;
  }

  public boolean isExperienced() {
    return context.getBasicCharacterContext().isExperienced();
  }

  public void addCharacterChangeListener(ICharacterChangeListener listener) {
    context.getCharacterListening().addChangeListener(listener);
  }

  public void addTraitListChangeListener(IChangeListener listener) {
    traitControl.addChangeListener(listener);
  }

}