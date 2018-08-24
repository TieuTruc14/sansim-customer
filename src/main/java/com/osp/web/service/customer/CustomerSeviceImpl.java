package com.osp.web.service.customer;

import com.osp.web.dao.customer.CustomerDao;
import com.osp.model.Customer;
import com.osp.model.ForwardLog;
import com.osp.model.MOLog;
import com.osp.model.TranspayLog;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by Admin on 12/18/2017.
 */
@Service
public class CustomerSeviceImpl implements CustomerService {

    @Autowired
    CustomerDao customerDao;
    @Autowired
    BCryptPasswordEncoder encoder;

    public Customer getById(Long id) {
        Customer customer = customerDao.getById(id);
        return customer;
    }

    @Override
    public Optional<Customer> get(Long id) {
        return customerDao.get(id);
    }

    public boolean checkLogin(Customer user) {
        return customerDao.checkLogin(user);
    }

    public Customer getUserInfo(String value, String type) {
        Customer customer = customerDao.getUserInfo(value, type);
        return customer;
    }

    public boolean isExits(String value, String type) {
        boolean result = customerDao.isExits(value, type);
        return result;
    }

    public boolean insertCustomer(Customer user) {
        String pass = encoder.encode(user.getPassword());
        user.setPassword(pass);
        return customerDao.insertCustomer(user);
    }

    @Override
    public boolean updateCustomer(Customer user) {
        return customerDao.updateCustomer(user);
    }

    @Override
    public int countTotalTranspayLog(String packageName, String type, Date fromDate, Date toDate, Long userId) {
        int total = 0;
        total = customerDao.countTotalTranspayLog(packageName, type, fromDate, toDate, userId);
        return total;

    }

    @Override
    public List<TranspayLog> getPagingTranspayLog(int page, int limit, String packageName, String type, Date fromDate, Date toDate, Long userId) {
        List<TranspayLog> listResult = new ArrayList<TranspayLog>();
        listResult = customerDao.getPagingTranspayLog(page, limit, packageName, type, fromDate, toDate, userId);
        return listResult;
    }

    @Override
    public int countTotalSMSLog(String msisdn, Date fromDate, Date toDate, Long userId) {
        int total = 0;
        total = customerDao.countTotalSMSLog(msisdn, fromDate, toDate, userId);
        return total;
    }

    @Override
    public List<MOLog> getPagingSMSLog(int page, int limit, String msisdn, Date fromDate, Date toDate, Long userId) {
        List<MOLog> listResult = new ArrayList<MOLog>();
        listResult = customerDao.getPagingSMSLog(page, limit, msisdn, fromDate, toDate, userId);
        return listResult;
    }

    @Override
    public List<MOLog> getListConfirm(String msisdn, Long id) {
        List<MOLog> listResult = new ArrayList<MOLog>();
        listResult = customerDao.getListConfirm(msisdn, id);
        return listResult;
    }

    @Override
    public List<Customer> getCustomerWithUserName(String userName) {
        List<Customer> customers = new ArrayList<Customer>();
        customers = customerDao.getCustomerWithUserName(userName);
        return customers;
    }

    @Override
    public int countTotalForwardLog(String msisdn, Customer userName, Date fromDate, Date toDate, Long userId) {
        int total = 0;
        total = customerDao.countTotalForwardLog(msisdn, userName, fromDate, toDate, userId);
        return total;
    }

    @Override
    public List<ForwardLog> getPagingForwardLog(int page, int limit, String msisdn, Customer cusReceiver, Date fromDate, Date toDate, Long userId) {
        List<ForwardLog> listResult = new ArrayList<ForwardLog>();
        listResult = customerDao.getPagingForwardLog(page, limit, msisdn, cusReceiver, fromDate, toDate, userId);
        return listResult;
    }

    @Override
    public int countTotalReceiverLog(String msisdn, Customer cusSender, Date fromDate, Date toDate, Long userId) {
        int total = 0;
        total = customerDao.countTotalReceiverLog(msisdn, cusSender, fromDate, toDate, userId);
        return total;
    }

    @Override
    public List<ForwardLog> getPagingReceiverLog(int page, int limit, String msisdn, Customer cusSender, Date fromDate, Date toDate, Long userId) {
        List<ForwardLog> listResult = new ArrayList<ForwardLog>();
        listResult = customerDao.getPagingReceiverLog(page, limit, msisdn, cusSender, fromDate, toDate, userId);
        return listResult;
    }

    @Override
    public boolean insertForwardLog(ForwardLog forwardLog) {
        return customerDao.insertForwardLog(forwardLog);
    }

    @Override
    public Customer getShopManagerAccess(String host) {
        Customer shopManager = customerDao.getShopManagerAccess(host);
        return shopManager;
    }

    @Override
    public int editStatus(long id, Short status) {
        return customerDao.editStatus(id, status);
    }

    @Override
    public boolean isBlackList(String username) {
        boolean result = customerDao.isBlackList(username);
        return result;
    }

    @Override
    public boolean isExitDomain(String textdomain3, long id) {
        boolean result = customerDao.isExitDomain(textdomain3, id);
        return result;
    }

}
