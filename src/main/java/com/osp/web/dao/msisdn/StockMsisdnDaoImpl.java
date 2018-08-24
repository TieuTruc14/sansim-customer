package com.osp.web.dao.msisdn;

import com.osp.common.PagingResult;
import com.osp.common.QueryBuilder;
import com.osp.common.Utils;
import com.osp.model.*;
import com.osp.model.view.SearchIndex;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.*;
import javax.persistence.Query;

/**
 * Created by Admin on 12/21/2017.
 */
@Repository
@Transactional
public class StockMsisdnDaoImpl implements StockMsisdnDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<PagingResult> searchIndex(PagingResult page, Long id, SearchIndex searchIndex) {
        int offset = 0;
        if (page.getPageNumber() > 0) {
            offset = (page.getPageNumber() - 1) * page.getNumberPerPage();
        }
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<StockMsisdn> q = cb.createQuery(StockMsisdn.class);
        CriteriaQuery<Object[]> q = cb.createQuery(Object[].class);
        Root<StockMsisdn> root = q.from(StockMsisdn.class);

        String valueOrder=randomKeyMsisdn();
        List<Predicate> predicates = new ArrayList<Predicate>();

        if (id != null) {
            predicates.add(cb.equal(root.get("customer").get("id"), id));
        }

        predicates.add(cb.equal(root.get("status"), 1));
        if (StringUtils.isNotBlank(searchIndex.getTelco())) {
            String telco = searchIndex.getTelco();
            predicates.add(cb.equal(root.get("telco"), Byte.valueOf(telco)));
        }

        if (StringUtils.isNotBlank(searchIndex.getDauso())) {
            Expression<Integer> length = cb.length(root.get("msisdn"));
            switch (searchIndex.getDauso()) {
                case "10":
                    predicates.add(cb.equal(length, 10));
                    break;
                case "11":
                    predicates.add(cb.equal(length, 11));
                    break;
                default:
//                    predicates.add(cb.like(root.get("msisdn"), searchIndex.getDauso() + "%"));
                    break;
            }
        }
        if (StringUtils.isNotBlank(searchIndex.getSo())) {
            if (searchIndex.getSo().contains("*")) {
                predicates.add(cb.like(root.get("suffix"), searchIndex.getSo().replaceAll("\\*", "%")));
            } else {
                predicates.add(cb.like(root.get("suffix"), "%" + searchIndex.getSo()));
            }
        }

        if (searchIndex.getConfirmStatus() != null && searchIndex.getConfirmStatus() == true) {
            predicates.add(cb.equal(root.get("confirmStatus"), Short.valueOf("1")));
        }
        if (StringUtils.isNotBlank(searchIndex.getBirthday()) && searchIndex.getBirthday().length() == 10) {
            Date date = null;
            try {
                date = Utils.str2date(searchIndex.getBirthday(), "dd/MM/yyyy");
            } catch (Exception e) {
            }
            if (date != null) {
                List<String> myList = new ArrayList<String>(Arrays.asList(searchIndex.getBirthday().split("/")));
                List<Predicate> predicatesChild = new ArrayList<Predicate>();
                if (myList.get(0).contains("0") && myList.get(1).contains("0")) {//kiem tra ngay` va thang co chua so 0: vd-05/08/1988
                    predicatesChild.add(cb.like(root.get("msisdn"), "%" + myList.get(0).substring(1) + myList.get(1).substring(1) + myList.get(2)));//581988
                }
                predicatesChild.add(cb.like(root.get("msisdn"), "%" + myList.get(0) + myList.get(1) + myList.get(2).substring(2)));//050888
                predicatesChild.add(cb.like(root.get("msisdn"), "%" + myList.get(1) + myList.get(2)));//081988
                predicatesChild.add(cb.like(root.get("msisdn"), "%" + myList.get(2)));//1988
                Predicate preBirthday = cb.or(predicatesChild.toArray(new Predicate[]{}));
                predicates.add(preBirthday);
            }
        }

