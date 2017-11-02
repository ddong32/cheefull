package com.chee.dao;

import com.chee.common.Page;
import com.chee.common.PropertyFilter;
import com.chee.common.PropertyFilter.MatchType;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;

public abstract interface BaseDao<T, PK extends Serializable>
{
  public abstract T get(PK paramPK);
  
  public abstract T load(PK paramPK);
  
  public abstract List<T> get(PK[] paramArrayOfPK);
  
  public abstract T get(String paramString, Object paramObject);
  
  public abstract List<T> getList(String paramString, Object paramObject);
  
  public abstract List<T> getAll();
  
  public abstract Long getTotalCount();
  
  public abstract boolean isUnique(String paramString, Object paramObject1, Object paramObject2);
  
  public abstract boolean isExist(String paramString, Object paramObject);
  
  public abstract void saveOrUpdate(T paramT);
  
  public abstract PK save(T paramT);
  
  public abstract void update(T paramT);
  
  public abstract void delete(T paramT);
  
  public abstract void delete(PK paramPK);
  
  public abstract void delete(PK[] paramArrayOfPK);
  
  public abstract void flush();
  
  public abstract void clear();
  
  public abstract void evict(Object paramObject);
  
  public abstract List<T> getAllDistinct();
  
  public abstract boolean exists(PK paramPK);
  
  public abstract Session getSession();
  
  public abstract List<T> get(Collection<PK> paramCollection);
  
  public abstract List<T> getAll(String paramString, boolean paramBoolean);
  
  public abstract List<T> findBy(String paramString, Object paramObject);
  
  public abstract T findUniqueBy(String paramString, Object paramObject);
  
  public abstract <X> List<X> find(String paramString, Object[] paramArrayOfObject);
  
  public abstract <X> List<X> find(String paramString, Map<String, ?> paramMap);
  
  public abstract <X> X findUnique(String paramString, Object[] paramArrayOfObject);
  
  public abstract <X> X findUnique(String paramString, Map<String, ?> paramMap);
  
  public abstract int batchExecute(String paramString, Object[] paramArrayOfObject);
  
  public abstract int batchExecute(String paramString, Map<String, ?> paramMap);
  
  public abstract Query createQuery(String paramString, Object[] paramArrayOfObject);
  
  public abstract Query createQuery(String paramString, Map<String, ?> paramMap);
  
  public abstract List<T> find(Criterion[] paramArrayOfCriterion);
  
  public abstract T findUnique(Criterion[] paramArrayOfCriterion);
  
  public abstract Criteria createCriteria(Criterion[] paramArrayOfCriterion);
  
  public abstract void initProxyObject(Object paramObject);
  
  public abstract Query distinct(Query paramQuery);
  
  public abstract Criteria distinct(Criteria paramCriteria);
  
  public abstract String getIdName();
  
  public abstract boolean isPropertyUnique(String paramString, Object paramObject1, Object paramObject2);
  
  public abstract Page<T> getAll(Page<T> paramPage);
  
  public abstract Page<T> findPage(Page<T> paramPage, String paramString, Object[] paramArrayOfObject);
  
  public abstract Page<T> findPage(Page<T> paramPage, String paramString, Map<String, ?> paramMap);
  
  public abstract Page<T> findPage(Page<T> paramPage, Criterion[] paramArrayOfCriterion);
  
  public abstract Page<T> findPage(Page<T> paramPage, DetachedCriteria paramDetachedCriteria);
  
  public abstract Page<T> findPage(Page<T> paramPage, StringBuffer paramStringBuffer, Map<String, ?> paramMap);
  
  public abstract List<T> find(DetachedCriteria paramDetachedCriteria);
  
  public abstract List<T> findBy(String paramString, Object paramObject, PropertyFilter.MatchType paramMatchType);
  
  public abstract List<T> find(List<PropertyFilter> paramList);
  
  public abstract Page<T> findPage(Page<T> paramPage, List<PropertyFilter> paramList);
  
  public abstract Page<T> findPage(Page<T> paramPage, String paramString, List<PropertyFilter> paramList);
  
  public abstract long countHqlResult(String paramString, Object... paramVarArgs);
  
  public abstract long countHqlResult(String paramString, Map<String, ?> paramMap);
}


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.BaseDao
 * JD-Core Version:    0.7.0.1
 */