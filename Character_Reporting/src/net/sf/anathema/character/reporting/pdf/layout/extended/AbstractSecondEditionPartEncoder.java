package net.sf.anathema.character.reporting.pdf.layout.extended;

import com.lowagie.text.pdf.BaseFont;
import net.sf.anathema.character.generic.traits.types.OtherTraitType;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.combat.CombatStatsContentBoxEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.combat.SecondEditionCombatRulesTableEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.combat.SecondEditionCombatValueEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.essence.ExtendedEssenceBoxContentEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.health.Extended2ndEditionHealthEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.health.Extended2ndEditionMovementEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.health.SecondEditionHealthAndMovementEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.social.SocialCombatStatsBoxEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.general.DotBoxContentEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.general.box.IBoxContentEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.general.box.IContentEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.general.box.IVariableBoxContentEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.general.table.ITableEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.page.IPdfPageEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.page.IVoidStateFormatConstants;
import net.sf.anathema.character.reporting.pdf.rendering.page.PdfPageConfiguration;
import net.sf.anathema.lib.resources.IResources;

public abstract class AbstractSecondEditionPartEncoder implements IExtendedPartEncoder {

  private final IResources resources;
  private final BaseFont baseFont;
  private final int essenceMax;

  public AbstractSecondEditionPartEncoder(IResources resources, BaseFont baseFont, int essenceMax) {
    this.resources = resources;
    this.baseFont = baseFont;
    this.essenceMax = essenceMax;
  }

  public final IResources getResources() {
    return resources;
  }

  protected int getEssenceMax() {
    return essenceMax;
  }

  public boolean hasSecondPage() {
    return true;
  }

  protected int getFontSize() {
    return IVoidStateFormatConstants.SMALLER_FONT_SIZE;
  }

  public IBoxContentEncoder getEssenceEncoder() {
    return new ExtendedEssenceBoxContentEncoder();
  }

  public IBoxContentEncoder getDotsEncoder(OtherTraitType trait, int traitMax, String traitHeaderKey) {
    return new DotBoxContentEncoder(trait, traitMax, traitHeaderKey);
  }

  public IBoxContentEncoder getOverdriveEncoder() {
    return null;
  }

  public final IBoxContentEncoder getCombatStatsEncoder() {
    IContentEncoder valueEncoder = new SecondEditionCombatValueEncoder();
    ITableEncoder rulesEncoder = new SecondEditionCombatRulesTableEncoder();
    return new CombatStatsContentBoxEncoder(rulesEncoder, valueEncoder);
  }

  public IBoxContentEncoder getSocialCombatEncoder() {
    return new SocialCombatStatsBoxEncoder(resources);
  }

  public IBoxContentEncoder getIntimaciesEncoder(ExtendedEncodingRegistry registry) {
    return registry.getIntimaciesEncoder();
  }

  public IBoxContentEncoder getHealthAndMovementEncoder() {
    return new SecondEditionHealthAndMovementEncoder(resources);
  }

  public IBoxContentEncoder getHealthEncoder() {
    return new Extended2ndEditionHealthEncoder(resources);
  }

  public IBoxContentEncoder getMovementEncoder() {
    return new Extended2ndEditionMovementEncoder(resources, baseFont);
  }

  public float getWeaponryHeight() {
    return 102;
  }

  public IVariableBoxContentEncoder[] getAdditionalFirstPageEncoders() {
    return new IVariableBoxContentEncoder[0];
  }

  public IPdfPageEncoder[] getAdditionalPages(PdfPageConfiguration configuration) {
    return new IPdfPageEncoder[0];
  }

  public boolean isEncodeAttributeAsFavorable() {
    return false;
  }
}
