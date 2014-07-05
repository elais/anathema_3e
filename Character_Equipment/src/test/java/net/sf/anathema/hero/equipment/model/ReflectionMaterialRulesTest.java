package net.sf.anathema.hero.equipment.model;

import net.sf.anathema.equipment.core.MagicalMaterial;
import net.sf.anathema.hero.individual.splat.HeroType;
import net.sf.anathema.library.dummy.ConfigurableDummyObjectFactory;
import org.junit.Test;

import static net.sf.anathema.equipment.core.MagicalMaterial.Adamant;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReflectionMaterialRulesTest {

  public static final String ID_TEST_TYPE = "TestType";
  private ConfigurableDummyObjectFactory factory = new ConfigurableDummyObjectFactory();

  @Test
  public void knowsDefaultRulesForCharacterTypes() {
    factory.add(CharacterTypeMaterialRules.class, new DummyMaterialRules(Adamant));
    HeroType type = mock(HeroType.class);
    when(type.getId()).thenReturn(ID_TEST_TYPE);
    MagicalMaterial result = new ReflectionMaterialRules(factory).getDefault(type);
    assertThat(result, is(Adamant));
  }
}
