package net.sf.anathema.hero.experience;

import net.sf.anathema.hero.individual.model.Hero;

public class ExperienceModelFetcher {

  public static ExperienceModel fetch(Hero hero) {
    return hero.getModel(ExperienceModel.ID);
  }
}
