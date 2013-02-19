package net.sf.anathema.character.equipment;

import net.sf.anathema.character.generic.equipment.ArtifactAttuneType;

import static net.sf.anathema.character.generic.equipment.ArtifactAttuneType.*;

public abstract class SimpleMaterialRules implements MaterialRules {

  public ArtifactAttuneType[] getAttunementTypes(MagicalMaterial material) {
    return getDefaultAttunementOptions(resonatesWithMaterial(material));
  }

  @Override
  public boolean canAttuneToMalfeanMaterials() {
    return false;
  }

  protected boolean resonatesWithMaterial(MagicalMaterial material) {
    return material == getDefault();
  }

  protected ArtifactAttuneType[] getNullAttunementTypes() {
    return new ArtifactAttuneType[]{Unattuned};
  }

  protected ArtifactAttuneType[] getDefaultAttunementOptions(boolean resonatesWithMaterial) {
    if (resonatesWithMaterial) {
      return new ArtifactAttuneType[]{Unattuned, FullyAttuned};
    }
    return new ArtifactAttuneType[]{Unattuned, PartiallyAttuned, UnharmoniouslyAttuned};
  }
}