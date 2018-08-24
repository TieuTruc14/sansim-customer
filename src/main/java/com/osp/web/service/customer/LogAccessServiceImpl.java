package com.osp.web.service.customer;

import com.osp.model.Customer;
import com.osp.model.LogAccess;
import com.osp.web.dao.customer.LogAccessDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 1/3/2018.
 */
@Service
public class LogAccessServiceImpl implements LogAccessService {

    @Autowired
    LogAccessDao logAccessDao;

    @Override
    public boolean addLog(String action, String msisdn, String ipClient) {
        Customer user = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LogAccess log = new LogAccess();
        log.setActions(action);
        log.setMsisdn(msisdn);
        log.setCustomerId(user.getId());
        log.setIp(ipClient);
        log.setGenDate(new Date());
        return logAccessDao.add(log);
    }

    @Override
    public boolean addLogWithUserId(Long userId, String action, String msisdn, String ipClient) {
        LogAccess log = new LogAccess();
        log.setActions(action);
        log.setMsisdn(msisdn);
        log.setCustomerId(userId);
        log.setIp(ipClient);
        log.setGenDate(new Date());
        return logAccessDao.add(log);
    }


}
