package org.infokin.service;

import org.infokin.model.Sale;
import org.infokin.repository.SaleRepository;

import java.util.List;

/**
 * Service for {@link Sale} entities.
 */
public class SaleService {

    /*--------------
    | Repositories |
    --------------*/

    private SaleRepository saleRepository = new SaleRepository();

    /*---------
    | Methods |
    ---------*/

    /**
     * Saves a sale to the database.
     *
     * @param sale The sale that is going to be saved.
     * @return The completed sale that was saved to the database.
     */
    public Sale save(Sale sale) {
        Long id = saleRepository.save(sale);
        return saleRepository.get(id);
    }

    /**
     * Returns a list with every {@code Sale} in the database.
     *
     * @return A complete list of sales.
     */
    public List<Sale> getAllSales() {
        return saleRepository.getAll();
    }

    /**
     * Calculates the average selling price.
     *
     * @return The average selling price.
     */
    public Long calculateAverageSellingPrice() {
        Long result = 0L;

        List<Sale> sales = getAllSales();
        int count = sales.size();

        if (count > 0) {
            for (Sale sale : sales) {
                result += sale.getPrice();
            }

            result /= count;
        }

        return result;
    }
}
