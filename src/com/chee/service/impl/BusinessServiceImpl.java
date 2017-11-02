package com.chee.service.impl;

import com.chee.common.Page;
import com.chee.dao.BusinessDao;
import com.chee.entity.Business;
import com.chee.service.AccountFlowService;
import com.chee.service.AccountService;
import com.chee.service.BusinessCustomerService;
import com.chee.service.BusinessService;
import com.chee.util.DateUtil;
import com.chee.util.SequenceUtil;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class BusinessServiceImpl extends BaseServiceImpl<Business, Integer> implements BusinessService {
    @Resource
    private BusinessDao businessDao;
    @Resource
    private BusinessCustomerService businessCustomerService;
    @Resource
    private AccountService accountService;
    @Resource
    private AccountFlowService accountFlowService;
    @Resource
    private SequenceUtil sequenceUtil;

    @Resource
    public void setBaseDao(BusinessDao businessDao) {
        super.setBaseDao(businessDao);
    }

    public Page<Business> findBusinessPage(Page<Business> page, Business business) {
        return this.businessDao.findBusinessPage(page, business);
    }

    public List<Business> findBusinessListTotal(Business business) {
        return this.businessDao.findBusinessListTotal(business);
    }

    public int updateBusinessStat(Integer id, String stat) {
        return this.businessDao.updateBusinessStat(id, stat);
    }

    public Business findBusinessRecountJe(String orderId) {
        return this.businessDao.findBusinessRecountJe(orderId);
    }

    public long noGenerate() {
        return this.businessDao.noGenerate();
    }

    public List<String> getcfsjorderid(Date cfsj) {
        return this.businessDao.getcfsjorderid(cfsj);
    }

    public void updateOrderId(Business business) throws Exception {
        String orderId = "";
        Business persistent = (Business) this.businessDao.get(business.getId());
        persistent.setTravelLine(business.getTravelLine());
        persistent.setYwlx(business.getYwlx());
        persistent.setCfsj(business.getCfsj());
        persistent.setBz(business.getBz());
        persistent.setGxsj(new Date());
        persistent.setDdlx(business.getDdlx());
        if (!business.getSdate().equals(DateUtil.DateToString(business.getCfsj(), "yyyy-MM-dd"))) {
            orderId = this.sequenceUtil.ProductOrderId(business.getCfsj());
            persistent.setOrderId(orderId);

            this.businessCustomerService.updateOrderId(business.getId(), business.getOrderId(), orderId);
            this.accountService.updateOrderId(business.getId(), business.getOrderId(), orderId);
            this.accountFlowService.updateOrderId(business.getOrderId(), orderId);
        }
        this.businessDao.update(persistent);
    }
}
