package net.sf.anathema.hero.martial.model;

import net.sf.anathema.characterengine.support.Announcer;
import net.sf.anathema.hero.charms.model.CharmTree;
import net.sf.anathema.hero.charms.model.CharmsModel;
import net.sf.anathema.hero.charms.model.CharmsModelFetcher;
import net.sf.anathema.hero.environment.HeroEnvironment;
import net.sf.anathema.hero.individual.change.ChangeAnnouncer;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.traits.model.Trait;
import net.sf.anathema.hero.traits.model.TraitImpl;
import net.sf.anathema.hero.traits.model.TraitModel;
import net.sf.anathema.hero.traits.model.TraitModelFetcher;
import net.sf.anathema.hero.traits.model.TraitType;
import net.sf.anathema.hero.traits.model.rules.TraitRulesImpl;
import net.sf.anathema.hero.traits.template.TraitTemplate;
import net.sf.anathema.library.event.ChangeListener;
import net.sf.anathema.library.identifier.Identifier;
import net.sf.anathema.magic.data.reference.CategoryReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MartialArtsModelImpl implements MartialArtsModel {
  private final List<Trait> availableStyles = new ArrayList<>();
  private final List<Trait> learnedStyles = new ArrayList<>();
  private final Announcer<ChangeListener> selectionAnnouncer = Announcer.to(ChangeListener.class);
  private final Announcer<StyleLearnListener> learnAnnouncer = Announcer.to(StyleLearnListener.class);
  private final Announcer<StyleForgetListener> forgetAnnouncer = Announcer.to(StyleForgetListener.class);
  private Trait selectedStyle;

  @Override
  public Identifier getId() {
    return MartialArtsModel.ID;
  }

  @Override
  public void initialize(HeroEnvironment environment, Hero hero) {
    TraitModel traitModel = TraitModelFetcher.fetch(hero);
    CharmsModel charmsModel = CharmsModelFetcher.fetch(hero);
    Collection<CharmTree> martialArtsTrees = charmsModel.getTreesFor(new CategoryReference("MartialArts"));
    for (CharmTree charmTree : martialArtsTrees) {
      String treeId = charmTree.getReference().name.text;
      TraitImpl style = new TraitImpl(hero, new TraitRulesImpl(new TraitType(treeId), new TraitTemplate(), hero));
      availableStyles.add(style);
      traitModel.addTraits(style);
    }
    this.selectedStyle = availableStyles.get(0);
  }

  @Override
  public void initializeListening(ChangeAnnouncer announcer) {

  }

  @Override
  public List<Trait> getAllStyles() {
    return availableStyles;
  }

  @Override
  public void selectStyle(Trait newValue) {
    if (newValue == selectedStyle) {
      return;
    }
    this.selectedStyle = newValue;
    selectionAnnouncer.announce().changeOccurred();
  }


  @Override
  public Trait getSelectedStyle() {
    return selectedStyle;
  }

  @Override
  public void learnSelectedStyle() {
    learnedStyles.add(selectedStyle);
    learnAnnouncer.announce().styleLearned(selectedStyle);
  }

  @Override
  public void forget(Trait style) {
    learnedStyles.remove(style);
    forgetAnnouncer.announce().styleForgotten(style);
  }

  @Override
  public void whenStyleIsSelected(ChangeListener listener) {
    selectionAnnouncer.addListener(listener);
  }

  @Override
  public void whenStyleIsLearned(StyleLearnListener listener) {
    learnAnnouncer.addListener(listener);
  }

  @Override
  public void whenStyleIsForgotten(StyleForgetListener listener) {
    forgetAnnouncer.addListener(listener);
  }
}