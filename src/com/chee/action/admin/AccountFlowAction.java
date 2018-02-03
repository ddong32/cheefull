package com.chee.action.admin;

import com.chee.annotation.rmpfLog;
import com.chee.entity.AccountFlow;
import com.chee.entity.Bank;
import com.chee.entity.BankRunning;
import com.chee.entity.User;
import com.chee.service.AccountFlowService;
import com.chee.service.BankRunningService;
import com.chee.service.BankService;
import com.chee.util.DateUtil;
import com.chee.util.StaticUtils;
import com.chee.util.StringUtils;
import com.chee.util.Utils;
import java.io.PrintStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import net.sf.json.JSONArray;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Results;

/**
 * @author qin
 *
 */
@ParentPackage("admin")
@Results({ @org.apache.struts2.convention.annotation.Result(name = "index", location = "account_flow!list.action", type = "redirect") })
public class AccountFlowAction extends BaseAction<AccountFlow, Integer> {
    private static final long serialVersionUID = 4984599292597512018L;
    @Resource
    private AccountFlowService accountFlowService;
    @Resource
    private BankRunningService bankRunningService;
    @Resource
    private BankService bankService;
    private AccountFlow accountFlow;
    private BankRunning bankRunning;
    private List<AccountFlow> accountFlowlist;
    private Integer[] ids;
    private String type;
    private String nextId;
    private String id;
    private String bankRunningId;

    public String takeFinaneChecked() {
        if (this.accountFlow == null) {
            this.accountFlow = new AccountFlow();
            if (StringUtils.isEmpty(this.accountFlow.getBeginDate())) {
                this.accountFlow.setBeginDate(DateUtil.afterNDay(new Date(), -100, "yyyy-MM-dd"));
            }
            if (StringUtils.isEmpty(this.accountFlow.getEndDate())) {
                this.accountFlow.setEndDate(DateUtil.DateToString(new Date(), "yyyy-MM-dd"));
            }
            this.accountFlow.setStat("1");
        }
        this.page = this.accountFlowService.findWaitAuditDataPage(this.page, this.accountFlow, "takeFinaneChecked");
        return "takeFinaneChecked";
    }

    public String takeFinaneRechecked() {
        if (this.accountFlow == null) {
            this.accountFlow = new AccountFlow();
            if (StringUtils.isEmpty(this.accountFlow.getBeginDate())) {
                this.accountFlow.setBeginDate(DateUtil.afterNDay(new Date(), -100, "yyyy-MM-dd"));
            }
            if (StringUtils.isEmpty(this.accountFlow.getEndDate())) {
                this.accountFlow.setEndDate(DateUtil.DateToString(new Date(), "yyyy-MM-dd"));
            }
        }
        this.page = this.accountFlowService.findWaitAuditDataPage(this.page, this.accountFlow, "takeFinaneRechecked");
        return "takeFinaneRechecked";
    }

    public String payFinaneChecked() {
        if (this.accountFlow == null) {
            this.accountFlow = new AccountFlow();
            this.accountFlow.setStat("2");
        }
        this.page = this.accountFlowService.findWaitAuditDataPage(this.page, this.accountFlow, "payFinaneChecked");
        return "payFinaneChecked";
    }

    public String takePayment() {
        if (this.accountFlow == null) {
            this.accountFlow = new AccountFlow();

            this.accountFlow.setStat("2");
        }
        this.page = this.accountFlowService.findWaitAuditDataPage(this.page, this.accountFlow, "takePayment");
        return "takePayment";
    }

    public String payFinaneRechecked() {
        if (this.accountFlow == null) {
            this.accountFlow = new AccountFlow();
            if (StringUtils.isEmpty(this.accountFlow.getBeginDate())) {
                this.accountFlow.setBeginDate(DateUtil.afterNDay(new Date(), -100, "yyyy-MM-dd"));
            }
            if (StringUtils.isEmpty(this.accountFlow.getEndDate())) {
                this.accountFlow.setEndDate(DateUtil.DateToString(new Date(), "yyyy-MM-dd"));
            }
        }
        this.page = this.accountFlowService.findWaitAuditDataPage(this.page, this.accountFlow, "payFinaneRechecked");
        return "payFinaneRechecked";
    }

    public String payManageChecked() {
        if (this.accountFlow == null) {
            this.accountFlow = new AccountFlow();

            this.accountFlow.setStat("1");
        }
        this.page = this.accountFlowService.findWaitAuditDataPage(this.page, this.accountFlow, "payManageChecked");
        return "payManageChecked";
    }

    public String payManageRechecked() {
        if (this.accountFlow == null) {
            this.accountFlow = new AccountFlow();
            if (StringUtils.isEmpty(this.accountFlow.getBeginDate())) {
                this.accountFlow.setBeginDate(DateUtil.afterNDay(new Date(), -100, "yyyy-MM-dd"));
            }
            if (StringUtils.isEmpty(this.accountFlow.getEndDate())) {
                this.accountFlow.setEndDate(DateUtil.DateToString(new Date(), "yyyy-MM-dd"));
            }
        }
        this.page = this.accountFlowService.findWaitAuditDataPage(this.page, this.accountFlow, "payManageRechecked");
        return "payManageRechecked";
    }

