package com.chee.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

public class Config {
    private Logger logger = Logger.getLogger(getClass());
    private static Config instance;
    private String systemName;
    private String systemCity;
    private String systemVersion;
    private String systemTaskUpload;
    private String systemServiceQuery;
    private String systemInvalidReason;
    private String webVersion;
    private String dogLicenseType;
    private String dogExpiretimeRemind;
    private String dbName;
    private String dbIp;
    private Integer dbPort;
    private String dbUserName;
    private String dbUserPwd;
    private Integer dbConnMode;
    private String dbIp2;
    private Integer dbPort2;
    private String dbSid;
    private String smsSyncSjhmPlayer;
    private String smsPlayer;
    private String manualCode;

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public String[] ResolveDBUrl(String dbUrl) {
        String[] db = (String[]) null;
        try {
            if (dbUrl != null) {
                String[] dul = dbUrl.split(":");
                int i = dul.length;
                if (i == 6) {
                    db = new String[3];
                    db[0] = dul[3].substring(dul[3].indexOf("@") + 1);
                    db[1] = dul[4];
                    db[2] = dul[5];
                }
            }
        } catch (Exception e) {
            this.logger.error("It has error when resolve URL");
        }
        return db;
    }

    public Config() {
        initProperties();
    }

    private void initProperties() {
        InputStream is = null;
        try {
            Properties prop = new Properties();
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties");
            prop.load(is);
            this.systemVersion = prop.getProperty("system.version");
            this.systemCity = prop.getProperty("system.city");
            this.systemCity = new String(this.systemCity.getBytes("ISO-8859-1"), "utf-8");
            this.systemName = prop.getProperty("system.name");
            this.systemName = new String(this.systemName.getBytes("ISO-8859-1"), "utf-8");
        } catch (IOException e1) {
            this.logger.error("It has error when init properties");
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e2) {
                    this.logger.error("It has error when close InputStream");
                }
            }
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e3) {
                    this.logger.error("It has error when close InputStream");
                }
            }
        }
    }

    public String getSystemVersion() {
        return this.systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public String getSystemName() {
        return this.systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemCity() {
        return this.systemCity;
    }

    public void setSystemCity(String systemCity) {
        this.systemCity = systemCity;
    }

    public String getSystemTaskUpload() {
        return this.systemTaskUpload;
    }

    public void setSystemTaskUpload(String systemTaskUpload) {
        this.systemTaskUpload = systemTaskUpload;
    }

    public String getSystemServiceQuery() {
        return this.systemServiceQuery;
    }

    public void setSystemServiceQuery(String systemServiceQuery) {
        this.systemServiceQuery = systemServiceQuery;
    }

    public String getSystemInvalidReason() {
        return this.systemInvalidReason;
    }

    public void setSystemInvalidReason(String systemInvalidReason) {
        this.systemInvalidReason = systemInvalidReason;
    }

    public String getDogLicenseType() {
        return this.dogLicenseType;
    }

    public void setDogLicenseType(String dogLicenseType) {
        this.dogLicenseType = dogLicenseType;
    }

    public String getDogExpiretimeRemind() {
        return this.dogExpiretimeRemind;
    }

    public void setDogExpiretimeRemind(String dogExpiretimeRemind) {
        this.dogExpiretimeRemind = dogExpiretimeRemind;
    }

    public String getWebVersion() {
        return this.webVersion;
    }

    public void setWebVersion(String webVersion) {
        this.webVersion = webVersion;
    }

    public String getDbName() {
        return this.dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbIp() {
        return this.dbIp;
    }

    public void setDbIp(String dbIp) {
        this.dbIp = dbIp;
    }

    public Integer getDbPort() {
        return this.dbPort;
    }

    public void setDbPort(Integer dbPort) {
        this.dbPort = dbPort;
    }

    public String getDbUserName() {
        return this.dbUserName;
    }

    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }

    public String getDbUserPwd() {
        return this.dbUserPwd;
    }

    public void setDbUserPwd(String dbUserPwd) {
        this.dbUserPwd = dbUserPwd;
    }

    public Integer getDbConnMode() {
        return this.dbConnMode;
    }

    public void setDbConnMode(Integer dbConnMode) {
        this.dbConnMode = dbConnMode;
    }

    public String getDbIp2() {
        return this.dbIp2;
    }

    public void setDbIp2(String dbIp2) {
        this.dbIp2 = dbIp2;
    }

    public Integer getDbPort2() {
        return this.dbPort2;
    }

    public void setDbPort2(Integer dbPort2) {
        this.dbPort2 = dbPort2;
    }

    public String getDbSid() {
        return this.dbSid;
    }

    public void setDbSid(String dbSid) {
        this.dbSid = dbSid;
    }

    public String getSmsPlayer() {
        return this.smsPlayer;
    }

    public void setSmsPlayer(String smsPlayer) {
        this.smsPlayer = smsPlayer;
    }

    public String getSmsSyncSjhmPlayer() {
        return this.smsSyncSjhmPlayer;
    }

    public void setSmsSyncSjhmPlayer(String smsSyncSjhmPlayer) {
        this.smsSyncSjhmPlayer = smsSyncSjhmPlayer;
    }

    public String getManualCode() {
        return this.manualCode;
    }

    public void setManualCode(String manualCode) {
        this.manualCode = manualCode;
    }
}
