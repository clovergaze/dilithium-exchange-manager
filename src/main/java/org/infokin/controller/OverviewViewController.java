package org.infokin.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.infokin.controller.api.Controller;
import org.infokin.service.BalanceService;
import org.infokin.service.PurchaseService;
import org.infokin.service.SaleService;

/**
 * Controller for the overview view.
 */
public class OverviewViewController extends Controller {

    /*---------------------------
    | User interface components |
    ---------------------------*/

    /**
     * The root node of this view.
     */
    @FXML
    private AnchorPane rootNode;

    @FXML
    private Label zenBalanceLabel;

    @FXML
    private Label averageBuyingPriceLabel;

    @FXML
    private Label averageSellingPriceLabel;

    @FXML
    private Label averagePriceLabel;

    /*----------
    | Services |
    ----------*/

    private PurchaseService purchaseService = new PurchaseService();
    private SaleService saleService = new SaleService();
    private BalanceService balanceService = new BalanceService();

    /*---------
    | Methods |
    ---------*/

    /**
     * Updates the view using current information.
     */
    public void updateInterface() {
        // Calculate balances
        Long zenBalance = balanceService.calculateCurrentZenBalance();

        zenBalanceLabel.setText(zenBalance.toString() + " ZEN");

        // Calculate average prices
        Long averageBuyingPrice = purchaseService.calculateAverageBuyingPrice();
        Long averageSellingPrice = saleService.calculateAverageSellingPrice();

        Long averagePrice = (averageBuyingPrice + averageSellingPrice) / 2;

        averageBuyingPriceLabel.setText(averageBuyingPrice + " Dilithium");
        averageSellingPriceLabel.setText(averageSellingPrice + " Dilithium");
        averagePriceLabel.setText(averagePrice + " Dilithium");
    }

    /*---------------------
    | Getters and Setters |
    ---------------------*/

    @Override
    public Node getRootNode() {
        return rootNode;
    }
}
