package com.chee.dao;

import com.chee.common.Page;
import com.chee.entity.AccountFlow;
import java.util.List;

public abstract interface AccountFlowDao extends BaseDao<AccountFlow, Integer> {
    public abstract Page<AccountFlow> findWaitAuditDataPage(Page<AccountFlow> paramPage, AccountFlow paramAccountFlow, String paramString);

    public abstract List<Integer> findAuditIdList(AccountFlow paramAccountFlow, String paramString);

    public abstract AccountFlow doSearchServices(Integer paramInteger);

    public abstract AccountFlow checkRepeatRefer(String paramString);

    public abstract int deleteAccountFlow(Integer paramInteger);

    public abstract long noGenerate();
}
