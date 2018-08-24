package com.osp.web.dao.group;

import com.osp.model.GroupMsisdn;
import com.osp.model.GroupPrice;
import com.osp.model.GroupYear;

import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 2/7/2018.
 */
public interface GroupTaskDao {
    Optional<List<GroupMsisdn>> listGroupMsisdn();
    Optional<List<GroupPrice>> listGroupPrice();
    Optional<List<GroupYear>> listGroupYear();
    Optional<GroupPrice> getGroupPrice(Integer id);
    Optional<GroupMsisdn> getGroupMsisdn(Long id);
    Optional<GroupYear> getGroupYear(Integer id);
}
