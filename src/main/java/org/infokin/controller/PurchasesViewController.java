package org.infokin.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.infokin.controller.api.Controller;
import org.infokin.model.Purchase;
import org.infokin.service.PurchaseService;
import tornadofx.control.DateTimePicker;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Controller for purchases view.
 */
public class PurchasesViewController extends Controller {

    /*---------------------------
    | User interface components |
    ---------------------------*/

    /**
     * The root node of this view.
     */
    @FXML
    private AnchorPane rootNode;

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

    /*----------
    | Services |
    ----------*/

    private PurchaseService purchaseService = new PurchaseService();

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
    }

    /*-------------------------
    | User interface handlers |
    -------------------------*/

    @FXML
    private void handleInputChanged() {
        updateInputInterface();
    }

    @FXML
    private void handleResetButtonClick() {
        resetInterface();
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

        updateInterface();
    }

    /*---------
    | Methods |
    ---------*/

    /**
     * Updates the purchases table and resets the input interface.
     */
    public void updateInterface() {
        updateTable();
        resetInterface();
    }

    /**
     * Updates the purchases table with current information.
     */
    private void updateTable() {
        ObservableList<Purchase> purchases = FXCollections.observableArrayList(purchaseService.getAllPurchases());

        purchaseTable.setItems(purchases);
    }

    /**
     * Uses current time for element display.
     */
    private void updateDateTimePicker() {
        purchaseDateTimePicker.setDateTimeValue(LocalDateTime.now());
    }

    /**
     * Updates the input interface whenever input values change.
     */
    private void updateInputInterface() {
        Long dilithium = null;

        if (purchasePrice.getLength() > 0 && purchaseAmountZen.getLength() > 0) {
            Long price = Long.parseUnsignedLong(purchasePrice.getText());
            Long zen = Long.parseUnsignedLong(purchaseAmountZen.getText());

            dilithium = price * zen;
        }

        if (dilithium != null) {
            purchaseAmountDilithium.setText(dilithium.toString());
            addPurchaseButton.setDisable(false);
        } else {
            purchaseAmountDilithium.setText("");
            addPurchaseButton.setDisable(true);
        }
    }

    /**
     * Resets the input interface.
     */
    private void resetInterface() {
        updateDateTimePicker();

        purchasePrice.setText("");
        purchaseAmountZen.setText("");
        purchaseAmountDilithium.setText("");

        addPurchaseButton.setDisable(true);

        Platform.runLater(() -> purchasePrice.requestFocus());
    }

    /*---------------------
    | Getters and Setters |
    ---------------------*/

    @Override
    public Node getRootNode() {
        return rootNode;
    }
}
