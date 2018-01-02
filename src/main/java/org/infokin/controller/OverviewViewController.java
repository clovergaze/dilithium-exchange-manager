package org.infokin.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.infokin.controller.api.Controller;
import org.infokin.model.Purchase;
import org.infokin.model.Sale;
import org.infokin.service.PurchaseService;
import org.infokin.service.SaleService;

import java.util.List;

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

    /*---------
    | Methods |
    ---------*/

    /**
     * Updates the view using current information.
     */
    public void updateInterface() {
        // Get list of all purchases and sales
        List<Purchase> purchases = purchaseService.getAllPurchases();
        List<Sale> sales = saleService.getAllSales();

        // Calculate balances
        Long zenBalance = calculateCurrentZenBalance(purchases, sales);

        zenBalanceLabel.setText(zenBalance.toString() + " ZEN");

        // Calculate average prices
        Long averageBuyingPrice = purchaseService.calculateAverageBuyingPrice();
        Long averageSellingPrice = saleService.calculateAverageSellingPrice();

        Long averagePrice = (averageBuyingPrice + averageSellingPrice) / 2;

        averageBuyingPriceLabel.setText(averageBuyingPrice + " Dilithium");
        averageSellingPriceLabel.setText(averageSellingPrice + " Dilithium");
        averagePriceLabel.setText(averagePrice + " Dilithium");
    }

    /**
     * Calculates the current ZEN balance.
     *
     * @param purchases A list of purchases.
     * @param sales     A list of sales.
     * @return The current amount of ZEN.
     */
    private Long calculateCurrentZenBalance(List<Purchase> purchases, List<Sale> sales) {
        Long result = 0L;

        for (Purchase purchase : purchases) {
            result += purchase.getAmountZen();
        }

        for (Sale sale : sales) {
            result -= sale.getAmountZen();
        }

        return result;
    }

    /*---------------------
    | Getters and Setters |
    ---------------------*/

    @Override
    public Node getRootNode() {
        return rootNode;
    }
}
