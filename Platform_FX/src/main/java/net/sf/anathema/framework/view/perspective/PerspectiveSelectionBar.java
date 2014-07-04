package net.sf.anathema.framework.view.perspective;

import javafx.scene.Node;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import net.sf.anathema.interaction.Command;
import net.sf.anathema.library.resources.Resources;
import net.sf.anathema.platform.tool.FxToggleTool;

public class PerspectiveSelectionBar {
  private final ToolBar toolbar = new ToolBar();
  private final ToggleGroup buttonGroup = new ToggleGroup();
  private final PerspectiveStack perspectiveStack;

  public PerspectiveSelectionBar(PerspectiveStack perspectiveStack) {
    this.perspectiveStack = perspectiveStack;
  }

  public void addPerspective(final Perspective perspective, Resources resources) {
    FxToggleTool tool = FxToggleTool.create();
    Command command = createCommand(perspective, tool);
    tool.setCommand(command);
    PerspectiveToggle toggle = new ToolPerspectiveToggle(tool, resources);
    perspective.configureToggle(toggle);
    toolbar.getItems().add(tool.getNode());
    tool.registerWithGroup(buttonGroup);
  }

  private Command createCommand(final Perspective perspective, FxToggleTool tool) {
      return new Command() {
      @Override
      public void execute() {
        perspectiveStack.show(perspective);
        tool.select();
      }
    };
  }

  public Node getContent() {
    return toolbar;
  }

  public void selectFirstButton() {
    perspectiveStack.showFirst();
  }
}