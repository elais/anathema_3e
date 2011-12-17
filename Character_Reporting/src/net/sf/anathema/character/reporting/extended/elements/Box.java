package net.sf.anathema.character.reporting.extended.elements;

import com.lowagie.text.pdf.PdfContentByte;
import net.sf.anathema.character.reporting.util.Bounds;

import java.awt.*;

public class Box {

  private final Bounds bounds;

  public Box(Bounds bounds) {
    this.bounds = bounds;
  }

  public void encode(PdfContentByte directContent, float lineWidth) {
    directContent.setColorStroke(Color.BLACK);
    directContent.setLineWidth(lineWidth);
    directContent.rectangle(bounds.x, bounds.y, bounds.width, bounds.height);
    directContent.stroke();
  }

  public void encodeTotalType(PdfContentByte directContent) {
    encode(directContent, 0.75f);
  }
}