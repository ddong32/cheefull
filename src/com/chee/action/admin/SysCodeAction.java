package com.chee.action.admin;

import com.chee.entity.SysCode;
import com.chee.service.SysCodeService;
import javax.annotation.Resource;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.DetachedCriteria;

@ParentPackage("admin")
@Results({ @org.apache.struts2.convention.annotation.Result(name = "index", location = "business!list.action", type = "redirect") })
public class SysCodeAction extends BaseAction<SysCode, Integer> {
    private static final long serialVersionUID = 4984599292597512018L;
    @Resource
    private SysCodeService sysCodeService;
    private SysCode sysCode;

    public String list() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SysCode.class);

        this.page = this.sysCodeService.findPage(this.page, detachedCriteria);
        return "list";
    }

    public SysCode getSyscode() {
        return this.sysCode;
    }

    public void setSyscode(SysCode sysCode) {
        this.sysCode = sysCode;
    }
}
