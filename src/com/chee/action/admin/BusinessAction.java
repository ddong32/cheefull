package com.chee.action.admin;

import com.chee.annotation.rmpfLog;
import com.chee.entity.Account;
import com.chee.entity.Business;
import com.chee.entity.BusinessCustomer;
import com.chee.entity.Customer;
import com.chee.service.BusinessService;
import com.chee.util.DateUtil;
import com.chee.util.SequenceUtil;
import com.chee.util.StringUtils;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.annotation.Resource;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import net.sf.json.JSONArray;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Results;

@ParentPackage("admin")
@Results({ @org.apache.struts2.convention.annotation.Result(name = "index", location = "business!list.action", type = "redirect") })
public class BusinessAction extends BaseAction<Business, Integer> {
	private static final long serialVersionUID = 4984599292597512018L;
	@Resource
	private BusinessService businessService;
	@Resource
	private SequenceUtil sequenceUtil;
	private Business business;
	private Customer customer;
	private Account account;
	private ArrayList<Account> accountList;
	private ArrayList<BusinessCustomer> businessCustomerList;
	private String buessinID;
	private Integer[] ids;

    public String list() {
		if (this.business == null) {
			this.business = new Business();
			if (StringUtils.isEmpty(this.business.getBeginDate())) {
				this.business.setBeginDate(DateUtil.afterNDay(new Date(), -100, "yyyy-MM-dd"));
			}
			if (StringUtils.isEmpty(this.business.getEndDate())) {
				this.business.setEndDate(DateUtil.afterNDay(new Date(), 100, "yyyy-MM-dd"));
			}
			this.business.setSdate("1");
		}
		this.page = this.businessService.findBusinessPage(this.page, this.business);
		return "list";
	}

	public String ajaxListTotal() {
		Map<String, String> map = new HashMap<String, String>();
		try {
			if (this.business == null) {
				this.business = new Business();
				if (StringUtils.isEmpty(this.business.getBeginDate())) {
					this.business.setBeginDate(DateUtil.afterNDay(new Date(), -100, "yyyy-MM-dd"));
				}
				if (StringUtils.isEmpty(this.business.getEndDate())) {
					this.business.setEndDate(DateUtil.afterNDay(new Date(), 100, "yyyy-MM-dd"));
				}
				this.business.setSdate("1");
			}
			List<Business> list = this.businessService.findBusinessListTotal(this.business);

			int sumAdultNo  = 0;
			int sumChildNo  = 0;
			int sumEscortNo = 0;
			double sumYgs = 0.0D;
			double sumYgf = 0.0D;
			double sumYjs = 0.0D;
			double sumYjf = 0.0D;
			for (Business bean : list) {
				sumAdultNo += bean.getAdultNo().intValue();
				sumChildNo += bean.getChildNo().intValue();
				sumEscortNo += bean.getEscortNo().intValue();
				sumYgs += bean.getYgs();
				sumYgf += bean.getYgf();
				sumYjs += bean.getYjs();
				sumYjf += bean.getYjf();
			}
			map.put("adultNo", sumAdultNo+"");
			map.put("childNo", sumChildNo+"");
			map.put("escortNo", sumEscortNo+"");
			map.put("ygs", getFormatMoney(sumYgs));
			map.put("ygf", getFormatMoney(sumYgf));
			map.put("yjs", getFormatMoney(sumYjs));
			map.put("yjf", getFormatMoney(sumYjf));
			map.put("wsk", getFormatMoney(sumYgs - sumYjs));
			map.put("lr", getFormatMoney(sumYgs - sumYgf));
			map.put("error", "");
		} catch (Exception e) {
			map.put("error", e.toString());
		}
		JSONArray jsonArray = JSONArray.fromObject(map);
		return ajaxJson(jsonArray.toString());
	}

	@rmpfLog(desc = "订单录入")
	@InputConfig(resultName = "error")
	public void save() throws Exception {
		if (this.business.getId() == null) {
			try {
				if (this.business.getCfsj() != null) {
					this.business.setOrderId(this.sequenceUtil
							.ProductOrderId(this.business.getCfsj()));
					this.business.setDdzt("0");
					this.business.setLrr(getLoginUser().getName());
					this.business.setUser(getLoginUser());
					this.business.setLrsj(new Date());
					this.business.setGxsj(new Date());
					this.buessinID = ((Integer) this.businessService
							.save(this.business)).toString();
					ajaxJsonSuccessMessage(this.buessinID);
				} else {
					ajaxJsonWarnMessage("[action]：出发时间不能为空!");
				}
			} catch (Exception e) {
				ajaxJsonErrorMessage(e.toString());
			}
		} else {
			ajaxJsonErrorMessage("");
		}
	}

