package net.sf.anathema.character.abyssal.reporting;

import java.util.List;

import net.sf.anathema.character.generic.character.*;
import net.sf.anathema.character.generic.impl.traits.ValueWeightGenericTraitSorter;
import net.sf.anathema.character.generic.traits.IGenericTrait;
import net.sf.anathema.character.generic.traits.types.VirtueType;
import net.sf.anathema.character.reporting.common.TableEncodingUtilities;
import net.sf.anathema.character.reporting.common.boxes.VirtueFlawBoxEncoder;
import net.sf.anathema.character.reporting.common.encoder.AbstractPdfEncoder;
import net.sf.anathema.character.reporting.common.encoder.IPdfContentBoxEncoder;
import net.sf.anathema.character.reporting.common.PdfEncodingUtilities;
import net.sf.anathema.character.reporting.common.pageformat.IVoidStateFormatConstants;
import net.sf.anathema.character.reporting.common.PdfTextEncodingUtilities;
import net.sf.anathema.character.reporting.common.Bounds;
import net.sf.anathema.lib.resources.IResources;

import com.lowagie.text.Chunk;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;

public class AbyssalResonanceEncoder extends AbstractPdfEncoder implements IPdfContentBoxEncoder {
  private final BaseFont baseFont;
  private final VirtueFlawBoxEncoder traitEncoder;
  private final IResources resources;
  private final Chunk symbolChunk;

  public AbyssalResonanceEncoder(BaseFont baseFont, BaseFont symbolBaseFont, IResources resources) {
    this.baseFont = baseFont;
    this.resources = resources;
    this.traitEncoder = new VirtueFlawBoxEncoder(baseFont);
    this.symbolChunk = PdfEncodingUtilities.createCaretSymbolChunk(symbolBaseFont);
  }

  @Override
  protected BaseFont getBaseFont() {
    return baseFont;
  }

  public String getHeaderKey(IGenericCharacter character, IGenericDescription description) {
    return "GreatCurse.Abyssal"; //$NON-NLS-1$
  }

  public void encode(PdfContentByte directContent, IGenericCharacter character, IGenericDescription description, Bounds bounds) throws DocumentException {
    Bounds textBounds = traitEncoder.encode(directContent, bounds, 0);
    Font font = TableEncodingUtilities.createFont(getBaseFont());
    Phrase phrase = new Phrase("", font); //$NON-NLS-1$
    phrase.add(symbolChunk);
    phrase.add(resources.getString("Sheet.GreatCurse.SocialPoolMessage", getMaxVirtueValue(character)) + "\n"); //$NON-NLS-1$ //$NON-NLS-2$    
    phrase.add(symbolChunk);
    phrase.add(resources.getString("Sheet.GreatCurse.VirtueDifficulty")); //$NON-NLS-1$
    PdfTextEncodingUtilities.encodeText(directContent, phrase, textBounds, IVoidStateFormatConstants.LINE_HEIGHT - 2);
  }
  
  public boolean hasContent(IGenericCharacter character)
  {
	  return true;
  }

  private int getMaxVirtueValue(IGenericCharacter character) {
    IGenericTrait[] virtues = character.getTraitCollection().getTraits(VirtueType.values());
    List<IGenericTrait> sortedVirtues = new ValueWeightGenericTraitSorter().sortDescending(virtues);
    return sortedVirtues.get(0).getCurrentValue();
  }
}
