package org.infokin.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import org.infokin.controller.api.Controller;
import org.infokin.model.Sale;
import org.infokin.service.PurchaseService;
import org.infokin.service.SaleService;
import tornadofx.control.DateTimePicker;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

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
        saleDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        salePriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        saleAmountZenColumn.setCellValueFactory(new PropertyValueFactory<>("amountZen"));
        saleAmountDilithiumColumn.setCellValueFactory(new PropertyValueFactory<>("amountDilithium"));
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
