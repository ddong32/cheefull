package com.chee.action.admin;

import com.chee.entity.BankTransferlog;
import com.chee.entity.WxCode;
import com.chee.entity.WxOption;
import com.chee.service.CommonService;
import com.chee.service.WxCodeService;
import com.chee.service.WxOptionService;
import com.chee.util.StringUtils;
import com.chee.util.WechatApiUtil;
import com.chee.util.WechatConsts;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.ReturnCode;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.BeanUtils;

@ParentPackage("admin")
@Results({ @org.apache.struts2.convention.annotation.Result(name = "index", location = "wechar!list.action", type = "redirect") })
public class WechatAction extends BaseAction<BankTransferlog, Integer> {
    private static final long serialVersionUID = 4984599292597512018L;
    @Resource
    private WxOptionService wxOptionService;
    @Resource
    private WxCodeService wxCodeService;
    @Resource
    private CommonService commonService;
    private WxOption wxOption;
    private WxCode wxCode;
    private List<WxCode> wx_menus;
    private List<WxCode> wx_menulist;

    public String option() {
        return "option";
    }

    public String saveOption() throws Exception {
        if (this.wxOption.getId() != null) {
            WxOption persistent = (WxOption) this.wxOptionService.load(this.wxOption.getId());

            BeanUtils.copyProperties(this.wxOption, persistent, new String[] { "id", "lrsj" });

            this.wxOptionService.update(persistent);
        } else {
            this.wxOptionService.save(this.wxOption);
        }
        this.commonService.init();
        this.redirectionUrl = "bank!list.action";
        return "success";
    }

    public String ajaxLoadOption() {
        Map<String, String> map = new HashMap();
        try {
            List<WxOption> optionList = this.wxOptionService.getAll();
            for (WxOption option : optionList) {
                map.put(option.getOption_key(), option.getOption_value());
            }
            map.put("success", "true");
            JSONArray jsonArray = JSONArray.fromObject(map);
            return ajaxJson(jsonArray.toString());
        } catch (Exception e) {
            map.put("success", "false");
            map.put("message", e.toString());
            return ajaxJson(e.toString());
        }
    }

    public String menu() {
        return "menu";
    }

    public String loadmenus() {
        List<WxCode> wx_menus = this.wxCodeService.findMenus();

        Map optionMap = new HashMap();
        List optionList = new ArrayList();
        for (WxCode menu : wx_menus) {
            Map<String, String> map = new HashMap();
            map.put("id", menu.getId().toString());
            map.put("parent_id", menu.getParent().getId().toString());
            if (menu.getParent() == null) {
                map.put("title", menu.getTitle());
            } else {
                map.put("title", "— " + menu.getTitle());
            }
            map.put("flag", menu.getFlag());
            map.put("text", menu.getText());
            map.put("order_number", menu.getOrder_number().toString());
            optionList.add(map);
        }
        optionMap.put("optionList", optionList);

        JSONArray jsonArray = JSONArray.fromObject(optionMap);
        return ajaxJson(jsonArray.toString());
    }

    public String loadparent() {
        List<WxCode> wx_menus = this.wxCodeService.findParentMenus();

        Map optionMap = new HashMap();
        List parentList = new ArrayList();
        for (WxCode menu : wx_menus) {
            Map<String, String> map = new HashMap();
            map.put("id", menu.getId().toString());
            map.put("title", menu.getTitle());
            map.put("flag", menu.getFlag());
            map.put("text", menu.getText());
            parentList.add(map);
        }
        optionMap.put("parentList", parentList);

        JSONArray jsonArray = JSONArray.fromObject(optionMap);
        return ajaxJson(jsonArray.toString());
    }

    public String loadMenu() {
        WxCode menu = this.wxCodeService.loadMenu(Integer.valueOf(this.wxCode.getId().intValue()));

        Map<String, String> map = new HashMap();
        map.put("id", menu.getId().toString());
        map.put("title", menu.getTitle());
        map.put("flag", menu.getFlag());
        map.put("text", menu.getText());
        map.put("order_number", menu.getOrder_number().toString());
        map.put("parent_id", menu.getParent().getId().toString());
        JSONArray jsonArray = JSONArray.fromObject(map);
        return ajaxJson(jsonArray.toString());
    }

