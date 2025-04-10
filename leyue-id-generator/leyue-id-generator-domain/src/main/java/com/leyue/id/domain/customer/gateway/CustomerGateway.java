package com.leyue.id.domain.customer.gateway;

import com.leyue.id.domain.customer.Customer;

public interface CustomerGateway {
    Customer getByById(String customerId);
}
