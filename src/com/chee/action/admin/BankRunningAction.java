package com.chee.action.admin;

import com.chee.annotation.rmpfLog;
import com.chee.entity.Bank;
import com.chee.entity.BankRunning;
import com.chee.service.BankRunningService;
import com.chee.service.BankService;
import com.chee.util.DateUtil;
import com.chee.util.StringUtils;
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
@Results({ @org.apache.struts2.convention.annotation.Result(name = "index", location = "BankRunning!list.action", type = "redirect") })
public class BankRunningAction extends BaseAction<BankRunning, Integer> {
	private static final long serialVersionUID = 4984599292597512018L;
	@Resource
	private BankRunningService bankRunningService;
	@Resource
	private BankService bankService;
	private BankRunning bankRunning;
	private Bank bank;
	private List<BankRunning> bankRunningList;
	private String currentBankId;

	@rmpfLog(desc = "银行流水修改/添加")
	public String save() throws Exception {
		if ("1".equals(this.bankRunning.getShbj())) {
			return "error";
		}
		String result = this.bankRunningService.saveBankRunning(
				this.bankRunning, getLoginUser().getName(), this.currentBankId);
		if (("".equals(result)) && (result == "")) {
			return "success";
		}
		addActionError(result);
		return "error";
	}

	public String list() {
		if (this.bankRunning == null) {
			this.bankRunning = new BankRunning();
			if (StringUtils.isEmpty(this.bankRunning.getBeginDate())) {
				this.bankRunning.setBeginDate(DateUtil.afterNDay(new Date(),
						-100, "yyyy-MM-dd"));
			}
			if (StringUtils.isEmpty(this.bankRunning.getEndDate())) {
				this.bankRunning.setEndDate(DateUtil.DateToString(new Date(),
						"yyyy-MM-dd"));
			}
			this.bankRunning.setShbj("0");
		}
		this.page = this.bankRunningService.findBankRunningPage(this.page,
				this.bankRunning);
		return "list";
	}

	public String input() {
		if ((this.bankRunning != null) && (this.bankRunning.getId() != null)) {
			this.bankRunning = ((BankRunning) this.bankRunningService
					.get(this.bankRunning.getId()));
			this.bankRunning.setToday(DateUtil.DateToString(
					this.bankRunning.getJzsj(), "yyyy-MM-dd"));
			if ("1".equals(this.bankRunning.getSflx())) {
				this.bankRunning.setJe(this.bankRunning.getSrje());
			} else {
				this.bankRunning.setJe(this.bankRunning.getZcje());
			}
		} else {
			this.bankRunning = new BankRunning();
			this.bankRunning.setToday(DateUtil.DateToString(new Date(),
					"yyyy-MM-dd"));
		}
		return "input";
	}

	public String inputrec() {
		if ((this.bankRunning != null) && (this.bankRunning.getId() != null)) {
			this.bankRunning = ((BankRunning) this.bankRunningService
					.get(this.bankRunning.getId()));
			this.bankRunning.setToday(DateUtil.DateToString(
					this.bankRunning.getJzsj(), "yyyy-MM-dd"));
			if ("1".equals(this.bankRunning.getSflx())) {
				this.bankRunning.setJe(this.bankRunning.getSrje());
			} else {
				this.bankRunning.setJe(this.bankRunning.getZcje());
			}
		} else {
			this.bankRunning = new BankRunning();
			this.bankRunning.setToday(DateUtil.DateToString(new Date(),
					"yyyy-MM-dd"));
		}
		return "inputrec";
	}

	@rmpfLog(desc = "银行流水删除")
	public String delete() {
		BankRunning persistent = (BankRunning) this.bankRunningService
				.load(this.bankRunning.getId());
		if ("1".equals(persistent.getShbj())) {
			return "error";
		}
		this.bankRunningService.delete(this.bankRunning.getId());
		return "success";
	}