        if (searchIndex.getFrom() != null) {
            predicates.add(cb.ge(root.get("price"), searchIndex.getFrom()));
        }
        if (searchIndex.getTo() != null) {
            predicates.add(cb.le(root.get("price"), searchIndex.getTo()));
        }

        q.select(cb.array(root.get("id"), root.get("msisdn"), root.get("price"), root.get("customer").get("id"), root.get("customer").get("full_name"), root.get("confirmStatus"), root.get("telco"), root.get("customer").get("domain"), root.get("msisdnAlias"))).where(predicates.toArray(new Predicate[]{}));
        if(searchIndex.getSortPrice()!=null && searchIndex.getSortPrice().intValue()>0){
            if(searchIndex.getSortPrice().intValue()==1){
                q.orderBy(cb.asc(root.get("price")));
            }else{
                q.orderBy(cb.desc(root.get("price")));
            }
        }else{
            Random r = new Random();
            int order=r.nextInt(2);
            if(order==1){
                q.orderBy(cb.desc(root.get("confirmStatus")), cb.desc(root.get(valueOrder)));
            }else{
                q.orderBy(cb.desc(root.get("confirmStatus")), cb.asc(root.get(valueOrder)));
            }

        }
        List<Object[]> list = entityManager.createQuery(q).setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();

        if (list != null) {
            page.setItems(list);
        }
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<StockMsisdn> rootCount = criteriaQuery.from(StockMsisdn.class);
        criteriaQuery.select(cb.count(rootCount)).where(predicates.toArray(new Predicate[]{}));
        Long rowCount = entityManager.createQuery(criteriaQuery).getSingleResult();
        if (rowCount != null) {
            page.setRowCount(rowCount.longValue());
        }

