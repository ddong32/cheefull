package com.chee.action.admin;

import com.chee.annotation.rmpfLog;
import com.chee.entity.Account;
import com.chee.entity.Business;
import com.chee.entity.Cooperator;
import com.chee.service.AccountFlowService;
import com.chee.service.AccountService;
import com.chee.service.CooperatorService;
import com.chee.util.DateUtil;
import com.chee.util.StaticUtils;
import com.chee.util.StringUtils;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.Validations;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import net.sf.json.JSONArray;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Results;

@ParentPackage("admin")
@Results({ @org.apache.struts2.convention.annotation.Result(name = "index", location = "Account!list.action", type = "redirect") })
public class AccountAction extends BaseAction<Account, Integer> {
    private static final long serialVersionUID = 4984599292597512018L;
    @Resource
    private AccountService accountService;
    @Resource
    private AccountFlowService accountFlowService;
    @Resource
    private CooperatorService cooperatorService;
    private Account account;
    private List<Account> accountList;
    private List<Cooperator> cooperatorList;
    private String orderId;
    private Integer[] ids;
    private String account_ids;

    //订单录入 - 收付项目
    public String ajaxBusinessAccountTable() {
        try {
            List<Account> accountList = new ArrayList<Account>();
            if ((this.account != null) && (!"".equals(this.account.getOrderId()))) {
                accountList = this.accountService.findOrderID(this.account.getOrderId());
            }
            Map optionMap = new HashMap();
            List optionList = new ArrayList();
            List ztsLockList = new ArrayList();

            double sumskje = 0.0D;
            double sumfkje = 0.0D;
            for (Account account : accountList) {
                Map<String, String> map = new HashMap();
                map.put("id", account.getId() + "");
                map.put("stat", account.getStat());
                map.put("statZw", (String) StaticUtils.getAccountStatMap().get(account.getStat()));
                map.put("sflx", (String) StaticUtils.getSflxMap().get(account.getSflx()));
                if (("1".equals(account.getSflx())) || (account.getSflx() == "1")) {
                    if ((account.getBusinessCustomer() != null) && (account.getBusinessCustomer().getCustomer() != null) && (account.getBusinessCustomer().getCustomer().getPathName() != null)) {
                        map.put("sfmc", account.getBusinessCustomer().getCustomer().getPathName());
                    } else {
                        map.put("sfmc", "");
                    }
                } else if ((account.getCooperator() != null) && (account.getCooperator().getDwmc() != null)) {
                    map.put("sfmc", account.getCooperator().getDwmc());
                } else {
                    map.put("sfmc", "");
                }
                if (account.getBank() != null) {
                    map.put("bankdjm", account.getBank().getDjm());
                } else {
                    map.put("bankdjm", "");
                }
                map.put("fklx", (String) StaticUtils.getFklxMap().get(account.getFklx()));
                map.put("sffs", account.getSffs());
                map.put("sffsZw", (String) StaticUtils.getSffsMap().get(account.getSffs()));

                map.put("skje", getFormatMoney(account.getSkje()));
                map.put("fkje", getFormatMoney(account.getFkje()));
                map.put("lrsj", DateUtil.formatDate(account.getLrsj(), "yyyy-MM-dd"));
                map.put("lrr", account.getBusiness().getUser().getName());
                map.put("bz", account.getBz());
                map.put("shr", account.getShr());
                if (account.getShsj() != null) {
                    map.put("shsj", DateUtil.formatDate(account.getShsj(), "yyyy-MM-dd"));
                } else {
                    map.put("shsj", "");
                }
                if (account.getSfsj() != null) {
                    map.put("sfsj", DateUtil.formatDate(account.getSfsj(), "yyyy-MM-dd"));
                } else {
                    map.put("sfsj", "");
                }
                optionList.add(map);
                sumskje += account.getSkje();
                sumfkje += account.getFkje();
            }
            optionMap.put("optionList", optionList);
            optionMap.put("ztsLockList", ztsLockList);
            optionMap.put("sumskje", getFormatMoney(sumskje));
            optionMap.put("sumfkje", getFormatMoney(sumfkje));

            JSONArray jsonArray = JSONArray.fromObject(optionMap);
            return ajaxJson(jsonArray.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
            return ajaxJson(e.toString());
        }
    }

    public void ajaxLoadAccountId() {
        Map map = new HashMap();
        if (this.ids != null) {
            try {
                this.accountList = this.accountService.findAccountUnique(Integer.valueOf(this.ids[0].intValue()));
                this.account = ((Account) this.accountList.get(0));
                if (this.account == null) {
                    ajaxJson("");
                    return;
                }
                map.put("id", this.account.getId());
                map.put("sflx", this.account.getSflx());
                map.put("sffs", this.account.getSffs());
                map.put("skje", Double.valueOf(this.account.getSkje()));
                map.put("fkje", Double.valueOf(this.account.getFkje()));
                map.put("fklx", this.account.getFklx());
                map.put("bz", this.account.getBz());
                if ((this.account.getBusinessCustomer() != null) && (this.account.getBusinessCustomer().getId() != null)) {
                    map.put("bucId", this.account.getBusinessCustomer().getId());
                } else {
                    map.put("bucId", "");
                }
                if ((this.account.getBank() != null) && (this.account.getBank().getId() != null)) {
                    map.put("bankId", this.account.getBank().getId());
                } else {
                    map.put("bankId", "");
                }
                if ((this.account.getCooperator() != null) && (this.account.getCooperator().getId() != null)) {
                    map.put("coopId", this.account.getCooperator().getId());
                    map.put("coopDwmc", this.account.getCooperator().getDwmc());
                } else {
                    map.put("coopId", "");
                    map.put("coopDwmc", "");
                }
                if (this.account.getSfsj() != null) {
                    map.put("sfsj", DateUtil.formatDate(this.account.getSfsj(), "yyyy-MM-dd"));
                } else {
                    map.put("sfsj", "");
                }
                JSONArray jsonArray = JSONArray.fromObject(map);
                ajaxJson(jsonArray.toString());
            } catch (Exception e) {
                ajaxJson(e.toString());
            }
        } else {
            ajaxJson("");
        }
    }

    public String orderInput() {
        if ((this.orderId != null) && (!"".equals(this.orderId))) {
            if (this.ids != null) {
                this.account = ((Account) this.accountService.get(Integer.valueOf(this.ids[0].intValue())));
            }
        } else {
            this.account = new Account();
        }
        return "orderInput";
    }

    @Validations(stringLengthFields = {
            @com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator(fieldName = "bank.name", minLength = "1", maxLength = "20", message = "角色名称长度必须在${minLength}到${maxLength}之间!"),
            @com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator(fieldName = "bank.code", minLength = "1", maxLength = "30", message = "角色标识长度必须在${minLength}到${maxLength}之间!"),
            @com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator(fieldName = "bank.bankname_log", maxLength = "256", message = "角色描述长度不允许大于${maxLength}!") })
    @InputConfig(resultName = "error")
    public String save() throws Exception {
        this.accountService.doSave(this.account);
        this.redirectionUrl = "account!list.action";
        return "success";
    }

    public void ajaxBatchDelete() {
        String msg = "";
        if ((this.orderId != null) && (!"".equals(this.orderId))) {
            if (this.ids != null) {
                msg = this.accountService.doAjaxBatchDelete(this.ids);
            } else {
                msg = "没有选中数据,操作失败!";
            }
        } else {
            msg = "订单编号不能为空！";
        }
        ajaxJsonSuccessMessage(msg);
    }

    public void ajaxAccountStat() {
        String result = "";
        try {
            if (this.ids != null) {
                result = this.accountService.doAccountStat(this.ids);
            } else {
                result = "请勾选不能为空！";
            }
        } catch (Exception e) {
            result = e.toString();
        }
        if ("".equals(result)) {
            ajaxJsonSuccessMessage("操作完成!");
        } else {
            ajaxJsonErrorMessage(result);
        }
    }

    public void ajaxAccountStatRecall() {
        Account persistent = null;
        if (this.ids != null) {
            try {
                boolean flag = false;
                String stat = "";
                for (Integer id : this.ids) {
                    persistent = (Account) this.accountService.get(id);
                    if ("1".equals(persistent.getStat())) {
                        persistent.setGxsj(new Date());
                        persistent.setStat("8");

                        this.accountService.update(persistent);
                        this.accountFlowService.deleteAccountFlow(id);
                    } else {
                        flag = true;
                        stat = persistent.getOrderId() + ": 该收款状态为" + persistent.getStat() + "不能招回操作！";
                        break;
                    }
                }
                if (flag) {
                    ajaxJsonErrorMessage(stat);
                } else {
                    ajaxJsonSuccessMessage("操作完成!");
                }
            } catch (Exception e) {
                ajaxJsonSuccessMessage(e.toString());
            }
        } else {
            ajaxJsonSuccessMessage("没有选中数据,操作失败!");
        }
    }

    public String loadAccount() {
        List<Account> list = new ArrayList();
        if (this.account_ids != null) {
            String[] str = this.account_ids.split(",");
            Integer[] array = new Integer[str.length];
            for (int i = 0; i < str.length; i++) {
                array[i] = Integer.valueOf(Integer.parseInt(str[i]));
            }
            list = this.accountService.findAccountIds(array);
        }
        Map optionMap = new HashMap();
        List optionList = new ArrayList();
        List ztsLockList = new ArrayList();

        double sumskje = 0.0D;
        double sumfkje = 0.0D;
        for (Account temp : list) {
            Map<String, String> map = new HashMap();
            map.put("id", temp.getId() + "");
            map.put("stat", (String) StaticUtils.getAccountStatMap().get(temp.getStat()));
            map.put("orderId", temp.getOrderId());
            map.put("sflx", (String) StaticUtils.getSflxMap().get(temp.getSflx()));
            if (("1".equals(temp.getSflx())) || (temp.getSflx() == "1")) {
                if ((temp.getBusinessCustomer() != null) && (temp.getBusinessCustomer().getCustomer() != null)) {
                    map.put("sfmc", temp.getBusinessCustomer().getCustomer().getPathName());
                } else {
                    map.put("sfmc", "");
                }
                map.put("yhhm", "");
                map.put("yhzh", "");
            } else if ((temp.getCooperator() != null) && (temp.getCooperator().getDwmc() != null)) {
                map.put("sfmc", temp.getCooperator().getDwmc());
                map.put("yhhm", temp.getCooperator().getYhhm());
                map.put("yhzh", temp.getCooperator().getYhzh());
                map.put("cpid", temp.getCooperator().getId() + "");
            } else {
                map.put("sfmc", "");
                map.put("yhhm", "");
                map.put("yhzh", "");
                map.put("cpid", "");
            }
            map.put("fklx", (String) StaticUtils.getFklxMap().get(temp.getFklx()));
            map.put("sffs", (String) StaticUtils.getSffsMap().get(temp.getSffs()));
            map.put("skje", getFormatMoney(temp.getSkje()));
            map.put("fkje", getFormatMoney(temp.getFkje()));
            map.put("lrsj", DateUtil.formatDate(temp.getLrsj(), "yyyy-MM-dd"));
            map.put("lrr", temp.getBusiness().getUser().getName());
            map.put("ygs", getFormatMoney(temp.getBusiness().getYgs()));
            map.put("yjs", getFormatMoney(temp.getBusiness().getYjs()));
            map.put("wsk", getFormatMoney(temp.getBusiness().getYgs() - temp.getBusiness().getYjs()));
            map.put("bz", temp.getBz());
            if (temp.getBank() != null) {
                map.put("bankdjm", temp.getBank().getDjm());
            } else {
                map.put("bankdjm", "");
            }
            if ((temp.getBusiness() != null) && (temp.getBusiness().getTravelLine() != null)) {
                map.put("travelLine", temp.getBusiness().getTravelLine());
            } else {
                map.put("travelLine", "");
            }
            optionList.add(map);
            sumskje += temp.getSkje();
            sumfkje += temp.getFkje();
        }
        optionMap.put("optionList", optionList);
        optionMap.put("ztsLockList", ztsLockList);
        optionMap.put("sumskje", getFormatMoney(sumskje));
        optionMap.put("sumfkje", getFormatMoney(sumfkje));

        JSONArray jsonArray = JSONArray.fromObject(optionMap);
        return ajaxJson(jsonArray.toString());
    }

    public String onReceiptsList() {
        if (this.account == null) {
            this.account = new Account();
            if (StringUtils.isEmpty(this.account.getBeginDate())) {
                this.account.setBeginDate(DateUtil.afterNDay(new Date(), -100, "yyyy-MM-dd"));
            }
            if (StringUtils.isEmpty(this.account.getEndDate())) {
                this.account.setEndDate(DateUtil.DateToString(new Date(), "yyyy-MM-dd"));
            }
            this.account.setStat("0");
            this.account.setBusiness(new Business());
            this.account.getBusiness().setDdzt("1");
        }
        this.page = this.accountService.findOnTrustPage(this.page, this.account);
        return "onReceiptsList";
    }

    public String onPaymentList() {
        if (this.account == null) {
            this.account = new Account();
            if (StringUtils.isEmpty(this.account.getBeginDate())) {
                this.account.setBeginDate(DateUtil.afterNDay(new Date(), -100, "yyyy-MM-dd"));
            }
            if (StringUtils.isEmpty(this.account.getEndDate())) {
                this.account.setEndDate(DateUtil.DateToString(new Date(), "yyyy-MM-dd"));
            }
            this.account.setStat("1");
            this.account.setBusiness(new Business());
        }
        this.page = this.accountService.findOnPaymentPage(this.page, this.account);
        return "onPaymentList";
    }

    public String input() {
        if (this.ids != null) {
            this.accountList = this.accountService.findAccountIds(this.ids);
            double sumskje = 0.0D;
            double sumfkje = 0.0D;
            int counts = 0;
            for (int i = 0; this.accountList.size() > i; i++) {
                this.account = ((Account) this.accountList.get(i));
                sumskje += this.account.getSkje();
                sumfkje += this.account.getFkje();
                counts = i + 1;
            }
            this.account.setCounts(Integer.valueOf(counts));
            this.account.setZwsflx((String) getSflxMap().get(this.account.getSflx()));
            if ("1".equals(this.account.getSflx())) {
                this.account.setDwmc(this.account.getBusinessCustomer().getCustomer().getParent().getPathName());
                this.account.setJe(sumskje);
            } else {
                this.account.setDwmc(this.account.getCooperator().getDwmc());
                this.account.setJe(sumfkje);
            }
            this.account_ids = StringUtils.join(this.ids, "", ",");
        }
        return "onTrustInput";
    }

    public String receiptsInput() {
        if ((this.account != null) && (this.account.getId() != null)) {
            this.account = ((Account) this.accountService.load(this.account.getId()));
            this.account.setZwsflx((String) getSflxMap().get(this.account.getSflx()));
            if ("1".equals(this.account.getSflx())) {
                this.account.setDwmc(this.account.getBusinessCustomer().getCustomer().getParent().getPathName());
            } else {
                this.account.setDwmc(this.account.getCooperator().getDwmc());
            }
            this.account_ids = this.account.getId() + "";
        }
        return "receiptsInput";
    }

    public String paymentInput() {
        if (this.ids != null) {
            this.accountList = this.accountService.findAccountIds(this.ids);
            double sumskje = 0.0D;
            double sumfkje = 0.0D;
            int counts = 0;
            for (int i = 0; this.accountList.size() > i; i++) {
                this.account = ((Account) this.accountList.get(i));
                sumskje += this.account.getSkje();
                sumfkje += this.account.getFkje();
                counts = i + 1;
            }
            this.account.setCounts(Integer.valueOf(counts));
            this.account.setZwsflx((String) getSflxMap().get(this.account.getSflx()));
            if ("1".equals(this.account.getSflx())) {
                this.account.setDwmc(this.account.getBusinessCustomer().getCustomer().getParent().getPathName());
                this.account.setJe(sumskje);
            } else {
                if ((this.account.getCooperator() != null) && (this.account.getCooperator().getDwmc() != null)) {
                    this.account.setDwmc(this.account.getCooperator().getDwmc());
                } else {
                    this.account.setDwmc("");
                }
                this.account.setJe(sumfkje);
            }
            this.account_ids = StringUtils.join(this.ids, "", ",");
        }
        return "paymentInput";
    }

    public String lookinput() {
        return "input";
    }

    public void ajaxAccountOnTrustRefer() {
        String result = "";
        try {
            if ((this.account_ids != null) && (!"".equals(this.account_ids))) {
                String[] str = this.account_ids.split(",");
                Integer[] array = new Integer[str.length];
                for (int i = 0; i < str.length; i++) {
                    array[i] = Integer.valueOf(Integer.parseInt(str[i]));
                }
                result = this.accountService.doAccountRefer(array, this.account);
            } else {
                result = "请勾选数据不能为空！";
            }
        } catch (Exception e) {
            result = e.toString();
        }
        if ("".equals(result)) {
            ajaxJsonSuccessMessage("操作完成!");
        } else {
            ajaxJsonErrorMessage(result);
        }
    }

    @rmpfLog(desc = "订单审核-更新-备注")
    public void ajaxUpdateAccountBz() {
        if ((this.account != null) && (this.account.getId() != null)) {
            try {
                this.accountService.updateAccountBz(this.account);
                ajaxJsonSuccessMessage("操作完成!");
            } catch (Exception e) {
                ajaxJsonErrorMessage(e.toString());
            }
        } else {
            ajaxJsonWarnMessage("没有选中数据,操作失败!");
        }
    }

    public List<Cooperator> getCooperatorList() {
        if (this.cooperatorList == null) {
            StringBuffer hqlSb = new StringBuffer(" from Cooperator u where 1=1 order by dwmc");
            Map map = new HashMap();
            this.cooperatorList = this.cooperatorService.find(hqlSb.toString(), map);
        }
        return this.cooperatorList;
    }

    public void setCooperatorList(List<Cooperator> cooperatorList) {
        this.cooperatorList = cooperatorList;
    }

    public Account getAccount() {
        return this.account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Account> getAccountList() {
        return this.accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
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

    public String getAccount_ids() {
        return this.account_ids;
    }

    public void setAccount_ids(String account_ids) {
        this.account_ids = account_ids;
    }
}
