package com.chee.service.impl;

import com.chee.common.Page;
import com.chee.dao.CooperatorDao;
import com.chee.entity.Cooperator;
import com.chee.service.CooperatorService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CooperatorServiceImpl extends BaseServiceImpl<Cooperator, Integer> implements CooperatorService {
    @Resource
    CooperatorDao cooperatorDao;

    @Resource
    public void setBaseDao(CooperatorDao cooperatorDao) {
        super.setBaseDao(cooperatorDao);
    }

    public Page<Cooperator> findCooperatorDialogPage(Page<Cooperator> page, Cooperator cooperator) {
        return this.cooperatorDao.findCooperatorDialogPage(page, cooperator);
    }
}