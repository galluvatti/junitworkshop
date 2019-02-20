package com.iseed.paymentGateway.domain;

import java.math.BigDecimal;
import java.util.Currency;

public class Amount {

    private BigDecimal amount;
    private Currency currency;

    public Amount(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }
}
