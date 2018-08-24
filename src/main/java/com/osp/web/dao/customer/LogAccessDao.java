package com.osp.web.dao.customer;

import com.osp.common.PagingResult;
import com.osp.model.LogAccess;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 1/3/2018.
 */
public interface LogAccessDao {
    boolean add(LogAccess item);
}
