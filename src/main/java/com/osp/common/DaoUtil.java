//package com.osp.common;
//
//import org.hibernate.*;
//import org.hibernate.criterion.Criterion;
//import org.hibernate.criterion.Order;
//import org.hibernate.criterion.Projections;
//import org.hibernate.type.Type;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import java.io.Serializable;
//import java.util.Iterator;
//import java.util.List;
//
///**
// * Created by Admin on 12/15/2017.
// */
//@Repository
//public class DaoUtil {
//    @Autowired
//    private SessionFactory sessionFactory;
//
//    public void closeSession(Session session) {
//        if(session != null) {
//            try {
//                session.close();
//            } catch (HibernateException var3) {
//                var3.printStackTrace();
//            }
//        }
//
//    }
//
//    public Object saveOrUpdate(Object item) {
//        Session session = this.sessionFactory.getCurrentSession();
//
//        try {
//            session.saveOrUpdate(item);
//            session.flush();
//        } catch (Exception var8) {
//            var8.printStackTrace();
//        } finally {
//            closeSession(session);
//        }
//
//        return item;
//    }
//
//    public List<?> saveOrUpdateMany(List<?> items, int size) {
//        Session session = this.sessionFactory.getCurrentSession();
//        session.getTransaction().begin();
//
//        try {
//            int i = 1;
//            int total = items.size();
//
//            for(Iterator var7 = items.iterator(); var7.hasNext(); ++i) {
//                Object object = var7.next();
//                session.saveOrUpdate(object);
//                if(i % size == 0 || i == total) {
//                    session.flush();
//                    session.clear();
//                }
//            }
//
//            session.getTransaction().commit();
//        } catch (Exception var12) {
//            session.getTransaction().rollback();
//            var12.printStackTrace();
//        } finally {
//            closeSession(session);
//        }
//
//        return items;
//    }
//
//    public List<?> list(String model) {
//        Session session = this.sessionFactory.getCurrentSession();
//        List items = null;
//
//        try {
//            session.beginTransaction();
//            items = session.createQuery("from " + model).list();
//            session.getTransaction().commit();
//        } catch (Exception var9) {
//            var9.printStackTrace();
//        } finally {
//            closeSession(session);
//        }
//
//        return items;
//    }
//
//    public Object add(Object item) throws Exception {
//        Session session = this.sessionFactory.getCurrentSession();
//
//        try {
//            session.beginTransaction();
//            session.save(item);
//            session.flush();
//            session.getTransaction().commit();
//        } catch (Exception var8) {
//            var8.printStackTrace();
//        } finally {
//            closeSession(session);
//        }
//
//        return item;
//    }
//
//    public Object edit(Object item) throws Exception {
//        Session session = this.sessionFactory.getCurrentSession();
//
//        try {
//            session.beginTransaction();
//            session.update(item);
//            session.flush();
//            session.getTransaction().commit();
//        } catch (Exception var8) {
//            var8.printStackTrace();
//        } finally {
//            closeSession(session);
//        }
//
//        return item;
//    }
//
//    public Object delete(Object item) throws Exception {
//        Session session = this.sessionFactory.getCurrentSession();
//
//        try {
//            session.beginTransaction();
//            session.delete(item);
//            session.flush();
//            session.getTransaction().commit();
//        } catch (Exception var8) {
//            var8.printStackTrace();
//        } finally {
//            closeSession(session);
//        }
//
//        return item;
//    }
//
//    public void deleteAll(String model) throws Exception {
//        Session session = this.sessionFactory.getCurrentSession();
//
//        try {
//            session.beginTransaction();
//            String hql = "delete from " + model;
//            Query query = session.createQuery(hql);
//            query.executeUpdate();
//            session.flush();
//            session.getTransaction().commit();
//        } catch (Exception var9) {
//            var9.printStackTrace();
//        } finally {
//            closeSession(session);
//        }
//
//    }
//
//    public List<?> query(Class modelClass, List<Criterion> criterions) {
//        List items = null;
//        Session session = this.sessionFactory.getCurrentSession();
//
//        try {
//            session.beginTransaction();
//            Criteria criteria = session.createCriteria(modelClass);
//            if(criterions != null) {
//                for(int i = 0; i < criterions.size(); ++i) {
//                    Criterion criterion = (Criterion)criterions.get(i);
//                    criteria.add(criterion);
//                }
//            }
//
//            items = criteria.list();
//            session.getTransaction().commit();
//        } catch (Exception var12) {
//            session.getTransaction().rollback();
//            var12.printStackTrace();
//        } finally {
//            closeSession(session);
//        }
//
//        return items;
//    }
//
//    public List<?> query(Class modelClass, List<Criterion> criterions, List<Order> orderes) {
//        List items = null;
//        Session session = this.sessionFactory.getCurrentSession();
//
//        try {
//            session.beginTransaction();
//            Criteria criteria = session.createCriteria(modelClass);
//            int i;
//            if(criterions != null) {
//                for(i = 0; i < criterions.size(); ++i) {
//                    Criterion criterion = (Criterion)criterions.get(i);
//                    criteria.add(criterion);
//                }
//            }
//
//            if(orderes != null) {
//                for(i = 0; i < orderes.size(); ++i) {
//                    Order order = (Order)orderes.get(i);
//                    criteria.addOrder(order);
//                }
//            }
//
//            items = criteria.list();
//            session.getTransaction().commit();
//        } catch (Exception var13) {
//            session.getTransaction().rollback();
//            var13.printStackTrace();
//        } finally {
//            closeSession(session);
//        }
//
//        return items;
//    }
//
//    public Object get(Class cls, Serializable id) {
//        Object obj = null;
//        Session session = this.sessionFactory.getCurrentSession();
//
//        try {
//            session.beginTransaction();
//            obj = session.get(cls, id);
//            session.getTransaction().commit();
//        } catch (Exception var10) {
//            System.out.println(var10.getMessage());
//        } finally {
//            closeSession(session);
//        }
//
//        return obj;
//    }
//
//    public List<?> list(String model, int limit, int offset) {
//        Session session = this.sessionFactory.getCurrentSession();
//        List items = null;
//
//        try {
//            session.beginTransaction();
//            Query sql = session.createQuery("from " + model);
//            sql.setFirstResult(offset);
//            sql.setMaxResults(limit);
//            items = sql.list();
//            session.getTransaction().commit();
//        } catch (Exception var11) {
//            var11.printStackTrace();
//        } finally {
//            closeSession(session);
//        }
//
//        return items;
//    }
//
//    public int count(Class model) {
//        Session session = this.sessionFactory.getCurrentSession();
//        List items = null;
//
//        try {
//            session.beginTransaction();
//            Criteria criteria = session.createCriteria(model);
//            criteria.setProjection(Projections.rowCount());
//            items = criteria.list();
//            session.getTransaction().commit();
//            int var6 = ((Long)items.get(0)).intValue();
//            return var6;
//        } catch (Exception var10) {
//            var10.printStackTrace();
//        } finally {
//            closeSession(session);
//        }
//
//        return 0;
//    }
//
//    public int count(Class model, List<Criterion> criterions) {
//        Session session = this.sessionFactory.getCurrentSession();
//        List items = null;
//
//        try {
//            session.beginTransaction();
//            Criteria criteria = session.createCriteria(model);
//            int i;
//            if(criterions != null) {
//                for(i = 0; i < criterions.size(); ++i) {
//                    Criterion criterion = (Criterion)criterions.get(i);
//                    criteria.add(criterion);
//                }
//            }
//
//            criteria.setProjection(Projections.rowCount());
//            items = criteria.list();
//            session.getTransaction().commit();
//            i = ((Long)items.get(0)).intValue();
//            return i;
//        } catch (Exception var12) {
//            var12.printStackTrace();
//        } finally {
//            closeSession(session);
//        }
//
//        return 0;
//    }
//
//    public List<?> query(String hqlQuery, List<Object> params) {
//        List<?> items = null;
//        Session session = this.sessionFactory.getCurrentSession();
//
//        try {
//            session.beginTransaction();
//            Query query = session.createQuery(hqlQuery);
//            if(params != null && params.size() > 0) {
//                for(int i = 0; i < params.size(); ++i) {
//                    query.setParameter(i, params.get(i));
//                }
//            }
//
//            items = query.list();
//            session.flush();
//            session.getTransaction().commit();
//        } catch (Exception var11) {
//            session.getTransaction().rollback();
//            var11.printStackTrace();
//        } finally {
//            closeSession(session);
//        }
//
//        return items;
//    }
//
//    public List<?> querySQL(String sqlQuery, List<Object> params) {
//        List<?> items = null;
//        Session session = this.sessionFactory.getCurrentSession();
//
//        try {
//            session.beginTransaction();
//            SQLQuery query = session.createSQLQuery(sqlQuery);
//            if(params != null && params.size() > 0) {
//                for(int i = 0; i < params.size(); ++i) {
//                    query.setParameter(i, params.get(i));
//                }
//            }
//
//            items = query.list();
//            session.flush();
//            session.getTransaction().commit();
//        } catch (Exception var11) {
//            session.getTransaction().rollback();
//            var11.printStackTrace();
//        } finally {
//            closeSession(session);
//        }
//
//        return items;
//    }
//
//    public List<?> nativeQuerySQL(String sqlQuery, List<Object> params, List<String> columns, List<Type> types) {
//        List<?> items = null;
//        Session session = this.sessionFactory.getCurrentSession();
//
//        try {
//            session.beginTransaction();
//            SQLQuery query = session.createSQLQuery(sqlQuery);
//            int i;
//            if(columns != null && columns.size() > 0) {
//                for(i = 0; i < columns.size(); ++i) {
//                    query.addScalar((String)columns.get(i), (Type)types.get(i));
//                }
//            }
//
//            if(params != null && params.size() > 0) {
//                for(i = 0; i < params.size(); ++i) {
//                    query.setParameter(i, params.get(i));
//                }
//            }
//
//            items = query.list();
//            session.flush();
//            session.getTransaction().commit();
//        } catch (Exception var13) {
//            session.getTransaction().rollback();
//            var13.printStackTrace();
//        } finally {
//            closeSession(session);
//        }
//
//        return items;
//    }
//
//    public int executeSQL(String sqlQuery, List<Object> params) {
//        int result = -1;
//        Session session = this.sessionFactory.getCurrentSession();
//
//        try {
//            session.beginTransaction();
//            SQLQuery query = session.createSQLQuery(sqlQuery);
//            if(params != null && params.size() > 0) {
//                for(int i = 0; i < params.size(); ++i) {
//                    query.setParameter(i, params.get(i));
//                }
//            }
//
//            result = query.executeUpdate();
//            session.flush();
//            session.getTransaction().commit();
//        } catch (Exception var11) {
//            var11.printStackTrace();
//            session.getTransaction().rollback();
//        } finally {
//            closeSession(session);
//        }
//
//        return result;
//    }
//}
