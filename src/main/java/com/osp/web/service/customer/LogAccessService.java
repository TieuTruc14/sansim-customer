package com.osp.web.service.customer;

import com.osp.common.PagingResult;
import com.osp.model.LogAccess;

import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 1/3/2018.
 */
public interface LogAccessService {
    boolean addLog(String action,String msisdn,String ipClient);
    boolean addLogWithUserId(Long userId,String action,String msisdn,String ipClient);
}
