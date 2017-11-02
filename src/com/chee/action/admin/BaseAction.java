package com.chee.action.admin;

import com.chee.common.Page;
import com.chee.entity.User;
import com.chee.service.DepartmentService;
import com.chee.service.UserService;
import com.chee.template.MultipartRequest;
import com.chee.util.AttachmentUtils;
import com.chee.util.JsoupUtils;
import com.chee.util.RequestUtils;
import com.chee.util.StaticUtils;
import com.chee.util.StringUtils;
import com.chee.util.UserUtil;
import com.jfinal.upload.UploadFile;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

public class BaseAction<T, PK extends Serializable> extends ActionSupport {
	private static final long serialVersionUID = 6718838822334455667L;
	public static final String SUCCESS = "success";
	public static final String VIEW = "view";
	public static final String LIST = "list";
	public static final String STATUS = "status";
	public static final String WARN = "warn";
	public static final String ERROR = "error";
	public static final String MESSAGE = "message";
	protected Map<String, String> bankMap;
	protected Map<String, String> fklxMap;
	protected Map<String, String> sflxMap;
	protected Map<String, String> sffsMap;
	protected Map<String, String> accountStatMap;
	protected Map<String, String> ddztMap;
	protected Map<String, String> YwlxMap;
	protected Map<String, String> ddlxMap;
	protected Map<String, String> expenceMap;
	protected Map<String, String> expenceStatMap;
	protected Map<String, String> cooptypeMap;
	protected String redirectionUrl;
	protected Page<T> page = new Page<T>();
	public String paramJson;
	@Resource
	UserUtil userUtil;
	@Resource
	DepartmentService departmentService;
	@Resource
	private UserService userService;
	private List<User> userList;

	public User getLoginUser() {
		return this.userUtil.getLoginUser();
	}

	public Object getAttribute(String name) {
		return ServletActionContext.getRequest().getAttribute(name);
	}

	public void setAttribute(String name, Object value) {
		ServletActionContext.getRequest().setAttribute(name, value);
	}

	public String getParameter(String name) {
		return getRequest().getParameter(name);
	}

	public String[] getParameterValues(String name) {
		return getRequest().getParameterValues(name);
	}

	public Object getSession(String name) {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		return session.get(name);
	}

