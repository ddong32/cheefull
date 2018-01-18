package com.chee.service;

import com.chee.entity.Role;

public abstract interface CommonService extends BaseService<Role, Integer> {
    public abstract void init();
}