	@rmpfLog(desc = "订单基本信息修改")
	@InputConfig(resultName = "error")
	public String update() {
		if ((this.business != null) && (this.business.getId() != null)) {
			try {
				this.businessService.updateOrderId(this.business);
			} catch (Exception e) {
				System.out.println(e.toString());
				return "error";
			}
		}
		return "success";
	}

	public String input() {
		this.redirectionUrl = "business!input.action";
		return "input";
	}

	public String order() {
		this.redirectionUrl = "business!order.action";
		return "order";
	}

	public String print() {
		System.out.println("打印开始，订单编号：" + this.business.getOrderId());
		this.redirectionUrl = "business!print.action";
		return "print";
	}

	public String ajaxBusinessId() {
		try {
			Map<String, String> map = new HashMap<String, String>();
			if ((this.buessinID != null) && (!"".equals(this.buessinID))) {
				this.business = ((Business) this.businessService.get(Integer.valueOf(Integer.parseInt(this.buessinID))));
				map.put("id", this.business.getId()+"");
				map.put("orderId", this.business.getOrderId());
				map.put("travelLine", this.business.getTravelLine());
				map.put("ddzt", this.business.getDdzt());
				map.put("ddzt_zw", (String) getDdztMap().get(this.business.getDdzt()));
				map.put("lrr", this.business.getUser().getName());
				map.put("ywlx", this.business.getYwlx());
				map.put("ddlx", this.business.getDdlx());
				map.put("cfsj", DateUtil.formatDate(this.business.getCfsj(), "yyyy-MM-dd"));
				map.put("lrsj", DateUtil.formatDate(this.business.getLrsj(), "yyyy-MM-dd"));
				map.put("adultNo", this.business.getAdultNo()+"");
				map.put("childNo", this.business.getChildNo()+"");
				map.put("escortNo", this.business.getEscortNo()+"");
				map.put("ygs", getFormatMoney(this.business.getYgs()));
				map.put("ygf", getFormatMoney(this.business.getYgf()));
				map.put("yjs", getFormatMoney(this.business.getYjs()));
				map.put("yjf", getFormatMoney(this.business.getYjf()));
				map.put("wsk", getFormatMoney(this.business.getYgs() - this.business.getYjs()));
				map.put("lr", getFormatMoney(this.business.getYgs() - this.business.getYgf()));
				Boolean isCurrentUser = Boolean.valueOf((getLoginUser().getId().equals(this.business.getUser().getId())) || ("100000".equals(getLoginUser().getDepartmentCode())));
				map.put("isCurrentUser", isCurrentUser.booleanValue() ? "true" : "false");
				JSONArray jsonArray = JSONArray.fromObject(map);
				return ajaxJson(jsonArray.toString());
			}
			return ajaxJson("");
		} catch (Exception e) {
			return ajaxJson(e.toString());
		}
	}

	@rmpfLog(desc = "订单信息取销")
	public String ajaxBusinessStat() {
		if ((this.business != null) && (this.business.getId() != null)) {
			this.businessService.updateBusinessStat(this.business.getId(),
					this.business.getDdzt());
		}
		return "success";
	}

