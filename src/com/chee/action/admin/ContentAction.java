package com.chee.action.admin;

import com.chee.entity.WxContent;
import com.chee.service.WxContentService;
import com.chee.util.EncodeUtils;
import com.chee.util.JsoupUtils;
import com.chee.util.StringUtils;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.BeanUtils;

@ParentPackage("admin")
@Results({ @org.apache.struts2.convention.annotation.Result(name = "index", location = "content!article_list.action", type = "redirect") })
public class ContentAction extends BaseAction<WxContent, Integer> {
    private static final long serialVersionUID = 4984599292597512018L;
    @Resource
    private WxContentService wxContentService;
    private WxContent wxContent;
    private List<WxContent> wechat_menus;
    private List<WxContent> wechat_menulist;
    private String contentID;

    public String article_list() {
        this.page = this.wxContentService.findContentPage(this.page, this.wxContent);
        return "article_list";
    }

    public String article_input() throws Exception {
        if (this.wxContent == null) {
            this.wxContent = new WxContent();
        }
        this.wxContent.setUcode(EncodeUtils.salt());
        return "article_input";
    }

    public void save() {
        Map<String, String> metas = getMetas();
        String slug = StringUtils.isBlank(this.wxContent.getSlug()) ? this.wxContent.getTitle() : this.wxContent.getSlug();
        boolean isAddAction = this.wxContent.getId() == null;
        Integer id = null;
        WxContent persistent = null;
        if (isAddAction) {
            if (!this.wxContentService.isUnique("title", null, this.wxContent.getTitle())) {
                ajaxJsonWarnMessage("标题不能为空!");
                return;
            }
            persistent = this.wxContent;
            persistent.setText(JsoupUtils.getBodyHtml(this.wxContent.getText()));
            persistent.setSlug(slug);
            persistent.setCreated(new Date());
            persistent.setUser(getLoginUser());
            id = (Integer) this.wxContentService.save(persistent);

            ajaxJsonSuccessMessage(id.toString());
        } else {
            persistent = (WxContent) this.wxContentService.load(this.wxContent.getId());
            if (!this.wxContentService.isUnique("title", persistent.getTitle(), this.wxContent.getTitle())) {
                ajaxJsonWarnMessage("标题: " + this.wxContent.getTitle() + " 已经存在!请重新输入!");
                return;
            }
            BeanUtils.copyProperties(this.wxContent, persistent, new String[] { "id", "created", "user_id" });
            persistent.setText(JsoupUtils.getBodyHtml(this.wxContent.getText()));
            persistent.setSlug(slug);
            this.wxContentService.update(persistent);

            ajaxJsonSuccessMessage("");
        }
    }

    public String article_taxonomy() throws Exception {
        return "article_taxonomy";
    }

    public String roundtrip_layer() {
        return "roundtrip_layer";
    }

    public WxContent getWxContent() {
        return this.wxContent;
    }

    public void setWxContent(WxContent wxContent) {
        this.wxContent = wxContent;
    }

    public List<WxContent> getWechat_menus() {
        return this.wechat_menus;
    }

    public void setWechat_menus(List<WxContent> wechat_menus) {
        this.wechat_menus = wechat_menus;
    }

    public List<WxContent> getWechat_menulist() {
        return this.wechat_menulist;
    }

    public void setWechat_menulist(List<WxContent> wechat_menulist) {
        this.wechat_menulist = wechat_menulist;
    }

    public String getContentID() {
        return this.contentID;
    }

    public void setContentID(String contentID) {
        this.contentID = contentID;
    }
}
