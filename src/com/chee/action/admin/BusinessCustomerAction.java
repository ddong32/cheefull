package com.chee.action.admin;

import com.chee.annotation.rmpfLog;
import com.chee.entity.Business;
import com.chee.entity.BusinessCustomer;
import com.chee.entity.Customer;
import com.chee.entity.User;
import com.chee.service.BusinessCustomerService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import net.sf.json.JSONArray;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Results;

@ParentPackage("admin")
@Results({ @org.apache.struts2.convention.annotation.Result(name = "index", location = "business_customer!list.action", type = "redirect") })
public class BusinessCustomerAction extends
		BaseAction<BusinessCustomer, Integer> {
	private static final long serialVersionUID = 4984599292597512018L;
	@Resource
	private BusinessCustomerService businessCustomerService;
	private BusinessCustomer businessCustomer;
	private Customer customer;
	private String orderId;
	private Integer[] ids;

	@InputConfig(resultName = "error")
	@rmpfLog(desc = "订单组团社添加/修改")
	public void save() {
		String msg = "";
		try {
			msg = this.businessCustomerService.doBusinessCustomerSaveOrUpdate(
					this.businessCustomer, getLoginUser().getName());
		} catch (Exception e) {
			System.out.println(e.toString());
			msg = e.toString();
		}
		if ((msg == "") && ("".equals(msg))) {
			ajaxJsonSuccessMessage("操作完成!");
		} else {
			ajaxJsonErrorMessage(msg);
		}
	}

	@rmpfLog(desc = "订单组团社删除")
	public String delete() {
		try {
			this.businessCustomerService.delete(this.businessCustomer.getId());
			this.redirectionUrl = "businessCustomer!list.action";
			return "success";
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return "error";
	}

	public String ajaxFindOrderID() {
		List<BusinessCustomer> businessCustomerList = new ArrayList();
		if ((this.businessCustomer != null)
				&& (!"".equals(this.businessCustomer.getOrderId()))) {
			businessCustomerList = this.businessCustomerService
					.findOrderID(this.businessCustomer.getOrderId());
		}
		Map<String, Object> optionMap = new HashMap();
		Map<String, String> ztsMap = new HashMap();
		List optionList = new ArrayList();
		double hjygs = 0.0D;
		double hjyjs = 0.0D;
		double hjwsk = 0.0D;
		int sumAdultNo = 0;
		int sumChildNo = 0;
		int sumEscortNo = 0;
		for (BusinessCustomer businessCustomer : businessCustomerList) {
			Map<String, String> map = new HashMap();
			map.put("id", businessCustomer.getId()+"");
			map.put("ygs", getFormatMoney(businessCustomer.getYgs()));
			map.put("yjs", getFormatMoney(businessCustomer.getYjs()));
			map.put("wsk", getFormatMoney(businessCustomer.getYgs() - businessCustomer.getYjs()));
			map.put("adultNo", businessCustomer.getAdultNo());
			map.put("childNo", businessCustomer.getChildNo());
			map.put("escortNo", businessCustomer.getEscortNo());
			map.put("lrsj", DateUtil.formatDate(businessCustomer.getLrsj(), "yyyy-MM-dd"));
			map.put("lrr", businessCustomer.getBusiness() == null ? "" : businessCustomer.getBusiness().getUser().getName());
			map.put("crj", businessCustomer.getCrj()+"");
			map.put("etj", businessCustomer.getEtj()+"");
			map.put("cbcbz", businessCustomer.getBz());
			if (businessCustomer.getCustomer() != null) {
				map.put("customerDwmc", businessCustomer.getCustomer().getPathName());
				ztsMap.put(businessCustomer.getId()+"", businessCustomer.getCustomer().getPathName());
			} else {
				map.put("customerDwmc", "");
			}
			optionList.add(map);
			hjygs += businessCustomer.getYgs();
			hjyjs += businessCustomer.getYjs();
			hjwsk += businessCustomer.getYgs() - businessCustomer.getYjs();
			sumAdultNo += Integer.valueOf(businessCustomer.getAdultNo())
					.intValue();
			sumChildNo += Integer.valueOf(businessCustomer.getChildNo())
					.intValue();
			sumEscortNo += Integer.valueOf(businessCustomer.getEscortNo())
					.intValue();
		}
		optionMap.put("optionList", optionList);
		optionMap.put("hjygs", getFormatMoney(hjygs));
		optionMap.put("hjyjs", getFormatMoney(hjyjs));
		optionMap.put("hjwsk", getFormatMoney(hjwsk));
		optionMap.put("sumAdultNo", Integer.valueOf(sumAdultNo));
		optionMap.put("sumChildNo", Integer.valueOf(sumChildNo));
		optionMap.put("sumEscortNo", Integer.valueOf(sumEscortNo));
		optionMap.put("ztsmap", ztsMap);
		JSONArray jsonArray = JSONArray.fromObject(optionMap);
		return ajaxJson(jsonArray.toString());
	}

	public String input() {
		if ((this.orderId != null) && (!"".equals(this.orderId))) {
			if (this.ids != null) {
				this.businessCustomer = ((BusinessCustomer) this.businessCustomerService
						.get(Integer.valueOf(this.ids[0].intValue())));
			}
		} else {
			this.businessCustomer = new BusinessCustomer();
		}
		return "input";
	}

	@rmpfLog(desc = "订单删除组团社")
	public void ajaxBatchDelete() {
		String msg = "";
		try {
			msg = this.businessCustomerService.doBatchDelete(this.ids,
					this.orderId);
		} catch (Exception e) {
			System.out.println(e.toString());
			msg = e.toString();
		}
		ajaxJsonSuccessMessage(msg);
	}

	@rmpfLog(desc = "提交组团社收款财务审核")
	public void ajaxSktjcw() {
		if (this.ids != null) {
			Integer[] arrayOfInteger;
			int j = (arrayOfInteger = this.ids).length;
			for (int i = 0; i < j; i++) {
				int id = arrayOfInteger[i].intValue();
				BusinessCustomer businessCustomer = (BusinessCustomer) this.businessCustomerService
						.load(Integer.valueOf(id));
				businessCustomer.setGxsj(new Date());
				this.businessCustomerService.update(businessCustomer);
			}
			ajaxJsonSuccessMessage("操作完成!");
		} else {
			ajaxJsonSuccessMessage("没有选中数据,操作失败!");
		}
	}

	public BusinessCustomer getBusinessCustomer() {
		return this.businessCustomer;
	}

	public void setBusinessCustomer(BusinessCustomer businessCustomer) {
		this.businessCustomer = businessCustomer;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer[] getIds() {
		return this.ids;
	}

	public void setIds(Integer[] ids) {
		this.ids = ids;
	}
}
