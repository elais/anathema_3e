package net.sf.anathema.character.main.presenter.magic;

import net.sf.anathema.character.generic.magic.ICharm;
import net.sf.anathema.character.generic.magic.description.MagicDescriptionProvider;
import net.sf.anathema.character.generic.template.magic.ICharmTemplate;
import net.sf.anathema.character.main.model.charms.CharmsModel;
import net.sf.anathema.character.main.model.charms.CharmsModelFetcher;
import net.sf.anathema.character.main.model.concept.HeroConceptFetcher;
import net.sf.anathema.character.main.charm.ILearningCharmGroup;
import net.sf.anathema.character.main.charm.options.DefaultCharmTemplateRetriever;
import net.sf.anathema.hero.concept.model.concept.CasteSelection;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.lib.control.ChangeListener;

public class CharacterCharmModel {
  private Hero hero;
  private MagicDescriptionProvider magicDescriptionProvider;

  public CharacterCharmModel(Hero hero, MagicDescriptionProvider magicDescriptionProvider) {
    this.hero = hero;
    this.magicDescriptionProvider = magicDescriptionProvider;
  }

  public boolean isAllowedAlienCharms() {
    ICharmTemplate charmTemplate = DefaultCharmTemplateRetriever.getNativeTemplate(hero);
    return charmTemplate.isAllowedAlienCharms(getCaste().getType());
  }

  public void addCasteChangeListener(ChangeListener listener) {
    getCaste().addChangeListener(listener);
  }

  public CharmsModel getCharmConfiguration() {
    return CharmsModelFetcher.fetch(hero);
  }

  private CasteSelection getCaste() {
    return HeroConceptFetcher.fetch(hero).getCaste();
  }

  public void toggleLearned(String charmId) {
    CharmsModel charms = getCharmConfiguration();
    ILearningCharmGroup charmGroup = getCharmGroupByCharmId(charmId);
    charmGroup.toggleLearned(charms.getCharmById(charmId));
  }

  private ILearningCharmGroup getCharmGroupByCharmId(String charmId) {
    CharmsModel charms = getCharmConfiguration();
    ICharm charm = charms.getCharmById(charmId);
    return charms.getGroup(charm);
  }

  public MagicDescriptionProvider getMagicDescriptionProvider() {
    return magicDescriptionProvider;
  }
}