	public String loadBankRunning() {
		List<BankRunning> bankRunningList = new ArrayList();
		if (this.bankRunning != null) {
			bankRunningList = this.bankRunningService
					.findAuditBankRunningList(this.bankRunning);
		}
		Map optionMap = new HashMap();
		List optionList = new ArrayList();

		double sumskje = 0.0D;
		double sumfkje = 0.0D;
		for (BankRunning bankRunning : bankRunningList) {
			Map<String, String> map = new HashMap();
			map.put("id", bankRunning.getId() + "");
			map.put("jzsj",
					DateUtil.formatDate(bankRunning.getJzsj(), "yyyy-MM-dd"));
			map.put("djm", bankRunning.getBank().getDjm());
			map.put("dfzh",
					bankRunning.getDfzh() == null ? "" : bankRunning.getDfzh());
			map.put("dfhm",
					bankRunning.getDfhm() == null ? "" : bankRunning.getDfhm());
			map.put("srje", getFormatMoney(bankRunning.getSrje()));
			map.put("zcje", getFormatMoney(bankRunning.getZcje()));
			map.put("bz", bankRunning.getBz());
			optionList.add(map);
		}
		optionMap.put("optionList", optionList);

		JSONArray jsonArray = JSONArray.fromObject(optionMap);
		return ajaxJson(jsonArray.toString());
	}

	public void exportXls() throws IOException {
		List<BankRunning> list = this.bankRunningService
				.findBankRunningListTotal(this.bankRunning);

		double sumSrje = 0.0D;
		double sumZcje = 0.0D;

		String filename = "订单列表数据.xls";
		WritableWorkbook workbook = null;
		WritableSheet sheet = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int rowNum = 1;
		try {
			workbook = Workbook.createWorkbook(baos);
			sheet = workbook.createSheet("银行流水", 0);
			Vector columnName = new Vector();
			columnName.add("状态");
			columnName.add("订单号");
			columnName.add("记帐日期");
			columnName.add("收支账户");
			columnName.add("收入");
			columnName.add("支出");
			columnName.add("对方账户");
			columnName.add("对方户名");
			columnName.add("操作人");
			columnName.add("录入时间");
			columnName.add("备注");

			sheet.setColumnView(0, 7);
			sheet.setColumnView(1, 15);
			sheet.setColumnView(2, 12);
			sheet.setColumnView(3, 12);
			sheet.setColumnView(4, 12);
			sheet.setColumnView(5, 12);
			sheet.setColumnView(6, 12);
			sheet.setColumnView(7, 12);
			sheet.setColumnView(8, 12);
			sheet.setColumnView(9, 12);
			sheet.setColumnView(10, 80);

			sheet.setRowView(0, 300);
			writeCol(sheet, columnName, 0);
			for (BankRunning bean : list) {
				Vector col = new Vector();
				col.add("0".equals(bean.getShbj()) ? "未审核" : "已审核");
				col.add(bean.getOrderId());
				col.add(DateUtil.formatDate(bean.getJzsj(), "yyyy-MM-dd"));
				col.add(bean.getBank().getDjm());
				col.add(getFormatMoney(bean.getSrje()));
				col.add(getFormatMoney(bean.getZcje()));
				col.add(bean.getDfzh());
				col.add(bean.getDfhm());
				col.add(bean.getLrr());
				col.add(DateUtil.formatDate(bean.getLrsj(), "yyyy-MM-dd"));
				col.add(bean.getBz());
				sheet.setRowView(rowNum, 300);
				writeCol(sheet, col, rowNum++);

				sumSrje += bean.getSrje();
				sumZcje += bean.getZcje();
			}
			Vector col = new Vector();
			col.add("");
			col.add("");
			col.add("合计：");
			col.add(rowNum - 1 + "条");
			col.add(getFormatMoney(sumSrje));
			col.add(getFormatMoney(sumZcje));
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

		getResponse().setHeader("Content-Disposition",
				"attachment;filename=" + filename);
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
			if (((col.get(i) instanceof Integer))
					|| ((col.get(i) instanceof Float))) {
				Number label = new Number(i, rowNum, Float.parseFloat(col
						.get(i) + ""));
				sheet.addCell(label);
			} else {
				Label label = new Label(i, rowNum, (String) col.get(i));
				sheet.addCell(label);
			}
		}
	}

	public BankRunning getBankRunning() {
		return this.bankRunning;
	}

	public void setBankRunning(BankRunning bankRunning) {
		this.bankRunning = bankRunning;
	}

	public Bank getBank() {
		return this.bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public List<BankRunning> getBankRunningList() {
		return this.bankRunningList;
	}

	public void setBankRunningList(List<BankRunning> bankRunningList) {
		this.bankRunningList = bankRunningList;
	}

	public String getCurrentBankId() {
		return this.currentBankId;
	}

	public void setCurrentBankId(String currentBankId) {
		this.currentBankId = currentBankId;
	}
}