	public Map<String, Object> getSession() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		return session;
	}

	public void setSession(String name, Object value) {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		session.put(name, value);
	}

	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	public ServletContext getApplication() {
		return ServletActionContext.getServletContext();
	}

	public String ajax(String content, String type) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType(type + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String ajaxText(String text) {
		return ajax(text, "text/plain");
	}

	public String ajaxHtml(String html) {
		return ajax(html, "text/html");
	}

	public String ajaxXml(String xml) {
		return ajax(xml, "text/xml");
	}

	public String ajaxJson(String jsonString) {
		return ajax(jsonString, "text/html");
	}

	public String ajaxJson(Map<String, String> jsonMap) {
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html");
	}

	public String ajaxJsonWarnMessage(String message) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("status", "warn");
		jsonMap.put("message", message);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html");
	}

	public String ajaxJsonSuccessMessage(String message) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("status", "success");
		jsonMap.put("message", message);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html");
	}

	public String ajaxJsonErrorMessage(String message) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("status", "error");
		jsonMap.put("message", message);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html");
	}

	public void setResponseNoCache() {
		getResponse().setHeader("progma", "no-cache");
		getResponse().setHeader("Cache-Control", "no-cache");
		getResponse().setHeader("Cache-Control", "no-store");
		getResponse().setDateHeader("Expires", 0L);
	}

	public String getPara(String name) {
		return JsoupUtils.clear(getRequest().getParameter(name));
	}

	public String getPara(String name, String defaultValue) {
		String html = getRequest().getParameter(name);
		if (html != null) {
			return JsoupUtils.clear(html);
		}
		return defaultValue;
	}

	public void setHeader(String key, String value) {
		getResponse().setHeader(key, value);
	}

	public String getRedirectionUrl() {
		return this.redirectionUrl;
	}

	public void setRedirectionUrl(String redirectionUrl) {
		this.redirectionUrl = redirectionUrl;
	}

	public Page<T> getPage() {
		return this.page;
	}

	public void setPage(Page<T> page) {
		this.page = page;
	}

	public String getParamJson() {
		return this.paramJson;
	}

	public void setParamJson(String paramJson) {
		this.paramJson = paramJson;
	}

	public double formatDouble(String... s) {
		double value = 0.0D;
		if (s != null) {
			for (int i = 0; i < s.length; i++) {
				value += StringUtils.toDouble(s[i]).doubleValue();
			}
		}
		return value;
	}

	public String getFormatMoney(double s) {
		DecimalFormat fmt = new DecimalFormat("##,##0.00");
		return fmt.format(s);
	}

	public Map<String, String> getBankMap() {
		this.bankMap = StaticUtils.getBankMap();
		return this.bankMap;
	}

	public void setBankMap(Map<String, String> bankMap) {
		this.bankMap = bankMap;
	}

	public Map<String, String> getSflxMap() {
		this.sflxMap = StaticUtils.getSflxMap();
		return this.sflxMap;
	}

	public void setSflxMap(Map<String, String> sflxMap) {
		this.sflxMap = sflxMap;
	}

	public Map<String, String> getSffsMap() {
		this.sffsMap = StaticUtils.getSffsMap();
		return this.sffsMap;
	}

	public void setSffsMap(Map<String, String> sffsMap) {
		this.sffsMap = sffsMap;
	}

	public Map<String, String> getFklxMap() {
		this.fklxMap = StaticUtils.getFklxMap();
		return this.fklxMap;
	}

	public void setFklxMap(Map<String, String> fklxMap) {
		this.fklxMap = fklxMap;
	}

	public Map<String, String> getAccountStatMap() {
		this.accountStatMap = StaticUtils.getAccountStatMap();
		return this.accountStatMap;
	}

	public void setAccountStatMap(Map<String, String> accountStatMap) {
		this.accountStatMap = accountStatMap;
	}

	public Map<String, String> getDdztMap() {
		this.ddztMap = StaticUtils.getDdztMap();
		return this.ddztMap;
	}

	public void setDdztMap(Map<String, String> ddztMap) {
		this.ddztMap = ddztMap;
	}

	public Map<String, String> getYwlxMap() {
		this.YwlxMap = StaticUtils.getYwlxMap();
		return this.YwlxMap;
	}

	public void setYwlxMap(Map<String, String> ywlxMap) {
		this.YwlxMap = ywlxMap;
	}

	public Map<String, String> getDdlxMap() {
		this.ddztMap = StaticUtils.getDdlxMap();
		return this.ddztMap;
	}

	public void setDdlxMap(Map<String, String> ddlxMap) {
		this.ddlxMap = ddlxMap;
	}

	public Map<String, String> getExpenceMap() {
		this.expenceMap = StaticUtils.getExpenceMap();
		return this.expenceMap;
	}

	public void setExpenceMap(Map<String, String> expenceMap) {
		this.expenceMap = expenceMap;
	}

	public Map<String, String> getExpenceStatMap() {
		this.expenceStatMap = StaticUtils.getExpenceStatMap();
		return this.expenceStatMap;
	}

	public void setExpenceStatMap(Map<String, String> expenceStatMap) {
		this.expenceStatMap = expenceStatMap;
	}

	public Map<String, String> getCooptypeMap() {
		this.cooptypeMap = StaticUtils.getCooptypeMap();
		return this.cooptypeMap;
	}

	public void setCooptypeMap(Map<String, String> cooptypeMap) {
		this.cooptypeMap = cooptypeMap;
	}

	public List<User> getUserList() {
		if (this.userList == null) {
			StringBuffer hqlSb = new StringBuffer(" from User u where 1=1 ");
			Map map = new HashMap();
			hqlSb.append(" and u.isAccountEnabled =:isAccountEnabled ");
			map.put("isAccountEnabled", Boolean.valueOf(true));
			if (!getLoginUser().getRoleValues().contains("ROLE_ADMIN")) {
				if (getLoginUser().getRoleValues().contains("ROLE_MANAGER")) {
					List orgCodeList = this.departmentService.getConnectOrgCodes(getLoginUser().getDepartment().getId());
					if (orgCodeList != null) {
						hqlSb.append(" and ( ");
						for (int i = 0; i < orgCodeList.size(); i++) {
							if (i != 0) {
								hqlSb.append(" or ");
							}
							hqlSb.append(" u.department.code =:orgCode_" + i);
							map.put("orgCode_" + i, orgCodeList.get(i));
						}
						hqlSb.append(" ) ");
					}
				} else {
					hqlSb.append(" and u.id=:loginUserId ");
					map.put("loginUserId", getLoginUser().getId());
				}
			}
			this.userList = this.userService.find(hqlSb.toString(), map);
		}
		return this.userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public BigInteger[] getParaValuesToBigInteger(String name) {
		String[] values = getRequest().getParameterValues(name);
		if (values == null) {
			return null;
		}
		BigInteger[] result = new BigInteger[values.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = new BigInteger(values[i]);
		}
		return result;
	}

	public BigInteger getParaToBigInteger(String name) {
		return toBigInteger(getRequest().getParameter(name), null);
	}

	public BigInteger getParaToBigInteger(String name, BigInteger defaultValue) {
		return toBigInteger(getRequest().getParameter(name), defaultValue);
	}

	private BigInteger toBigInteger(String value, BigInteger defaultValue) {
		try {
			if ((value == null) || ("".equals(value.trim()))) {
				return defaultValue;
			}
			value = value.trim();
			if ((value.startsWith("N")) || (value.startsWith("n"))) {
				return new BigInteger(value).negate();
			}
			return new BigInteger(value);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public List<UploadFile> getFiles(String uploadPath, Integer maxPostSize, String encoding) {
		HttpServletRequest request = getRequest();
		if (!(request instanceof MultipartRequest)) {
			request = new MultipartRequest(getRequest(), uploadPath, maxPostSize.intValue(), encoding);
		}
		return ((MultipartRequest) request).getFiles();
	}

	public UploadFile getFile(String parameterName, String uploadPath, Integer maxPostSize, String encoding) {
		getFiles(uploadPath, maxPostSize, encoding);
		return getFile(parameterName);
	}

	public List<UploadFile> getFiles(String uploadPath, int maxPostSize) {
		HttpServletRequest request = getRequest();
		if (!(request instanceof MultipartRequest)) {
			request = new MultipartRequest(request, uploadPath, maxPostSize);
		}
		return ((MultipartRequest) request).getFiles();
	}

	public UploadFile getFile(String parameterName, String uploadPath, int maxPostSize) {
		getFiles(uploadPath, maxPostSize);
		return getFile(parameterName);
	}

	public List<UploadFile> getFiles(String uploadPath) {
		HttpServletRequest request = getRequest();
		if (!(request instanceof MultipartRequest)) {
			request = new MultipartRequest(request, uploadPath);
		}
		return ((MultipartRequest) request).getFiles();
	}

	public UploadFile getFile(String parameterName, String uploadPath) {
		getFiles(uploadPath);
		return getFile(parameterName);
	}

	public List<UploadFile> getFiles() {
		HttpServletRequest request = getRequest();
		if (!(request instanceof MultipartRequest)) {
			request = new MultipartRequest(request);
		}
		return ((MultipartRequest) request).getFiles();
	}

	public UploadFile getFile() {
		List<UploadFile> uploadFiles = getFiles();
		return uploadFiles.size() > 0 ? (UploadFile) uploadFiles.get(0) : null;
	}

	public UploadFile getFile(String parameterName) {
		List<UploadFile> uploadFiles = getFiles();
		for (UploadFile uploadFile : uploadFiles) {
			if (uploadFile.getParameterName().equals(parameterName)) {
				return uploadFile;
			}
		}
		return null;
	}

	public Map<String, String> getMetas(Map<String, String> filesMap) {
		HashMap<String, String> metas = null;
		Map<String, String[]> requestMap = getRequest().getParameterMap();
		if ((requestMap != null) && (!requestMap.isEmpty())) {
			for (Map.Entry<String, String[]> entry : requestMap.entrySet()) {
				String key = (String) entry.getKey();
				if (key.startsWith("meta:")) {
					if (metas == null) {
						metas = new HashMap();
					}
					String value = null;
					for (String v : (String[]) entry.getValue()) {
						if (StringUtils.isNotEmpty(v)) {
							value = v;
							break;
						}
					}
					metas.put(key.substring(5), value);
				}
			}
		}
		if (filesMap != null) {
			for (Map.Entry<String, String> entry : filesMap.entrySet()) {
				String key = (String) entry.getKey();
				if (key.startsWith("meta:")) {
					if (metas == null) {
						metas = new HashMap();
					}
					metas.put(key.substring(5), (String) entry.getValue());
				}
			}
		}
		return metas;
	}

	public Map<String, String> getMetas() {
		return getMetas(getUploadFilesMap());
	}

	public HashMap<String, String> getUploadFilesMap() {
		List<UploadFile> fileList = null;
		if (isMultipartRequest()) {
			fileList = getFiles();
		}
		HashMap<String, String> filesMap = null;
		if (fileList != null) {
			filesMap = new HashMap<String, String>();
			for (UploadFile ufile : fileList) {
				String filePath = AttachmentUtils.moveFile(ufile).replace("\\", "/");
				filesMap.put(ufile.getParameterName(), filePath);
			}
		}
		return filesMap;
	}

	public boolean isMultipartRequest() {
		return RequestUtils.isMultipartRequest(getRequest());
	}
}
