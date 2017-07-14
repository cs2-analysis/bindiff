package com.google.security.zynamics.bindiff.graph.editmode;

import com.google.security.zynamics.zylib.gui.zygraph.editmode.CDefaultActionFactory;
import com.google.security.zynamics.zylib.gui.zygraph.editmode.IStateAction;
import com.google.security.zynamics.zylib.gui.zygraph.editmode.helpers.CMouseCursorHelper;
import com.google.security.zynamics.zylib.gui.zygraph.editmode.states.CBackgroundDraggedLeftState;
import com.google.security.zynamics.zylib.gui.zygraph.editmode.states.CBackgroundDraggedRightState;
import com.google.security.zynamics.zylib.yfileswrap.gui.zygraph.AbstractZyGraph;
import com.google.security.zynamics.zylib.yfileswrap.gui.zygraph.edges.ZyGraphEdge;
import com.google.security.zynamics.zylib.yfileswrap.gui.zygraph.nodes.ZyGraphNode;
import java.awt.event.MouseEvent;

/**
 * An IStateActionFactory that changes the cursor on mouse left-drag instead of default right-drag
 * when the graph viewport is moved.
 */
public class InvertMoveViewPortGraphActionFactory<
        NodeType extends ZyGraphNode<?>, EdgeType extends ZyGraphEdge<?, ?, ?>>
    extends CDefaultActionFactory<NodeType, EdgeType> {

  private static boolean isInvertMoveViewPort(final AbstractZyGraph<?, ?> graph) {
    return ((EditMode<?, ?>) graph.getEditMode()).isInvertMoveViewPort();
  }

  @Override
  public IStateAction<CBackgroundDraggedLeftState> createBackgroundDraggedLeftAction() {
    return new IStateAction<CBackgroundDraggedLeftState>() {
      @Override
      public void execute(CBackgroundDraggedLeftState state, MouseEvent event) {
        final AbstractZyGraph<?, ?> graph = state.getGraph();
        if (isInvertMoveViewPort(graph)
            && ((event.getModifiersEx() & MouseEvent.CTRL_DOWN_MASK) == 0)) {
          CMouseCursorHelper.setMoveCursor(graph);
        }
      }
    };
  }

  @Override
  public IStateAction<CBackgroundDraggedRightState> createBackgroundDraggedRightAction() {
    return new IStateAction<CBackgroundDraggedRightState>() {
      @Override
      public void execute(CBackgroundDraggedRightState state, MouseEvent event) {
        final AbstractZyGraph<?, ?> graph = state.getGraph();
        if (!isInvertMoveViewPort(graph)) {
          CMouseCursorHelper.setMoveCursor(graph);
        }
      }
    };
  }
}