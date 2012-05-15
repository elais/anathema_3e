package net.sf.anathema.charmentry.presenter.model;

import net.sf.anathema.character.generic.magic.charms.ICharmAttribute;
import net.sf.anathema.character.library.removableentry.presenter.IRemovableEntryModel;
import net.sf.anathema.lib.util.IIdentificate;

public interface IKeywordEntryModel extends IRemovableEntryModel<ICharmAttribute> {

  void setCurrentKeyword(IIdentificate keyword);

  void clear();

  IIdentificate[] getAvailableKeywords();
}