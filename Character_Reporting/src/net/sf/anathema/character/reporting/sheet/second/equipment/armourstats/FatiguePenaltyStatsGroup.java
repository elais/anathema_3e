package net.sf.anathema.character.reporting.sheet.second.equipment.armourstats;

import net.sf.anathema.character.generic.equipment.weapon.IArmour;
import net.sf.anathema.character.reporting.sheet.second.equipment.stats.AbstractValueEquipmentStatsGroup;
import net.sf.anathema.lib.resources.IResources;

import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPTable;

public class FatiguePenaltyStatsGroup extends AbstractValueEquipmentStatsGroup<IArmour> {

  public FatiguePenaltyStatsGroup(IResources resources) {
    super(resources, "Fatigue"); //$NON-NLS-1$
  }

  public int getColumnCount() {
    return 1;
  }

  public void addContent(PdfPTable table, Font font, IArmour armour) {
    if (armour == null) {
      table.addCell(createEmptyEquipmentValueCell(font));
    }
    else {
      table.addCell(createEquipmentValueCell(font, armour.getFatigue()));
    }
  }
}