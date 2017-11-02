package com.chee.action.admin;

import com.chee.annotation.rmpfLog;
import com.chee.entity.Travel;
import com.chee.service.TravelService;
import com.chee.util.HibernateUtils;
import com.chee.util.JsonDateValueProcessor;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;

@ParentPackage("admin")
@Results({ @org.apache.struts2.convention.annotation.Result(name = "index", location = "travel!list.action", type = "redirect") })
public class TravelAction extends BaseAction<Travel, Integer> {
    private static final long serialVersionUID = 4984599292597512018L;
    @Resource
    private TravelService travelService;
    private String travelLine;
    private Travel travel;
    private List<Travel> travelLst;

    public String list() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Travel.class);
        if ((this.travel != null) && (StringUtils.isNotEmpty(this.travel.getLine()))) {
            detachedCriteria.add(Restrictions.sqlRestriction("line like ? escape '/'", HibernateUtils.escapeSQLLike(this.travel.getLine()), Hibernate.STRING));
        }
        this.page.setOrderBy("id");
        this.page.setOrder("desc");
        this.page = this.travelService.findPage(this.page, detachedCriteria);
        return "list";
    }

    @InputConfig(resultName = "error")
    @rmpfLog(desc = "游行线路修改/添加")
    public String save() throws Exception {
        if (this.travel.getId() != null) {
            Travel persistent = (Travel) this.travelService.load(this.travel.getId());
            BeanUtils.copyProperties(this.travel, persistent, new String[] { "id", "lrr", "lrsj" });
            persistent.setLrsj(new Date());
            this.travelService.update(persistent);
        } else {
            this.travel.setLrr(getLoginUser().getName());
            this.travel.setLrsj(new Date());
            this.travelService.save(this.travel);
        }
        this.redirectionUrl = "travel!list.action";
        return "success";
    }

    public String input() {
        if ((this.travel != null) && (this.travel.getId() != null)) {
            this.travel = ((Travel) this.travelService.load(this.travel.getId()));
        } else {
            this.travel = new Travel();
        }
        return "input";
    }

    @rmpfLog(desc = "客户信息删除")
    public String delete() {
        this.travelService.delete(this.travel.getId());
        return "success";
    }

    public String dialog() {
        if (this.travel == null) {
            this.travel = new Travel();
        }
        this.page = this.travelService.findTravelDialogPage(this.page, this.travel);
        return "dialog";
    }

    public void ajaxDialog() {
        if (this.travel == null) {
            this.travel = new Travel();
        }
        try {
            this.travel.setLine(URLDecoder.decode(this.travel.getLine(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        this.page = this.travelService.findTravelDialogPage(this.page, this.travel);
        this.travelLst = this.page.getResult();
        JsonConfig config = new JsonConfig();
        config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONArray jsonArray = JSONArray.fromObject(this.travelLst, config);
        ajaxJson(jsonArray.toString());
    }

    public String ajaxAutoTravel() {
        List<Travel> list = this.travelService.find("from Travel where line like ? order by line asc", new Object[] { "%" + getTravelLine() + "%" });
        List optionList = new ArrayList();
        for (Travel object : list) {
            Map map = new HashMap();
            map.put("line", object.getLine().toString());
            optionList.add(map);
        }
        JSONArray jsonArray = JSONArray.fromObject(optionList);

        return ajaxJson(jsonArray.toString());
    }

    public String getTravelLine() {
        return this.travelLine;
    }

    public void setTravelLine(String travelLine) {
        this.travelLine = travelLine;
    }

    public Travel getTravel() {
        return this.travel;
    }

    public void setTravel(Travel travel) {
        this.travel = travel;
    }

    public List<Travel> getTravelLst() {
        return this.travelLst;
    }

    public void setTravelLst(List<Travel> travelLst) {
        this.travelLst = travelLst;
    }
}
