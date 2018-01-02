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
import org.infokin.model.Sale;
import org.infokin.service.SaleService;
import tornadofx.control.DateTimePicker;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class SalesViewController extends Controller {

    /*---------------------------
    | User interface components |
    ---------------------------*/

    /**
     * The root node of this view.
     */
    @FXML
    private AnchorPane rootNode;

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

    private SaleService saleService = new SaleService();

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
    private void handleInputChanged() {
        updateInputInterface();
    }

    @FXML
    private void handleResetButtonClick() {
        resetInterface();
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

        updateInterface();
    }

    /*---------
    | Methods |
    ---------*/

    /**
     * Updates the sales table and resets the input interface.
     */
    public void updateInterface() {
        updateTable();
        resetInterface();
    }

    /**
     * Updates the sales table with current information.
     */
    private void updateTable() {
        ObservableList<Sale> sales = FXCollections.observableArrayList(saleService.getAllSales());

        saleTable.setItems(sales);
    }

    /**
     * Uses current time for element display.
     */
    private void updateDateTimePicker() {
        saleDateTimePicker.setDateTimeValue(LocalDateTime.now());
    }

    /**
     * Updates the input interface whenever input values change.
     */
    private void updateInputInterface() {
        Long dilithium = null;

        if (salePrice.getLength() > 0 && saleAmountZen.getLength() > 0) {
            Long price = Long.parseUnsignedLong(salePrice.getText());
            Long zen = Long.parseUnsignedLong(saleAmountZen.getText());

            dilithium = price * zen;
        }

        if (dilithium != null) {
            saleAmountDilithium.setText(dilithium.toString());
            addSaleButton.setDisable(false);
        } else {
            saleAmountDilithium.setText("");
            addSaleButton.setDisable(true);
        }
    }

    /**
     * Resets the input interface.
     */
    private void resetInterface() {
        updateDateTimePicker();

        salePrice.setText("");
        saleAmountZen.setText("");
        saleAmountDilithium.setText("");

        addSaleButton.setDisable(true);

        Platform.runLater(() -> salePrice.requestFocus());
    }

    /*---------------------
    | Getters and Setters |
    ---------------------*/

    @Override
    public Node getRootNode() {
        return rootNode;
    }
}