	public void exportBusinessList() throws IOException {
		if (this.business == null) {
			this.business = new Business();
			if (StringUtils.isEmpty(this.business.getBeginDate())) {
				this.business.setBeginDate(DateUtil.afterNDay(new Date(), -100,
						"yyyy-MM-dd"));
			}
			if (StringUtils.isEmpty(this.business.getEndDate())) {
				this.business.setEndDate(DateUtil.afterNDay(new Date(), 100,
						"yyyy-MM-dd"));
			}
			this.business.setSdate("1");
		}
		List<Business> list = this.businessService
				.findBusinessListTotal(this.business);

		int sumAdultNo = 0;
		int sumChildNo = 0;
		int sumEscortNo = 0;
		double sumYgs = 0.0D;
		double sumYgf = 0.0D;
		double sumYjs = 0.0D;
		double sumYjf = 0.0D;

		String filename = "订单列表数据.xls";
		WritableWorkbook workbook = null;
		WritableSheet sheet = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int rowNum = 1;
		try {
			workbook = Workbook.createWorkbook(baos);
			sheet = workbook.createSheet("订单列表", 0);
			Vector columnName = new Vector();
			columnName.add("状态");
			columnName.add("订单号");
			columnName.add("类型");
			columnName.add("线路名称");
			columnName.add("操作人");
			columnName.add("人数");
			columnName.add("应收");
			columnName.add("应付");
			columnName.add("已收");
			columnName.add("未收");
			columnName.add("利润");

			sheet.setColumnView(0, 7);
			sheet.setColumnView(1, 15);
			sheet.setColumnView(2, 7);
			sheet.setColumnView(3, 80);
			sheet.setColumnView(4, 12);
			sheet.setColumnView(5, 12);
			sheet.setColumnView(6, 12);
			sheet.setColumnView(7, 12);
			sheet.setColumnView(8, 12);
			sheet.setColumnView(9, 12);
			sheet.setColumnView(10, 12);

			sheet.setRowView(0, 300);
			writeCol(sheet, columnName, 0);
			for (Business bean : list) {
				Vector col = new Vector();
				col.add(getDdztMap().get(bean.getDdzt()));
				col.add(bean.getOrderId());
				col.add(getYwlxMap().get(bean.getYwlx()));
				col.add(bean.getTravelLine());
				col.add(bean.getLrr());
				col.add(bean.getAdultNo().toString() + "|" + bean.getChildNo().toString() + "|" + bean.getEscortNo().toString());
				col.add(getFormatMoney(bean.getYgs()));
				col.add(getFormatMoney(bean.getYgf()));
				col.add(getFormatMoney(bean.getYgs() - bean.getYgf()));
				col.add(getFormatMoney(bean.getYjs()));
				col.add(getFormatMoney(bean.getYgs() - bean.getYjs()));
				sheet.setRowView(rowNum, 300);
				writeCol(sheet, col, rowNum++);

				sumAdultNo += bean.getAdultNo().intValue();
				sumChildNo += bean.getChildNo().intValue();
				sumEscortNo += bean.getEscortNo().intValue();
				sumYgs += bean.getYgs();
				sumYgf += bean.getYgf();
				sumYjs += bean.getYjs();
				sumYjf += bean.getYjf();
			}
			Vector<String> col = new Vector<String>();
			col.add("");
			col.add("");
			col.add("");
			col.add("合计：");
			col.add(rowNum - 1 + "条");
			col.add(sumAdultNo + "|" + sumChildNo + "|" + sumEscortNo);
			col.add(getFormatMoney(sumYgs));
			col.add(getFormatMoney(sumYgf));
			col.add(getFormatMoney(sumYgs - sumYgf));
			col.add(getFormatMoney(sumYjs));
			col.add(getFormatMoney(sumYgs - sumYjs));
			sheet.setRowView(rowNum, 300);
			writeCol(sheet, col, rowNum++);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				workbook.write();
				workbook.close();
				baos.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} finally {
			try {
				workbook.write();
				workbook.close();
				baos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		filename = URLEncoder.encode(filename, "UTF-8");

		getResponse().setContentType("application/force-download");

		getResponse().setHeader("Content-Disposition", "attachment;filename=" + filename);
		OutputStream out = null;
		ByteArrayInputStream in = null;
		try {
			out = getResponse().getOutputStream();
			in = new ByteArrayInputStream(baos.toByteArray());
			byte[] buffer = new byte[1024];
			int i = -1;
			while ((i = in.read(buffer)) != -1) {
				out.write(buffer, 0, i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			in.close();
			baos.close();
			out.close();
		}
	}

	private void writeCol(WritableSheet sheet, Vector col, int rowNum)
			throws RowsExceededException, WriteException {
		int size = col.size();
		for (int i = 0; i < size; i++) {
			if (((col.get(i) instanceof Integer)) || ((col.get(i) instanceof Float))) {
				Number label = new Number(i, rowNum, Float.parseFloat(col.get(i) + ""));
				sheet.addCell(label);
			} else {
				Label label = new Label(i, rowNum, (String) col.get(i));
				sheet.addCell(label);
			}
		}
	}

	public Business getBusiness() {
		return this.business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getBuessinID() {
		return this.buessinID;
	}

	public void setBuessinID(String buessinID) {
		this.buessinID = buessinID;
	}

	public ArrayList<Account> getAccountList() {
		return this.accountList;
	}

	public void setAccountList(ArrayList<Account> accountList) {
		this.accountList = accountList;
	}

	public ArrayList<BusinessCustomer> getBusinessCustomerList() {
		return this.businessCustomerList;
	}

	public void setBusinessCustomerList(
			ArrayList<BusinessCustomer> businessCustomerList) {
		this.businessCustomerList = businessCustomerList;
	}

	public Integer[] getIds() {
		return this.ids;
	}

	public void setIds(Integer[] ids) {
		this.ids = ids;
	}
}
