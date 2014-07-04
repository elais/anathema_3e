package net.sf.anathema.hero.model.change;

import net.sf.anathema.hero.individual.model.change.ChangeAnnouncer;
import net.sf.anathema.hero.individual.model.change.ChangeFlavor;
import net.sf.anathema.library.event.ChangeListener;

public class UnspecifiedChangeListener implements ChangeListener {

  private ChangeAnnouncer changeAnnouncer;

  public UnspecifiedChangeListener(ChangeAnnouncer changeAnnouncer) {
    this.changeAnnouncer = changeAnnouncer;
  }

  @Override
  public void changeOccurred() {
    changeAnnouncer.announceChangeOf(ChangeFlavor.UNSPECIFIED);
  }
}
