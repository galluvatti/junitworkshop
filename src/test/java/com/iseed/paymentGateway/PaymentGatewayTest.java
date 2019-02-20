package com.iseed.paymentGateway;

import com.iseed.paymentGateway.circuits.Circuit;
import com.iseed.paymentGateway.circuits.CreditCardCircuit;
import com.iseed.paymentGateway.circuits.PaypalCircuit;
import com.iseed.paymentGateway.domain.Amount;
import com.iseed.paymentGateway.domain.Order;
import com.iseed.paymentGateway.domain.OrderItem;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Currency;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PaymentGatewayTest {

    public static final Amount ONE_EURO = new Amount(BigDecimal.ONE, Currency.getInstance("EUR"));
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    private PaypalCircuit paypalCircuit = context.mock(PaypalCircuit.class);
    private CreditCardCircuit creditCardCircuit = context.mock(CreditCardCircuit.class);

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenAmountIsZero() {
        new PaymentGateway(paypalCircuit, creditCardCircuit)
                .pay(new Amount(BigDecimal.ZERO, Currency.getInstance("EUR")), new Order(Collections.EMPTY_LIST), Circuit.PAYPAL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenCurrencyIsNotEUR() {
        new PaymentGateway(paypalCircuit, creditCardCircuit)
                .pay(new Amount(BigDecimal.ONE, Currency.getInstance("USD")), new Order(Collections.EMPTY_LIST), Circuit.PAYPAL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenOrderIsEmpty() {
        new PaymentGateway(paypalCircuit, creditCardCircuit)
                .pay(new Amount(BigDecimal.ONE, Currency.getInstance("EUR")), new Order(Collections.EMPTY_LIST), Circuit.PAYPAL);
    }

    @Test
    public void shouldSuccessfullyPayOnPaypalCircuit() {
        context.checking(new Expectations() {{
            oneOf(paypalCircuit).pay(ONE_EURO);
            will(returnValue(true));
        }});
        String paymentId = new PaymentGateway(paypalCircuit, creditCardCircuit)
                .pay(ONE_EURO, new Order(Collections.singletonList(new OrderItem("CD", 1))), Circuit.PAYPAL);
        assertEquals("123456", paymentId);
    }

    @Test
    public void shouldReturnNullIfPayOnPaypalCircuitFails() {
        context.checking(new Expectations() {{
            oneOf(paypalCircuit).pay(ONE_EURO);
            will(returnValue(false));
        }});
        String paymentId = new PaymentGateway(paypalCircuit, creditCardCircuit)
                .pay(ONE_EURO, new Order(Collections.singletonList(new OrderItem("CD", 1))), Circuit.PAYPAL);
        assertNull(paymentId);
    }

    @Test
    public void shouldSuccessfullyPayOnCreditCardCircuit() {
        context.checking(new Expectations() {{
            oneOf(creditCardCircuit).pay(ONE_EURO);
            will(returnValue(true));
        }});
        String paymentId = new PaymentGateway(paypalCircuit, creditCardCircuit)
                .pay(ONE_EURO, new Order(Collections.singletonList(new OrderItem("CD", 1))), Circuit.CREDIT_CARD);
        assertEquals("123456", paymentId);
    }

    @Test
    public void shouldReturnNullIfPayOnCreditCardCircuitFails() {
        context.checking(new Expectations() {{
            oneOf(creditCardCircuit).pay(ONE_EURO);
            will(returnValue(false));
        }});
        String paymentId = new PaymentGateway(paypalCircuit, creditCardCircuit)
                .pay(ONE_EURO, new Order(Collections.singletonList(new OrderItem("CD", 1))), Circuit.CREDIT_CARD);
        assertNull(paymentId);
    }

}