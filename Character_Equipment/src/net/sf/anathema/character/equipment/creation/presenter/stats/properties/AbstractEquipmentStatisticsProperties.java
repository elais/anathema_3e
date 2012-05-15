package net.sf.anathema.character.equipment.creation.presenter.stats.properties;

import net.sf.anathema.lib.message.BasicMessage;
import net.sf.anathema.lib.message.IBasicMessage;
import net.sf.anathema.lib.message.MessageType;
import net.sf.anathema.lib.resources.IResources;

public abstract class AbstractEquipmentStatisticsProperties extends AbstractProperties {

  public AbstractEquipmentStatisticsProperties(IResources resources) {
    super(resources);
  }

  public String getNameLabel() {
    return getLabelString("Equipment.Creation.Stats.Name"); //$NON-NLS-1$
  }

  public IBasicMessage getUndefinedNameMessage() {
    return new BasicMessage(getString("Equipment.Creation.Stats.NoNameMessage"), MessageType.ERROR); //$NON-NLS-1$
  }

  public IBasicMessage getDuplicateNameMessage() {
    return new BasicMessage(getString("Equipment.Creation.Stats.NameExistsMessage"), MessageType.ERROR); //$NON-NLS-1$
  }

  public abstract String getDefaultName();

  public abstract IBasicMessage getDefaultMessage();

  public abstract String getPageDescription();
}