    public String viewData() {
        if (this.accountFlow == null) {
            this.accountFlow = new AccountFlow();
            if (StringUtils.isEmpty(this.accountFlow.getBeginDate())) {
                this.accountFlow.setBeginDate(DateUtil.afterNDay(new Date(), -100, "yyyy-MM-dd"));
            }
            if (StringUtils.isEmpty(this.accountFlow.getEndDate())) {
                this.accountFlow.setEndDate(DateUtil.DateToString(new Date(), "yyyy-MM-dd"));
            }
        }
        this.page = this.accountFlowService.findWaitAuditDataPage(this.page, this.accountFlow, "viewData");
        return "viewData";
    }

    public String inputpay() {
        if ((this.bankRunning != null) && (this.bankRunning.getId() != null)) {
            this.bankRunning = ((BankRunning) this.bankRunningService.get(this.bankRunning.getId()));
            this.bankRunning.setToday(DateUtil.DateToString(this.bankRunning.getJzsj(), "yyyy-MM-dd"));
            if ("1".equals(this.bankRunning.getSflx())) {
                this.bankRunning.setJe(this.bankRunning.getSrje());
            } else {
                this.bankRunning.setJe(this.bankRunning.getZcje());
            }
        } else {
            this.bankRunning = new BankRunning();
            this.bankRunning.setToday(DateUtil.DateToString(new Date(), "yyyy-MM-dd"));
        }
        return "inputpay";
    }

