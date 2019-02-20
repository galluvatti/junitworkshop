package com.iseed.paymentGateway.domain;

import java.util.Collections;
import java.util.List;

public class Order {
    private List<OrderItem> items;

    public Order(List<OrderItem> items) {
        if (null != items)
            this.items = items;
        else this.items = Collections.EMPTY_LIST;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }
}
