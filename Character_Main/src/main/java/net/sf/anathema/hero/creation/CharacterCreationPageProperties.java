package net.sf.anathema.hero.creation;

import net.sf.anathema.hero.framework.CharacterUI;
import net.sf.anathema.hero.individual.splat.CharacterType;
import net.sf.anathema.hero.individual.splat.HeroSplat;
import net.sf.anathema.library.presenter.AgnosticUIConfiguration;
import net.sf.anathema.library.resources.RelativePath;
import net.sf.anathema.library.resources.Resources;

public class CharacterCreationPageProperties {

  private final Resources resources;
  private final CharacterUI iconProvider;

  public CharacterCreationPageProperties(Resources resources) {
    this.resources = resources;
    this.iconProvider = new CharacterUI();
  }

  public String getTitle() {
    return resources.getString("CharacterDialog.Title");
  }

  public String getTypeString(CharacterType type) {
    return new CharacterTypeUi(resources).getLabel(type);
  }

  public RelativePath getTypeIcon(CharacterType type) {
    return iconProvider.getSmallTypeIconPath(type);
  }

  public AgnosticUIConfiguration<HeroSplat> getTemplateUI() {
    return new TemplateTypeUiConfiguration(resources);
  }

  public String getCancelButtonString() {
    return resources.getString("CharacterDialog.Cancel");
  }

  public String getOkButtonString() {
    return resources.getString("CharacterDialog.OK");
  }
}