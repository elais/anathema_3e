package net.sf.anathema.campaign.music.impl.persistence.categorization;

import net.sf.anathema.campaign.music.impl.persistence.MusicDatabasePersister;
import net.sf.anathema.lib.lang.ArrayFactory;

import java.util.List;

public abstract class AbstractProvider<C> {

  private final C[] availableValues;

  public AbstractProvider(MusicDatabasePersister dataBasePersister, Class<C> objectClass) {
    List<C> objectList = dataBasePersister.getAllObjects(objectClass);
    if (objectList.isEmpty()) {
      initDataBase(dataBasePersister);
      objectList = dataBasePersister.getAllObjects(objectClass);
    }
    ArrayFactory<C> arrayFactory = new ArrayFactory<>(objectClass);
    availableValues = objectList.toArray(arrayFactory.createArray(objectList.size()));
  }

  private void initDataBase(MusicDatabasePersister dataBasePersister) {
    for (C value : createDefaultValues()) {
      dataBasePersister.setSimpleObject(value);
    }
  }

  protected abstract C[] createDefaultValues();

  public C[] getAvailableValues() {
    return availableValues;
  }
}