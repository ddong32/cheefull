package com.chee.dao.impl;

import com.chee.common.Page;
import com.chee.dao.BusinessDao;
import com.chee.entity.Business;
import com.chee.entity.Department;
import com.chee.entity.User;
import com.chee.service.DepartmentService;
import com.chee.util.DateUtil;
import com.chee.util.UserUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class BusinessDaoImpl extends BaseDaoImpl<Business, Integer> implements BusinessDao {
    @Resource
    UserUtil userUtil;
    @Resource
    DepartmentService departmentService;

    public User getLoginUser() {
        return this.userUtil.getLoginUser();
    }

    public Page<Business> findBusinessPage(Page<Business> page, Business business) {
        StringBuffer hqlSb = new StringBuffer();
        hqlSb.append("select");
        hqlSb.append(" this_.id,");
        hqlSb.append(" this_.ddzt,");
        hqlSb.append(" this_.orderId,");
        hqlSb.append(" this_.ywlx,");
        hqlSb.append(" this_.travelLine,");
        hqlSb.append(" this_.lrsj,");
        hqlSb.append(" this_.user.name,");
        hqlSb.append(" this_.adultNo,");
        hqlSb.append(" this_.childNo,");
        hqlSb.append(" this_.escortNo,");
        hqlSb.append(" this_.ygs,");
        hqlSb.append(" this_.ygf,");
        hqlSb.append(" this_.ygs - this_.ygf,");
        hqlSb.append(" this_.yjs,");
        hqlSb.append(" this_.ygs - this_.yjs,");
        hqlSb.append(" this_.yjf,");
        hqlSb.append(" this_.lrsj");
        hqlSb.append(" from Business this_ where 1=1 ");

        Map<String, Object> map = new HashMap();
        if ((business.getSdate() != null) && (!"".equals(business.getSdate()))) {
            if (("1".equals(business.getSdate())) || (business.getSdate() == "1")) {
                if ((business.getBeginDate() != null) && (!"".equals(business.getBeginDate()))) {
                    hqlSb.append("and date_format(this_.cfsj,'%Y-%m-%d')>='" + business.getBeginDate() + "' ");
                }
                if ((business.getEndDate() != null) && (!"".equals(business.getEndDate()))) {
                    hqlSb.append("and date_format(this_.cfsj,'%Y-%m-%d') <= '" + business.getEndDate() + "' ");
                }
            } else {
                if ((business.getBeginDate() != null) && (!"".equals(business.getBeginDate()))) {
                    hqlSb.append("and date_format(this_.lrsj,'%Y-%m-%d') >= '" + business.getBeginDate() + "' ");
                }
                if ((business.getEndDate() != null) && (!"".equals(business.getEndDate()))) {
                    hqlSb.append("and date_format(this_.lrsj,'%Y-%m-%d') <= '" + business.getEndDate() + "' ");
                }
            }
            if (("1".equals(business.getWskchecked())) || (business.getWskchecked() == "1")) {
                hqlSb.append("and (this_.ygs - this_.yjs) <> 0 ");
            }
            if ((business.getDdzt() != null) && (!"".equals(business.getDdzt()))) {
                hqlSb.append("and this_.ddzt =:ddzt ");
                map.put("ddzt", business.getDdzt());
            }
            if ((business.getDdlx() != null) && (!"".equals(business.getDdlx()))) {
                hqlSb.append("and this_.ddlx =:ddlx ");
                map.put("ddlx", business.getDdlx());
            }
            if ((business.getTravelLine() != null) && (!"".equals(business.getTravelLine()))) {
                hqlSb.append("and this_.travelLine like:travelLine ");
                map.put("travelLine", "%" + business.getTravelLine() + "%");
            }
            if ((business.getOrderId() != null) && (!"".equals(business.getOrderId()))) {
                hqlSb.append("and this_.orderId like:orderId ");
                map.put("orderId", business.getOrderId() + "%");
            }
        }
        if ((business.getProUserIds() != null) && (business.getProUserIds().size() > 0)) {
            hqlSb.append("and this_.user.id in (:lrr) ");
            map.put("lrr", business.getProUserIds());
        } else if (!getLoginUser().getRoleValues().contains("ROLE_ADMIN")) {
            if (getLoginUser().getRoleValues().contains("ROLE_MANAGER")) {
                List orgCodeList = this.departmentService.getConnectOrgCodes(getLoginUser().getDepartment().getId());
                if (orgCodeList != null) {
                    hqlSb.append(" and ( ");
                    for (int i = 0; i < orgCodeList.size(); i++) {
                        if (i != 0) {
                            hqlSb.append(" or ");
                        }
                        hqlSb.append(" this_.user.department.code =:orgCode_" + i);
                        map.put("orgCode_" + i, orgCodeList.get(i));
                    }
                    hqlSb.append(" ) ");
                }
            } else {
                hqlSb.append("and this_.user.id =:lrr ");
                map.put("lrr", getLoginUser().getId());
            }
        }
        hqlSb.append("order by this_.ddzt, this_.cfsj desc ");

        return findPage(page, hqlSb.toString(), map);
    }

    public List<Business> findBusinessListTotal(Business business) {
        StringBuffer hqlSb = new StringBuffer();
        hqlSb.append("from Business this_ where 1=1 ");

        Map<String, Object> map = new HashMap();
        if ((business.getSdate() != null) && (!"".equals(business.getSdate()))) {
            if (("1".equals(business.getSdate())) || (business.getSdate() == "1")) {
                if ((business.getBeginDate() != null) && (!"".equals(business.getBeginDate()))) {
                    hqlSb.append("and date_format(this_.cfsj,'%Y-%m-%d')>='" + business.getBeginDate() + "' ");
                }
                if ((business.getEndDate() != null) && (!"".equals(business.getEndDate()))) {
                    hqlSb.append("and date_format(this_.cfsj,'%Y-%m-%d') <= '" + business.getEndDate() + "' ");
                }
            } else {
                if ((business.getBeginDate() != null) && (!"".equals(business.getBeginDate()))) {
                    hqlSb.append("and date_format(this_.lrsj,'%Y-%m-%d') >= '" + business.getBeginDate() + "' ");
                }
                if ((business.getEndDate() != null) && (!"".equals(business.getEndDate()))) {
                    hqlSb.append("and date_format(this_.lrsj,'%Y-%m-%d') <= '" + business.getEndDate() + "' ");
                }
            }
            if (("1".equals(business.getWskchecked())) || (business.getWskchecked() == "1")) {
                hqlSb.append("and (this_.ygs - this_.yjs) <> 0 ");
            }
            if ((business.getDdzt() != null) && (!"".equals(business.getDdzt()))) {
                hqlSb.append("and this_.ddzt =:ddzt ");
                map.put("ddzt", business.getDdzt());
            }
            if ((business.getDdlx() != null) && (!"".equals(business.getDdlx()))) {
                hqlSb.append("and this_.ddlx =:ddlx ");
                map.put("ddlx", business.getDdlx());
            }
            if ((business.getTravelLine() != null) && (!"".equals(business.getTravelLine()))) {
                hqlSb.append("and this_.travelLine like:travelLine ");
                map.put("travelLine", "%" + business.getTravelLine() + "%");
            }
            if ((business.getOrderId() != null) && (!"".equals(business.getOrderId()))) {
                hqlSb.append("and this_.orderId like:orderId ");
                map.put("orderId", business.getOrderId() + "%");
            }
        }
        if ((business.getUserids() != null) && (!"".equals(business.getUserids()))) {
            hqlSb.append("and this_.user.id in (:lrr) ");
            List<Integer> proUserIds = new ArrayList();
            String[] strArray = business.getUserids().split(",");
            for (int i = 0; i < strArray.length; i++) {
                proUserIds.add(Integer.valueOf(Integer.parseInt(strArray[i])));
            }
            map.put("lrr", proUserIds);
        } else if (!getLoginUser().getRoleValues().contains("ROLE_ADMIN")) {
            if (getLoginUser().getRoleValues().contains("ROLE_MANAGER")) {
                List orgCodeList = this.departmentService.getConnectOrgCodes(getLoginUser().getDepartment().getId());
                if (orgCodeList != null) {
                    hqlSb.append(" and ( ");
                    for (int i = 0; i < orgCodeList.size(); i++) {
                        if (i != 0) {
                            hqlSb.append(" or ");
                        }
                        hqlSb.append(" this_.user.department.code =:orgCode_" + i);
                        map.put("orgCode_" + i, orgCodeList.get(i));
                    }
                    hqlSb.append(" ) ");
                }
            } else {
                hqlSb.append("and this_.user.id =:lrr ");
                map.put("lrr", getLoginUser().getId());
            }
        }
        hqlSb.append("order by this_.ddzt, this_.cfsj desc ");

        return find(hqlSb.toString(), map);
    }

    public int updateBusinessStat(Integer id, String stat) {
        String sql = "UPDATE chee_business SET ddzt = '" + stat + "' WHERE id = " + id;
        return getSession().createSQLQuery(sql).executeUpdate();
    }

    public Business findBusinessRecountJe(String orderId) {
        StringBuffer hqlSb = new StringBuffer();
        hqlSb.append("FROM Business WHERE 1=1 and orderId = '" + orderId + "'");
        return (Business) findUnique(hqlSb.toString(), new Object[0]);
    }

    public long noGenerate() {
        String hql = "select count(*) from Business where date_format(lrsj,'%Y-%m-%d') ='" + DateUtil.formatDate(new Date(), "yyyy-MM-dd") + "'";
        return ((Long) getSession().createQuery(hql).uniqueResult()).longValue();
    }

    public List<String> getcfsjorderid(Date cfsj) {
        StringBuffer hqlSb = new StringBuffer();
        hqlSb.append("select orderId from Business");
        hqlSb.append(" where date_format(cfsj,'%Y-%m-%d') ='" + DateUtil.formatDate(cfsj, "yyyy-MM-dd") + "'");
        hqlSb.append(" and orderId like'" + DateUtil.DateToString(cfsj, "yyyyMMdd") + "%'");
        hqlSb.append(" order by orderId");
        return find(hqlSb.toString(), new Object[0]);
    }
}
