package com.chee.action.admin;

import com.chee.common.Config;
import com.chee.entity.Affiche;
import com.chee.entity.User;
import com.chee.service.AfficheService;
import com.chee.service.ResourceService;
import com.chee.util.DateUtil;
import java.util.Date;
import java.util.List;
import org.apache.struts2.convention.annotation.ParentPackage;

@ParentPackage("admin")
public class MainAction extends BaseAction<User, Integer> {
    private static final long serialVersionUID = 1L;
    private User user;
    @javax.annotation.Resource
    private ResourceService resourceService;
    @javax.annotation.Resource
    private AfficheService afficheService;
    private List<com.chee.entity.Resource> resourceList;
    private List<Affiche> afficheList;
    private String alertBeginTime;
    private String alertEndTime;

    public String frame() {
        return "frame";
    }

    public String header() {
        setAttribute("systemName", Config.getInstance().getSystemName());
        setAttribute("systemCity", Config.getInstance().getSystemCity());
        setAttribute("sessionId", getRequest().getSession().getId());
        return "header";
    }

    public String content() {
        return "content";
    }

    public String about() {
        return "about";
    }

    public String help() {
        return "help";
    }

    public String left() {
        return "left";
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<com.chee.entity.Resource> getResourceList() {
        if (this.resourceList == null) {
            this.resourceList = this.resourceService.find("from Resource where grade = 1 order by sort asc", new Object[0]);
        }
        return this.resourceList;
    }

    public void setResourceList(List<com.chee.entity.Resource> resourceList) {
        this.resourceList = resourceList;
    }

    public String getAlertBeginTime() {
        if (this.alertBeginTime == null) {
            this.alertBeginTime = DateUtil.afterNDay(new Date(), -10, "yyyy-MM-dd 00:00:00");
        }
        return this.alertBeginTime;
    }

    public void setAlertBeginTime(String alertBeginTime) {
        this.alertBeginTime = alertBeginTime;
    }

    public String getAlertEndTime() {
        if (this.alertEndTime == null) {
            this.alertEndTime = DateUtil.DateToString(new Date(), "yyyy-MM-dd 23:59:59");
        }
        return this.alertEndTime;
    }

    public void setAlertEndTime(String alertEndTime) {
        this.alertEndTime = alertEndTime;
    }

    public List<Affiche> getAfficheList() {
        if (this.afficheList == null) {
            this.afficheList = this.afficheService.getMainPageAfficheList();
        }
        return this.afficheList;
    }

    public void setAfficheList(List<Affiche> afficheList) {
        this.afficheList = afficheList;
    }
}
