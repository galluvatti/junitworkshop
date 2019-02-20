package com.iseed.paymentGateway;

import com.iseed.paymentGateway.circuits.Circuit;
import com.iseed.paymentGateway.circuits.CreditCardCircuit;
import com.iseed.paymentGateway.circuits.PaypalCircuit;
import com.iseed.paymentGateway.domain.Amount;
import com.iseed.paymentGateway.domain.Order;

import java.math.BigDecimal;
import java.util.Currency;

public class PaymentGateway {

    private PaypalCircuit paypalCircuit;
    private CreditCardCircuit creditCardCircuit;

    private static final String PAYMENT_ID = "123456";

    public PaymentGateway(PaypalCircuit paypalCircuit, CreditCardCircuit creditCardCircuit) {

        this.paypalCircuit = paypalCircuit;
        this.creditCardCircuit = creditCardCircuit;
    }

    public String pay(Amount amount, Order order, Circuit circuit) {
        if (amount.getAmount().compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Amount must be greater than 0");
        if (!amount.getCurrency().equals(Currency.getInstance("EUR")))
            throw new IllegalArgumentException("Only EUR currency is enabled for payments");
        if (order.getItems().isEmpty())
            throw new IllegalArgumentException("Order must contain at least one item");
        boolean success;
        if (circuit.equals(Circuit.PAYPAL))
            success = paypalCircuit.pay(amount);
        else
            success = creditCardCircuit.pay(amount);
        return success ? PAYMENT_ID : null;
    }


}
