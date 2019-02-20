package com.iseed.paymentGateway.circuits;

import com.iseed.paymentGateway.domain.Amount;

public interface CreditCardCircuit {

    /**
     *
     * @param amount
     * @return true if payment is successful, false if not
     */
    boolean pay(Amount amount);
}
