package org.infokin.service;

import org.infokin.model.Purchase;
import org.infokin.model.Sale;

import java.util.List;

/**
 * Provides balance related methods.
 */
public class BalanceService {

    /*----------
    | Services |
    ----------*/

    private PurchaseService purchaseService = new PurchaseService();
    private SaleService saleService = new SaleService();

    /*---------
    | Methods |
    ---------*/

    /**
     * Calculates the current ZEN balance based on all purchases and sales.
     *
     * @return The current amount of ZEN.
     */
    public Long calculateCurrentZenBalance() {
        Long result = 0L;

        List<Purchase> purchases = purchaseService.getAllPurchases();
        List<Sale> sales = saleService.getAllSales();

        for (Purchase purchase : purchases) {
            result += purchase.getAmountZen();
        }

        for (Sale sale : sales) {
            result -= sale.getAmountZen();
        }

        return result;
    }
}
