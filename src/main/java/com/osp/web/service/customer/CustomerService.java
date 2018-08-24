package com.osp.web.service.customer;

import com.osp.model.Customer;
import com.osp.model.ForwardLog;
import com.osp.model.MOLog;
import com.osp.model.TranspayLog;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 12/18/2017.
 */
public interface CustomerService {

    Customer getById(Long id);
    Optional<Customer> get(Long id);

    boolean checkLogin(Customer user);

    boolean isExits(String value, String type);

    Customer getUserInfo(String value, String type);

    boolean insertCustomer(Customer user);

    boolean updateCustomer(Customer user);

    int countTotalTranspayLog(String packageName, String type, Date fromDate, Date toDate, Long userId);

    List<TranspayLog> getPagingTranspayLog(int page, int limit, String packageName, String type, Date fromDate, Date toDate, Long userId);

    int countTotalSMSLog(String msisdn, Date fromDate, Date toDate, Long userId);

    List<MOLog> getPagingSMSLog(int page, int limit, String msisdn, Date fromDate, Date toDate, Long userId);

    List<MOLog> getListConfirm(String msisdn, Long id);

    List<Customer> getCustomerWithUserName(String userName);

    int countTotalForwardLog(String msisdn, Customer userName, Date fromDate, Date toDate, Long userId);

    List<ForwardLog> getPagingForwardLog(int page, int limit, String msisdn, Customer cusReceiver, Date fromDate, Date toDate, Long userId);

    int countTotalReceiverLog(String msisdn, Customer cusSender, Date fromDate, Date toDate, Long userId);

    List<ForwardLog> getPagingReceiverLog(int page, int limit, String msisdn, Customer cusSender, Date fromDate, Date toDate, Long userId);

    boolean insertForwardLog(ForwardLog forwardLog);

    Customer getShopManagerAccess(String host);
    
    int editStatus(long id, Short status);

    boolean isBlackList(String username);

    boolean isExitDomain(String textdomain3,long id);
}
