package com.chee.action.admin;

import com.chee.entity.City;
import com.chee.entity.Customer;
import com.chee.entity.District;
import com.chee.service.CityService;
import com.chee.service.CustomerService;
import com.chee.service.DistrictService;
import com.chee.util.HibernateUtils;
import com.chee.util.JsonDateValueProcessor;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.Validations;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;

@ParentPackage("admin")
@Results({ @org.apache.struts2.convention.annotation.Result(name = "index", location = "customer!list.action", type = "redirect") })
public class CustomerAction extends BaseAction<Customer, Integer> {
    private static final long serialVersionUID = 4984599292597512018L;
    @Resource
    private CustomerService customerService;
    @Resource
    private CityService cityService;
    @Resource
    private DistrictService districtService;
    private Customer customer;
    private Customer parent;
    private String customerIds;
    private String province;
    private String city;
    private String district;
    private String cityID;
    private String customerID;
    private List<Customer> customerlist;
    private List<City> cityList;
    private List<District> districtList;

    public String list() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Customer.class);
        if (this.customer != null) {
            boolean b = false;
            if ((this.customer.getAreaName() != null) && (!"".equals(this.customer.getAreaName()))) {
                b = true;
                detachedCriteria.add(Restrictions.sqlRestriction("area_name like ? escape '/'", HibernateUtils.escapeSQLLike(this.customer.getAreaName()), Hibernate.STRING));
            }
            if (!b) {
                detachedCriteria.add(Restrictions.isNull("parent"));
            }
        } else {
            detachedCriteria.add(Restrictions.isNull("parent"));
        }
        this.page.setPageSize(50);
        this.page.setOrderBy("sort");
        this.page.setOrder("asc");
        this.page = this.customerService.findPage(this.page, detachedCriteria);

        return "list";
    }

    public String ajaxChildren() {
        List<Customer> childrenList = new ArrayList<Customer>();
        if ((this.customer != null) && (this.customer.getId() != null)) {
            childrenList = this.customerService.find("from Customer where parent = ? order by areaName asc", new Object[] { this.customer });
        } else {
            childrenList = this.customerService.find("from Customer where parent is null order by stat desc,lrsj asc", new Object[0]);
        }
        List<Map<String, String>> optionList = new ArrayList<Map<String, String>>();
        for (Customer address : childrenList) {
            Map<String, String> map = new HashMap<String, String>();
            String typeName = "";
            if (address.getType().intValue() == 0) {
                typeName = "城市";
            } else if (address.getType().intValue() == 1) {
                typeName = "客户";
            } else if (address.getType().intValue() == 2) {
                typeName = "联系人";
            }
            map.put("id", address.getId().toString());
            map.put("type", String.valueOf(address.getType()));
            map.put("typeName", typeName);
            map.put("areaName", address.getAreaName() == null ? "" : address.getAreaName());
            map.put("dwdz", address.getDwdz() == null ? "" : address.getDwdz());
            map.put("lxdh", address.getLxdh() == null ? "" : address.getLxdh());
            map.put("qq", address.getQq() == null ? "" : address.getQq());
            map.put("wxh", address.getWxh() == null ? "" : address.getWxh());
            map.put("gsdh", address.getGsdh() == null ? "" : address.getGsdh());
            map.put("stat", String.valueOf(address.getStat()));
            map.put("statName", address.getStat().intValue() == 1 ? "启用" : "<font color=\"red\">禁用<font>");
            map.put("grade", address.getGrade() + "");
            map.put("sort", address.getSort() + "");
            map.put("parentId", address.getParent() == null ? "" : address.getParent().getId().toString());
            map.put("parentAreaName", address.getParent() == null ? "" : address.getParent().getAreaName());
            map.put("pathName", address.getPathName() == null ? "" : address.getPathName());
            map.put("path", address.getPath() == null ? "" : address.getPath());
            map.put("hasChildren", address.getChildren().size() == 0 ? "0" : "1");
            map.put("lrr", address.getLrr() == null ? "" : address.getLrr());
            optionList.add(map);
        }
        JSONArray jsonArray = JSONArray.fromObject(optionList);
        return ajaxJson(jsonArray.toString());
    }

    /**
     * 描述：组团社添加左边树形结构
     * 
     * @Author qin_dongliang
     * @Date 2017-12-09 14:04:38
     */
    public String ajaxCustomerZTree() {
        Integer grade = Integer.valueOf(9);
        if ((getRequest().getParameter("grade") != null) && (!"".equals(getRequest().getParameter("grade")))) {
            grade = Integer.valueOf(Integer.parseInt(getRequest().getParameter("grade")));
        }
        List<String> pathList = new ArrayList<String>();

        List<Customer> cacheCustomerList = this.customerService.getCustomerList(grade);
        if ((cacheCustomerList != null) && (cacheCustomerList.size() > 0)) {
            for (Customer cacheCustomer : cacheCustomerList) {
                pathList.add(cacheCustomer.getPath());
            }
        }

        List addressMapList = new ArrayList();
        HashMap allMap = new HashMap();
        allMap.put("id", Integer.valueOf(0));
        allMap.put("pId", Integer.valueOf(-1));
        allMap.put("name", "全部");
        allMap.put("type", "0");
        allMap.put("checked", Boolean.valueOf(false));
        allMap.put("open", Boolean.valueOf(true));
        allMap.put("pathName", "");
        allMap.put("visible", Boolean.valueOf(true));
        addressMapList.add(allMap);

        if ((pathList != null) && (pathList.size() > 0)) {
            Set<Integer> pathIdSet = new HashSet<Integer>();
            for (String path : pathList) {
                if ((path != null) && (!"".equals(path))) {
                    for (String id : path.split(",")) {
                        pathIdSet.add(Integer.valueOf(Integer.parseInt(id)));
                    }
                }
            }
            List<Customer> addressList = this.customerService.getPathCustomerList(pathIdSet);
            if ((addressList != null) && (addressList.size() > 0)) {
                for (Customer customer : addressList) {
                    HashMap addressMap = new HashMap();
                    addressMap.put("id", customer.getId());
                    addressMap.put("pId", Integer.valueOf(customer.getParent() == null ? 0 : customer.getParent().getId().intValue()));
                    addressMap.put("name", customer.getAreaName());
                    addressMap.put("type", customer.getType());
                    addressMap.put("checked", Boolean.valueOf(false));
                    addressMap.put("pathName", customer.getPathName() == null ? "" : customer.getPathName());
                    addressMap.put("visible", Boolean.valueOf(true));
                    addressMap.put("parentNode", null);
                    addressMapList.add(addressMap);
                }
            }
        }
        JSONArray jsonArray = JSONArray.fromObject(addressMapList);
        return ajaxJson(jsonArray.toString());
    }

    public void ajaxDialog() {
        if (this.customer == null) {
            this.customer = new Customer();
        }
        if ((this.province != null) && (!"".equals(this.province))) {
            this.customer.setSzdsf(this.province);
        } else {
            this.province = "20";
            this.customer.setSzdsf(this.province);
        }
        if ((this.city != null) && (!"".equals(this.city))) {
            this.customer.setSzdcs(this.city);
        }
        if ((this.district != null) && (!"".equals(this.district))) {
            this.customer.setSzdxf(this.district);
        }
        try {
            this.customer.setAreaName(URLDecoder.decode(this.customer.getAreaName(), "utf-8"));
            this.customer.setLxr(URLDecoder.decode(this.customer.getLxr(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JsonConfig config = new JsonConfig();
        config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONArray jsonArray = JSONArray.fromObject(this.customerlist, config);
        ajaxJson(jsonArray.toString());
    }

    public String input() {
        if ((this.parent != null) && (this.parent.getId() != null)) {
            this.parent = ((Customer) this.customerService.get(this.parent.getId()));
        } else {
            this.parent = new Customer();
        }
        if ((this.customer != null) && (this.customer.getId() != null)) {
            this.customer = ((Customer) this.customerService.get(this.customer.getId()));
        } else {
            this.customer = new Customer();
            this.customer.setType(Integer.valueOf(2));
        }
        return "input";
    }

    @InputConfig(resultName = "error")
    public void add() throws Exception {
        try {
            if (this.customer.getId() == null) {
                if ((this.province != null) && (!"".equals(this.province))) {
                    this.customer.setSzdsf(this.province);
                } else {
                    this.province = "20";
                    this.customer.setSzdsf(this.province);
                }
                if ((this.city != null) && (!"".equals(this.city))) {
                    this.customer.setSzdcs(this.city);
                }
                if ((this.district != null) && (!"".equals(this.district))) {
                    this.customer.setSzdxf(this.district);
                }
                this.customer.setLrsj(new Date());
                this.customer.setLrr(getLoginUser().getName());
                this.customerID = ((Integer) this.customerService.save(this.customer)).toString();
                ajaxJsonSuccessMessage(this.customerID);
            } else {
                ajaxJsonWarnMessage("");
            }
        } catch (Exception e) {
            ajaxJsonErrorMessage(e.toString());
        }
    }

    @Validations(requiredStrings = { @com.opensymphony.xwork2.validator.annotations.RequiredStringValidator(fieldName = "customer.areaName", message = "目录名称不允许为空!") })
    @InputConfig(resultName = "error")
    public String saveDirectory() {
        Customer persistent = null;
        if (this.customer.getId() != null) {
            persistent = (Customer) this.customerService.get(this.customer.getId());
            BeanUtils.copyProperties(this.customer, persistent, new String[] { "id", "lrsj", "lrr", "gxsj", "path", "parent", "children", "type", "stat" });
            if (this.parent.getId() != null) {
                persistent.setParent((Customer) this.customerService.get(this.parent.getId()));
            } else {
                persistent.setParent(null);
            }
            this.customerService.update(persistent);
        } else {
            if (this.parent.getId() != null) {
                this.customer.setParent((Customer) this.customerService.get(this.parent.getId()));
            } else {
                this.customer.setParent(null);
            }
            this.customer.setLrsj(new Date());
            this.customer.setLrr(getLoginUser().getName());
            this.customer.setGxsj(new Date());

            persistent = this.customer;
            persistent.setStat(Integer.valueOf(1));
            this.customerService.save(persistent);
        }
        this.redirectionUrl = "customer!list.action";
        return "success";
    }

    @Validations(stringLengthFields = {
            @com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator(fieldName = "customer.areaName", minLength = "1", maxLength = "512", message = "单位名称长度必须在${minLength}到${maxLength}之间!"),
            @com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator(fieldName = "customer.dwdz", minLength = "1", maxLength = "512", message = "单位地址长度必须在${minLength}到${maxLength}之间!"),
            @com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator(fieldName = "customer.lxdh", maxLength = "20", message = "联系电话长度不允许大于${maxLength}!") })
    @InputConfig(resultName = "error")
    public String save() {
        Customer persistent = null;
        if ((this.customer != null) && (this.customer.getId() != null)) {
            try {
                persistent = (Customer) this.customerService.get(this.customer.getId());
                if (!this.customerService.isUnique("areaName", persistent.getAreaName(), this.customer.getAreaName())) {
                    String text = "";
                    if ("1".equals(this.customer.getType())) {
                        text = "单位名称: ";
                    } else {
                        text = "联系人: ";
                    }
                    addActionError(text + this.customer.getAreaName() + " 已经存在!请重新命名!");
                }
                persistent = (Customer) this.customerService.get(this.customer.getId());

                BeanUtils.copyProperties(this.customer, persistent, new String[] { "id", "lrsj", "lrr", "gxsj", "path", "parent", "children", "type", "stat" });
                if (this.parent.getId() != null) {
                    persistent.setParent((Customer) this.customerService.get(this.parent.getId()));
                } else {
                    persistent.setParent(null);
                }
                persistent.setGxsj(new Date());

                this.customerService.update(persistent);
            } catch (Exception e) {
                addActionError(e.toString());
            }
        } else {
            if (!this.customerService.isUnique("areaName", null, this.customer.getAreaName())) {
                String text = "";
                if ("1".equals(this.customer.getType())) {
                    text = "单位名称: ";
                } else {
                    text = "联系人: ";
                }
                addActionError(text + this.customer.getAreaName() + " 已经存在!请重新命名!");
                return "error";
            }
            if (this.parent.getId() != null) {
                this.customer.setParent((Customer) this.customerService.get(this.parent.getId()));
            } else {
                this.customer.setParent(null);
            }
            persistent = this.customer;
            persistent.setLrr(getLoginUser().getName());
            persistent.setLrsj(new Date());
            persistent.setGxsj(new Date());
            persistent.setStat(Integer.valueOf(1));
            this.customerService.save(persistent);
        }
        this.redirectionUrl = "violation_address!list.action";
        return "success";
    }

    @Validations(requiredStrings = { @com.opensymphony.xwork2.validator.annotations.RequiredStringValidator(fieldName = "customer.areaName", message = "目录名称不允许为空!") })
    @InputConfig(resultName = "error")
    public String saveCity() {
        Customer persistent = null;
        if (this.customer.getId() != null) {
            persistent = (Customer) this.customerService.get(this.customer.getId());
            BeanUtils.copyProperties(this.customer, persistent, new String[] { "id", "lrsj", "lrr", "gxsj", "path", "parent", "children", "type", "stat" });
            if (this.parent.getId() != null) {
                persistent.setParent((Customer) this.customerService.get(this.parent.getId()));
            } else {
                persistent.setParent(null);
            }
            this.customerService.update(persistent);
        } else {
            if (this.parent.getId() != null) {
                this.customer.setParent((Customer) this.customerService.get(this.parent.getId()));
            } else {
                this.customer.setParent(null);
            }
            this.customer.setLrr(getLoginUser().getName());
            this.customer.setLrsj(new Date());
            this.customer.setGxsj(new Date());

            persistent = this.customer;
            persistent.setStat(Integer.valueOf(1));
            this.customerService.save(persistent);
        }
        this.redirectionUrl = "customer!list.action";
        return "success";
    }

    public String inputb() {
        if ((this.customer != null) && (this.customer.getId() != null)) {
            this.customer = ((Customer) this.customerService.load(this.customer.getId()));
        } else {
            this.customer = new Customer();
        }
        return "inputb";
    }

    public String delete() {
        this.customerService.delete(this.customer.getId());
        this.redirectionUrl = "customer!list.action";
        return "success";
    }

    public List<Customer> getCustomerlist() {
        return this.customerlist;
    }

    public void setCustomerlist(List<Customer> customerlist) {
        this.customerlist = customerlist;
    }

    public List<City> getCityList() {
        if (this.cityList == null) {
            this.cityList = this.cityService.getCity(Integer.valueOf(20));
        }
        return this.cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }

    public void loadDistrict() {
        if (this.cityID != null) {
            this.districtList = this.districtService.getDistrict(Integer.valueOf(Integer.parseInt(this.cityID)));
            JsonConfig config = new JsonConfig();
            config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
            JSONArray jsonArray = JSONArray.fromObject(this.districtList, config);
            ajaxJson(jsonArray.toString());
        }
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getParent() {
        return this.parent;
    }

    public void setParent(Customer parent) {
        this.parent = parent;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return this.district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCityID() {
        return this.cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getCustomerID() {
        return this.customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerIds() {
        return this.customerIds;
    }

    public void setCustomerIds(String customerIds) {
        this.customerIds = customerIds;
    }
}
