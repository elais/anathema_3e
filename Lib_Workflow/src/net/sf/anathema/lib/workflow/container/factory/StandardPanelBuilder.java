package net.sf.anathema.lib.workflow.container.factory;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import net.disy.commons.swing.border.TitledPanel;
import net.disy.commons.swing.layout.grid.GridAlignment;
import net.disy.commons.swing.layout.grid.GridDialogLayoutData;
import net.disy.commons.swing.layout.grid.IDialogComponent;
import net.disy.commons.swing.util.ToggleComponentEnabler;
import net.sf.anathema.lib.gui.gridlayout.DefaultGridDialogPanel;
import net.sf.anathema.lib.gui.gridlayout.IGridDialogPanel;
import net.sf.anathema.lib.gui.selection.IObjectSelectionView;
import net.sf.anathema.lib.gui.selection.ObjectSelectionView;
import net.sf.anathema.lib.gui.widgets.IntegerSpinner;
import net.sf.anathema.lib.workflow.booleanvalue.CheckBoxBooleanView;
import net.sf.anathema.lib.workflow.booleanvalue.IBooleanValueView;
import net.sf.anathema.lib.workflow.textualdescription.ICheckableTextView;
import net.sf.anathema.lib.workflow.textualdescription.ITextView;
import net.sf.anathema.lib.workflow.textualdescription.view.AreaTextView;
import net.sf.anathema.lib.workflow.textualdescription.view.LabelTextView;
import net.sf.anathema.lib.workflow.textualdescription.view.LineTextView;

public class StandardPanelBuilder {

  private final IGridDialogPanel dialogPanel = new DefaultGridDialogPanel(false);

  public void addDialogComponent(IDialogComponent component) {
    dialogPanel.add(component);
  }

  public ITextView addLineTextView(final String labelName, int columnCount) {
    return addLabelledTextView(labelName, new LineTextView(columnCount), false);
  }

  public ICheckableTextView addCheckableLineTextView(String labelName, int textFieldColumns) {
    final CheckBoxBooleanView checkBoxView = new CheckBoxBooleanView(labelName);
    final ITextView textView = new LineTextView(textFieldColumns);
    ToggleComponentEnabler.connect(checkBoxView.getContent(), textView.getComponent());
    dialogPanel.add(new IDialogComponent() {
      public int getColumnCount() {
        return 2;
      }

      public void fillInto(JPanel panel, int columnCount) {
        GridDialogLayoutData labelLayoutData = new GridDialogLayoutData();
        labelLayoutData.setHorizontalAlignment(GridAlignment.BEGINNING);
        labelLayoutData.setVerticalAlignment(GridAlignment.BEGINNING);
        panel.add(checkBoxView.getContent(), labelLayoutData);
        JComponent textContent = textView.getComponent();
        GridDialogLayoutData contentData = new GridDialogLayoutData(GridDialogLayoutData.FILL_HORIZONTAL);
        contentData.setHorizontalSpan(columnCount - 1);
        panel.add(textContent, contentData);
      }
    });
    return new ICheckableTextView() {

      public IBooleanValueView getBooleanValueView() {
        return checkBoxView;
      }

      public ITextView getTextView() {
        return textView;
      }
    };
  }

  public ITextView addAreaTextView(final String labelName, int rowCount, int columnCount) {
    return addLabelledTextView(labelName, new AreaTextView(rowCount, columnCount), true);
  }

  private ITextView addLabelledTextView(final String labelText, final ITextView textView, boolean scroll) {
    new LabelTextView(labelText, textView, scroll).addTo(dialogPanel);
    return textView;
  }

  public JPanel getTitledContent(String title) {
    return new TitledPanel(title, dialogPanel.getContent());
  }

  public JPanel getUntitledContent() {
    return dialogPanel.getContent();
  }

  public IntegerSpinner addIntegerSpinner(final String labelString, int startValue) {
    final IntegerSpinner spinner = new IntegerSpinner(startValue);
    dialogPanel.add(new IDialogComponent() {
      public int getColumnCount() {
        return 2;
      }

      public void fillInto(JPanel panel, int columnCount) {
        panel.add(new JLabel(labelString));
        panel.add(spinner.getComponent(), GridDialogLayoutData.FILL_HORIZONTAL);
      }
    });
    return spinner;
  }

  public <V> IObjectSelectionView<V> addObjectSelectionView(String label, ListCellRenderer renderer, V[] objects) {
    ObjectSelectionView<V> view = new ObjectSelectionView<V>(label, renderer, objects);
    view.addComponents(dialogPanel);
    return view;
  }

  public <V> IObjectSelectionView<V> addEditableObjectSelectionView(String label, ListCellRenderer renderer, V[] objects) {
    ObjectSelectionView<V> view = new ObjectSelectionView<V>(label, renderer, true, objects);
    view.addComponents(dialogPanel);
    return view;
  }
}