    public String menuSave() {
        if ((this.wxCode.getParent() != null) && (this.wxCode.getParent().getId() != null)) {
            long count = this.wxCodeService.findCountInNormalByParentId(this.wxCode.getParent().getId()).longValue();
            if (count > 5L) {
                addActionError("子级菜单不能超过5个！");
                return "error";
            }
        } else {
            long count = this.wxCodeService.findCountInNormalByParentId(null).longValue();
            if (count > 3L) {
                addActionError("顶级菜单不能超过3个！");
                return "error";
            }
        }
        if (this.wxCode.getId() != null) {
            WxCode persistent = (WxCode) this.wxCodeService.load(this.wxCode.getId());

            BeanUtils.copyProperties(this.wxCode, persistent, new String[] { "id", "modified", "module", "status" });
            persistent.setModified(new Date());
            this.wxCodeService.update(persistent);
        } else {
            this.wxCode.setCreated(new Date());
            this.wxCode.setModified(new Date());
            this.wxCode.setMarkdown_enable(Integer.valueOf(0));
            this.wxCode.setModule("wx_menu");
            this.wxCode.setStatus("normal");
            this.wxCodeService.save(this.wxCode);
        }
        this.redirectionUrl = "bank!list.action";
        return "success";
    }

    public String menuDel() {
        this.wxCodeService.delete(this.wxCode.getId());
        return "success";
    }

    public void menuSync() {
        List<WxCode> wxMenus = this.wxCodeService.findParentMenus();
        if (wxMenus != null) {
            JSONArray button = new JSONArray();
            for (WxCode content : wxMenus) {
                if (content.getChildren().size() > 0) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", content.getTitle());
                    Set<WxCode> childMenus = content.getChildren();
                    JSONArray sub_buttons = new JSONArray();
                    for (WxCode c : childMenus) {
                        JSONObject sub_button = new JSONObject();
                        sub_button.put("type", c.getFlag());
                        sub_button.put("name", c.getTitle());
                        if ("view".equals(c.getFlag())) {
                            sub_button.put("url", c.getText());
                        } else {
                            sub_button.put("key", c.getText());
                        }
                        sub_buttons.add(sub_button);
                    }
                    jsonObject.put("sub_button", sub_buttons);
                    button.add(jsonObject);
                } else {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("type", content.getFlag());
                    jsonObject.put("name", content.getTitle());
                    if ("view".equals(content.getFlag())) {
                        jsonObject.put("url", content.getText());
                    } else {
                        jsonObject.put("key", content.getText());
                    }
                    button.add(jsonObject);
                }
            }
            JSONObject wxMenuJson = new JSONObject();
            wxMenuJson.put("button", button);
            String jsonString = wxMenuJson.toString();
            try {
                ApiConfig ac = WechatApiUtil.getApiConfig();
                ApiConfigKit.setThreadLocalApiConfig(ac);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
            try {
                ApiResult result = WechatApiUtil.createMenu(jsonString);
                if ((result != null) && (result.isSucceed())) {
                    ajaxJsonSuccessMessage("操作成功");
                } else {
                    String message = WechatConsts.getErrorMessage(result.getErrorCode().intValue());
                    if (StringUtils.isBlank(message)) {
                        message = ReturnCode.get(result.getErrorCode().intValue());
                    }
                    if (StringUtils.isBlank(message)) {
                        message = "未知错误";
                    }
                    ajaxJsonErrorMessage("操作失败：" + result.getErrorCode() + message);
                }
            } catch (Exception e) {
                System.out.println(e.toString());
                ajaxJsonErrorMessage(e.toString());
            }
        } else {
            ajaxJsonErrorMessage("还没有添加菜单信息");
        }
    }

    public WxOption getwxOption() {
        return this.wxOption;
    }

    public void setwxOption(WxOption wxOption) {
        this.wxOption = wxOption;
    }

    public WxCode getWxCode() {
        return this.wxCode;
    }

    public void setWxCode(WxCode wxCode) {
        this.wxCode = wxCode;
    }

    public List<WxCode> getwx_menus() {
        return this.wx_menus;
    }

    public void setwx_menus(List<WxCode> wx_menus) {
        this.wx_menus = wx_menus;
    }

    public List<WxCode> getwx_menulist() {
        return this.wx_menulist;
    }

    public void setwx_menulist(List<WxCode> wx_menulist) {
        this.wx_menulist = wx_menulist;
    }
}
