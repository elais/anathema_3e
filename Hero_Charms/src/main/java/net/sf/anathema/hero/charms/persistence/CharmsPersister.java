package net.sf.anathema.hero.charms.persistence;

import net.sf.anathema.charm.data.Charm;
import net.sf.anathema.charm.data.reference.CharmName;
import net.sf.anathema.hero.charms.model.CharmsModel;
import net.sf.anathema.hero.charms.model.learn.LearningCharmTree;
import net.sf.anathema.hero.charms.persistence.special.SpecialCharmListPersister;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.persistence.AbstractModelJsonPersister;
import net.sf.anathema.library.identifier.Identifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.sf.anathema.library.message.MessageType.Error;

@SuppressWarnings("UnusedDeclaration")
public class CharmsPersister extends AbstractModelJsonPersister<CharmListPto, CharmsModel> {

  public CharmsPersister() {
    super("charms", CharmListPto.class);
  }

  @Override
  public Identifier getModelId() {
    return CharmsModel.ID;
  }

  @Override
  protected void loadModelFromPto(Hero hero, CharmsModel model, CharmListPto pto) {
    for (CharmPto charmPto : pto.charms) {
      learnCharm(model, charmPto, pto);
    }
  }

  private void learnCharm(CharmsModel model, CharmPto charmPto, CharmListPto pto) {
    SpecialCharmListPersister specialPersister = new SpecialCharmListPersister(model);
    try {
      Charm charm = model.getCharmById(new CharmName(charmPto.charm));
      LearningCharmTree group = model.getGroup(charm);
      if (!group.isLearned(charm, false)) {
        group.learnCharmNoParents(charm, charmPto.isExperienceLearned, false);
      }
      specialPersister.loadSpecials(model, charm, pto, charmPto.isExperienceLearned);
    } catch (IllegalArgumentException e) {
      messaging.addPermanentMessage(Error, "CharmPersistence.NoCharmFound", charmPto.charm);
    }
  }

  @Override
  protected CharmListPto saveModelToPto(CharmsModel model) {
    CharmListPto pto = new CharmListPto();
    saveCharms(model, pto);
    saveCharmSpecials(model, pto);
    return pto;
  }

  private void saveCharms(CharmsModel model, CharmListPto pto) {
    Map<String, Boolean> isExperiencedLearned = getExperiencedLearnedMap(model);
    List<Charm> sortedCharmList = getSortedCharmList(model);
    for (Charm charm : sortedCharmList) {
      saveCharm(charm, isExperiencedLearned.get(charm.getName().text), pto);
    }
  }

  private void saveCharm(Charm charm, boolean isExperienceLearned, CharmListPto pto) {
    CharmPto charmPto = new CharmPto();
    charmPto.charm = charm.getName().text;
    charmPto.tree = charm.getTreeReference().name.text;
    charmPto.category = charm.getTreeReference().category.text;
    charmPto.isExperienceLearned = isExperienceLearned;
    pto.charms.add(charmPto);
  }

  private void saveCharmSpecials(CharmsModel model, CharmListPto pto) {
    for (Charm charm : getSortedCharmList(model)) {
      saveCharmSpecials(model, charm, pto);
    }
  }

  private void saveCharmSpecials(CharmsModel charmsModel, Charm charm, CharmListPto charmListPto) {
    SpecialCharmListPersister persister = new SpecialCharmListPersister(charmsModel);
    persister.saveCharmSpecials(charmsModel, charm, charmListPto);
  }

  private List<Charm> getSortedCharmList(CharmsModel model) {
    List<Charm> charms = new ArrayList<>();
    for (LearningCharmTree group : model.getAllGroups()) {
      Collections.addAll(charms, group.getCreationLearnedCharms());
      Collections.addAll(charms, group.getExperienceLearnedCharms());
    }
    Collections.sort(charms, (o1, o2) -> o1.getName().compareTo(o2.getName()));
    return charms;
  }

  private Map<String, Boolean> getExperiencedLearnedMap(CharmsModel model) {
    HashMap<String, Boolean> isExperiencedLearned = new HashMap<>();
    for (LearningCharmTree group : model.getAllGroups()) {
      for (Charm charm : group.getCreationLearnedCharms()) {
        isExperiencedLearned.put(charm.getName().text, false);
      }
      for (Charm charm : group.getExperienceLearnedCharms()) {
        isExperiencedLearned.put(charm.getName().text, true);
      }
    }
    return isExperiencedLearned;
  }
}
