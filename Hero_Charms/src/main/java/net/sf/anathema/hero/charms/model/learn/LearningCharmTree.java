package net.sf.anathema.hero.charms.model.learn;

import net.sf.anathema.charm.data.Charm;
import net.sf.anathema.hero.charms.model.BasicLearnCharmTree;
import net.sf.anathema.hero.charms.model.CharmTree;

public interface LearningCharmTree extends CharmTree, BasicLearnCharmTree {

  void toggleLearned(Charm charm);

  void addCharmLearnListener(ICharmLearnListener listener);

  Charm[] getCreationLearnedCharms();

  void learnCharm(Charm charm, boolean experienced);

  void learnCharmNoParents(Charm charm, boolean experienced, boolean announce);

  boolean isForgettable(Charm charm);

  Charm[] getExperienceLearnedCharms();

  void forgetCharm(Charm child, boolean experienced);

  void forgetAll();

  Charm[] getCoreCharms();

  void forgetExclusives();

  void fireRecalculateRequested();
}