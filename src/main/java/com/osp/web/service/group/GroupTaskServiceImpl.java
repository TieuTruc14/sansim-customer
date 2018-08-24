package com.osp.web.service.group;

import com.osp.model.GroupMsisdn;
import com.osp.model.GroupPrice;
import com.osp.model.GroupYear;
import com.osp.web.dao.group.GroupTaskDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 2/7/2018.
 */
@Service
public class GroupTaskServiceImpl implements GroupTaskService {
    @Autowired
    GroupTaskDao groupTaskDao;

    @Override
    public Optional<List<GroupMsisdn>> listGroupMsisdn() {
        return groupTaskDao.listGroupMsisdn();
    }

    @Override
    public Optional<List<GroupPrice>> listGroupPrice() {
        return groupTaskDao.listGroupPrice();
    }

    @Override
    public Optional<List<GroupYear>> listGroupYear() {
        return groupTaskDao.listGroupYear();
    }
}
