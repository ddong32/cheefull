package com.chee.action.admin;

import com.chee.annotation.rmpfLog;
import com.chee.entity.Affiche;
import com.chee.entity.User;
import com.chee.service.AfficheService;
import com.chee.util.HibernateUtils;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.Validations;
import java.util.Date;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;

@ParentPackage("admin")
@Results({ @org.apache.struts2.convention.annotation.Result(name = "index", location = "affiche!list.action", type = "redirect") })
public class AfficheAction extends BaseAction<Affiche, Integer> {
    private static final long serialVersionUID = 1L;
    @Resource
    private AfficheService afficheService;
    private Affiche affiche;
    private Integer[] ids;

    @InputConfig(resultName = "error")
    public String list() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Affiche.class);
        if ((this.affiche != null) && (StringUtils.isNotEmpty(this.affiche.getTitle()))) {
            detachedCriteria.add(Restrictions.sqlRestriction("TITLE like ? escape '/'", HibernateUtils.escapeSQLLike(this.affiche.getTitle()), Hibernate.STRING));
        }
        this.page.setOrderBy("id");
        this.page.setOrder("desc");
        this.page = this.afficheService.findPage(this.page, detachedCriteria);
        return "list";
    }

    @rmpfLog(desc = "公告修改")
    public String input() {
        if ((this.affiche != null) && (this.affiche.getId() != null)) {
            this.affiche = ((Affiche) this.afficheService.get(this.affiche.getId()));
        } else {
            this.affiche = new Affiche();
        }
        return "input";
    }

    @rmpfLog(desc = "公告修改/添加 ")
    @Validations(requiredStrings = { @com.opensymphony.xwork2.validator.annotations.RequiredStringValidator(fieldName = "affiche.title", message = "标题不允许为空!"),
            @com.opensymphony.xwork2.validator.annotations.RequiredStringValidator(fieldName = "affiche.content", message = "内容不允许为空!") }, stringLengthFields = {
            @com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator(fieldName = "affiche.title", maxLength = "256", message = "标题名称长度不允许大于${maxLength}!"),
            @com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator(fieldName = "affiche.content", maxLength = "1024", message = "公告内容长度不允许大于${maxLength}!") })
    @InputConfig(resultName = "error")
    public String save() throws Exception {
        Affiche persistent = null;
        if (this.affiche.getId() != null) {
            persistent = (Affiche) this.afficheService.get(this.affiche.getId());
            BeanUtils.copyProperties(this.affiche, persistent, new String[] { "id", "createDate", "user" });
            this.afficheService.update(persistent);
        } else {
            persistent = this.affiche;
            persistent.setCreateDate(new Date());
            User user = new User();
            user.setId(getLoginUser().getId());
            persistent.setUser(user);
            this.afficheService.save(persistent);
        }
        this.redirectionUrl = "affiche!list.action";
        return "success";
    }

    @rmpfLog(desc = "公告删除")
    public void ajaxBatchDelete() {
        if (this.ids != null) {
            this.afficheService.doBatchDelete(this.ids);
            ajaxJsonSuccessMessage("操作完成!");
        } else {
            ajaxJsonSuccessMessage("没有选中数据,操作失败!");
        }
    }

    public Affiche getAffiche() {
        return this.affiche;
    }

    public void setAffiche(Affiche affiche) {
        this.affiche = affiche;
    }

    public Integer[] getIds() {
        return this.ids;
    }

    public void setIds(Integer[] ids) {
        this.ids = ids;
    }
}
