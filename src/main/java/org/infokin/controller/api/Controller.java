package org.infokin.controller.api;

import javafx.scene.Node;
import org.infokin.Global;

/**
 * Controller interface that is used by every controller implementation.
 */
public abstract class Controller {

    /**
     * Gets the top-most element in the layout hierarchy tree.
     *
     * @return The top-most node in the layout hierarchy.
     */
    abstract public Node getRootNode();

    /**
     * Sets the currently displayed view to parameter view.
     *
     * @param view The new view that is set as main view.
     */
    protected void setCurrentView(Node view) {
        Global.mainViewController.getRootNode().setCenter(view);
    }
}
