package net.sf.anathema.hero.magic.testing;

import net.sf.anathema.character.main.dummy.DummyCharm;
import net.sf.anathema.charms.MartialArtsLevel;
import net.sf.anathema.lib.util.Identifier;

public class CharmObjectMother {
  public static DummyCharm createMartialArtsCharm(final MartialArtsLevel level) {
    return new DummyCharm("Dummy") {
      @Override
      public boolean hasAttribute(Identifier attribute) {
        return attribute.getId().equals("MartialArts") || attribute.getId().equals(level.getId());
      }
    };
  }
}
