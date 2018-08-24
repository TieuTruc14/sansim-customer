package com.osp.web.dao.customer;

import com.osp.common.PagingResult;
import com.osp.common.QueryBuilder;
import com.osp.model.LogAccess;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 1/3/2018.
 */
@Repository
@Transactional
public class LogAccessDaoImpl implements LogAccessDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean add(LogAccess item) {
        boolean result = false;
        try {
            entityManager.persist(item);
            entityManager.flush();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
