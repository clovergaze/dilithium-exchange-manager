package org.infokin.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import org.infokin.controller.api.Controller;
import org.infokin.model.Purchase;
import org.infokin.model.Sale;
import org.infokin.service.PurchaseService;
import org.infokin.service.SaleService;
import tornadofx.control.DateTimePicker;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

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

    //
    // Overview tab
    //

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

    //
    // Purchase tab
    //

    @FXML
    private TableView<Purchase> purchaseTable;

    @FXML
    private TableColumn<Purchase, Date> purchaseDateColumn;

    @FXML
    private TableColumn<Purchase, Integer> purchasePriceColumn;

    @FXML
    private TableColumn<Purchase, Long> purchaseAmountZenColumn;

    @FXML
    private TableColumn<Purchase, Long> purchaseAmountDilithiumColumn;

    @FXML
    private DateTimePicker purchaseDateTimePicker;

    @FXML
    private TextField purchasePrice;

    @FXML
    private TextField purchaseAmountZen;

    @FXML
    private TextField purchaseAmountDilithium;

    @FXML
    private Button addPurchaseButton;

    //
    // Sale tab
    //

    @FXML
    private TableView<Sale> saleTable;

    @FXML
    private TableColumn<Sale, Date> saleDateColumn;

    @FXML
    private TableColumn<Sale, Integer> salePriceColumn;

    @FXML
    private TableColumn<Sale, Long> saleAmountZenColumn;

    @FXML
    private TableColumn<Sale, Long> saleAmountDilithiumColumn;

    @FXML
    private DateTimePicker saleDateTimePicker;

    @FXML
    private TextField salePrice;

    @FXML
    private TextField saleAmountZen;

    @FXML
    private TextField saleAmountDilithium;

    @FXML
    private Button addSaleButton;

    /*----------
    | Services |
    ----------*/

    private PurchaseService purchaseService = new PurchaseService();
    private SaleService saleService = new SaleService();

    /*------------------
    | Member variables |
    ------------------*/

    /* ... */

    /*--------------------
    | Life cycle methods |
    --------------------*/

    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     */
    @FXML
    private void initialize() {
        purchaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        purchasePriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        purchaseAmountZenColumn.setCellValueFactory(new PropertyValueFactory<>("amountZen"));
        purchaseAmountDilithiumColumn.setCellValueFactory(new PropertyValueFactory<>("amountDilithium"));

        saleDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        salePriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        saleAmountZenColumn.setCellValueFactory(new PropertyValueFactory<>("amountZen"));
        saleAmountDilithiumColumn.setCellValueFactory(new PropertyValueFactory<>("amountDilithium"));
    }

    /*-------------------------
    | User interface handlers |
    -------------------------*/

    //
    // Main
    //

    @FXML
    private void handleCloseApplication() {
        Platform.exit();
        System.exit(0);
    }

    //
    // Overview tab
    //

    @FXML
    private void handleOverviewTabSelection() {
        List<Purchase> purchases = purchaseService.getAllPurchases();
        List<Sale> sales = saleService.getAllSales();

        Long zenBalance = calculateCurrentZenBalance(purchases, sales);
        Long dilithiumBalance = calculateCurrentDilithiumBalance(purchases, sales);

        overviewZENBalance.setText(zenBalance.toString() + " ZEN");
        overviewDilithiumBalance.setText(dilithiumBalance.toString() + " Dilithium");

        Long averageBuyingPrice = calculateAverageBuyingPrice(purchases);
        Long averageSellingPrice = calculateAverageSellingPrice(sales);

        Long averagePrice = (averageBuyingPrice + averageSellingPrice) / 2;

        overviewAverageBuyingPrice.setText(averageBuyingPrice + " Dilithium");
        overviewAverageSellingPrice.setText(averageSellingPrice + " Dilithium");
        overviewAveragePrice.setText(averagePrice + " Dilithium");
    }

    //
    // Purchase tab
    //

    @FXML
    private void handlePurchaseTabSelection() {
        resetPurchaseInterface();
        updatePurchasesTable();
    }

    @FXML
    private void handlePurchasePriceChanged() {
        updatePurchaseInterface();
    }

    @FXML
    private void handlePurchaseAmountChanged() {
        updatePurchaseInterface();
    }

    @FXML
    private void handleResetPurchaseButtonClick() {
        resetPurchaseInterface();
    }

    @FXML
    private void handleAddPurchaseButtonClick() {
        Purchase purchase = new Purchase();

        Date date = Date.from(purchaseDateTimePicker.getDateTimeValue().atZone(ZoneId.systemDefault()).toInstant());

        purchase.setDate(date);
        purchase.setPrice(Integer.parseUnsignedInt(purchasePrice.getText()));
        purchase.setAmountZen(Long.parseUnsignedLong(purchaseAmountZen.getText()));
        purchase.setAmountDilithium(Long.parseUnsignedLong(purchaseAmountDilithium.getText()));

        purchaseService.save(purchase);

        updatePurchasesTable();

        resetPurchaseInterface();
    }

    //
    // Sale tab
    //

    @FXML
    private void handleSaleTabSelection() {
        resetSaleInterface();
        updateSalesTable();
    }

    @FXML
    private void handleSalePriceChanged() {
        updateSaleInterface();
    }

    @FXML
    private void handleSaleAmountChanged() {
        updateSaleInterface();
    }

    @FXML
    private void handleResetSaleButtonClick() {
        resetSaleInterface();
    }

    @FXML
    private void handleAddSaleButtonClick() {
        Sale sale = new Sale();

        Date date = Date.from(saleDateTimePicker.getDateTimeValue().atZone(ZoneId.systemDefault()).toInstant());

        sale.setDate(date);
        sale.setPrice(Integer.parseUnsignedInt(salePrice.getText()));
        sale.setAmountZen(Long.parseUnsignedLong(saleAmountZen.getText()));
        sale.setAmountDilithium(Long.parseUnsignedLong(saleAmountDilithium.getText()));

        saleService.save(sale);

        updateSalesTable();

        resetSaleInterface();
    }

    /*---------
    | Methods |
    ---------*/

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
     * Calculates the average buying price from a list of purchases.
     *
     * @param purchases A list of purchases.
     * @return The average buying price.
     */
    private Long calculateAverageBuyingPrice(List<Purchase> purchases) {
        Long result = 0L;

        for (Purchase purchase : purchases) {
            result += purchase.getPrice();
        }

        result /= purchases.size();

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

        for (Sale sale : sales) {
            result += sale.getPrice();
        }

        result /= sales.size();

        return result;
    }

    /**
     * Sets current date and time to specified {@link DateTimePicker}.
     *
     * @param dateTimePicker The specified user interface element.
     */
    private void setCurrentDateTime(DateTimePicker dateTimePicker) {
        dateTimePicker.setDateTimeValue(LocalDateTime.now());
    }

    /**
     * Updates the specified user interface elements by calculating the amount of dilithium.
     *
     * @param priceField     The dilithium price field.
     * @param zenField       The ZEN field.
     * @param dilithiumField The amount of dilithium.
     * @param addButton      The add button reference for enabling and disabling the control element.
     */
    private void updateInterface(TextField priceField, TextField zenField, TextField dilithiumField, Button addButton) {
        Long dilithium = null;

        if (priceField.getLength() > 0 && zenField.getLength() > 0) {
            Long price = Long.parseUnsignedLong(priceField.getText());
            Long zen = Long.parseUnsignedLong(zenField.getText());

            dilithium = price * zen;
        }

        if (dilithium != null) {
            dilithiumField.setText(dilithium.toString());
            addButton.setDisable(false);
        } else {
            dilithiumField.setText("");
            addButton.setDisable(true);
        }
    }

    //
    // Purchase tab
    //

    private void updatePurchasesTable() {
        ObservableList<Purchase> purchases = FXCollections.observableArrayList(purchaseService.getAllPurchases());

        purchaseTable.setItems(purchases);
    }

    private void updatePurchaseDateTimePicker() {
        setCurrentDateTime(purchaseDateTimePicker);
    }

    private void updatePurchaseInterface() {
        updateInterface(purchasePrice, purchaseAmountZen, purchaseAmountDilithium, addPurchaseButton);
    }

    private void resetPurchaseInterface() {
        updatePurchaseDateTimePicker();

        purchasePrice.setText("");
        purchaseAmountZen.setText("");
        purchaseAmountDilithium.setText("");

        addPurchaseButton.setDisable(true);

        Platform.runLater(() -> purchasePrice.requestFocus());
    }

    //
    // Sale tab
    //

    private void updateSalesTable() {
        ObservableList<Sale> sales = FXCollections.observableArrayList(saleService.getAllSales());

        saleTable.setItems(sales);
    }

    private void updateSaleDateTimePicker() {
        setCurrentDateTime(saleDateTimePicker);
    }

    private void updateSaleInterface() {
        updateInterface(salePrice, saleAmountZen, saleAmountDilithium, addSaleButton);
    }

    private void resetSaleInterface() {
        updateSaleDateTimePicker();

        salePrice.setText("");
        saleAmountZen.setText("");
        saleAmountDilithium.setText("");

        addSaleButton.setDisable(true);

        Platform.runLater(() -> salePrice.requestFocus());
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
