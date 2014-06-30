package net.sf.anathema.hero.points.model;

import net.sf.anathema.hero.advance.creation.BonusPointManagement;
import net.sf.anathema.hero.advance.experience.ExperiencePointConfiguration;
import net.sf.anathema.hero.model.HeroModel;
import net.sf.anathema.hero.points.advance.HeroBonusPointCalculator;
import net.sf.anathema.hero.points.display.overview.IOverviewModel;
import net.sf.anathema.hero.points.display.overview.IValueModel;
import net.sf.anathema.hero.points.display.overview.WeightedCategory;
import net.sf.anathema.lib.util.Identifier;
import net.sf.anathema.lib.util.SimpleIdentifier;

public interface PointsModel extends HeroModel {

  Identifier ID = new SimpleIdentifier("Points");

  void addBonusPointCalculator(HeroBonusPointCalculator bonusPointCalculator);

  void addBonusCategory(WeightedCategory category);

  void addToBonusOverview(IOverviewModel bonusPointModel);

  void addToExperienceOverview(IValueModel<Integer> model);

  Iterable<IValueModel<Integer>> getExperienceOverviewModels();

  Iterable<IOverviewModel> getBonusOverviewModels();

  Iterable<WeightedCategory> getBonusCategories();

  BonusPointManagement getBonusPointManagement();

  ExperiencePointConfiguration getExperiencePoints();
}