        return Optional.ofNullable(page);
    }
    private String randomKeyMsisdn(){
        List<String> listOrder=Arrays.asList("price","msisdn","genDate","telco","groupId","id","lastUpdate");
        Random r = new Random();
        int orderNumberInList=r.nextInt(7);
        return listOrder.get(orderNumberInList);
    }

    @Override
    public Optional<PagingResult> searchDetail(PagingResult page, Long id) {
        int offset = 0;
        if (page.getPageNumber() > 0) {
            offset = (page.getPageNumber() - 1) * page.getNumberPerPage();
        }
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = cb.createQuery(Object[].class);
        Root<StockMsisdn> root = q.from(StockMsisdn.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (id != null) {
            predicates.add(cb.equal(root.get("customer").get("id"), id));
        }
        q.select(cb.array(root.get("id"), root.get("msisdn"), root.get("price"), root.get("confirmStatus"), root.get("telco"), root.get("msisdnAlias"))).where(predicates.toArray(new Predicate[]{}));
        q.orderBy(cb.desc(root.get("confirmStatus")), cb.desc(root.get("genDate")));
        List<Object[]> list = entityManager.createQuery(q).setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
        if (list != null) {
            page.setItems(list);
        }
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<StockMsisdn> rootCount = criteriaQuery.from(StockMsisdn.class);
        criteriaQuery.select(cb.count(rootCount)).where(predicates.toArray(new Predicate[]{}));
        Long rowCount = entityManager.createQuery(criteriaQuery).getSingleResult();
        if (rowCount != null) {
            page.setRowCount(rowCount.longValue());
        }

        return Optional.ofNullable(page);
    }

    @Override
    public Optional<PagingResult> searchByGroupPrice(PagingResult page, Long id, Long from, Long to) {
        int offset = 0;
        if (page.getPageNumber() > 0) {
            offset = (page.getPageNumber() - 1) * page.getNumberPerPage();
        }
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = cb.createQuery(Object[].class);
        Root<StockMsisdn> root = q.from(StockMsisdn.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (id != null) {
            predicates.add(cb.equal(root.get("customer").get("id"), id));
        }
        predicates.add(cb.equal(root.get("status"), 1));
        if (from != null) {
            predicates.add(cb.ge(root.get("price"), from));
        }
        if (to != null) {
            predicates.add(cb.le(root.get("price"), to));
        }
        q.select(cb.array(root.get("id"), root.get("msisdn"), root.get("price"), root.get("customer").get("id"), root.get("customer").get("full_name"), root.get("confirmStatus"), root.get("telco"), root.get("customer").get("domain"), root.get("msisdnAlias"))).where(predicates.toArray(new Predicate[]{}));
        q.orderBy(cb.desc(root.get("confirmStatus")), cb.desc(root.get("genDate")));
        List<Object[]> list = entityManager.createQuery(q).setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
        if (list != null) {
            page.setItems(list);
        }
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<StockMsisdn> rootCount = criteriaQuery.from(StockMsisdn.class);
        criteriaQuery.select(cb.count(rootCount)).where(predicates.toArray(new Predicate[]{}));
        Long rowCount = entityManager.createQuery(criteriaQuery).getSingleResult();
        if (rowCount != null) {
            page.setRowCount(rowCount.longValue());
        }
        return Optional.ofNullable(page);
    }

    @Override
    public Optional<PagingResult> searchByGroupMsisdn(PagingResult page, Long customerId, Long groupId) {
        int offset = 0;
        if (page.getPageNumber() > 0) {
            offset = (page.getPageNumber() - 1) * page.getNumberPerPage();
        }
        List<Object[]> list = new ArrayList<>();
        Long count = 0L;
        if (customerId != null && customerId.longValue() > 0) {
            BigDecimal count12 = (BigDecimal) entityManager.createNativeQuery("SELECT count(g.id) from SIMA_STOCK_MSISDN g left join SIMA_CUSTOMER cus ON g.customer_id=cus.id where g.customer_id=:customerId and g.status =1 AND BITAND(g.GROUP_ID,:groupId)>0 order by g.confirm_status desc,g.gen_date desc")
                    .setParameter("customerId", customerId).setParameter("groupId", groupId).getSingleResult();
            count = count12.longValue();
            list = entityManager.createNativeQuery("SELECT DISTINCT stock.id AS stockID,stock.MSISDN,stock.PRICE,cus.ID,cus.FULLNAME,stock.CONFIRM_STATUS, stock.telco, cus.domain,stock.msisdn_alias from SIMA_STOCK_MSISDN stock left join SIMA_CUSTOMER cus ON stock.customer_id=cus.id where stock.customer_id=:customerId and stock.status =1 AND BITAND(stock.GROUP_ID,:groupId)>0 order by stock.confirm_status desc,stock.gen_date desc")
                    .setParameter("customerId", customerId).setParameter("groupId", groupId).setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
        } else {
            BigDecimal count12 = (BigDecimal) entityManager.createNativeQuery("SELECT count(g.id) from SIMA_STOCK_MSISDN g left join SIMA_CUSTOMER cus ON g.customer_id=cus.id where g.status =1 and BITAND(g.GROUP_ID,:groupId)>0 order by g.confirm_status desc,g.gen_date desc")
                    .setParameter("groupId", groupId).getSingleResult();
            count = count12.longValue();
            list = entityManager.createNativeQuery("SELECT DISTINCT stock.id AS stockID,stock.MSISDN,stock.PRICE,cus.ID,cus.FULLNAME,stock.CONFIRM_STATUS,stock.telco,cus.domain,stock.msisdn_alias from SIMA_STOCK_MSISDN stock left join SIMA_CUSTOMER cus ON stock.customer_id=cus.id where stock.status =1 and BITAND(stock.GROUP_ID,:groupId)>0 order by stock.confirm_status desc,stock.gen_date desc")
                    .setParameter("groupId", groupId).setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
        }
        if (list != null) {
            page.setItems(list);
        }
        if (count != null && count.longValue() > 0) {
            page.setRowCount(count);
        }
        return Optional.of(page);
    }

    @Override
    public Optional<PagingResult> searchByGroupYear(PagingResult page, Long id, Integer year) {
        int offset = 0;
        if (page.getPageNumber() > 0) {
            offset = (page.getPageNumber() - 1) * page.getNumberPerPage();
        }
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = cb.createQuery(Object[].class);
        Root<StockMsisdn> root = q.from(StockMsisdn.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (id != null) {
            predicates.add(cb.equal(root.get("customer").get("id"), id));
        }
        predicates.add(cb.equal(root.get("status"), 1));
        //chi xu ly year khi trong khoang 1000-9999
        if (year != null && (999 < year.intValue()) && year.intValue() < 10000) {
            List<Predicate> predicatesChild = new ArrayList<Predicate>();
//            predicatesChild.add(cb.like(root.get("msisdn"), "%" + year.toString().substring(2) ));//1988
            predicatesChild.add(cb.like(root.get("msisdn"), "%" + year));//1988
            Predicate preBirthday = cb.or(predicatesChild.toArray(new Predicate[]{}));
            predicates.add(preBirthday);
        }
        q.select(cb.array(root.get("id"), root.get("msisdn"), root.get("price"), root.get("customer").get("id"), root.get("customer").get("full_name"), root.get("confirmStatus"), root.get("telco"), root.get("customer").get("domain"), root.get("msisdnAlias"))).where(predicates.toArray(new Predicate[]{}));
        q.orderBy(cb.desc(root.get("confirmStatus")), cb.desc(root.get("genDate")));
        List<Object[]> list = entityManager.createQuery(q).setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
        if (list != null) {
            page.setItems(list);
        }
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<StockMsisdn> rootCount = criteriaQuery.from(StockMsisdn.class);
        criteriaQuery.select(cb.count(rootCount)).where(predicates.toArray(new Predicate[]{}));
        Long rowCount = entityManager.createQuery(criteriaQuery).getSingleResult();
        if (rowCount != null) {
            page.setRowCount(rowCount.longValue());
        }
        return Optional.ofNullable(page);
    }

    @Override
    public Optional<PagingResult> searchByGroup(PagingResult page, Long cusId, GroupPrice groupPrice, Long gmsisdnId, GroupYear groupYear,Byte sortPrice,Byte telco) {
        int offset = 0;
        if (page.getPageNumber() > 0) {
            offset = (page.getPageNumber() - 1) * page.getNumberPerPage();
        }
        StringBuffer sqlBuffer = new StringBuffer("SELECT DISTINCT stock.id AS stockID,stock.MSISDN,stock.PRICE,cus.ID,cus.FULLNAME,stock.CONFIRM_STATUS,stock.telco, cus.domain,stock.msisdn_alias "
                + "from SIMA_STOCK_MSISDN stock left join SIMA_CUSTOMER cus ON stock.customer_id=cus.id ");
        StringBuffer sqlBufferCount = new StringBuffer("SELECT count(stock.id) from SIMA_STOCK_MSISDN stock left join SIMA_CUSTOMER cus ON stock.customer_id=cus.id ");
        Query query = filterBuilderSearchGroup(sqlBuffer, cusId, groupPrice, gmsisdnId, groupYear,sortPrice,telco);
        List<Object[]> list = query.setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
        if (list != null) {
            page.setItems(list);
        }

        Query queryCount = filterBuilderSearchGroupCount(sqlBufferCount, cusId, groupPrice, gmsisdnId, groupYear,telco);
        BigDecimal rowCount = (BigDecimal) queryCount.getSingleResult();
        if (rowCount != null) {
            page.setRowCount(rowCount.longValue());
        }

        return Optional.of(page);
    }

    private Query filterBuilderSearchGroup(StringBuffer stringBuffer, Long cusId, GroupPrice groupPrice, Long gmsisdnId, GroupYear groupYear,Byte sortPrice,Byte telco) {
        Query result = null;
        try {
            QueryBuilder builder = new QueryBuilder(entityManager, stringBuffer);
            builder.and(QueryBuilder.EQ, "stock.status", 1);
            if (cusId != null) {
                builder.and(QueryBuilder.EQ, "stock.customer_id", cusId);
            }
            if (groupPrice != null) {
                builder.and(QueryBuilder.GE, "stock.price", groupPrice.getMin());
                builder.and(QueryBuilder.LE, "stock.price", groupPrice.getMax());
            }
            if (gmsisdnId != null) {
                builder.andBitAnd(QueryBuilder.GT, "stock.GROUP_ID", gmsisdnId);
            }
            if (groupYear != null) {
                builder.and(QueryBuilder.LIKE, "stock.msisdn", "%" + groupYear.getYear().toString());
            }
            if(telco!=null){
                builder.and(QueryBuilder.EQ, "stock.telco", telco);
            }
            //sap xep theo gia
            if(sortPrice!=null && sortPrice.intValue()>0){
                if(sortPrice.intValue()==1){
                    builder.addOrder("stock.price", QueryBuilder.ASC);
                }else{
                    builder.addOrder("stock.price", QueryBuilder.DESC);
                }
            }else{
                builder.addOrder("stock.confirm_status", QueryBuilder.DESC);
                builder.addOrder("stock.gen_date", QueryBuilder.DESC);
            }

            result = builder.initNativeQuery();
        } catch (Exception e) {

        }
        return result;
    }

    private Query filterBuilderSearchGroupCount(StringBuffer stringBuffer, Long cusId, GroupPrice groupPrice, Long gmsisdnId, GroupYear groupYear,Byte telco) {
        Query result = null;
        try {
            QueryBuilder builder = new QueryBuilder(entityManager, stringBuffer);
            builder.and(QueryBuilder.EQ, "stock.status", 1);
            if (cusId != null) {
                builder.and(QueryBuilder.EQ, "stock.customer_id", cusId);
            }
            if (groupPrice != null) {
                builder.and(QueryBuilder.GE, "stock.price", groupPrice.getMin());
                builder.and(QueryBuilder.LE, "stock.price", groupPrice.getMax());
            }
            if (gmsisdnId != null) {
                builder.andBitAnd(QueryBuilder.GT, "stock.GROUP_ID", gmsisdnId);
            }
            if (groupYear != null) {
                builder.and(QueryBuilder.LIKE, "stock.msisdn", "%" + groupYear.getYear().toString());
            }

            if(telco!=null){
                builder.and(QueryBuilder.EQ, "stock.telco", telco);
            }
            result = builder.initNativeQuery();
        } catch (Exception e) {

        }
        return result;
    }

    @Override
    public Optional<Object[]> simDetailById(Long id) {
        List<Object[]> list = entityManager.createQuery("select sim.id,sim.msisdn,sim.price,sim.confirmStatus,sim.customer.id,sim.customer.full_name,sim.customer.msisdn,sim.customer.address,sim.customer.description,sim.description,sim.telco,sim.customer.domain,sim.msisdnAlias from StockMsisdn sim where sim.status =1 and sim.id = :id",
                Object[].class).setParameter("id", id).setFirstResult(0).setMaxResults(1).getResultList();
//        Object[] sim = null;
        if (list != null && list.size() > 0) {
            Object[] sim = list.get(0);
            return Optional.ofNullable(sim);
        }
        return Optional.ofNullable(null);
    }

    @Override
    public int countTotalMsisdn(String msisdn, String tel, String confirmExpired, Date fromDate, Date toDate, Long userId, String confStatus, String[] telco) {
        int result = 0;
        StringBuffer sqlBuffer = new StringBuffer("SELECT Count(o) FROM StockMsisdn o");
        Query query = filterBuilderSingle(sqlBuffer, null, msisdn, tel, confirmExpired, fromDate, toDate, userId, confStatus, telco);
        List list = query.getResultList();
        result = ((Long) list.get(0)).intValue();
        return result;
    }

    @Override
    public List<StockMsisdn> getPagingMsisdn(int page, int limit, String msisdn, String tel, String confirmExpired, Date fromDate, Date toDate, Long userId, String confStatus, String[] telco) {
        List<StockMsisdn> listResult = new ArrayList<StockMsisdn>();
        try {
            int start = page * limit - limit;
            StringBuffer sqlBuffer = new StringBuffer("SELECT o FROM StockMsisdn o");
            Query query = filterBuilderSingle(sqlBuffer, StockMsisdn.class, msisdn, tel, confirmExpired, fromDate, toDate, userId, confStatus, telco);
            listResult = query.setFirstResult(start).setMaxResults(limit).getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return listResult;

    }

    private Query filterBuilderSingle(StringBuffer stringBuffer, Class cls, String msisdn, String tel, String confirmExpired, Date fromDate, Date toDate, Long userId, String confStatus, String[] telco) {
        Query result = null;
        try {
            QueryBuilder builder = new QueryBuilder(entityManager, stringBuffer);

            if (userId != 0) {
                Customer cus = new Customer();
                cus.setId(userId);
                builder.and(QueryBuilder.EQ, "o.customer", cus);
            }

            if (msisdn != null && !msisdn.isEmpty()) {
                builder.and(QueryBuilder.LIKE, "o.msisdn", "%" + msisdn.trim() + "%");
            }
            if (tel != null && !tel.isEmpty()) {
                if (tel.equals("10")) {
                    builder.and(QueryBuilder.EQ, "length(o.msisdn)", 10);
                } else if (tel.equals("11")) {
                    builder.and(QueryBuilder.EQ, "length(o.msisdn)", 11);
                }
            }

            if (telco != null && telco.length > 0) {
                List<Byte> listTelco = new ArrayList<Byte>();
                for (String a : telco) {
                    if (a != null && (a.equals("0") || a.equals("1") || a.equals("2") || a.equals("3") || a.equals("4") || a.equals("5"))) {
                        listTelco.add(Byte.parseByte(a));
                    }
                }
                if (listTelco != null && listTelco.size() > 0) {
                    builder.and(QueryBuilder.IN, "o.telco", listTelco);
                }
            }

            if (confirmExpired != null && !confirmExpired.isEmpty() && !confirmExpired.equals("-1")) {
                builder.sqlManual(" and o.confirmExpired <= sysdate + " + confirmExpired);
            }
            if (confStatus != null && !confStatus.isEmpty() && !confStatus.equals("-1")) {
                builder.and(QueryBuilder.EQ, "o.confirmStatus", new Short(confStatus));
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
    public boolean checkExitsMsisdn(String msisdn, Long userId) {
        boolean result = true;
        try {
            Customer cus = new Customer();
            cus.setId(userId);
            Query query = entityManager.createQuery("select count(o) from StockMsisdn o where o.msisdn = :msisdn and o.customer = :customer ");
            query.setParameter("msisdn", msisdn);
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

    @Override
    public CustService getServiceRegister(Long userId) {
        CustService result = null;
        try {
            StringBuffer sqlbuffer = new StringBuffer();
            sqlbuffer = new StringBuffer("select o from CustService o where o.customer = :customer and o.status =1 and o.expiredDate >= sysdate ");
            Customer cus = new Customer();
            cus.setId(userId);
            Query query = entityManager.createQuery(sqlbuffer.toString(), CustService.class);
            query.setParameter("customer", cus);
            result = (CustService) query.getSingleResult();
        } catch (Exception ex) {
            System.out.println("No result found " + ex.getMessage());
        }
        return result;
    }

    @Override
    public int countTotalMsisdnUpload(Long userId) {
        int result = 0;
        StringBuffer sqlBuffer = new StringBuffer("SELECT Count(o) FROM StockMsisdn o where o.customer = :customer and o.status = 1 and o.approve = 1 ");
        Query query = entityManager.createQuery(sqlBuffer.toString());
        Customer cus = new Customer();
        cus.setId(userId);
        query.setParameter("customer", cus);
        List list = query.getResultList();
        result = ((Long) list.get(0)).intValue();
        return result;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean insertMsisdn(StockMsisdn msiUpload) {
        boolean result = false;
        try {
            entityManager.persist(msiUpload);
            entityManager.flush();
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;

    }

    @Override
    public StockMsisdn getMsisdnById(Long id, Long userId) {
        StockMsisdn result = null;
        try {
            StringBuffer sqlbuffer = new StringBuffer();
            sqlbuffer = new StringBuffer("select o from StockMsisdn o where o.id =:id and o.customer = :customer");
            Query query = entityManager.createQuery(sqlbuffer.toString(), StockMsisdn.class);
            query.setParameter("id", id);
            Customer cus = new Customer();
            cus.setId(userId);
            query.setParameter("customer", cus);
            result = (StockMsisdn) query.getSingleResult();
        } catch (Exception ex) {
            System.out.println("No result found " + ex.getMessage());
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean updateMsisdn(StockMsisdn msi) {
        boolean result = false;
        try {
            entityManager.merge(msi);
            entityManager.flush();
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;

    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean removeListMsisdn(List<StockMsisdn> listMsisdn) {
        boolean result = false;
        try {
            for (StockMsisdn stockMsisdn : listMsisdn) {
                Query query = entityManager.createQuery("Delete from StockMsisdn where id = :id");
                query.setParameter("id", stockMsisdn.getId());
                query.executeUpdate();

                entityManager.flush();
            }
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;

    }

    @Override
    public ConfPackage getPackageById(Long id) {
        ConfPackage result = null;
        try {
            StringBuffer sqlbuffer = new StringBuffer();
            sqlbuffer = new StringBuffer("select o from ConfPackage o where o.id =:id ");
            Query query = entityManager.createQuery(sqlbuffer.toString(), ConfPackage.class);
            query.setParameter("id", id);
            result = (ConfPackage) query.getSingleResult();
        } catch (Exception ex) {
            System.out.println("No result found " + ex.getMessage());
        }
        return result;

    }

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
    public StockMsisdn getMsisdn(String msisdn, Long userId) {
        StockMsisdn result = null;
        try {
            StringBuffer sqlbuffer = new StringBuffer();
            sqlbuffer = new StringBuffer("select o from StockMsisdn o where o.msisdn =:msisdn and o.customer = :customer");
            Query query = entityManager.createQuery(sqlbuffer.toString(), StockMsisdn.class);
            query.setParameter("msisdn", msisdn);
            Customer cus = new Customer();
            cus.setId(userId);
            query.setParameter("customer", cus);
            result = (StockMsisdn) query.getSingleResult();
        } catch (Exception ex) {
            System.out.println("No result found " + ex.getMessage());
        }
        return result;
    }

    @Override
    public int editFee(long id, long price) {

        return entityManager.createQuery("UPDATE StockMsisdn set price = :price where id = :id").setParameter("price", price).setParameter("id", id).executeUpdate();
    }

    @Override
    public int insertBatchMsisdn(List<StockMsisdn> msiUploads) {
        int result = 0;
        int i = 0;
        try {
            for (StockMsisdn msiUpload : msiUploads) {
                i++;
                entityManager.persist(msiUpload);
                if (i % 50 == 0) { 
                    entityManager.flush();
                    entityManager.clear();
                }
                result = i;
            }
            entityManager.flush();
            entityManager.clear();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;

    }

}
