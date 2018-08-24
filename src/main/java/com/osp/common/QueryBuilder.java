package com.osp.common;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class QueryBuilder {

    private Query query;
    private StringBuffer sqlbuffer;
    private List<String> keys;
    private List<Object> values;
    private List<String> orderField;
    private List<String> orderVal;
    EntityManager em;

    public static final String EQ = "=";
    public static final String NE = "<>";
    public static final String LIKE = "LIKE";
    public static final String GT = ">";
    public static final String LT = "<";
    public static final String GE = ">=";
    public static final String LE = "<=";
    public static final String NOT_LIKE = "NOT LIKE";
    public static final String IN = "IN";
    public static final String NOT_IN = "NOT IN";
    public static final String AND = "AND";
    public static final String OR = "OR";
    public static final String DESC = "DESC";
    public static final String ASC = "ASC";

    public QueryBuilder(EntityManager em, StringBuffer sqlbuffer) {
        this.sqlbuffer = sqlbuffer;
        this.sqlbuffer.append(" WHERE 1 = 1 ");
        this.keys = new ArrayList<String>();
        this.values = new ArrayList<Object>();
        this.orderField = new ArrayList<String>();
        this.orderVal = new ArrayList<String>();
        this.em = em;
    }

    public void sqlManual(String sql) {
        this.sqlbuffer.append(" " + sql + " ");
    }

    public void and(String operator, String key, Object value) {
        this.sqlbuffer.append(" AND (");
        this.sqlbuffer.append(" " + key + " ");
        this.sqlbuffer.append(" " + operator + " ");
        this.sqlbuffer.append(" ( :param_" + (keys.size() + 1) + " ) ");
        this.sqlbuffer.append(" )");
        this.keys.add("param_" + (keys.size() + 1) + "");
        this.values.add(value);
    }

    public void andBitAnd(String operator, String key, Object value) {
        this.sqlbuffer.append(" AND (BITAND(");
        this.sqlbuffer.append(" " + key + " ");
        this.sqlbuffer.append(" , ");
        this.sqlbuffer.append(" :param_" + (keys.size() + 1) + " ) ");
        this.sqlbuffer.append(" " + operator + " ");
        this.sqlbuffer.append(" 0 ");
        this.sqlbuffer.append(" )");
        this.keys.add("param_" + (keys.size() + 1) + "");
        this.values.add(value);
    }

    public void or(String operator, String key, Object value) {
        this.sqlbuffer.append(" OR (");
        this.sqlbuffer.append(" " + key + " ");
        this.sqlbuffer.append(" " + operator + " ");
        this.sqlbuffer.append("( :param_" + (keys.size() + 1) + " )");
        this.sqlbuffer.append(" )");
        this.keys.add("param_" + (keys.size() + 1) + "");
        this.values.add(value);
    }

    public void reference(String from, String to) {
        this.sqlbuffer.append(" AND " + from + " = " + to + " ");
    }

    public void block(String syntax, List<Condition> conditions) {
        this.sqlbuffer.append(" " + syntax + " (");
        int i = 0;
        for (Condition condition : conditions) {
            this.sqlbuffer.append(" ( ");
            this.sqlbuffer.append(" " + condition.getKey() + " ");
            this.sqlbuffer.append(" " + condition.getOperator() + " ");
            this.sqlbuffer.append("( :param_" + (keys.size() + 1) + " )");
            this.sqlbuffer.append(" ) ");
            this.sqlbuffer.append(" " + condition.getSubSyntax() + " ");
            this.keys.add("param_" + (keys.size() + 1) + "");
            this.values.add(condition.getValue());
        }
        this.sqlbuffer.append(" )");
    }

    public void addOrder(String key, String val) {
        this.orderField.add(key);
        this.orderVal.add(val);
    }

    public Query initQuery(Class cls) {
        if (this.orderField != null && this.orderField.size() > 0) {
            this.sqlbuffer.append(" ORDER BY ");
            for (int i = 0; i < this.orderField.size(); i++) {
                if (i == 0) {
                    this.sqlbuffer.append(" " + this.orderField.get(i) + " " + this.orderVal.get(i));
                } else {
                    this.sqlbuffer.append(" , " + this.orderField.get(i) + " " + this.orderVal.get(i));
                }
            }
        }
        System.out.println(sqlbuffer.toString());
        this.query = this.em.createQuery(sqlbuffer.toString(), cls);
        if (this.keys.size() > 0) {
            for (int i = 0; i < this.keys.size(); i++) {
                this.query.setParameter(keys.get(i), values.get(i));
            }
        }
        return this.query;
    }

    public Query initQuery() {
        System.out.println(sqlbuffer.toString());
        this.query = this.em.createQuery(sqlbuffer.toString());
        if (this.keys.size() > 0) {
            for (int i = 0; i < this.keys.size(); i++) {
                this.query.setParameter(keys.get(i), values.get(i));
            }
        }
        return this.query;
    }

    public Query initNativeQuery(){
        System.out.println(sqlbuffer.toString());
        if (this.orderField != null && this.orderField.size() > 0) {
            this.sqlbuffer.append(" ORDER BY ");
            for (int i = 0; i < this.orderField.size(); i++) {
                if (i == 0) {
                    this.sqlbuffer.append(" " + this.orderField.get(i) + " " + this.orderVal.get(i));
                } else {
                    this.sqlbuffer.append(" , " + this.orderField.get(i) + " " + this.orderVal.get(i));
                }
            }
        }
        this.query = this.em.createNativeQuery(sqlbuffer.toString());
        if (this.keys.size() > 0) {
            for (int i = 0; i < this.keys.size(); i++) {
                this.query.setParameter(keys.get(i), values.get(i));
            }
        }
        return this.query;
    }

}
