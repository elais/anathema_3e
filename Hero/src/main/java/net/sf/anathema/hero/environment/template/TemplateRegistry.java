package net.sf.anathema.hero.environment.template;

import net.sf.anathema.hero.individual.splat.CharacterType;
import net.sf.anathema.hero.individual.splat.HeroSplat;
import net.sf.anathema.hero.individual.splat.SplatType;

import java.util.Collection;

public interface TemplateRegistry {

  Collection<HeroSplat> getAllSupportedTemplates(CharacterType type);

  void register(HeroSplat template);

  HeroSplat getTemplate(SplatType type);
}