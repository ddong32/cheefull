package com.chee.action.admin;

import com.chee.entity.BankRunning;
import com.chee.entity.Expence;
import com.chee.entity.ExpenceFlow;
import com.chee.service.BankRunningService;
import com.chee.service.ExpenceFlowService;
import com.chee.service.ExpenceService;
import com.chee.util.DateUtil;
import com.chee.util.StringUtils;
import com.chee.util.Utils;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import net.sf.json.JSONArray;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.BeanUtils;

@ParentPackage("admin")
@Results({ @org.apache.struts2.convention.annotation.Result(name = "index", location = "expence!list.action", type = "redirect") })
public class ExpenceAction extends BaseAction<Expence, Integer> {
    private static final long serialVersionUID = 4984599292597512018L;
    @Resource
    private ExpenceService expenceService;
    @Resource
    private ExpenceFlowService expenceFlowService;
    @Resource
    private BankRunningService bankRunningService;
    private Integer[] ids;
    private String nextId;
    private String id;
    private Expence expence;
    private ExpenceFlow expenceFlow;
    private BankRunning bankRunning;

    @InputConfig(resultName = "error")
    public String save() throws Exception {
        List<Expence> list = this.expenceService.find("From Expence where bxr = ? and bxsj = ? and bxje = ?", new Object[] { this.expence.getBxr(), this.expence.getBxsj(), this.expence.getBxje() });
        if ((list != null) && (list.size() > 0)) {
            addActionError("报销人: " + this.expence.getBxr() + " 报账时间：" + this.expence.getBxsj() + " 报销金额： " + this.expence.getBxje());
            return "error";
        }
        if (this.expence.getId() != null) {
            Expence persistent = (Expence) this.expenceService.load(this.expence.getId());
            if (("0".equals(persistent.getStat())) || (persistent.getStat() == "0") || ("1".equals(persistent.getStat())) || (persistent.getStat() == "1")) {
                BeanUtils.copyProperties(this.expence, persistent, new String[] { "id", "gxsj", "bxsj", "bxr", "stat", "user" });
                this.expenceService.update(persistent);
            } else {
                addActionError("报销状态这:" + persistent.getStat() + "不能更新！");
                return "error";
            }
        } else {
            this.expence.setUser(getLoginUser());
            this.expence.setBxr(getLoginUser().getName());
            this.expence.setBxsj(new Date());
            this.expence.setStat("1");
            this.expenceService.save(this.expence, getLoginUser());
        }
        return "success";
    }

    public String delete() {
        this.expenceService.delete(this.expence.getId());
        this.redirectionUrl = "bank!list.action";
        return "success";
    }

    public String input() {
        if ((this.expence != null) && (this.expence.getId() != null)) {
            this.expence = ((Expence) this.expenceService.get(this.expence.getId()));
        } else {
            this.expence = new Expence();
            this.expence.setUser(getLoginUser());
        }
        return "input";
    }

    public void pass() {
        String msg = "";
        if ((this.expence != null) && (this.expence.getId() != null)) {
            try {
                msg = this.expenceService.doPass(this.expence, getLoginUser());
            } catch (Exception e) {
                System.out.println("审核时出错，ID: " + this.id + " 审核失败! Exception : " + e);
                msg = "审核时出错，ID: " + this.id + " 审核失败! Exception : " + e;
            }
        }
        Map<String, String> map = new HashMap<String, String>();
        if (("".equals(msg)) && (msg == "")) {
            map.put("error", "");
        } else {
            map.put("error", msg.toString());
        }
        JSONArray jsonArray = JSONArray.fromObject(map);
        ajaxJson(jsonArray.toString());
    }

    public void payy() {
        String msg = "";
        if ((this.expence != null) && (this.expence.getId() != null)) {
            try {
                msg = this.expenceService.doPayy(this.expence, this.bankRunning, getLoginUser());
            } catch (Exception e) {
                System.out.println("审核时出错，ID: " + this.id + " 审核失败! Exception : " + e);
                msg = "审核时出错，ID: " + this.id + " 审核失败! Exception : " + e;
            }
        }
        Map<String, String> map = new HashMap<String, String>();
        if (("".equals(msg)) && (msg == "")) {
            map.put("error", "");
        } else {
            map.put("error", msg.toString());
        }
        JSONArray jsonArray = JSONArray.fromObject(map);
        ajaxJson(jsonArray.toString());
    }

    public void cancel() {
        String msg = "";
        if ((this.expence != null) && (this.expence.getId() != null)) {
            try {
                msg = this.expenceService.doCancel(this.expence.getId(), getLoginUser());
            } catch (Exception e) {
                System.out.println("审核时出错，ID: " + this.id + "通过失败! Exception : " + e);
                msg = "审核时出错，ID: " + this.id + "通过失败! Exception : " + e;
            }
        }
        Map<String, String> map = new HashMap<String, String>();
        if (("".equals(msg)) && (msg == "")) {
            map.put("error", "");
        } else {
            map.put("error", msg.toString());
        }
        JSONArray jsonArray = JSONArray.fromObject(map);
        ajaxJson(jsonArray.toString());
    }

