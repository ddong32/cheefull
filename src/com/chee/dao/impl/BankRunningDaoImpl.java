package com.chee.dao.impl;

import com.chee.common.Page;
import com.chee.dao.BankRunningDao;
import com.chee.entity.Bank;
import com.chee.entity.BankRunning;
import com.chee.entity.Business;
import com.chee.util.DateUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class BankRunningDaoImpl extends BaseDaoImpl<BankRunning, Integer> implements BankRunningDao {
    public Page<BankRunning> findBankRunningPage(Page<BankRunning> page, BankRunning bankRunning) {
        StringBuffer hqlSb = new StringBuffer();
        hqlSb.append("select");
        hqlSb.append(" this_.id,");
        hqlSb.append(" this_.shbj,");
        hqlSb.append(" b.djm,");
        hqlSb.append(" this_.jzsj,");
        hqlSb.append(" b.cush,");
        hqlSb.append(" this_.srje,");
        hqlSb.append(" this_.zcje,");
        hqlSb.append(" this_.dfzh,");
        hqlSb.append(" this_.dfhm,");
        hqlSb.append(" this_.lrr,");
        hqlSb.append(" this_.lrsj,");
        hqlSb.append(" this_.bz,");
        hqlSb.append(" this_.orderId");
        hqlSb.append(" from BankRunning this_ left join this_.bank b left join this_.business a where 1=1");

        Map<String, Object> map = new HashMap();
        if ((bankRunning.getBeginDate() != null) && (!"".equals(bankRunning.getBeginDate()))) {
            hqlSb.append(" and date_format(this_.jzsj,'%Y-%m-%d') >= '" + bankRunning.getBeginDate() + "'");
        }
        if ((bankRunning.getEndDate() != null) && (!"".equals(bankRunning.getEndDate()))) {
            hqlSb.append(" and date_format(this_.jzsj,'%Y-%m-%d') <= '" + bankRunning.getEndDate() + "'");
        }
        if ((bankRunning.getSflx() != null) && (!"".equals(bankRunning.getSflx()))) {
            hqlSb.append(" and this_.sflx = " + bankRunning.getSflx());
        }
        if ((bankRunning.getBusiness() != null) && (bankRunning.getBusiness().getDdlx() != null) && (!"".equals(bankRunning.getBusiness().getDdlx()))) {
            hqlSb.append(" and a.ddlx = " + bankRunning.getBusiness().getDdlx());
        }
        if ((bankRunning.getBank() != null) && (bankRunning.getBank().getId() != null) && (!"".equals(bankRunning.getBank().getId()))) {
            hqlSb.append(" and b.id = " + bankRunning.getBank().getId());
        }
        if ((bankRunning.getShbj() != null) && (!"".equals(bankRunning.getShbj()))) {
            hqlSb.append(" and this_.shbj =:shbj ");
            map.put("shbj", bankRunning.getShbj());
        }
        if ((bankRunning.getBeginJe() != null) && (!"".equals(bankRunning.getBeginJe()))) {
            hqlSb.append(" and abs(this_.lrje) >= " + bankRunning.getBeginJe());
        }
        if ((bankRunning.getEndJe() != null) && (!"".equals(bankRunning.getEndJe()))) {
            hqlSb.append(" and abs(this_.lrje) <= " + bankRunning.getEndJe());
        }
        hqlSb.append(" order by this_.shbj asc, this_.jzsj desc");

        List<Object[]> list = find(hqlSb.toString(), map);

        double sumZcje = 0.0D;
        double sumSrje = 0.0D;
        for (Object[] bean : list) {
            sumSrje += Double.parseDouble(bean[5].toString());
            sumZcje += Double.parseDouble(bean[6].toString());
        }
        page.setSumSrje(getFormatMoney(sumSrje));
        page.setSumZcje(getFormatMoney(sumZcje));

        return findPage(page, hqlSb.toString(), map);
    }

    public BankRunning findBankRunningUnique(BankRunning bankRunning) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BankRunning.class);
        if (bankRunning != null) {
            if ((bankRunning.getBank() != null) && (bankRunning.getBank().getId() == null)) {
                detachedCriteria.createAlias("bank", "bank");
                detachedCriteria.add(Restrictions.eq("bank.id", bankRunning.getBank().getId()));
            }
            if ("1".equals(bankRunning.getSflx())) {
                detachedCriteria.add(Restrictions.eq("sflx", bankRunning.getSflx()));
                detachedCriteria.add(Restrictions.eq("srje", Double.valueOf(bankRunning.getJe())));
            } else {
                detachedCriteria.add(Restrictions.eq("sflx", bankRunning.getSflx()));
                detachedCriteria.add(Restrictions.eq("zcje", Double.valueOf(bankRunning.getJe())));
            }
            if (bankRunning.getJzsj() != null) {
                detachedCriteria.add(Restrictions.eq("jzsj", bankRunning.getJzsj()));
            }
            if ((bankRunning.getDfzh() != null) && (!"".equals(bankRunning.getDfzh()))) {
                detachedCriteria.add(Restrictions.eq("dfzh", bankRunning.getDfzh()));
            }
            if ((bankRunning.getDfhm() != null) && (!"".equals(bankRunning.getDfhm()))) {
                detachedCriteria.add(Restrictions.eq("dfhm", bankRunning.getDfhm()));
            }
        }
        return (BankRunning) findUnique(detachedCriteria);
    }

    public List<BankRunning> findAuditBankRunningList(BankRunning bankRunning) {
        StringBuffer hqlSb = new StringBuffer();
        hqlSb.append("select a from BankRunning a left join a.bank b where 1=1 ");

        Map<String, Object> map = new HashMap();
        if ((bankRunning.getJzsj() != null) && (!"".equals(bankRunning.getJzsj()))) {
            hqlSb.append("and date_format(a.jzsj,'%Y-%m-%d') = '" + DateUtil.formatDate(bankRunning.getJzsj(), "yyyy-MM-dd") + "'");
        }
        if ((bankRunning.getBank() != null) && (bankRunning.getBank().getId() != null) && (!"".equals(bankRunning.getBank().getId()))) {
            hqlSb.append("and b.id = '" + bankRunning.getBank().getId() + "'");
        }
        if ((bankRunning.getId() != null) && (!"".equals(bankRunning.getId()))) {
            hqlSb.append("and a.id = '" + bankRunning.getId() + "'");
        } else {
            hqlSb.append("and a.shbj = '0' ");
        }
        if ((bankRunning.getSflx() != null) && (!"".equals(bankRunning.getSflx()))) {
            hqlSb.append("and a.sflx =:sflx ");
            map.put("sflx", bankRunning.getSflx());
            if (("1".equals(bankRunning.getSflx())) || (bankRunning.getSflx() == "1")) {
                hqlSb.append("and a.srje = '" + bankRunning.getJe() + "' ");
            } else {
                hqlSb.append("and a.zcje = '" + bankRunning.getJe() + "' ");
            }
        }
        return find(hqlSb.toString(), map);
    }

    public List<BankRunning> findBankRunningListTotal(BankRunning bankRunning) {
        StringBuffer hqlSb = new StringBuffer();
        hqlSb.append(" from BankRunning this_ where 1=1 ");

        Map<String, Object> map = new HashMap();
        if ((bankRunning.getBeginDate() != null) && (!"".equals(bankRunning.getBeginDate()))) {
            hqlSb.append(" and date_format(this_.jzsj,'%Y-%m-%d') >= '" + bankRunning.getBeginDate() + "'");
        }
        if ((bankRunning.getEndDate() != null) && (!"".equals(bankRunning.getEndDate()))) {
            hqlSb.append(" and date_format(this_.jzsj,'%Y-%m-%d') <= '" + bankRunning.getEndDate() + "'");
        }
        if ((bankRunning.getSflx() != null) && (!"".equals(bankRunning.getSflx()))) {
            hqlSb.append(" and this_.sflx =:sflx ");
            map.put("sflx", bankRunning.getSflx());
        }
        if ((bankRunning.getBank() != null) && (bankRunning.getBank().getId() != null) && (!"".equals(bankRunning.getBank().getId()))) {
            hqlSb.append(" and b.id = " + bankRunning.getBank().getId());
        }
        if ((bankRunning.getShbj() != null) && (!"".equals(bankRunning.getShbj()))) {
            hqlSb.append(" and this_.shbj =:shbj ");
            map.put("shbj", bankRunning.getShbj());
        }
        if ((bankRunning.getBeginJe() != null) && (!"".equals(bankRunning.getBeginJe()))) {
            hqlSb.append(" and (this_.srje >= " + bankRunning.getBeginJe() + " or this_.zcje >= " + bankRunning.getBeginJe() + ")");
        }
        if ((bankRunning.getEndJe() != null) && (!"".equals(bankRunning.getEndJe()))) {
            hqlSb.append(" and (this_.srje <= " + bankRunning.getEndJe() + " and this_.zcje <= " + bankRunning.getEndJe() + ")");
        }
        hqlSb.append(" order by this_.shbj asc, this_.jzsj desc ");

        return find(hqlSb.toString(), map);
    }
}
