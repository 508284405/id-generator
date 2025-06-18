package com.leyue.id.customer;

import com.leyue.id.customer.mapper.CustomerMapper;
import com.leyue.id.domain.customer.Customer;
import com.leyue.id.domain.customer.gateway.CustomerGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerGatewayImpl implements CustomerGateway {
    @Autowired
    private CustomerMapper customerMapper;

    public Customer getByById(String customerId){
      CustomerDO customerDO = customerMapper.getById(customerId);
      //Convert to Customer
      return null;
    }
}
