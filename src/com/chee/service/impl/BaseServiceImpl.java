package com.chee.service.impl;

import com.chee.common.Page;
import com.chee.common.PropertyFilter;
import com.chee.common.PropertyFilter.MatchType;
import com.chee.dao.BaseDao;
import com.chee.service.BaseService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

@Service
public class BaseServiceImpl<T, PK extends Serializable> implements BaseService<T, PK> {
    private BaseDao<T, PK> baseDao;

    public BaseDao<T, PK> getBaseDao() {
        return this.baseDao;
    }

    public void setBaseDao(BaseDao<T, PK> baseDao) {
        this.baseDao = baseDao;
    }

    public T get(PK id) {
        return this.baseDao.get(id);
    }

    public T load(PK id) {
        return this.baseDao.load(id);
    }

    public List<T> get(PK[] ids) {
        return this.baseDao.get(ids);
    }

    public T get(String propertyName, Object value) {
        return this.baseDao.get(propertyName, value);
    }

    public List<T> getList(String propertyName, Object value) {
        return this.baseDao.getList(propertyName, value);
    }

    public List<T> getAll() {
        return this.baseDao.getAll();
    }

    public Long getTotalCount() {
        return this.baseDao.getTotalCount();
    }

    public boolean isUnique(String propertyName, Object oldValue, Object newValue) {
        return this.baseDao.isUnique(propertyName, oldValue, newValue);
    }

    public boolean isPropertyUnique(String propertyName, Object newValue, Object oldValue) {
        return this.baseDao.isPropertyUnique(propertyName, newValue, oldValue);
    }

    public boolean isExist(String propertyName, Object value) {
        return this.baseDao.isExist(propertyName, value);
    }

    public void saveOrUpdate(T entity) {
        this.baseDao.saveOrUpdate(entity);
    }

    public PK save(T entity) {
        return this.baseDao.save(entity);
    }

    public void update(T entity) {
        this.baseDao.update(entity);
    }

    public void delete(T entity) {
        this.baseDao.delete(entity);
    }

    public void delete(PK id) {
        this.baseDao.delete(id);
    }

    public void delete(PK[] ids) {
        this.baseDao.delete(ids);
    }

    public void flush() {
        this.baseDao.flush();
    }

    public void clear() {
        this.baseDao.clear();
    }

    public void evict(Object object) {
        this.baseDao.evict(object);
    }

    public int batchExecute(String hql, Object[] values) {
        return this.baseDao.batchExecute(hql, values);
    }

    public int batchExecute(String hql, Map<String, ?> values) {
        return this.baseDao.batchExecute(hql, values);
    }

    public Criteria createCriteria(Criterion[] criterions) {
        return this.baseDao.createCriteria(criterions);
    }

    public Query createQuery(String queryString, Object[] values) {
        return this.baseDao.createQuery(queryString, values);
    }

    public Query createQuery(String queryString, Map<String, ?> values) {
        return this.baseDao.createQuery(queryString, values);
    }

    public Query distinct(Query query) {
        return this.baseDao.distinct(query);
    }

    public Criteria distinct(Criteria criteria) {
        return this.baseDao.distinct(criteria);
    }

    public boolean exists(PK id) {
        return this.baseDao.exists(id);
    }

    public <X> List<X> find(String hql, Object[] values) {
        return this.baseDao.find(hql, values);
    }

    public <X> List<X> find(String hql, Map<String, ?> values) {
        return this.baseDao.find(hql, values);
    }

    public List<T> find(Criterion[] criterions) {
        return this.baseDao.find(criterions);
    }

    public List<T> find(List<PropertyFilter> filters) {
        return this.baseDao.find(filters);
    }

    public List<T> findBy(String propertyName, Object value) {
        return this.baseDao.findBy(propertyName, value);
    }

    public List<T> findBy(String propertyName, Object value, PropertyFilter.MatchType matchType) {
        return this.baseDao.findBy(propertyName, value, matchType);
    }

    public Page<T> findPage(Page<T> page, String hql, Object[] values) {
        return this.baseDao.findPage(page, hql, values);
    }

    public Page<T> findPage(Page<T> page, String hql, Map<String, ?> values) {
        return this.baseDao.findPage(page, hql, values);
    }

    public Page<T> findPage(Page<T> page, Criterion[] criterions) {
        return this.baseDao.findPage(page, criterions);
    }

    public Page<T> findPage(Page<T> page, DetachedCriteria detachedCriteria) {
        return this.baseDao.findPage(page, detachedCriteria);
    }

    public List<T> find(DetachedCriteria detachedCriteria) {
        return this.baseDao.find(detachedCriteria);
    }

    public Page<T> findPage(Page<T> page, List<PropertyFilter> filters) {
        return this.baseDao.findPage(page, filters);
    }

    public Page<T> findPage(Page<T> page, String sql, List<PropertyFilter> filters) {
        return this.baseDao.findPage(page, sql, new Object[] { filters });
    }

    public <X> X findUnique(String hql, Object[] values) {
        return this.baseDao.findUnique(hql, values);
    }

    public <X> X findUnique(String hql, Map<String, ?> values) {
        return this.baseDao.findUnique(hql, values);
    }

    public T findUnique(Criterion[] criterions) {
        return this.baseDao.findUnique(criterions);
    }

    public T findUniqueBy(String propertyName, Object value) {
        return this.baseDao.findUniqueBy(propertyName, value);
    }

    public List<T> get(Collection<PK> ids) {
        return this.baseDao.get(ids);
    }

    public List<T> getAll(String orderByProperty, boolean isAsc) {
        return this.baseDao.getAll(orderByProperty, isAsc);
    }

    public Page<T> getAll(Page<T> page) {
        return this.baseDao.getAll(page);
    }

    public List<T> getAllDistinct() {
        return this.baseDao.getAllDistinct();
    }

    public String getIdName() {
        return this.baseDao.getIdName();
    }

    public Session getSession() {
        return this.baseDao.getSession();
    }

    public void initProxyObject(Object proxy) {
        this.baseDao.initProxyObject(proxy);
    }

    public long countHqlResult(String hql, Object[] values) {
        return this.baseDao.countHqlResult(hql, values);
    }

    public long countHqlResult(String hql, Map<String, ?> values) {
        return this.baseDao.countHqlResult(hql, values);
    }
}
