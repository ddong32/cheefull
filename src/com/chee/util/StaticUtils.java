package com.chee.util;

import java.util.HashMap;
import java.util.Map;

public class StaticUtils {
    private static Map<String, String> bankMap = new HashMap<String, String>();
    private static Map<String, String> ddztMap = new HashMap<String, String>();
    private static Map<String, String> fklxMap = new HashMap<String, String>();
    private static Map<String, String> sflxMap = new HashMap<String, String>();
    private static Map<String, String> sffsMap = new HashMap<String, String>();
    private static Map<String, String> accountStatMap = new HashMap<String, String>();
    private static Map<String, String> ywlxMap = new HashMap<String, String>();
    private static Map<String, String> ddlxMap = new HashMap<String, String>();
    private static Map<String, String> expenceMap = new HashMap<String, String>();
    private static Map<String, String> expenceStatMap = new HashMap<String, String>();
    private static Map<String, String> cooptypeMap = new HashMap<String, String>();
    private static Map<String, String> optionMap = new HashMap<String, String>();

    public static Map<String, String> getBankMap() {
        return bankMap;
    }

    public static void setBankMap(Map<String, String> bankMap) {
        StaticUtils.bankMap = bankMap;
    }

    public static Map<String, String> getDdztMap() {
        return ddztMap;
    }

    public static void setDdztMap(Map<String, String> ddztMap) {
        StaticUtils.ddztMap = ddztMap;
    }

    public static Map<String, String> getFklxMap() {
        return fklxMap;
    }

    public static void setFklxMap(Map<String, String> fklxMap) {
        StaticUtils.fklxMap = fklxMap;
    }

    public static Map<String, String> getSflxMap() {
        return sflxMap;
    }

    public static void setSflxMap(Map<String, String> sflxMap) {
        StaticUtils.sflxMap = sflxMap;
    }

    public static Map<String, String> getSffsMap() {
        return sffsMap;
    }

    public static void setSffsMap(Map<String, String> sffsMap) {
        StaticUtils.sffsMap = sffsMap;
    }

    public static Map<String, String> getAccountStatMap() {
        return accountStatMap;
    }

    public static void setAccountStatMap(Map<String, String> accountStatMap) {
        StaticUtils.accountStatMap = accountStatMap;
    }

    public static Map<String, String> getYwlxMap() {
        return ywlxMap;
    }

    public static void setYwlxMap(Map<String, String> ywlxMap) {
        StaticUtils.ywlxMap = ywlxMap;
    }

    public static Map<String, String> getDdlxMap() {
        return ddlxMap;
    }

    public static void setDdlxMap(Map<String, String> ddlxMap) {
        StaticUtils.ddlxMap = ddlxMap;
    }

    public static Map<String, String> getExpenceMap() {
        return expenceMap;
    }

    public static void setExpenceMap(Map<String, String> expenceMap) {
        StaticUtils.expenceMap = expenceMap;
    }

    public static Map<String, String> getExpenceStatMap() {
        return expenceStatMap;
    }

    public static void setExpenceStatMap(Map<String, String> expenceStatMap) {
        StaticUtils.expenceStatMap = expenceStatMap;
    }

    public static Map<String, String> getCooptypeMap() {
        return cooptypeMap;
    }

    public static void setCooptypeMap(Map<String, String> cooptypeMap) {
        StaticUtils.cooptypeMap = cooptypeMap;
    }

    public static Map<String, String> getOptionMap() {
        return optionMap;
    }

    public static void setOptionMap(Map<String, String> optionMap) {
        StaticUtils.optionMap = optionMap;
    }
}
