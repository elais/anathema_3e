package net.sf.anathema.character.platform.module;

import net.sf.anathema.character.generic.framework.CharacterGenericsExtractor;
import net.sf.anathema.character.impl.model.advance.ExperiencePointManagement;
import net.sf.anathema.character.impl.model.creation.bonus.BonusPointManagement;
import net.sf.anathema.character.impl.view.SubViewMap;
import net.sf.anathema.character.impl.view.SubViewRegistry;
import net.sf.anathema.character.impl.view.TaskedCharacterView;
import net.sf.anathema.character.model.ICharacter;
import net.sf.anathema.character.model.advance.IExperiencePointManagement;
import net.sf.anathema.character.model.creation.IBonusPointManagement;
import net.sf.anathema.character.presenter.CharacterPresenter;
import net.sf.anathema.character.presenter.OverviewPresenter;
import net.sf.anathema.character.presenter.PlayerCharacterPointPresentation;
import net.sf.anathema.character.view.CharacterView;
import net.sf.anathema.character.view.OverviewContainer;
import net.sf.anathema.framework.IApplicationModel;
import net.sf.anathema.framework.repository.IItem;
import net.sf.anathema.framework.swing.IView;
import net.sf.anathema.lib.resources.Resources;
import net.sf.anathema.swing.character.perspective.ItemViewFactory;

public class CharacterViewFactory implements ItemViewFactory {
  private final Resources resources;
  private final IApplicationModel model;

  public CharacterViewFactory(Resources resources, IApplicationModel model) {
    this.resources = resources;
    this.model = model;
  }

  @Override
  public IView createView(IItem item) {
    ICharacter character = (ICharacter) item.getItemData();
    SubViewRegistry viewFactory = new SubViewMap(CharacterGenericsExtractor.getGenerics(model).getInstantiater());
    CharacterView characterView = new TaskedCharacterView(viewFactory);
    new CharacterPresenter(character, characterView, resources, model).initPresentation();
    initOverviewPresentation(character, characterView, resources);
    item.getItemData().setClean();
    return characterView;
  }

  private void initOverviewPresentation(ICharacter character, OverviewContainer container, Resources resources) {
    IBonusPointManagement bonusPointManagement = new BonusPointManagement(character);
    IExperiencePointManagement experiencePointManagement = new ExperiencePointManagement(character);
    new OverviewPresenter(resources, character, container, bonusPointManagement, experiencePointManagement).initPresentation();
  }
}
