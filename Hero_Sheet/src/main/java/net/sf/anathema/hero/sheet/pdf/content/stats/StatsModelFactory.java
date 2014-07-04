package net.sf.anathema.hero.sheet.pdf.content.stats;

import net.sf.anathema.hero.environment.template.TemplateFactory;
import net.sf.anathema.hero.initialization.SimpleModelTreeEntry;
import net.sf.anathema.hero.model.HeroModelFactory;

@SuppressWarnings("UnusedDeclaration")
public class StatsModelFactory extends SimpleModelTreeEntry implements HeroModelFactory {

  public StatsModelFactory() {
    super(StatsModel.ID);
  }

  @SuppressWarnings("unchecked")
  @Override
  public StatsModelImpl create(TemplateFactory templateFactory, String templateId) {
    return new StatsModelImpl();
  }
}
