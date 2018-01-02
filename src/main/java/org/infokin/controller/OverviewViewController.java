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
    private Label overviewZENBalance;

    @FXML
    private Label overviewDilithiumBalance;

    @FXML
    private Label overviewAverageBuyingPrice;

    @FXML
    private Label overviewAverageSellingPrice;

    @FXML
    private Label overviewAveragePrice;

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
        Long dilithiumBalance = calculateCurrentDilithiumBalance(purchases, sales);

        overviewZENBalance.setText(zenBalance.toString() + " ZEN");
        overviewDilithiumBalance.setText(dilithiumBalance.toString() + " Dilithium");

        // Calculate average prices
        Long averageBuyingPrice = purchaseService.calculateAverageBuyingPrice();
        Long averageSellingPrice = calculateAverageSellingPrice(sales);

        Long averagePrice = (averageBuyingPrice + averageSellingPrice) / 2;

        overviewAverageBuyingPrice.setText(averageBuyingPrice + " Dilithium");
        overviewAverageSellingPrice.setText(averageSellingPrice + " Dilithium");
        overviewAveragePrice.setText(averagePrice + " Dilithium");
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

    /**
     * Calculates the Dilithium balance.
     *
     * @param purchases A list of purchases.
     * @param sales     A list of sales.
     * @return The current amount of Dilithium.
     */
    private Long calculateCurrentDilithiumBalance(List<Purchase> purchases, List<Sale> sales) {
        Long result = 0L;

        for (Purchase purchase : purchases) {
            result += purchase.getAmountDilithium();
        }

        for (Sale sale : sales) {
            result -= sale.getAmountDilithium();
        }

        return result;
    }

    /**
     * Calculates the average selling price from a list of sales.
     *
     * @param sales A list of sales.
     * @return The average selling price.
     */
    private Long calculateAverageSellingPrice(List<Sale> sales) {
        Long result = 0L;
        int count = sales.size();

        if (count > 0) {
            for (Sale sale : sales) {
                result += sale.getPrice();
            }

            result /= count;
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
