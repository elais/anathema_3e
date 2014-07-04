package net.sf.anathema.hero.charms.display.presenter;

import net.sf.anathema.framework.IApplicationModel;
import net.sf.anathema.hero.magic.description.AggregatedCharmDescriptionProvider;
import net.sf.anathema.hero.magic.description.MagicDescriptionProvider;
import net.sf.anathema.hero.magic.description.MagicDescriptionProviderFactory;
import net.sf.anathema.hero.magic.description.RegisteredMagicDescriptionProviderFactory;
import net.sf.anathema.platform.environment.Environment;

import java.util.Collection;

public class CharmDescriptionProviderExtractor {

  public static MagicDescriptionProvider CreateFor(IApplicationModel model, Environment environment) {
    AggregatedCharmDescriptionProvider provider = new AggregatedCharmDescriptionProvider(environment);
    Collection<MagicDescriptionProviderFactory> factories = findFactories(environment);
    for (MagicDescriptionProviderFactory factory : factories) {
      provider.addProvider(factory.create(model));
    }
    return provider;
  }

  private static Collection<MagicDescriptionProviderFactory> findFactories(Environment environment) {
    return environment.instantiateAll(RegisteredMagicDescriptionProviderFactory.class);
  }

}
