package org.infokin.model;

import org.infokin.model.core.BaseEntity;

import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Base class for transactions.
 */
@MappedSuperclass
public class Transaction extends BaseEntity {

    /*------------------
    | Member variables |
    ------------------*/

    /**
     * Date and time of transaction.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    /**
     * Dilithium price per ZEN.
     */
    private Integer price;

    /**
     * Amount of ZEN.
     */
    private Long amountZen;

    /**
     * Amount of Dilithium
     */
    private Long amountDilithium;

    /*---------------------
    | Getters and Setters |
    ---------------------*/

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getAmountZen() {
        return amountZen;
    }

    public void setAmountZen(Long amountZen) {
        this.amountZen = amountZen;
    }

    public Long getAmountDilithium() {
        return amountDilithium;
    }

    public void setAmountDilithium(Long amountDilithium) {
        this.amountDilithium = amountDilithium;
    }
}
