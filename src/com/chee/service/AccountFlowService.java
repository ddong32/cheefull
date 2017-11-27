package com.chee.service;

import com.chee.common.Page;
import com.chee.entity.AccountFlow;
import com.chee.entity.BankRunning;
import java.util.List;

public abstract interface AccountFlowService extends BaseService<AccountFlow, Integer> {
    public abstract Page<AccountFlow> findWaitAuditDataPage(Page<AccountFlow> paramPage, AccountFlow paramAccountFlow, String paramString);

    public abstract List<Integer> findAuditIdList(AccountFlow paramAccountFlow, String paramString);

    public abstract AccountFlow doSearchServices(Integer paramInteger);

    public abstract AccountFlow checkRepeatRefer(String paramString);

    public abstract int deleteAccountFlow(Integer paramInteger);

    public abstract long noGenerate();

    public abstract String updateAccountFlowPayManageApprove(String paramString1, String paramString2);

    public abstract String updateAccountFlowPayFinaneApprove(String paramString1, BankRunning paramBankRunning, String paramString2);

    public abstract String updateAccountFlowTakeFinaneApprove(String paramString1, String paramString2, String paramString3);

    public abstract String deleteRecord(String paramString1, String paramString2);

    public abstract int updateOrderId(String paramString1, String paramString2);
}