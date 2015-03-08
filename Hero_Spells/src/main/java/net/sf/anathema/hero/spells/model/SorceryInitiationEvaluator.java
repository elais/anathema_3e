package net.sf.anathema.hero.spells.model;

import net.sf.anathema.hero.environment.HeroEnvironment;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.spells.data.CircleType;
import net.sf.anathema.hero.spells.template.SpellsTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class SorceryInitiationEvaluator {
  private final Collection<SorceryInitiation> initiations;

  public SorceryInitiationEvaluator(Hero hero, SpellsTemplate template, HeroEnvironment environment) {
    this.initiations = environment.getObjectFactory().instantiateAllImplementers(SorceryInitiation.class, hero, template);
  }

  public boolean isInitiated(CircleType circle) {
    for (SorceryInitiation initiation : initiations) {
      if (initiation.isInitiated(circle)) {
        return true;
      }
    }
    return false;
  }

  public boolean canInitiate() {
    for (SorceryInitiation initiation : initiations) {
      if (initiation.canInitiate()) {
        return true;
      }
    }
    return false;
  }

  public Collection<CircleType> getCirclesToInitiateInto() {
    List<CircleType> circles = new ArrayList<>();
    for (SorceryInitiation initiation : initiations) {
      circles.addAll(initiation.getCirclesToInitiateInto());
    }
    return circles.stream().distinct().collect(toList());
  }
}