package com.leyue.id.domain.customer.gateway;

import com.leyue.id.domain.customer.Credit;

//Assume that the credit info is in another distributed Service
public interface CreditGateway {
    Credit getCredit(String customerId);
}
