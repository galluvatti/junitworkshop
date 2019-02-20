package com.iseed.paymentGateway.circuits;

import com.iseed.paymentGateway.domain.Amount;

public class PaypalCircuitFake implements PaypalCircuit{
    public boolean pay(Amount amount) {
        throw new IllegalStateException("Should not be used in a Unit test...USE MOCK INSTEAD!!!");
    }
}
