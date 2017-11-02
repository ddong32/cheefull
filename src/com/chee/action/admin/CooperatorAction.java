package com.chee.action.admin;

import com.chee.annotation.rmpfLog;
import com.chee.common.Page;
import com.chee.entity.Cooperator;
import com.chee.entity.User;
import com.chee.service.CooperatorService;
import com.chee.util.HibernateUtils;
import com.chee.util.JsonDateValueProcessor;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.Validations;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
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
@Results({ @org.apache.struts2.convention.annotation.Result(name = "index", location = "cooperator!list.action", type = "redirect") })
public class CooperatorAction extends BaseAction<Cooperator, Integer> {
    private static final long serialVersionUID = 4984599292597512018L;
    @Resource
    private CooperatorService cooperatorService;
    private Cooperator cooperator;
    private List<Cooperator> cooperatorlist;

    public String list() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Cooperator.class);
        if ((this.cooperator != null) && (StringUtils.isNotEmpty(this.cooperator.getType()))) {
            detachedCriteria.add(Restrictions.eq("type", this.cooperator.getType()));
        }
        if ((this.cooperator != null) && (StringUtils.isNotEmpty(this.cooperator.getDwmc()))) {
            detachedCriteria.add(Restrictions.sqlRestriction("dwmc like ? escape '/'", HibernateUtils.escapeSQLLike(this.cooperator.getDwmc()), Hibernate.STRING));
        }
        this.page.setOrderBy("sort");
        this.page.setOrder("asc");
        this.page = this.cooperatorService.findPage(this.page, detachedCriteria);
        return "list";
    }

    @Validations(stringLengthFields = {
            @com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator(fieldName = "cooperator.dwmc", minLength = "1", maxLength = "256", message = "角色名称长度必须在${minLength}到${maxLength}之间!"),
            @com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator(fieldName = "cooperator.lxr", minLength = "1", maxLength = "256", message = "角色标识长度必须在${minLength}到${maxLength}之间!"),
            @com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator(fieldName = "cooperator.lxdh", maxLength = "256", message = "角色描述长度不允许大于${maxLength}!") })
    @InputConfig(resultName = "error")
    @rmpfLog(desc = "供应商修改/添加")
    public String save() throws Exception {
        if (this.cooperator.getId() != null) {
            Cooperator persistent = (Cooperator) this.cooperatorService.load(this.cooperator.getId());
            BeanUtils.copyProperties(this.cooperator, persistent, new String[] { "id", "gxsj", "lrr", "lrsj" });
            persistent.setGxsj(new Date());
            this.cooperatorService.update(persistent);
        } else {
            if (!this.cooperatorService.isUnique("dwmc", null, this.cooperator.getDwmc())) {
                addActionError("单位名称: " + this.cooperator.getDwmc() + " 已经存在请重新命名!");
                return "error";
            }
            this.cooperator.setLrr(getLoginUser().getName());
            this.cooperator.setLrsj(new Date());
            this.cooperatorService.save(this.cooperator);
        }
        this.redirectionUrl = "cooperator!list.action";
        return "success";
    }

    public String input() {
        if ((this.cooperator != null) && (this.cooperator.getId() != null)) {
            this.cooperator = ((Cooperator) this.cooperatorService.load(this.cooperator.getId()));
        } else {
            this.cooperator = new Cooperator();
        }
        return "input";
    }

    @rmpfLog(desc = "客户信息删除")
    public String delete() {
        this.cooperatorService.delete(this.cooperator.getId());
        this.redirectionUrl = "cooperator!list.action";
        return "success";
    }

    public String dialog() {
        if (this.cooperator == null) {
            this.cooperator = new Cooperator();
        }
        this.page = this.cooperatorService.findCooperatorDialogPage(this.page, this.cooperator);
        return "dialog";
    }

    public void ajaxDialog() {
        if (this.cooperator == null) {
            this.cooperator = new Cooperator();
        }
        try {
            this.cooperator.setDwmc(URLDecoder.decode(this.cooperator.getDwmc(), "utf-8"));
            this.cooperator.setLxr(URLDecoder.decode(this.cooperator.getLxr(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        this.page = this.cooperatorService.findCooperatorDialogPage(this.page, this.cooperator);
        this.cooperatorlist = this.page.getResult();
        JsonConfig config = new JsonConfig();
        config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONArray jsonArray = JSONArray.fromObject(this.cooperatorlist, config);
        ajaxJson(jsonArray.toString());
    }

    public Cooperator getCooperator() {
        return this.cooperator;
    }

    public void setCooperator(Cooperator cooperator) {
        this.cooperator = cooperator;
    }

    public List<Cooperator> getCooperatorlist() {
        return this.cooperatorlist;
    }

    public void setCooperatorlist(List<Cooperator> cooperatorlist) {
        this.cooperatorlist = cooperatorlist;
    }
}
