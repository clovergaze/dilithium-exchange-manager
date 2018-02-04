package org.infokin.util;

import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import org.apache.commons.lang3.Validate;
import org.infokin.Global;
import org.infokin.controller.api.Controller;

import java.net.URL;

/**
 * Helper class for loading a layout resource.
 */
public class LayoutLoader {

    /**
     * Nested class that serves as a data transport object for a
     * view and controller pair.
     */
    public static class ViewControllerPair {
        private final Object view;
        private final Controller controller;

        private ViewControllerPair(Object view, Controller controller) {
            this.view = view;
            this.controller = controller;
        }

        public Object getView() {
            return view;
        }

        public Controller getController() {
            return controller;
        }
    }

    /**
     * Loads a layout resource and returns its view and controller.
     *
     * @param resourceName Name of the layout resource
     * @return Transport object containing the view and its controller.
     * @throws LoadException Exception indicating an error while loading the resource.
     */
    public static ViewControllerPair loadLayout(String resourceName) throws LoadException {

        // Validate preconditions
        Validate.notNull(resourceName, "Resource name must not be null.");
        Validate.notEmpty(resourceName, "Resource name must not be empty.");

        // Locate resource relative to main application class path
        URL location = Global.application.getClass().getResource(resourceName);

        if (location == null) {
            throw new LoadException("Error loading resource");
        }

        // Load resource
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(location);

        Object view;

        try {
            view = loader.load();
        } catch (Exception e) {
            throw new LoadException("Error loading resource", e);
        }

        // Get controller
        Controller controller = loader.getController();

        return new ViewControllerPair(view, controller);
    }
}
