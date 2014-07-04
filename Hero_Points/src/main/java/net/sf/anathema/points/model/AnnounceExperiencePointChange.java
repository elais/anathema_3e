package net.sf.anathema.points.model;

import net.sf.anathema.library.change.ChangeAnnouncer;
import net.sf.anathema.library.change.ChangeFlavor;
import net.sf.anathema.points.model.xp.ExperiencePointsListener;

public class AnnounceExperiencePointChange implements ExperiencePointsListener {
  private final ChangeAnnouncer announcer;

  public AnnounceExperiencePointChange(ChangeAnnouncer announcer) {
    this.announcer = announcer;
  }

  @Override
  public void entriesAddedRemovedOrChanged() {
    announcer.announceChangeOf(ChangeFlavor.UNSPECIFIED);
  }
}