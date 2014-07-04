package net.sf.anathema.framework.view.status;

import javafx.scene.image.ImageView;
import net.sf.anathema.lib.file.RelativePath;
import net.sf.anathema.platform.tool.ImageContainer;
import net.sf.anathema.platform.tool.LoadImage;
import org.controlsfx.control.NotificationPane;

public class NotificationWithGraphicAndText implements WithGraphicAndText {
  private final NotificationPane pane;
  private ImageView imageView;

  public NotificationWithGraphicAndText(NotificationPane pane) {
    this.pane = pane;
  }

  @Override
  public void setText(String text) {
    pane.setText(text);
  }

  public void setGraphic(ImageView imageView) {
    this.imageView = imageView;
    pane.setGraphic(imageView);
  }

  @Override
  public void setImage(RelativePath iconPath) {
    setImage(iconPath, imageView);
  }

  public static void setImage(RelativePath iconPath, ImageView view) {
    LoadImage image = new LoadImage(iconPath);
    ImageContainer container = image.run();
    container.displayIn(view);
  }
}