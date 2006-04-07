package net.sf.anathema.character.intimacies.view;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import net.disy.commons.swing.layout.grid.GridDialogLayout;
import net.disy.commons.swing.layout.grid.GridDialogPanel;
import net.disy.commons.swing.layout.grid.IGridDialogPanel;
import net.sf.anathema.lib.control.stringvalue.IStringValueChangedListener;
import net.sf.anathema.lib.workflow.textualdescription.view.LabelTextView;
import net.sf.anathema.lib.workflow.textualdescription.view.LineTextView;

public class IntimaciesSelectionView implements IIntimaciesSelectionView {

  private final LabelTextView labelTextView;
  private final JButton button;

  public IntimaciesSelectionView(String labelText, Icon addButtonIcon) {
    LineTextView lineTextView = new LineTextView(45);
    labelTextView = new LabelTextView(labelText, lineTextView);
    this.button = new JButton(addButtonIcon);
    button.setPreferredSize(new Dimension(addButtonIcon.getIconWidth() + 4, addButtonIcon.getIconHeight() + 4));
  }

  public JComponent getComponent() {
    JPanel panel = new JPanel(new GridDialogLayout(2, false));
    IGridDialogPanel dialogPanel = new GridDialogPanel();
    labelTextView.addTo(dialogPanel);
    panel.add(dialogPanel.getContent());
    panel.add(button);
    return panel;
  }

  public void addTextChangeListener(IStringValueChangedListener listener) {
    labelTextView.addTextChangedListener(listener);
  }

  public void addAddButtonListener(ActionListener listener) {
    button.addActionListener(listener);
  }

  public void setAddButtonEnabled(boolean enabled) {
    button.setEnabled(enabled);
  }

  public void setName(String name) {
    labelTextView.setText(name);
  }
}