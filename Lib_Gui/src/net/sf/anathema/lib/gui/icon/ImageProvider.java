package net.sf.anathema.lib.gui.icon;

import net.sf.anathema.lib.file.RelativePath;
import net.sf.anathema.lib.gui.image.ImageLoader;
import net.sf.anathema.lib.gui.image.ImageLoadingException;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

public class ImageProvider {

  public ImageIcon getImageIcon(RelativePath relativePath) {
    Image image = getImage(relativePath);
    return image == null ? null : new ImageIcon(image);
  }

  public Image getImage(RelativePath relativePath) {
    InputStream inputStream = getInputStream(relativePath);
    return loadImage(inputStream);
  }

  private InputStream getInputStream(RelativePath relativePath) {
    InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(relativePath.relativePath);
    if (inputStream == null) {
      throw new ImageLoadingException("Cannot find image resource: " + relativePath);
    }
    return inputStream;
  }

  private Image loadImage(InputStream inputStream) {
    try {
      return ImageLoader.getMemoryImageWithoutCaching(inputStream);
    } catch (IOException e) {
      throw new ImageLoadingException("Cannot open image: " + e.getMessage());
    }
  }
}