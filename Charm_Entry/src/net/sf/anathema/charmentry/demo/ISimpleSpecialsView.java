package net.sf.anathema.charmentry.demo;

import javax.swing.ListCellRenderer;

import net.disy.commons.swing.dialog.core.IPageContent;
import net.sf.anathema.framework.value.IIntValueView;
import net.sf.anathema.lib.gui.selection.IObjectSelectionView;

public interface ISimpleSpecialsView extends IPageContent {

  public IIntValueView addIntegerSelectionView(String speedLabel, int maximum);

  public IObjectSelectionView addObjectSelectionView(String labelString, ListCellRenderer renderer, Object[] objects);

}
