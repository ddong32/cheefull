package com.chee.action.info;

import com.chee.action.admin.BaseAction;
import com.chee.entity.WxContent;
import com.chee.service.WxContentService;
import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONArray;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Results;

@Namespace("/info")
@ParentPackage("info")
@Results({ @org.apache.struts2.convention.annotation.Result(name = "index", location = "content!list.action", type = "redirect") })
public class ContentAction extends BaseAction<WxContent, Integer> {
	private static final long serialVersionUID = 4984599292597512018L;
	private WxContentService wxContentService;
	private WxContent wxContent;
	private String contentID;
	private String taxonomy;

	public String list() {
		return "list";
	}

	public String view() {
		return "view";
	}

	public String ajaxContentId() {
		try {
			Map<String, String> map = new HashMap();
			if ((this.contentID != null) && (!"".equals(this.contentID))) {
				this.wxContent = ((WxContent) this.wxContentService.get(Integer
						.valueOf(Integer.parseInt(this.contentID))));
				map.put("id", this.wxContent.getId()+"");
				map.put("title", this.wxContent.getTitle());
				JSONArray jsonArray = JSONArray.fromObject(map);
				return ajaxJson(jsonArray.toString());
			}
			return ajaxJson("");
		} catch (Exception e) {
			return ajaxJson(e.toString());
		}
	}

	public String getContentID() {
		return this.contentID;
	}

	public void setContentID(String contentID) {
		this.contentID = contentID;
	}
}
