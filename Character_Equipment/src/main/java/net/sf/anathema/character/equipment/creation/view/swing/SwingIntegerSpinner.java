package net.sf.anathema.character.equipment.creation.view.swing;

import net.sf.anathema.character.equipment.creation.presenter.IIntegerSpinner;
import net.sf.anathema.framework.swing.IView;
import net.sf.anathema.lib.control.IntValueChangedListener;
import net.sf.anathema.lib.data.IOverline;
import net.sf.anathema.lib.data.Range;
import net.sf.anathema.lib.gui.widgets.DigitsOnlyDocument;
import net.sf.anathema.lib.gui.widgets.IIntegerView;

import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class SwingIntegerSpinner implements IView, IIntegerView, IIntegerSpinner {

  private final JSpinner spinner;
  private final Map<IntValueChangedListener, ChangeListener> listenerMap = new HashMap<>();
  private final SpinnerNumberModel numberModel;

  public SwingIntegerSpinner(int initialValue) {
    numberModel = new SpinnerNumberModel(initialValue, null, null, 1);
    spinner = new JSpinner(numberModel);
    DecimalFormat decimalFormat = new DecimalFormat();
    decimalFormat.setGroupingSize(0);
    JSpinner.NumberEditor numberEditor = new JSpinner.NumberEditor(spinner, decimalFormat.toPattern());
    initDigitsOnlyDocument(numberEditor.getTextField());
    spinner.setEditor(numberEditor);
    numberEditor.getTextField().setValue(initialValue);
  }

  @Override
  public void setMaximum(Integer maximum) {
    getSpinnerModel().setMaximum(maximum);
  }

  @Override
  public void setMinimum(Integer minimum) {
    getSpinnerModel().setMinimum(minimum);
  }

  private SpinnerNumberModel getSpinnerModel() {
    return (SpinnerNumberModel) spinner.getModel();
  }

  @Override
  public JComponent getComponent() {
    return spinner;
  }

  @Override
  public void addChangeListener(final IntValueChangedListener listener) {
    ChangeListener changeListener = new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        listener.valueChanged(getValue());
      }
    };
    listenerMap.put(listener, changeListener);
    numberModel.addChangeListener(changeListener);
  }

  @Override
  public void setEnabled(boolean enabled) {
    spinner.setEnabled(enabled);
  }

  private void initDigitsOnlyDocument(final JTextComponent textField) {
    DigitsOnlyDocument document = new DigitsOnlyDocument(true, new IOverline() {
      @Override
      public int getNearestValue(int value) {
        return createRange().getNearestValue(value);
      }

      @Override
      public int getLowerBound() {
        return createRange().getLowerBound();
      }

      private Range createRange() {
        Integer maximum = (Integer) getSpinnerModel().getMaximum();
        Integer minimum = (Integer) getSpinnerModel().getMinimum();
        return new Range(minimum, maximum);
      }
    });
    textField.setDocument(document);
    document.addDocumentListener(new DocumentListener() {

      @Override
      public void changedUpdate(DocumentEvent evt) {
        updateSpinnerModel();
      }

      @Override
      public void insertUpdate(DocumentEvent evt) {
        updateSpinnerModel();
      }

      private void updateSpinnerModel() {
        try {
          String newText = textField.getText();
          int value = Integer.parseInt(newText);
          numberModel.setValue(value);
        } catch (NumberFormatException exc) {
          // nothing to do
        }
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        updateSpinnerModel();
      }
    });
  }

  public int getValue() {
    return ((Number) numberModel.getValue()).intValue();
  }

  @Override
  public void setValue(int newValue) {
    spinner.setValue(newValue);
  }

  @SuppressWarnings("UnusedDeclaration")
  public void removeChangeListener(IntValueChangedListener listener) {
    numberModel.removeChangeListener(listenerMap.get(listener));
  }
}