    public String audit() {
        try {
            int idIndex = -1;
            List<Integer> idList = null;
            idList = this.accountFlowService.findAuditIdList(this.accountFlow, this.type);
            int[] idArray;
            if ((idList != null) && (idList.size() > 0)) {
                idArray = new int[idList.size()];
                int i = 0;
                for (Integer auditId : idList) {
                    idArray[i] = auditId.intValue();
                    i++;
                }
                if (this.id != null) {
                    idIndex = idList.indexOf(Integer.valueOf(Integer.parseInt(this.id)));
                    idIndex--;
                }
            } else if (this.id != null) {
                idArray = new int[1];
                idArray[0] = Integer.parseInt(this.id);
            } else {
                idArray = new int[0];
            }
            setAttribute("idArray", idArray);
            setAttribute("idIndex", Integer.valueOf(idIndex));
            setAttribute("type", this.type);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return "interval";
    }

    @rmpfLog(desc = "财务审核")
    public void collateRecordJson() {
        String msg = "";
        if ((StringUtils.isNotEmpty(this.id)) && (Utils.isNumeric(this.id))) {
            try {
                if (("payManageChecked".equals(this.type)) || ("payManageRechecked".equals(this.type))) {
                    msg = this.accountFlowService.updateAccountFlowPayManageApprove(this.id, getLoginUser().getName());
                } else if (("payFinaneChecked".equals(this.type)) || ("payFinaneRechecked".equals(this.type)) || ("takePayment".equals(this.type)) || ("takePayment".equals(this.type))) {
                    msg = this.accountFlowService.updateAccountFlowPayFinaneApprove(this.id, this.bankRunning, getLoginUser().getName());
                } else if (("takeFinaneChecked".equals(this.type)) || ("takeFinaneRechecked".equals(this.type))) {
                    msg = this.accountFlowService.updateAccountFlowTakeFinaneApprove(this.id, this.bankRunningId, getLoginUser().getName());
                }
            } catch (Exception e) {
                System.out.println("审核时出错，ID: " + this.id + " 审核失败! Exception : " + e);
                msg = "审核时出错，ID: " + this.id + " 审核失败! Exception : " + e;
            }
        }
        AccountFlow nextAccountFlow = null;
        if ((this.nextId != null) && (!this.nextId.equals("")) && (!this.nextId.equals("NaN")) && (Utils.isNumeric(this.nextId))) {
            nextAccountFlow = (AccountFlow) this.accountFlowService.get(Integer.valueOf(this.nextId));
        }
        setJsonStr(nextAccountFlow, msg);
    }

    // 回退9
    public void deleteRecordJson() {
        String msg = "";
        if ((StringUtils.isNotEmpty(this.id)) && (Utils.isNumeric(this.id))) {
            try {
                msg = this.accountFlowService.deleteRecord(this.id, getLoginUser().getName());
            } catch (Exception e) {
                msg = "回退操作时出错，流水记录ID: " + this.id + "， Exception : " + e;
                System.out.println("回退操作时出错，流水记录ID: " + this.id + "， Exception : " + e);
                e.printStackTrace();
            }
        }
        AccountFlow nextAccountFlow = null;
        if ((this.nextId != null) && (!this.nextId.equals("")) && (!this.nextId.equals("NaN")) && (Utils.isNumeric(this.nextId))) {
            nextAccountFlow = (AccountFlow) this.accountFlowService.get(Integer.valueOf(this.nextId));
        }
        setJsonStr(nextAccountFlow, msg);
    }

    public void getUpRecordJson() {
        if ((StringUtils.isNotEmpty(this.id)) && (Utils.isNumeric(this.id))) {
            this.accountFlow = this.accountFlowService.doSearchServices(Integer.valueOf(this.id));
        } else {
            this.accountFlow = null;
        }
        setJsonStr(this.accountFlow, "");
    }

    public void getNextRecordJson() {
        if ((StringUtils.isNotEmpty(this.id)) && (Utils.isNumeric(this.id))) {
            this.accountFlow = this.accountFlowService.doSearchServices(Integer.valueOf(this.id));
        } else {
            this.accountFlow = null;
        }
        setJsonStr(this.accountFlow, "");
    }

    public void setJsonStr(AccountFlow accountFlow, String msg) {
        Map map = new HashMap();
        if (("".equals(msg)) && (msg == "")) {
            if (accountFlow != null) {
                map.put("id", accountFlow.getId());
                map.put("lsh", accountFlow.getLsh());
                map.put("lrr", accountFlow.getUser().getName());
                map.put("skje", getFormatMoney(accountFlow.getSkje()));
                map.put("fkje", getFormatMoney(accountFlow.getFkje()));
                map.put("shr", accountFlow.getShr() == null ? "" : accountFlow.getShr());
                map.put("spr", accountFlow.getSpr() == null ? "" : accountFlow.getSpr());
                map.put("stat", accountFlow.getStat());
                map.put("statZw", StaticUtils.getAccountStatMap().get(accountFlow.getStat()));
                map.put("sffsZw", StaticUtils.getSffsMap().get(accountFlow.getSffs()));
                map.put("sflx", accountFlow.getYwlx());
                map.put("sflxZw", StaticUtils.getSflxMap().get(accountFlow.getYwlx()));
                map.put("accountId", accountFlow.getAccountId());

                map.put("bankRunningId", accountFlow.getBankRunningId());
                if (accountFlow.getBank() != null) {
                    if (accountFlow.getBank().getDjm() == null) {
                        Bank bank = (Bank) this.bankService.get(accountFlow.getBank().getId());
                        map.put("djm", bank.getDjm());
                    } else {
                        map.put("djm", accountFlow.getBank().getDjm());
                    }
                    map.put("bankId", accountFlow.getBank().getId());
                } else {
                    map.put("djm", "");
                }
                if (accountFlow.getShsj() != null) {
                    map.put("shsj", DateUtil.formatDate(accountFlow.getShsj(), null));
                } else {
                    map.put("shsj", "");
                }
                if (accountFlow.getSpsj() != null) {
                    map.put("spsj", DateUtil.formatDate(accountFlow.getSpsj(), null));
                } else {
                    map.put("spsj", "");
                }
                if (accountFlow.getLrsj() != null) {
                    map.put("lrsj", DateUtil.formatDate(accountFlow.getLrsj(), null));
                } else {
                    map.put("lrsj", "");
                }
                if (accountFlow.getSksj() != null) {
                    map.put("sksj", DateUtil.formatDate(accountFlow.getSksj(), "yyyy-MM-dd"));
                } else {
                    map.put("sksj", "");
                }
                if ("2".equals(accountFlow.getYwlx())) {
                    map.put("je", Double.valueOf(accountFlow.getFkje()));
                } else {
                    map.put("je", Double.valueOf(accountFlow.getSkje()));
                }
                map.put("error", "");
                JSONArray jsonArray = JSONArray.fromObject(map);
                ajaxJson(jsonArray.toString());
            } else {
                ajaxJson("");
            }
        } else {
            map.put("error", msg.toString());
            JSONArray jsonArray = JSONArray.fromObject(map);
            ajaxJson(jsonArray.toString());
        }
    }

    public AccountFlow getAccountFlow() {
        return this.accountFlow;
    }

    public void setAccountFlow(AccountFlow accountFlow) {
        this.accountFlow = accountFlow;
    }

    public List<AccountFlow> getAccountFlowlist() {
        return this.accountFlowlist;
    }

    public void setAccountFlowlist(List<AccountFlow> accountFlowlist) {
        this.accountFlowlist = accountFlowlist;
    }

    public Integer[] getIds() {
        return this.ids;
    }

    public void setIds(Integer[] ids) {
        this.ids = ids;
    }

    public String getNextId() {
        return this.nextId;
    }

    public void setNextId(String nextId) {
        this.nextId = nextId;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBankRunningId() {
        return this.bankRunningId;
    }

    public void setBankRunningId(String bankRunningId) {
        this.bankRunningId = bankRunningId;
    }

    public BankRunning getBankRunning() {
        return this.bankRunning;
    }

    public void setBankRunning(BankRunning bankRunning) {
        this.bankRunning = bankRunning;
    }
}
