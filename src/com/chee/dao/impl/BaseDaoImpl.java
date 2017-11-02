package com.chee.dao.impl;

import com.chee.common.Page;
import com.chee.common.PropertyFilter;
import com.chee.dao.BaseDao;
import com.chee.util.DateUtil;
import com.chee.util.ReflectionUtils;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class BaseDaoImpl<T, PK extends Serializable> implements BaseDao<T, PK> {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    private Class<T> entityClass;
    @Resource
    protected SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    public BaseDaoImpl() {
        this.entityClass = null;
        Class<T> c = (Class<T>) getClass();
        Type type = c.getGenericSuperclass();
        if ((type instanceof ParameterizedType)) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            this.entityClass = ((Class) parameterizedType[0]);
        }
    }

    public T load(PK id) {
        Assert.notNull(id, "id is required");
        return (T) getSession().load(this.entityClass, id);
    }

    public List<T> get(PK[] ids) {
        Assert.notEmpty(ids, "ids must not be empty");
        String hql = "from " + this.entityClass.getName() + " as model where model.id in(:ids)";
        return getSession().createQuery(hql).setParameterList("ids", ids).list();
    }

    public T get(String propertyName, Object value) {
        Assert.hasText(propertyName, "propertyName must not be empty");
        Assert.notNull(value, "value is required");
        String hql = "from " + this.entityClass.getName() + " as model where model." + propertyName + " = ?";
        return (T) getSession().createQuery(hql).setParameter(0, value).uniqueResult();
    }

    public List<T> getList(String propertyName, Object value) {
        Assert.hasText(propertyName, "propertyName must not be empty");
        Assert.notNull(value, "value is required");
        String hql = "from " + this.entityClass.getName() + " as model where model." + propertyName + " = ?";
        return getSession().createQuery(hql).setParameter(0, value).list();
    }

    public Long getTotalCount() {
        String hql = "select count(*) from " + this.entityClass.getName();
        return (Long) getSession().createQuery(hql).uniqueResult();
    }

    public boolean isUnique(String propertyName, Object oldValue, Object newValue) {
        Assert.hasText(propertyName, "propertyName must not be empty");
        Assert.notNull(newValue, "newValue is required");
        if ((newValue == oldValue) || (newValue.equals(oldValue))) {
            return true;
        }
        if (((newValue instanceof String)) && (oldValue != null) && (StringUtils.equalsIgnoreCase((String) oldValue, (String) newValue))) {
            return true;
        }
        T object = get(propertyName, newValue);
        return object == null;
    }

    public boolean isPropertyUnique(String propertyName, Object newValue, Object oldValue) {
        if ((newValue == null) || (newValue.equals(oldValue))) {
            return true;
        }
        Object object = findUniqueBy(propertyName, newValue);
        return object == null;
    }

    public boolean isExist(String propertyName, Object value) {
        Assert.hasText(propertyName, "propertyName must not be empty");
        Assert.notNull(value, "value is required");
        T object = get(propertyName, value);
        return object != null;
    }

    public void update(T entity) {
        Assert.notNull(entity, "entity is required");
        getSession().update(entity);
    }

    public void delete(PK[] ids) {
        Assert.notEmpty(ids, "ids must not be empty");
        for (PK id : ids) {
            T entity = load(id);
            getSession().delete(entity);
        }
    }

    public void clear() {
        getSession().clear();
    }

    public void evict(Object object) {
        Assert.notNull(object, "object is required");
        getSession().evict(object);
    }

    public List<T> getAllDistinct() {
        Collection<T> result = new LinkedHashSet(getAll());
        return new ArrayList(result);
    }

    public boolean exists(PK id) {
        T entity = (T) getSession().get(this.entityClass, id);
        return entity != null;
    }

    public Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    public void saveOrUpdate(T entity) {
        Assert.notNull(entity, "entity不能为空");
        getSession().saveOrUpdate(entity);
        this.logger.debug("save entity: {}", entity);
    }

    public PK save(T entity) {
        Assert.notNull(entity, "entity is required");
        return (PK) getSession().save(entity);
    }

    public void delete(T entity) {
        Assert.notNull(entity, "entity不能为空");
        getSession().delete(entity);
        this.logger.error("delete entity: {}", entity);
    }

    public void delete(PK id) {
        Assert.notNull(id, "id不能为空");
        T entity = get(id);
        if (entity != null) {
            delete(entity);
        }
        this.logger.error("delete entity {},id is {}", this.entityClass.getSimpleName(), id);
    }

    public T get(PK id) {
        Assert.notNull(id, "id不能为空");
        return (T) getSession().get(this.entityClass, id);
    }

    public List<T> get(Collection<PK> ids) {
        return find(new Criterion[] { Restrictions.in(getIdName(), ids) });
    }

    public List<T> getAll() {
        return find(new Criterion[0]);
    }

    public List<T> getAll(String orderByProperty, boolean isAsc) {
        Criteria c = createCriteria(new Criterion[0]);
        if (isAsc) {
            c.addOrder(Order.asc(orderByProperty));
        } else {
            c.addOrder(Order.desc(orderByProperty));
        }
        return c.list();
    }

    public List<T> findBy(String propertyName, Object value) {
        Assert.hasText(propertyName, "propertyName不能为空");
        Criterion criterion = Restrictions.eq(propertyName, value);
        return find(new Criterion[] { criterion });
    }

    public T findUniqueBy(String propertyName, Object value) {
        Assert.hasText(propertyName, "propertyName不能为空");
        Criterion criterion = Restrictions.eq(propertyName, value);
        return (T) createCriteria(new Criterion[] { criterion }).uniqueResult();
    }

    public <X> List<X> find(String hql, Object... values) {
        return createQuery(hql, values).list();
    }

    public <X> List<X> find(String hql, Map<String, ?> values) {
        return createQuery(hql, values).list();
    }

    public <X> X findUnique(String hql, Object... values) {
        return (X) createQuery(hql, values).uniqueResult();
    }

    public <X> X findUnique(String hql, Map<String, ?> values) {
        return (X) createQuery(hql, values).uniqueResult();
    }

    public int batchExecute(String hql, Object... values) {
        return createQuery(hql, values).executeUpdate();
    }

    public int batchExecute(String hql, Map<String, ?> values) {
        return createQuery(hql, values).executeUpdate();
    }

    public Query createQuery(String queryString, Object... values) {
        Assert.hasText(queryString, "queryString不能为空");
        Query query = getSession().createQuery(queryString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }

    public Query createQuery(String queryString, Map<String, ?> values) {
        Assert.hasText(queryString, "queryString不能为空");
        Query query = getSession().createQuery(queryString);
        if (values != null) {
            query.setProperties(values);
        }
        return query;
    }

    public List<T> find(Criterion... criterions) {
        return createCriteria(criterions).list();
    }

    public T findUnique(Criterion... criterions) {
        return (T) createCriteria(criterions).uniqueResult();
    }

    public T findUnique(DetachedCriteria detachedCriteria) {
        return (T) createCriteria(detachedCriteria).uniqueResult();
    }

    public Criteria createCriteria(Criterion... criterions) {
        Criteria criteria = getSession().createCriteria(this.entityClass);
        for (Criterion c : criterions) {
            criteria.add(c);
        }
        return criteria;
    }

    public Criteria createCriteria(DetachedCriteria detachedCriteria) {
        Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
        return criteria;
    }

    public void initProxyObject(Object proxy) {
        Hibernate.initialize(proxy);
    }

    public void flush() {
        getSession().flush();
    }

    public Query distinct(Query query) {
        query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return query;
    }

    public Criteria distinct(Criteria criteria) {
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria;
    }

    public String getIdName() {
        ClassMetadata meta = getSessionFactory().getClassMetadata(this.entityClass);
        return meta.getIdentifierPropertyName();
    }

    public Page<T> getAll(Page<T> page) {
        return findPage(page, new Criterion[0]);
    }

    public Page<T> findPage(Page<T> page, String hql, Object... values) {
        Assert.notNull(page, "page不能为空");
        Query q = createQuery(hql, values);
        if (page.isAutoCount()) {
            long totalCount = countHqlResult(hql, values);
            page.setTotalCount(totalCount);
        }
        setPageParameterToQuery(q, page);
        List<T> result = q.list();
        page.setResult(result);
        return page;
    }

    public Page<T> findPage(Page<T> page, String hql, Map<String, ?> values) {
        Assert.notNull(page, "page不能为空");
        Query q = createQuery(hql, values);
        if (page.isAutoCount()) {
            long totalCount = countHqlResult(hql, values);
            page.setTotalCount(totalCount);
        }
        setPageParameterToQuery(q, page);
        List<T> result = q.list();
        page.setResult(result);
        return page;
    }

    public Page<T> findPage(Page<T> page, Criterion... criterions) {
        Assert.notNull(page, "page不能为空");
        Criteria c = createCriteria(criterions);
        if (page.isAutoCount()) {
            long totalCount = countCriteriaResult(c);
            page.setTotalCount(totalCount);
        }
        setPageParameterToCriteria(c, page);
        List<T> result = c.list();
        page.setResult(result);
        return page;
    }

    public Page<T> findPage(Page<T> page, DetachedCriteria detachedCriteria) {
        Assert.notNull(page, "page不能为空");
        Criteria c = createCriteria(detachedCriteria);
        if (page.isAutoCount()) {
            long totalCount = countCriteriaResult(c);
            page.setTotalCount(totalCount);
        }
        setPageParameterToCriteria(c, page);
        List<T> result = c.list();
        page.setResult(result);
        return page;
    }

    public Page<T> findPage(Page<T> page, StringBuffer sqlsb, Map<String, ?> values) {
        Assert.notNull(page, "page不能为空");
        Query q = getSession().createSQLQuery(sqlsb.toString());
        if (values != null) {
            q.setProperties(values);
        }
        int totalCount = q.list().size();
        if (page.isAutoCount()) {
            page.setTotalCount(totalCount);
        }
        setPageParameterToQuery(q, page);
        List<T> result = q.list();
        page.setResult(result);
        return page;
    }

    public List<T> find(DetachedCriteria detachedCriteria) {
        Criteria c = createCriteria(detachedCriteria);
        return c.list();
    }

    protected Query setPageParameterToQuery(Query q, Page<T> page) {
        Assert.isTrue(page.getPageSize() > 0, "Page Size must larger than zero");
        if (page.getPageNo() > page.getTotalPages()) {
            page.setPageNo(1);
        }
        q.setFirstResult(page.getFirst() - 1);
        q.setMaxResults(page.getPageSize());
        return q;
    }

    protected Criteria setPageParameterToCriteria(Criteria c, Page<T> page) {
        Assert.isTrue(page.getPageSize() > 0, "Page Size must larger than zero");
        if (page.getPageNo() > page.getTotalPages()) {
            page.setPageNo(1);
        }
        c.setFirstResult(page.getFirst() - 1);
        c.setMaxResults(page.getPageSize());
        if (page.isOrderBySetted()) {
            String[] orderByArray = StringUtils.split(page.getOrderBy(), ',');
            String[] orderArray = StringUtils.split(page.getOrder(), ',');
            Assert.isTrue(orderByArray.length == orderArray.length, "分页多重排序参数中,排序字段与排序方向的个数不相等");
            for (int i = 0; i < orderByArray.length; i++) {
                if ("asc".equals(orderArray[i])) {
                    c.addOrder(Order.asc(orderByArray[i]));
                } else {
                    c.addOrder(Order.desc(orderByArray[i]));
                }
            }
        }
        return c;
    }

    public long countHqlResult(String hql, Object... values) {
        String countHql = prepareCountHql(hql);
        try {
            Long count = (Long) findUnique(countHql, values);
            return count.longValue();
        } catch (Exception e) {
            throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
        }
    }

    public long countHqlResult(String hql, Map<String, ?> values) {
        String countHql = prepareCountHql(hql);
        try {
            Long count = (Long) findUnique(countHql, values);
            return count.longValue();
        } catch (Exception e) {
            throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
        }
    }

    private String prepareCountHql(String orgHql) {
        String fromHql = orgHql;

        fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
        fromHql = StringUtils.substringBefore(fromHql, "order by");
        String countHql = "select count(*) " + fromHql;
        return countHql;
    }

    protected long countCriteriaResult(Criteria c) {
        CriteriaImpl impl = (CriteriaImpl) c;

        Projection projection = impl.getProjection();
        ResultTransformer transformer = impl.getResultTransformer();
        List<CriteriaImpl.OrderEntry> orderEntries = null;
        try {
            orderEntries = (List) ReflectionUtils.getFieldValue(impl, "orderEntries");
            ReflectionUtils.setFieldValue(impl, "orderEntries", new ArrayList());
        } catch (Exception e) {
            this.logger.error("不可能抛出的异常:{}", e.getMessage());
        }
        Long totalCountObject = Long.valueOf(Long.parseLong(c.setProjection(Projections.rowCount()).uniqueResult().toString()));
        long totalCount = totalCountObject != null ? totalCountObject.longValue() : 0L;

        c.setProjection(projection);
        if (projection == null) {
            c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        }
        if (transformer != null) {
            c.setResultTransformer(transformer);
        }
        try {
            ReflectionUtils.setFieldValue(impl, "orderEntries", orderEntries);
        } catch (Exception e) {
            this.logger.error("不可能抛出的异常:{}", e.getMessage());
        }
        return totalCount;
    }

    public List<T> findBy(String propertyName, Object value, PropertyFilter.MatchType matchType) {
        Criterion criterion = buildCriterion(propertyName, value, matchType);
        return find(new Criterion[] { criterion });
    }

    public List<T> find(List<PropertyFilter> filters) {
        Criterion[] criterions = buildCriterionByPropertyFilter(filters);
        return find(criterions);
    }

    public Page<T> findPage(Page<T> page, List<PropertyFilter> filters) {
        Criterion[] criterions = buildCriterionByPropertyFilter(filters);
        return findPage(page, criterions);
    }

    public Page<T> findPage(Page<T> page, String sql, List<PropertyFilter> filters) {
        Assert.notNull(page, "page不能为空");
        StringBuffer sb = new StringBuffer();
        sb.append(sql);
        sb.append(buildSqlByPropertyFilter(filters));
        if (page.isOrderBySetted()) {
            if ("asc".equals(page.getOrder())) {
                sb.append(" order by " + page.getOrderBy() + " asc ");
            } else {
                sb.append(" order by " + page.getOrderBy() + " desc ");
            }
        }
        Query q = createQuery(sb.toString(), new Object[0]);
        if (page.isAutoCount()) {
            long totalCount = countHqlResult(sb.toString(), new Object[0]);
            page.setTotalCount(totalCount);
        }
        setPageParameterToQuery(q, page);
        List<T> result = q.list();
        page.setResult(result);
        return page;
    }

    protected Criterion buildCriterion(String propertyName, Object propertyValue, PropertyFilter.MatchType matchType) {
        Assert.hasText(propertyName, "propertyName不能为空");
        Criterion criterion = null;
        switch (matchType) {
        case EQ:
            criterion = Restrictions.eq(propertyName, propertyValue);
            break;
        case GE:
            criterion = Restrictions.like(propertyName, (String) propertyValue, MatchMode.ANYWHERE);
            break;
        case ISNULL:
            criterion = Restrictions.le(propertyName, propertyValue);
            break;
        case GT:
            criterion = Restrictions.lt(propertyName, propertyValue);
            break;
        case LE:
            criterion = Restrictions.ge(propertyName, propertyValue);
            break;
        case ISNOTNULL:
            criterion = Restrictions.gt(propertyName, propertyValue);
            break;
        case LIKE:
            criterion = Restrictions.isNull(propertyName);
            break;
        case LT:
            criterion = Restrictions.isNotNull(propertyName);
        }
        return criterion;
    }

    protected String buildSqlStr(String propertyName, Object propertyValue, PropertyFilter.MatchType matchType) {
        Assert.hasText(propertyName, "propertyName不能为空");
        String sql = "";
        switch (matchType) {
        case EQ:
            sql = propertyName + " = " + PropertyTypeToString(propertyValue);
            break;
        case GE:
            sql = propertyName + " like '%" + propertyValue + "%' ";
            break;
        case ISNULL:
            sql = propertyName + " <= " + PropertyTypeToString(propertyValue) + " ";
            break;
        case GT:
            sql = propertyName + " < " + PropertyTypeToString(propertyValue) + " ";
            break;
        case LE:
            sql = propertyName + " >= " + PropertyTypeToString(propertyValue) + " ";
            break;
        case ISNOTNULL:
            sql = propertyName + " > " + PropertyTypeToString(propertyValue) + " ";
            break;
        case LIKE:
            sql = propertyName + " is null ";
            break;
        case LT:
            sql = propertyName + " is not null ";
        }
        return sql;
    }

    protected String PropertyTypeToString(Object propertyValue) {
        if ((propertyValue instanceof String)) {
            propertyValue = "'" + propertyValue + "'";
        } else if ((propertyValue instanceof Date)) {
            propertyValue = " to_date('" + DateUtil.formatDate((Date) propertyValue, "yyyy-MM-dd HH:mm:ss") + "','yyyy-MM-dd hh24:mi:ss')";
        }
        return propertyValue.toString();
    }

    protected Criterion[] buildCriterionByPropertyFilter(List<PropertyFilter> filters) {
        List<Criterion> criterionList = new ArrayList();
        for (PropertyFilter filter : filters) {
            if (!filter.hasMultiProperties()) {
                Criterion criterion = buildCriterion(filter.getPropertyName(), filter.getMatchValue(), filter.getMatchType());
                criterionList.add(criterion);
            } else {
                Disjunction disjunction = Restrictions.disjunction();
                for (String param : filter.getPropertyNames()) {
                    Criterion criterion = buildCriterion(param, filter.getMatchValue(), filter.getMatchType());
                    disjunction.add(criterion);
                }
                criterionList.add(disjunction);
            }
        }
        return (Criterion[]) criterionList.toArray(new Criterion[criterionList.size()]);
    }

    protected StringBuffer buildSqlByPropertyFilter(List<PropertyFilter> filters) {
        StringBuffer sb = new StringBuffer();
        for (PropertyFilter filter : filters) {
            if (!filter.hasMultiProperties()) {
                String sql = buildSqlStr(filter.getPropertyName(), filter.getMatchValue(), filter.getMatchType());
                sb.append(" and ");
                sb.append(sql);
            } else {
                StringBuffer orSb = new StringBuffer();
                orSb.append(" and ( ");
                int c = 0;
                for (String param : filter.getPropertyNames()) {
                    String sql = buildSqlStr(param, filter.getMatchValue(), filter.getMatchType());
                    if (c != 0) {
                        orSb.append(" or ");
                    }
                    orSb.append(sql);
                    c++;
                }
                sb.append(orSb.toString());
                sb.append(") ");
            }
        }
        return sb;
    }

    public String getFormatMoney(double s) {
        DecimalFormat fmt = new DecimalFormat("##,##0.00");
        return fmt.format(s);
    }
}
