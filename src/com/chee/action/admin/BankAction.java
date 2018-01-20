package com.chee.action.admin;

import com.chee.annotation.rmpfLog;
import com.chee.entity.Bank;
import com.chee.entity.User;
import com.chee.service.BankRunningService;
import com.chee.service.BankService;
import com.chee.service.CommonService;
import com.chee.util.DateUtil;
import com.chee.util.StringUtils;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.Validations;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import net.sf.json.JSONArray;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.BeanUtils;

@ParentPackage("admin")
@Results({ @org.apache.struts2.convention.annotation.Result(name = "index", location = "Bank!list.action", type = "redirect") })
public class BankAction extends BaseAction<Bank, Integer> {
    private static final long serialVersionUID = 4984599292597512018L;
    @Resource
    private BankService bankService;
    @Resource
    private BankRunningService bankRunningService;
    @Resource
    private CommonService commonService;
    private Bank bank;
    private List<Bank> banklist;
    private Map<String, Object> resultMap;
    
    private String jzsj;

    public String list() throws Exception {
        this.resultMap = this.bankService.getBankRuningMoneyStat();
        return "list";
    }

    public String syslist() throws Exception {
        this.resultMap = this.bankService.getBankRuningMoneyStat();
        return "syslist";
    }

    @Validations(stringLengthFields = {
            @com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator(fieldName = "bank.name", minLength = "1", maxLength = "20", message = "角色名称长度必须在${minLength}到${maxLength}之间!"),
            @com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator(fieldName = "bank.code", minLength = "1", maxLength = "30", message = "角色标识长度必须在${minLength}到${maxLength}之间!"),
            @com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator(fieldName = "bank.khh", maxLength = "256", message = "角色描述长度不允许大于${maxLength}!") })
    @InputConfig(resultName = "error")
    @rmpfLog(desc = "银行帐户修改/添加")
    public String save() throws Exception {
        if (this.bank.getId() != null) {
            Bank persistent = (Bank) this.bankService.load(this.bank.getId());
            if (!this.bankService.isUnique("code", persistent.getCode(), this.bank.getCode())) {
                addActionError("卡号: " + this.bank.getCode() + " 已经存在!请重新输入!");
                return "error";
            }
            BeanUtils.copyProperties(this.bank, persistent, new String[] { "id", "gxsj"});
            persistent.setGxsj(new Date());
            this.bankService.update(persistent);
        } else {
            if (!this.bankService.isUnique("code", null, this.bank.getCode())) {
                addActionError("卡号: " + this.bank.getCode() + " 已经存在!请重新输入!");
                return "error";
            }
            this.bank.setLrsj(new Date());
            this.bankService.save(this.bank);
        }
        this.commonService.init();
        this.redirectionUrl = "bank!syslist.action";
        return "success";
    }

    @rmpfLog(desc = "银行帐户删除")
    public String delete() {
        this.bankService.delete(this.bank.getId());
        this.commonService.init();
        this.redirectionUrl = "bank!list.action";
        return "success";
    }

    public String ajaxBankCash() {
        Map<String, String> map = new HashMap();
        if ((this.bank != null) && (!"".equals(this.bank.getId()))) {
            this.bank = ((Bank) this.bankService.get(Integer.valueOf(this.bank.getId().intValue())));
            map.put("cush", getFormatMoney(this.bank.getCush()));
            map.put("cush_noformat", String.valueOf(this.bank.getCush()));
            JSONArray jsonArray = JSONArray.fromObject(map);
            return ajaxJson(jsonArray.toString());
        }
        return ajaxJson("");
    }

    public String input() {
        if ((this.bank != null) && (this.bank.getId() != null)) {
            this.bank = ((Bank) this.bankService.get(this.bank.getId()));
        } else {
            this.bank = new Bank();
        }
        return "input";
    }

    public String transferinput() {
        setJzsj(DateUtil.afterNDay(new Date(), 0, "yyyy-MM-dd"));
        return "transferinput";
    }

    public String transfer() throws Exception {
        return this.bankService.doTransfer(this.bank, getLoginUser().getName(), this.jzsj);
    }

    public String transferlog() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class);
        if (this.bank == null) {
            this.bank = new Bank();
            if (StringUtils.isEmpty(this.bank.getBeginDate())) {
                this.bank.setBeginDate(DateUtil.afterNDay(new Date(), -100, "yyyy-MM-dd"));
            }
            if (StringUtils.isEmpty(this.bank.getEndDate())) {
                this.bank.setEndDate(DateUtil.afterNDay(new Date(), 0, "yyyy-MM-dd"));
            }
        }
        return "transferlog";
    }

    public Bank getBank() {
        return this.bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public List<Bank> getBanklist() {
        return this.banklist;
    }

    public void setBanklist(List<Bank> banklist) {
        this.banklist = banklist;
    }

    public Map<String, Object> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    public String getJzsj() {
        return this.jzsj;
    }

    public void setJzsj(String jzsj) {
        this.jzsj = jzsj;
    }
}
