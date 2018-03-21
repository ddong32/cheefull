package com.chee.dao;

import com.chee.entity.Affiche;
import java.util.List;

public abstract interface AfficheDao extends BaseDao<Affiche, Integer> {
    public abstract List<Affiche> getMainPageAfficheList();
}