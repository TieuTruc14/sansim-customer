package com.osp.web.service.group;

import com.osp.model.GroupMsisdn;
import com.osp.model.GroupPrice;
import com.osp.model.GroupYear;

import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 2/7/2018.
 */
public interface GroupTaskService {
    Optional<List<GroupMsisdn>> listGroupMsisdn();
    Optional<List<GroupPrice>> listGroupPrice();
    Optional<List<GroupYear>> listGroupYear();
}
