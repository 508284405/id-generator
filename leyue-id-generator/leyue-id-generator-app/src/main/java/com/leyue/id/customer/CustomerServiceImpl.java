package com.leyue.id.customer;

import com.alibaba.cola.catchlog.CatchAndLog;
import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.leyue.id.api.CustomerServiceI;
import com.leyue.id.customer.executor.CustomerAddCmdExe;
import com.leyue.id.customer.executor.query.CustomerListByNameQryExe;
import com.leyue.id.dto.CustomerAddCmd;
import com.leyue.id.dto.CustomerListByNameQry;
import com.leyue.id.dto.data.CustomerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@CatchAndLog
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerServiceI {

    private final CustomerAddCmdExe customerAddCmdExe;

    private final CustomerListByNameQryExe customerListByNameQryExe;

    public Response addCustomer(CustomerAddCmd customerAddCmd) {
        return customerAddCmdExe.execute(customerAddCmd);
    }

    @Override
    public MultiResponse<CustomerDTO> listByName(CustomerListByNameQry customerListByNameQry) {
        return customerListByNameQryExe.execute(customerListByNameQry);
    }

}