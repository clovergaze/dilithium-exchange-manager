package org.infokin;

import javafx.application.Application;
import javafx.stage.Stage;
import org.infokin.controller.MainViewController;

/**
 * Container class for global constants and variables.
 */
public class Global {

	/*------------------
	| Global constants |
	------------------*/

    /**
     * Application title
     */
    public static final String APP_TITLE = "Dilithium Exchange Manager";

    /**
     * Main view resource
     */
    public static final String MAIN_VIEW_LAYOUT = "view/MainView.fxml";

    /*------------------
    | Global variables |
    ------------------*/

    /**
     * The application instance.
     */
    public static Application application = null;

    /**
     * The main window.
     */
    public static Stage primaryStage = null;

    /**
     * The main view controller with navigation elements.
     */
    public static MainViewController mainViewController = null;
}
