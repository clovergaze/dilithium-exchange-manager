package org.infokin;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.LoadException;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.infokin.controller.MainViewController;
import org.infokin.util.HibernateUtility;
import org.infokin.util.LayoutLoader;

/**
 * Contains entry point and setup code of the application.
 */
public class Main extends Application {

    /**
     * Entry point of the application.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        // Instantiate Hibernate database
        HibernateUtility.init();

        // Launch JavaFX application
        launch(args);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Initializes and displays the primary stage.
     */
    @Override
    public void start(Stage primaryStage) {
        // Save application instance for global usage
        Global.application = this;

        // Save main window instance
        Global.primaryStage = primaryStage;

        try {
            // Load main layout
            Global.mainViewController = (MainViewController) LayoutLoader.loadLayout(Global.MAIN_VIEW_LAYOUT).getController();
        } catch (LoadException e) {
            e.printStackTrace();

            // Terminate application if the main view does not load
            Platform.exit();
            System.exit(0);
        }

        // Initialize scene using main view
        Scene scene = new Scene(Global.mainViewController.getRootNode());

        primaryStage.setScene(scene);
        primaryStage.setTitle(Global.APP_TITLE);

        // Display stage
        primaryStage.show();

        // Terminate application when main window closes
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
    }
}