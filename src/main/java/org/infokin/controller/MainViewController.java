package org.infokin.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import org.infokin.controller.api.Controller;

/**
 * Controller for main view.
 */
public class MainViewController extends Controller {

    /*---------------------------
    | User interface components |
    ---------------------------*/

    /**
     * The root node of this view.
     */
    @FXML
    private BorderPane rootNode;

    @FXML
    private OverviewViewController overviewViewController;

    @FXML
    private PurchasesViewController purchasesViewController;

    @FXML
    private SalesViewController salesViewController;

    /*--------------------
    | Life cycle methods |
    --------------------*/

    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /*-------------------------
    | User interface handlers |
    -------------------------*/

    @FXML
    private void handleCloseApplication() {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void handleOverviewTabSelection() {
        overviewViewController.updateInterface();
    }

    @FXML
    private void handlePurchaseTabSelection() {
        purchasesViewController.updateInterface();
    }

    @FXML
    private void handleSaleTabSelection() {
        salesViewController.updateInterface();
    }

    /*---------------------
    | Getters and Setters |
    ---------------------*/

    /**
     * {@inheritDoc}
     */
    @Override
    public BorderPane getRootNode() {
        return rootNode;
    }
}
