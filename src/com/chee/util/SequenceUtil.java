package com.chee.util;

import com.chee.service.BusinessService;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class SequenceUtil {
    private static long orderId = 1L;
    private static long lsh = 1L;
    private static long bankLsh = 1L;
    @Resource
    private BusinessService businessService;

    public synchronized String ProductOrderId() throws Exception {
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String sequ = new DecimalFormat("000").format(orderId++);
        return date + "-" + sequ;
    }

    public String ProductOrderId(Date cfsj, long count) throws Exception {
        String date = new SimpleDateFormat("yyyyMMdd").format(cfsj);
        String sequ = new DecimalFormat("000").format(count);
        return date + "-" + sequ;
    }

    public String ProductOrderId(Date cfsj) throws Exception {
        TreeMap<Integer, Integer> tm = new TreeMap<Integer, Integer>();
        List<String> orderIdlist = this.businessService.getcfsjorderid(cfsj);
        Integer count = Integer.valueOf(0);
        if (orderIdlist.size() > 0) {
            boolean k = false;
            Integer xh;
            for (Integer i = Integer.valueOf(0); orderIdlist.size() > i.intValue(); i = Integer.valueOf(i.intValue() + 1)) {
                xh = Integer.valueOf(Integer.parseInt(((String) orderIdlist.get(i.intValue())).split("-")[1]));
                Integer j = Integer.valueOf(i.intValue() + 1);
                if (xh == j) {
                    tm.put(j, xh);
                } else {
                    tm.put(j, Integer.valueOf(0));
                    k = true;
                }
            }
            if (k) {
                count = Integer.valueOf(0);
                for (Entry<Integer, Integer> m : tm.entrySet()) {
                    if (((Integer) m.getValue()).intValue() == 0) {
                        count = (Integer) m.getKey();
                        break;
                    }
                }
            } else {
                count = Integer.valueOf(orderIdlist.size() + 1);
            }
        } else {
            count = Integer.valueOf(count.intValue() + 1);
        }
        String date = new SimpleDateFormat("yyyyMMdd").format(cfsj);
        String sequ = new DecimalFormat("000").format(count);
        return date + "-" + sequ;
    }

    public synchronized String ProductLsh() throws Exception {
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String sequ = new DecimalFormat("000").format(lsh++);
        return "LS" + date + "-" + sequ;
    }

    public static synchronized String ProductBankTransferOrderId() throws Exception {
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String sequ = new DecimalFormat("00").format(bankLsh++);
        return date + "-9" + sequ;
    }

    public Class<String> getObjectType() {
        return String.class;
    }

    public boolean isSingleton() {
        return false;
    }

    public void resetSequence() {
        SequenceUtil.orderId = 1L;
        SequenceUtil.lsh = 1L;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        SequenceUtil.orderId = orderId;
    }

    public long getLsh() {
        return lsh;
    }

    public void setLsh(long lsh) {
        SequenceUtil.lsh = lsh;
    }

    public static void main(String[] args) {
        System.out.println("20150809-001".substring(2));
        String id = "20150809-001";
        System.out.println(id.split("-")[0]);
    }
}
