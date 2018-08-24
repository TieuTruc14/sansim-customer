package com.osp.web.dao.group;

import com.osp.model.GroupMsisdn;
import com.osp.model.GroupPrice;
import com.osp.model.GroupYear;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 2/7/2018.
 */
@Repository
@Transactional
public class GroupTaskDaoImpl implements GroupTaskDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<List<GroupMsisdn>> listGroupMsisdn() {
        List<GroupMsisdn> items=entityManager.createQuery("Select gm from GroupMsisdn gm where gm.active=true order by gm.orderNumber",GroupMsisdn.class).getResultList();
        return Optional.ofNullable(items);
    }

    @Override
    public Optional<List<GroupPrice>> listGroupPrice() {
        List<GroupPrice> items=entityManager.createQuery("Select gm from GroupPrice gm where gm.active=true order by gm.orderNumber",GroupPrice.class).getResultList();
        return Optional.ofNullable(items);
    }

    @Override
    public Optional<List<GroupYear>> listGroupYear() {
        List<GroupYear> items=entityManager.createQuery("Select gm from GroupYear gm  where gm.active=true order by gm.orderNumber",GroupYear.class).getResultList();
        return Optional.ofNullable(items);
    }

    @Override
    public Optional<GroupPrice> getGroupPrice(Integer id) {
        GroupPrice item=entityManager.find(GroupPrice.class,id);
        return Optional.ofNullable(item);
    }

    @Override
    public Optional<GroupMsisdn> getGroupMsisdn(Long id) {
        GroupMsisdn item=entityManager.find(GroupMsisdn.class,id);
        return Optional.ofNullable(item);
    }

    @Override
    public Optional<GroupYear> getGroupYear(Integer id) {
        GroupYear item=entityManager.find(GroupYear.class,id);
        return Optional.ofNullable(item);
    }
}
