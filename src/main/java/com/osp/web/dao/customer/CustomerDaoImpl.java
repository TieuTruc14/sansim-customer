package com.osp.web.dao.customer;

import com.osp.common.QueryBuilder;
import com.osp.model.Customer;
import com.osp.model.ForwardLog;
import com.osp.model.MOLog;
import com.osp.model.TranspayLog;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by Admin on 12/18/2017.
 */
@Repository
@Transactional
public class CustomerDaoImpl implements CustomerDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Customer getById(Long id) {
        Customer customer = entityManager.find(Customer.class, id);
        return customer;
    }

    @Override
    public Optional<Customer> get(Long id) {
        return Optional.ofNullable(entityManager.find(Customer.class, id));
    }

    public boolean checkLogin(Customer user) {
        boolean result = true;
        try {
//			StringBuffer sqlbuffer = new StringBuffer("select count(o) from Customer o");
//			user.setPassword(Utils.md5(user.getPassword()));
//			QueryBuilder builder = new QueryBuilder(em, sqlbuffer);
//			builder.and(QueryBuilder.EQ, "o.msisdn", user.getMsisdn().trim());
//			builder.and(QueryBuilder.EQ, "o.password", user.getPassword().trim());
//			 = builder.initQuery();

            Query query = entityManager.createQuery("select count(o) from Customer o where o.msisdn = :msisdn and o.password = :password ");
            query.setParameter("msisdn", user.getUsername());
            query.setParameter("password", user.getPassword());

            List list = query.getResultList();
            int count = ((Long) list.get(0)).intValue();
            if (count == 0) {
                result = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public Customer getUserInfo(String value, String type) {
        Customer result = null;
        try {
            StringBuffer sqlbuffer = new StringBuffer();

            if (type.equalsIgnoreCase("msisdn")) {
                sqlbuffer = new StringBuffer("select o from Customer o where o.msisdn = :msisdn");
            } else if (type.equalsIgnoreCase("username")) {
                sqlbuffer = new StringBuffer("select o from Customer o where o.username = :username");
            }

            Query query = entityManager.createQuery(sqlbuffer.toString(), Customer.class);
            query.setParameter(type, value);
            result = (Customer) query.getSingleResult();
        } catch (Exception ex) {
            System.out.println("No result found " + ex.getMessage());
        }
        return result;
    }

    public boolean isExits(String value, String type) {
        boolean result = true;
        try {
            StringBuffer sqlbuffer = new StringBuffer();

            if (type.equalsIgnoreCase("msisdn")) {
                sqlbuffer = new StringBuffer("select count(o) from Customer o where o.msisdn = :msisdn");
            } else if (type.equalsIgnoreCase("username")) {
                sqlbuffer = new StringBuffer("select count(o) from Customer o where UPPER(o.username) = UPPER(:username)");
            }

            Query query = entityManager.createQuery(sqlbuffer.toString());
            query.setParameter(type, value);
            List list = query.getResultList();
            int count = ((Long) list.get(0)).intValue();
            if (count == 0) {
                result = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean insertCustomer(Customer user) {
        boolean result = false;
        try {
            user.setActive(0);
            user.setGen_date(new Date());
            user.setDomain(user.getUsername().toLowerCase() + ".sansim.vn");
            entityManager.persist(user);
            entityManager.flush();

            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean updateCustomer(Customer user) {
        boolean result = false;
        try {
            entityManager.merge(user);
            entityManager.flush();
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public int countTotalTranspayLog(String packageName, String type, Date fromDate, Date toDate, Long userId) {
        int result = 0;
        StringBuffer sqlBuffer = new StringBuffer("SELECT Count(o) FROM TranspayLog o, ConfPackage p ");
        Query query = filterBuilderSingle(sqlBuffer, null, packageName, type, fromDate, toDate, userId);
        List list = query.getResultList();
        result = ((Long) list.get(0)).intValue();
        return result;
    }

    @Override
    public List<TranspayLog> getPagingTranspayLog(int page, int limit, String packageName, String type, Date fromDate, Date toDate, Long userId) {
        List<TranspayLog> listResult = new ArrayList<TranspayLog>();
        try {
            int start = page * limit - limit;
            StringBuffer sqlBuffer = new StringBuffer("SELECT o FROM TranspayLog o , ConfPackage p ");
            Query query = filterBuilderSingle(sqlBuffer, TranspayLog.class, packageName, type, fromDate, toDate, userId);
            listResult = query.setFirstResult(start).setMaxResults(limit).getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return listResult;
    }

    private Query filterBuilderSingle(StringBuffer stringBuffer, Class cls, String packageName, String type, Date fromDate, Date toDate, Long userId) {
        Query result = null;
        try {
            QueryBuilder builder = new QueryBuilder(entityManager, stringBuffer);
            builder.sqlManual(" AND o.confPackage.id = p.id ");

            if (userId != 0) {
                Customer cus = new Customer();
                cus.setId(userId);
                builder.and(QueryBuilder.EQ, "o.customer", cus);
            }
            if (packageName != null && !packageName.isEmpty()) {
                builder.and(QueryBuilder.LIKE, "UPPER(p.packageName)", "%" + packageName.trim().toUpperCase() + "%");
            }
            if (type != null && !type.isEmpty() && !type.equals("-1")) {
                builder.and(QueryBuilder.EQ, "o.type", new Byte(type));
            }
            if (fromDate != null) {
                builder.and(QueryBuilder.GE, "o.genDate", fromDate);
            }
            if (toDate != null) {
                builder.and(QueryBuilder.LE, "o.genDate", toDate);
            }

            builder.addOrder("o.genDate", QueryBuilder.DESC);
            if (cls != null) {
                result = builder.initQuery(cls);
            } else {
                result = builder.initQuery();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public int countTotalSMSLog(String msisdn, Date fromDate, Date toDate, Long userId) {
        int result = 0;
        StringBuffer sqlBuffer = new StringBuffer("SELECT count(o) from MOLog o left JOIN MTLog mt ON o.requestId=mt.requestId ");
        Query query = null;
        QueryBuilder builder = new QueryBuilder(entityManager, sqlBuffer);
        if (userId != 0) {
            Customer cus = new Customer();
            cus.setId(userId);
            builder.and(QueryBuilder.EQ, "o.customer", cus);
        }
        if (msisdn != null && !msisdn.isEmpty()) {
            msisdn = msisdn.trim();
            if (msisdn.startsWith("84")) {
                msisdn = "0" + msisdn.substring(2);
            }
            builder.and(QueryBuilder.EQ, "o.senderNumber", msisdn.trim());
        }
        if (fromDate != null) {
            builder.and(QueryBuilder.GE, "o.genDate", fromDate);
        }
        if (toDate != null) {
            builder.and(QueryBuilder.LE, "o.genDate", toDate);
        }
        query = builder.initQuery();
        List list = query.getResultList();
        result = ((Long) list.get(0)).intValue();
        return result;
    }

    @Override
    public List<MOLog> getPagingSMSLog(int page, int limit, String msisdn, Date fromDate, Date toDate, Long userId) {
        List<MOLog> listResult = new ArrayList<MOLog>();
        StringBuffer sqlBuffer = new StringBuffer("SELECT mo.id,mo.genDate,mo.senderNumber,mo.mobileOperator,mo.info,mt.info, mo.serviceNumber "
                + " from MOLog mo LEFT JOIN MTLog mt ON mo.requestId=mt.requestId ");
        Query query = null;
        int start = page * limit - limit;
        QueryBuilder builder = new QueryBuilder(entityManager, sqlBuffer);
        if (userId != 0) {
            Customer cus = new Customer();
            cus.setId(userId);
            builder.and(QueryBuilder.EQ, "mo.customer", cus);
        }
        if (msisdn != null && !msisdn.isEmpty()) {
            msisdn = msisdn.trim();
            if (msisdn.startsWith("84")) {
                msisdn = "0" + msisdn.substring(2);
            }
            builder.and(QueryBuilder.EQ, "mo.senderNumber", msisdn.trim());
        }
        if (fromDate != null) {
            builder.and(QueryBuilder.GE, "mo.genDate", fromDate);
        }
        if (toDate != null) {
            builder.and(QueryBuilder.LE, "mo.genDate", toDate);
        }
//        builder.addOrder("mo.genDate", QueryBuilder.DESC);
        builder.sqlManual(" order by mo.genDate DESC ");
        query = builder.initQuery();
        List<Object[]> rs = query.setFirstResult(start).setMaxResults(limit).getResultList();
        if (rs != null && rs.size() > 0) {
            for (Object[] r : rs) {
                MOLog moLog = new MOLog();
                moLog.setId((Long) r[0]);
                moLog.setGenDate((Date) r[1]);
                moLog.setSenderNumber((String) r[2]);
                moLog.setMobileOperator((String) r[3]);
                moLog.setInfo((String) r[4]);
                moLog.setMt((String) r[5]);
                moLog.setServiceNumber((String) r[6]);
                listResult.add(moLog);

            }
        }

        return listResult;

    }

    @Override
    public List<MOLog> getListConfirm(String msisdn, Long userId) {
        List<MOLog> listResult = new ArrayList<MOLog>();
        StringBuffer sqlBuffer = new StringBuffer("SELECT mo.id,mo.genDate,mo.senderNumber,mo.mobileOperator,mo.info,mt.info, mo.serviceNumber "
                + " from MOLog mo LEFT JOIN MTLog mt ON mo.requestId=mt.requestId ");
        Query query = null;
        QueryBuilder builder = new QueryBuilder(entityManager, sqlBuffer);
        builder.sqlManual(" AND mo.commandCode ='XTH' ");
        if (userId != 0) {
            Customer cus = new Customer();
            cus.setId(userId);
            builder.and(QueryBuilder.EQ, "mo.customer", cus);
        }
        if (msisdn != null && !msisdn.isEmpty()) {
            msisdn = msisdn.trim();
            if (msisdn.startsWith("84")) {
                msisdn = "0" + msisdn.substring(2);
            }
            builder.and(QueryBuilder.EQ, "mo.senderNumber", msisdn.trim());
        }
        builder.sqlManual(" order by mo.genDate DESC ");
        query = builder.initQuery();
        List<Object[]> rs = query.getResultList();
        if (rs != null && rs.size() > 0) {
            for (Object[] r : rs) {
                MOLog moLog = new MOLog();
                moLog.setId((Long) r[0]);
                moLog.setGenDate((Date) r[1]);
                moLog.setSenderNumber((String) r[2]);
                moLog.setMobileOperator((String) r[3]);
                moLog.setInfo((String) r[4]);
                moLog.setMt((String) r[5]);
                moLog.setServiceNumber((String) r[6]);
                listResult.add(moLog);

            }
        }
        return listResult;
    }

    @Override
    public List<Customer> getCustomerWithUserName(String userName) {
        List<Customer> results = null;
        try {
            StringBuffer sqlbuffer = new StringBuffer();
            sqlbuffer = new StringBuffer("select o from Customer o where o.username like :username and active = 1");
            Query query = entityManager.createQuery(sqlbuffer.toString(), Customer.class);
            query.setParameter("username", "%" + userName + "%");
            results = query.getResultList();
        } catch (Exception ex) {
            System.out.println("No result found " + ex.getMessage());
        }
        return results;
    }

    @Override
    public int countTotalForwardLog(String msisdn, Customer userName, Date fromDate, Date toDate, Long userId) {
        int result = 0;
        StringBuffer sqlBuffer = new StringBuffer("SELECT count(o) from ForwardLog o  ");
        Query query = null;
        QueryBuilder builder = new QueryBuilder(entityManager, sqlBuffer);
        if (userId != 0) {
            Customer cus = new Customer();
            cus.setId(userId);
            builder.and(QueryBuilder.EQ, "o.customerSender", cus);
        }
        if (userName != null) {
            builder.and(QueryBuilder.EQ, "o.customerReceiver", userName);
        }
        if (msisdn != null && !msisdn.isEmpty()) {
            msisdn = msisdn.trim();
            if (msisdn.startsWith("84")) {
                msisdn = "0" + msisdn.substring(2);
            }
            builder.and(QueryBuilder.LIKE, "o.msisdn", "%" + msisdn.trim() + "%");
        }
        if (fromDate != null) {
            builder.and(QueryBuilder.GE, "o.genDate", fromDate);
        }
        if (toDate != null) {
            builder.and(QueryBuilder.LE, "o.genDate", toDate);
        }
        query = builder.initQuery();
        List list = query.getResultList();
        result = ((Long) list.get(0)).intValue();
        return result;
    }

    @Override
    public List<ForwardLog> getPagingForwardLog(int page, int limit, String msisdn, Customer cusReceiver, Date fromDate, Date toDate, Long userId) {
        List<ForwardLog> listResult = new ArrayList<ForwardLog>();
        StringBuffer sqlBuffer = new StringBuffer("SELECT o from ForwardLog o ");
        Query query = null;
        int start = page * limit - limit;
        QueryBuilder builder = new QueryBuilder(entityManager, sqlBuffer);
        if (userId != 0) {
            Customer cus = new Customer();
            cus.setId(userId);
            builder.and(QueryBuilder.EQ, "o.customerSender", cus);
        }
        if (cusReceiver != null) {
            builder.and(QueryBuilder.EQ, "o.customerReceiver", cusReceiver);
        }
        if (msisdn != null && !msisdn.isEmpty()) {
            msisdn = msisdn.trim();
            if (msisdn.startsWith("84")) {
                msisdn = "0" + msisdn.substring(2);
            }
            builder.and(QueryBuilder.LIKE, "o.msisdn", "%" + msisdn.trim() + "%");
        }
        if (fromDate != null) {
            builder.and(QueryBuilder.GE, "o.genDate", fromDate);
        }
        if (toDate != null) {
            builder.and(QueryBuilder.LE, "o.genDate", toDate);
        }

        builder.sqlManual(" order by o.genDate DESC ");
        query = builder.initQuery(ForwardLog.class);
        listResult = query.setFirstResult(start).setMaxResults(limit).getResultList();

        return listResult;
    }

    @Override
    public int countTotalReceiverLog(String msisdn, Customer cusSender, Date fromDate, Date toDate, Long userId) {
        int result = 0;
        StringBuffer sqlBuffer = new StringBuffer("SELECT count(o) from ForwardLog o  ");
        Query query = null;
        QueryBuilder builder = new QueryBuilder(entityManager, sqlBuffer);
        if (userId != 0) {
            Customer cus = new Customer();
            cus.setId(userId);
            builder.and(QueryBuilder.EQ, "o.customerReceiver", cus);
        }
        if (cusSender != null) {
            builder.and(QueryBuilder.EQ, "o.customerSender", cusSender);
        }
        if (msisdn != null && !msisdn.isEmpty()) {
            msisdn = msisdn.trim();
            if (msisdn.startsWith("84")) {
                msisdn = "0" + msisdn.substring(2);
            }
            builder.and(QueryBuilder.LIKE, "o.msisdn", "%" + msisdn.trim() + "%");
        }
        if (fromDate != null) {
            builder.and(QueryBuilder.GE, "o.genDate", fromDate);
        }
        if (toDate != null) {
            builder.and(QueryBuilder.LE, "o.genDate", toDate);
        }
        query = builder.initQuery();
        List list = query.getResultList();
        result = ((Long) list.get(0)).intValue();
        return result;
    }

    @Override
    public List<ForwardLog> getPagingReceiverLog(int page, int limit, String msisdn, Customer cusSender, Date fromDate, Date toDate, Long userId) {
        List<ForwardLog> listResult = new ArrayList<ForwardLog>();
        StringBuffer sqlBuffer = new StringBuffer("SELECT o from ForwardLog o ");
        Query query = null;
        int start = page * limit - limit;
        QueryBuilder builder = new QueryBuilder(entityManager, sqlBuffer);
        if (userId != 0) {
            Customer cus = new Customer();
            cus.setId(userId);
            builder.and(QueryBuilder.EQ, "o.customerReceiver", cus);
        }
        if (cusSender != null) {
            builder.and(QueryBuilder.EQ, "o.customerSender", cusSender);
        }
        if (msisdn != null && !msisdn.isEmpty()) {
            msisdn = msisdn.trim();
            if (msisdn.startsWith("84")) {
                msisdn = "0" + msisdn.substring(2);
            }
            builder.and(QueryBuilder.LIKE, "o.msisdn", "%" + msisdn.trim() + "%");
        }
        if (fromDate != null) {
            builder.and(QueryBuilder.GE, "o.genDate", fromDate);
        }
        if (toDate != null) {
            builder.and(QueryBuilder.LE, "o.genDate", toDate);
        }

        builder.sqlManual(" order by o.genDate DESC ");
        query = builder.initQuery(ForwardLog.class);
        listResult = query.setFirstResult(start).setMaxResults(limit).getResultList();

        return listResult;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean insertForwardLog(ForwardLog forwardLog) {
        boolean result = false;
        try {
            forwardLog.setGenDate(new Date());
            entityManager.persist(forwardLog);
            entityManager.flush();
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public int editStatus(long id, Short status) {
        return entityManager.createQuery("UPDATE StockMsisdn set status = :status where id = :id and rownum < 2")
                .setParameter("status", status)
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public Customer getShopManagerAccess(String host) {
        Customer result = null;
        try {
            StringBuffer sqlbuffer = new StringBuffer();
            sqlbuffer = new StringBuffer("select o from Customer o where UPPER(o.domain) = :domain");
            Query query = entityManager.createQuery(sqlbuffer.toString(), Customer.class);
            query.setParameter("domain", host.toUpperCase());
            result = (Customer) query.getSingleResult();
        } catch (Exception ex) {
            System.out.println("No result found " + ex.getMessage());
        }
        return result;
    }

    @Override
    public boolean isBlackList(String username) {
        boolean result = true;
        try {
            StringBuffer sqlbuffer = new StringBuffer();
            sqlbuffer = new StringBuffer("select count(o) from DomainBlackList o where UPPER(o.name) = UPPER(:username)");
            Query query = entityManager.createQuery(sqlbuffer.toString());
            query.setParameter("username", username);
            List list = query.getResultList();
            int count = ((Long) list.get(0)).intValue();
            if (count == 0) {
                result = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean isExitDomain(String textdomain3, long id) {
        boolean result = true;
        try {
            StringBuffer sqlbuffer = new StringBuffer();
            sqlbuffer = new StringBuffer("select count(o) from ShopManager o where UPPER(o.domain) = UPPER(:domain) and o.customer <> :customer");
            Query query = entityManager.createQuery(sqlbuffer.toString());
            query.setParameter("domain", textdomain3);
            Customer cus = new Customer();
            cus.setId(id);
            query.setParameter("customer", cus);
            List list = query.getResultList();
            int count = ((Long) list.get(0)).intValue();
            if (count == 0) {
                result = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

}
