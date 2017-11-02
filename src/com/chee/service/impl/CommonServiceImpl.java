package com.chee.service.impl;

import com.chee.entity.Bank;
import com.chee.entity.Role;
import com.chee.entity.SysCode;
import com.chee.service.AccountFlowService;
import com.chee.service.BankService;
import com.chee.service.BusinessService;
import com.chee.service.CommonService;
import com.chee.service.SysCodeService;
import com.chee.util.SequenceUtil;
import com.chee.util.StaticUtils;
import com.chee.util.StringUtils;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service
public class CommonServiceImpl extends BaseServiceImpl<Role, Integer> implements CommonService {
    private static Log logger = LogFactory.getLog(CommonServiceImpl.class);
    @Resource
    private SysCodeService sysCodeService;
    @Resource
    private BankService bankService;
    @Resource
    private SequenceUtil sequenceUtil;
    @Resource
    private BusinessService businessService;
    @Resource
    private AccountFlowService accountFlowService;

    @PostConstruct
    public void init() {
        logger.info("CommonServiceImpl initing sysCode to set Map...");
        StaticUtils.setBankMap(findBankMap());
        StaticUtils.setDdztMap(getCodeTypeMap(3));
        StaticUtils.setFklxMap(getCodeTypeMap(4));
        StaticUtils.setSflxMap(getCodeTypeMap(5));
        StaticUtils.setSffsMap(getCodeTypeMap(6));
        StaticUtils.setAccountStatMap(getCodeTypeMap(7));
        StaticUtils.setYwlxMap(getCodeTypeMap(8));
        StaticUtils.setDdlxMap(getCodeTypeMap(9));
        StaticUtils.setExpenceMap(getCodeTypeMap(10));
        StaticUtils.setExpenceStatMap(getCodeTypeMap(11));
        StaticUtils.setCooptypeMap(getCodeTypeMap(12));

        this.sequenceUtil.setOrderId(businessOrderIdGenerate() + 1L);
        this.sequenceUtil.setLsh(accountflowGenerate() + 1L);

        String baseUploadPath = "/attachment";
        int maxPostSize = 1048576;

        logger.info("CommonServiceImpl init finished...");
    }

    public Map<String, String> getCodeTypeMap(int codeType) {
        List<SysCode> list = this.sysCodeService.getSysCodeList(1, codeType);
        Map<String, String> map = new LinkedHashMap<String, String>();
        for (SysCode sysCode : list) {
            if (StringUtils.isNotEmpty(sysCode.getCodeCode())) {
                map.put(sysCode.getCodeCode(), sysCode.getCodeName());
                
            } else {
                map.put(sysCode.getCodeCode(), "");
            }
        }
        return map;
    }

    public Map<String, String> findBankMap() {
        List<Bank> list = this.bankService.getAll();
        Map<String, String> map = new TreeMap<String, String>();
        for (Bank bank : list) {
            map.put(bank.getId().toString(), bank.getDjm());
        }
        return map;
    }

    public long businessOrderIdGenerate() {
        return this.businessService.noGenerate();
    }

    public long accountflowGenerate() {
        return this.accountFlowService.noGenerate();
    }
}
