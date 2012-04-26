package net.sf.anathema.character.impl.model;

import net.sf.anathema.character.model.IIntegerDescription;
import net.sf.anathema.lib.control.IChangeListener;
import org.jmock.example.announcer.Announcer;

public class IntegerDescription implements IIntegerDescription {

  private final Announcer<IChangeListener> control = Announcer.to(IChangeListener.class);
  private int value;

  public IntegerDescription(int value) {
    this.value = value;
  }

  @Override
  public final int getValue() {
    return value;
  }

  @Override
  public final void setValue(int value) {
    if (this.value == value) {
      return;
    }
    this.value = value;
    fireValueChangedEvent();
  }

  @Override
  public final void addChangeListener(IChangeListener listener) {
    control.addListener(listener);
  }

  private void fireValueChangedEvent() {
    control.announce().changeOccurred();
  }
}