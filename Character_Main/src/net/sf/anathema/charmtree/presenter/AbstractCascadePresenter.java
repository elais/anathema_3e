package net.sf.anathema.charmtree.presenter;

import net.sf.anathema.character.generic.magic.ICharm;
import net.sf.anathema.character.generic.magic.charms.GroupCharmTree;
import net.sf.anathema.character.generic.magic.charms.ICharmGroup;
import net.sf.anathema.charmtree.filters.ICharmFilter;
import net.sf.anathema.charmtree.view.ICascadeSelectionView;
import net.sf.anathema.charmtree.view.ICharmGroupChangeListener;
import net.sf.anathema.interaction.Command;
import net.sf.anathema.interaction.Tool;
import net.sf.anathema.lib.compare.I18nedIdentificateSorter;
import net.sf.anathema.lib.control.ObjectValueListener;
import net.sf.anathema.lib.gui.AgnosticUIConfiguration;
import net.sf.anathema.lib.resources.Resources;
import net.sf.anathema.lib.util.Identifier;
import net.sf.anathema.platform.tree.presenter.view.CascadeLoadedListener;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCascadePresenter implements ICascadeSelectionPresenter {

  private final Resources resources;
  protected CharmFilterSet filterSet = new CharmFilterSet();
  private ICharmGroupChangeListener changeListener;
  private Identifier currentType;
  private ICascadeSelectionView view;
  private CharmDye dye;
  private CharmTypes charmTypes;
  protected CharmGroupCollection charmGroups;
  private SpecialCharmViewPresenter specialCharmPresenter = new NullSpecialCharmPresenter();
  private AlienCharmPresenter alienPresenter = new NullAlienCharmPresenter();
  private CharmInteractionPresenter interactionPresenter = new NullInteractionPresenter();

  public AbstractCascadePresenter(Resources resources) {
    this.resources = resources;
  }

  public void initPresentation() {
    createCharmTypeSelector();
    listenForCascadeLoading();
    initCharmTypeSelectionListening();
    specialCharmPresenter.initPresentation();
    view.whenCursorLeavesCharmAreaResetAllPopups();
    createCharmGroupSelector();
    initFilters();
    createHelpText();
    alienPresenter.initPresentation();
    interactionPresenter.initPresentation();
  }

  private void initCharmTypeSelectionListening() {
    view.addCharmTypeSelectionListener(new ObjectValueListener<Identifier>() {
      @Override
      public void valueChanged(Identifier cascadeType) {
        currentType = cascadeType;
        handleTypeSelectionChange(cascadeType);
      }
    });
  }

  private void listenForCascadeLoading() {
    view.addCascadeLoadedListener(new CascadeLoadedListener() {
      @Override
      public void cascadeLoaded() {
        dye.setCharmVisuals();
      }
    });
    view.addCascadeLoadedListener(new CascadeLoadedListener() {
      @Override
      public void cascadeLoaded() {
        specialCharmPresenter.showSpecialViews();
      }
    });
  }

  protected Resources getResources() {
    return resources;
  }

  protected void createCharmGroupSelector() {
    ICharmGroup[] allGroups = charmGroups.getCharmGroups();
    AgnosticUIConfiguration config = new SelectIdentifierConfiguration(resources);
    view.addCharmGroupSelector(getResources().getString("CardView.CharmConfiguration.AlienCharms.CharmGroup"), config, changeListener,
            allGroups);
  }

  protected void createCharmTypeSelector() {
    Identifier[] types = charmTypes.getCurrentCharmTypes();
    view.addCharmTypeSelector(getResources().getString("CharmTreeView.GUI.CharmType"), types, new SelectIdentifierConfiguration(resources));
  }

  protected void createFilterButton(ICascadeSelectionView selectionView) {
    Tool tool = selectionView.addCharmFilterButton(resources.getString("CharmFilters.Filters"));
    tool.setText(resources.getString("CharmFilters.Define"));
    tool.setCommand(new DefineCharmFilters());
  }

  private void createHelpText() {
    view.addCharmCascadeHelp(resources.getString("CharmTreeView.GUI.HelpText"));
  }

  private ICharmGroup[] sortCharmGroups(ICharmGroup[] originalGroups) {
    ArrayList<ICharmGroup> filteredGroups = new ArrayList<>();
    for (ICharmGroup group : originalGroups) {
      boolean acceptGroup = false;
      for (ICharm charm : group.getAllCharms()) {
        boolean acceptCharm = filterSet.acceptsCharm(charm);
        if (acceptCharm) {
          acceptGroup = true;
          break;
        }
      }
      if (acceptGroup) {
        filteredGroups.add(group);
      }
    }
    ICharmGroup[] filteredGroupArray = filteredGroups.toArray(new ICharmGroup[filteredGroups.size()]);
    if (!filteredGroups.isEmpty()) {
      I18nedIdentificateSorter<ICharmGroup> sorter = new I18nedIdentificateSorter<>();
      return sorter.sortAscending(filteredGroupArray, new ICharmGroup[filteredGroups.size()], resources);
    }
    return filteredGroupArray;
  }

  protected void setSpecialPresenter(SpecialCharmViewPresenter presenter) {
    this.specialCharmPresenter = presenter;
  }

  protected void setView(ICascadeSelectionView view) {
    this.view = view;
  }

  protected void setChangeListener(ICharmGroupChangeListener charmGroupChangeListener) {
    this.changeListener = charmGroupChangeListener;
  }

  protected void setCharmDye(CharmDye dye) {
    this.dye = dye;
  }

  protected void setCharmTypes(CharmTypes types) {
    this.charmTypes = types;
  }

  private void handleTypeSelectionChange(Identifier cascadeType) {
    if (cascadeType == null) {
      view.fillCharmGroupBox(new Identifier[0]);
      return;
    }
    GroupCharmTree charmTree = getCharmTree(cascadeType);
    if (charmTree == null) {
      view.fillCharmGroupBox(new Identifier[0]);
      return;
    }
    ICharmGroup[] allCharmGroups = charmTree.getAllCharmGroups();
    ICharmGroup[] sortedCharmGroups = sortCharmGroups(allCharmGroups);
    view.fillCharmGroupBox(sortedCharmGroups);
    specialCharmPresenter.showSpecialViews();
  }

  protected abstract GroupCharmTree getCharmTree(Identifier type);

  private void initFilters() {
    CharmFilterContainer charms = getFilterContainer();
    List<ICharmFilter> charmFilters = charms.getCharmFilters();
    filterSet.init(charmFilters);
    createFilterButton(view);
  }

  protected abstract CharmFilterContainer getFilterContainer();

  protected void setAlienCharmPresenter(AlienCharmPresenter presenter) {
    this.alienPresenter = presenter;
  }

  protected void setInteractionPresenter(CharmInteractionPresenter presenter) {
    this.interactionPresenter = presenter;
  }

  public void setCharmGroups(CharmGroupCollection charmGroups) {
    this.charmGroups = charmGroups;
  }

  private class DefineCharmFilters implements Command {
    @Override
    public void execute() {
      CharmFilterDefinitionView definitionView = view.startEditingFilters(resources, filterSet);
      definitionView.whenEditIsFinished(new FilterDefinitionListener() {
        @Override
        public void changeConfirmed() {
          filterSet.applyAllFilters();
          handleTypeSelectionChange(currentType);
          changeListener.reselect();
        }

        @Override
        public void changeAborted() {
          filterSet.resetAllFilters();
        }
      });
      definitionView.show();
    }
  }
}