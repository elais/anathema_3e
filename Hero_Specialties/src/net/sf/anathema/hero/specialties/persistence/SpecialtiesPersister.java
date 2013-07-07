package net.sf.anathema.hero.specialties.persistence;

import net.sf.anathema.character.main.library.trait.specialties.SpecialtiesModel;
import net.sf.anathema.character.main.library.trait.specialties.Specialty;
import net.sf.anathema.character.main.library.trait.subtrait.ISubTraitContainer;
import net.sf.anathema.character.main.traits.types.AbilityType;
import net.sf.anathema.hero.persistence.AbstractModelJsonPersister;
import net.sf.anathema.hero.persistence.HeroModelPersisterCollected;
import net.sf.anathema.hero.traits.persistence.TraitPersister;
import net.sf.anathema.hero.traits.persistence.TraitPto;
import net.sf.anathema.lib.util.Identifier;

@HeroModelPersisterCollected
public class SpecialtiesPersister extends AbstractModelJsonPersister<SpecialtiesPto, SpecialtiesModel> {

  private final TraitPersister traitPersister = new TraitPersister();

  public SpecialtiesPersister() {
    super("specialties", SpecialtiesPto.class);
  }

  @Override
  public Identifier getModelId() {
    return SpecialtiesModel.ID;
  }

  @Override
  protected void fillModel(SpecialtiesModel model, SpecialtiesPto pto) {
    for (SpecialtiesTraitPto traitPto : pto.traits) {
      ISubTraitContainer container = model.getSpecialtiesContainer(AbilityType.valueOf(traitPto.traitName));
      for (TraitPto specialtyPto : traitPto.specialties) {
        Specialty specialty = container.addSubTrait(specialtyPto.name);
        traitPersister.load(specialty, specialtyPto);
      }
    }
  }

  @Override
  protected SpecialtiesPto createPto(SpecialtiesModel model) {
    SpecialtiesPto pto = new SpecialtiesPto();
    for (AbilityType type : AbilityType.values()) {
      SpecialtiesTraitPto specialtiesTraitPto = new SpecialtiesTraitPto();
      specialtiesTraitPto.traitName = type.name();
      saveSpecialties(model.getSpecialtiesContainer(type), specialtiesTraitPto);
      if (!specialtiesTraitPto.specialties.isEmpty()) {
        pto.traits.add(specialtiesTraitPto);
      }
    }
    return pto;
  }

  private void saveSpecialties(ISubTraitContainer container, SpecialtiesTraitPto pto) {
    for (Specialty specialty : container.getSubTraits()) {
      TraitPto traitPto = new TraitPto();
      traitPersister.save(specialty, traitPto);
      pto.specialties.add(traitPto);
    }
  }
}
