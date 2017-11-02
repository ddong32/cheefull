package com.chee.common;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class Page<T> {
    public static final String ASC = "asc";
    public static final String DESC = "desc";
    protected int pageNo = 1;
    protected int pageSize = 20;
    protected String orderBy = "";
    protected String order = "";
    protected boolean autoCount = true;
    protected long totalCount = 0L;
    protected List<T> result = new ArrayList();

    public Page() {
    }

    public Page(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
        if (pageNo < 1) {
            this.pageNo = 1;
        }
    }

    public Page<T> pageNo(int thePageNo) {
        setPageNo(thePageNo);
        return this;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Page<T> pageSize(int thePageSize) {
        setPageSize(thePageSize);
        return this;
    }

    public int getFirst() {
        return (this.pageNo - 1) * this.pageSize + 1;
    }

    public String getOrderBy() {
        return this.orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Page<T> orderBy(String theOrderBy) {
        setOrderBy(theOrderBy);
        return this;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        String lowcaseOrder = StringUtils.lowerCase(order);

        String[] orders = StringUtils.split(lowcaseOrder, ',');
        for (String orderStr : orders) {
            if ((!StringUtils.equals("desc", orderStr)) && (!StringUtils.equals("asc", orderStr))) {
                throw new IllegalArgumentException("排序方向" + orderStr + "不是合法值");
            }
        }
        this.order = lowcaseOrder;
    }

    public Page<T> order(String theOrder) {
        setOrder(theOrder);
        return this;
    }

    public boolean isOrderBySetted() {
        return (StringUtils.isNotBlank(this.orderBy)) && (StringUtils.isNotBlank(this.order));
    }

    public boolean isAutoCount() {
        return this.autoCount;
    }

    public void setAutoCount(boolean autoCount) {
        this.autoCount = autoCount;
    }

    public Page<T> autoCount(boolean theAutoCount) {
        setAutoCount(theAutoCount);
        return this;
    }

    public List<T> getResult() {
        return this.result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public long getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getTotalPages() {
        if (this.totalCount < 0L) {
            return 0L;
        }
        long count = this.totalCount / this.pageSize;
        if (this.totalCount % this.pageSize > 0L) {
            count += 1L;
        }
        return count;
    }

    public boolean isHasNext() {
        return this.pageNo + 1 <= getTotalPages();
    }

    public int getNextPage() {
        if (isHasNext()) {
            return this.pageNo + 1;
        }
        return this.pageNo;
    }

    public boolean isHasPre() {
        return this.pageNo - 1 >= 1;
    }

    public int getPrePage() {
        if (isHasPre()) {
            return this.pageNo - 1;
        }
        return this.pageNo;
    }

    protected String sumZcje = "";
    protected String sumSrje = "";

    public String getSumZcje() {
        return this.sumZcje;
    }

    public void setSumZcje(String sumZcje) {
        this.sumZcje = sumZcje;
    }

    public String getSumSrje() {
        return this.sumSrje;
    }

    public void setSumSrje(String sumSrje) {
        this.sumSrje = sumSrje;
    }
}
