/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.web.dao.confpackage;

import com.osp.model.ConfPackage;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Admin
 */
@Repository
@Transactional
public class ConfPackageDaoImpl implements ConfPackageDao {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ConfPackage> getListPackage() {
        List<ConfPackage> listResult = new ArrayList<ConfPackage>();
        try {
            StringBuffer sqlBuffer = new StringBuffer("SELECT o FROM ConfPackage o where status = 1 ");
            Query query = entityManager.createQuery(sqlBuffer.toString(), ConfPackage.class);
            listResult = query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listResult;
    }

    @Override
    public int editFee(long id, long fee) {
        return entityManager.createQuery("UPDATE ConfPackage set fee = :fee where id = :id").setParameter("fee", fee).setParameter("id", id).executeUpdate();
    }
}