    public String list() {
        if (this.expence == null) {
            this.expence = new Expence();
            if (StringUtils.isEmpty(this.expence.getBeginDate())) {
                this.expence.setBeginDate(DateUtil.afterNDay(new Date(), -60, "yyyy-MM-dd"));
            }
            if (StringUtils.isEmpty(this.expence.getEndDate())) {
                this.expence.setEndDate(DateUtil.afterNDay(new Date(), 0, "yyyy-MM-dd"));
            }
        }
        this.page = this.expenceService.findExpencePage(this.page, this.expence, "list");
        return "list";
    }

    public String verificationData() {
        if (this.expence == null) {
            this.expence = new Expence();
            if (StringUtils.isEmpty(this.expence.getBeginDate())) {
                this.expence.setBeginDate(DateUtil.afterNDay(new Date(), -60, "yyyy-MM-dd"));
            }
            if (StringUtils.isEmpty(this.expence.getEndDate())) {
                this.expence.setEndDate(DateUtil.afterNDay(new Date(), 0, "yyyy-MM-dd"));
            }
            if (StringUtils.isEmpty(this.expence.getStat())) {
                this.expence.setStat("1");
            }
        }
        this.page = this.expenceService.findExpencePage(this.page, this.expence, "pass");
        return "verificationData";
    }

    public String financialData() {
        if (this.expence == null) {
            this.expence = new Expence();
            if (StringUtils.isEmpty(this.expence.getBeginDate())) {
                this.expence.setBeginDate(DateUtil.afterNDay(new Date(), -60, "yyyy-MM-dd"));
            }
            if (StringUtils.isEmpty(this.expence.getEndDate())) {
                this.expence.setEndDate(DateUtil.afterNDay(new Date(), 0, "yyyy-MM-dd"));
            }
            if (StringUtils.isEmpty(this.expence.getStat())) {
                this.expence.setStat("2");
            }
        }
        this.page = this.expenceService.findExpencePage(this.page, this.expence, "payy");
        return "financialData";
    }

    public String audit() {
        try {
            int idIndex = -1;
            List<Integer> idList = null;
            idList = this.expenceService.findAuditIdList(this.expence);
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
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return "interval";
    }

    public void getExpenceIdsJson() {
        if ((StringUtils.isNotEmpty(this.id)) && (Utils.isNumeric(this.id))) {
            this.expence = this.expenceService.getExpenceIds(Integer.valueOf(this.id));
        } else {
            this.expence = null;
        }
        setJsonStr(this.expence, "");
    }

    public void setJsonStr(Expence expence, String msg) {
        Map<String, String> map = new HashMap<String, String>();
        if (("".equals(msg)) && (msg == "")) {
            if (expence != null) {
                map.put("id", expence.getId().toString());
                map.put("bxlx", getExpenceMap().get(expence.getBxlx()));
                map.put("bxje", expence.getBxje());
                map.put("bxr", expence.getBxr());
                map.put("bxnr", expence.getBxnr());
                map.put("fssj", DateUtil.formatDate(expence.getFssj(), "yyyy-MM-dd"));
                map.put("bxsj", DateUtil.formatDate(expence.getBxsj(), "yyyy-MM-dd"));
                map.put("stat", expence.getStat());
                map.put("statZw", getExpenceStatMap().get(expence.getStat()));
                map.put("departmentName", expence.getUser().getDepartment().getName());
                map.put("bankrunningid", (expence.getBankRunningId() == null) && ("".equals(expence.getBankRunningId())) ? "" : expence.getBankRunningId().toString());
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

    public String loadExpenceFlow() {
        List<ExpenceFlow> expenceFlowList = new ArrayList<ExpenceFlow>();
        if (this.expence != null) {
            expenceFlowList = this.expenceFlowService.findExpenceFlowList(this.expence);
        }
        Map<String, Object> optionMap = new HashMap<String, Object>();
        List optionList = new ArrayList();
        for (ExpenceFlow expenceFlow : expenceFlowList) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", expenceFlow.getId().toString());
            map.put("userName", expenceFlow.getUser().getName());
            map.put("gxsj", DateUtil.formatDate(expenceFlow.getGxsj(), "yyyy-MM-dd HH:mm:ss"));
            map.put("opt", expenceFlow.getOptContent());
            map.put("ywlc", expenceFlow.getYwlc().toString());
            optionList.add(map);
        }
        optionMap.put("optionList", optionList);

        JSONArray jsonArray = JSONArray.fromObject(optionMap);
        return ajaxJson(jsonArray.toString());
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

    public Expence getExpence() {
        return this.expence;
    }

    public void setExpence(Expence expence) {
        this.expence = expence;
    }

    public ExpenceFlow getExpenceFlow() {
        return this.expenceFlow;
    }

    public void setExpenceFlow(ExpenceFlow expenceFlow) {
        this.expenceFlow = expenceFlow;
    }

    public BankRunning getBankRunning() {
        return this.bankRunning;
    }

    public void setBankRunning(BankRunning bankRunning) {
        this.bankRunning = bankRunning;
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

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
