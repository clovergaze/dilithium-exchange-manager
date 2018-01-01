package org.infokin.service;

import org.infokin.model.Purchase;
import org.infokin.repository.PurchaseRepository;

import java.util.List;

/**
 * Service for {@link Purchase} entities.
 */
public class PurchaseService {

    /*--------------
    | Repositories |
    --------------*/

    private PurchaseRepository purchaseRepository = new PurchaseRepository();

    /*---------
    | Methods |
    ---------*/

    /**
     * Saves a purchase to the database.
     *
     * @param purchase The purchase that is going to be saved.
     * @return The completed purchase that was saved to the database.
     */
    public Purchase save(Purchase purchase) {
        Long id = purchaseRepository.save(purchase);
        return purchaseRepository.get(id);
    }

    /**
     * Returns a list with every {@code Purchase} in the database.
     *
     * @return A complete list of purchases.
     */
    public List<Purchase> getAllPurchases() {
        return purchaseRepository.getAll();
    }
}
