package com.osp.model;

import java.text.ParseException;
import java.util.Date;

import com.osp.common.Utils;

public class DwMsisdn implements java.io.Serializable {

    public static final Short STATUS_ENABLE = 1;
    public static final Short STATUS_DISABLE = 0;
    public static final Short STATUS_AUCTION = 2;
    public static final Short STATUS_PENDING = 3;
    public static final Short STATUS_SOLVED = 4;
    public static final Short STATUS_CUS_LOCK = 6;

    public static final String TYPE_TRATRUOC = "0";
    public static final String TYPE_TRASAU = "1";
    //info in dw_msisdn_1
    private String msisdn;
    private Long groupId;
    private String type;
    private Short status;
    private Date startDate;
    private Date end_date;
    private Long sid;
    private String msisdn_reverse;
    private Short dwType;
    private Date eotTime;

    // Info search
    private String str_startDate;
    private String groupName;
    private Long minPrice;
    private Long feeRequire;

    //info in dw_msisdn_2
    private int isLock;
    private String description;
    private int bkFeeId;

    public DwMsisdn() {
    }

    //Constructer search
    public DwMsisdn(String msisdn, Long groupId, String type,
            Short status, String group_name, Long min_price, Long fee_require) {
        this.msisdn = msisdn;
        this.groupId = groupId;
        this.type = type;
        this.status = status;
        this.groupName = group_name;
        this.minPrice = min_price;
        this.feeRequire = fee_require;
    }

    public DwMsisdn(String msisdn, Long groupId, String type, Short status, Long sid, Short dwType, Date eotTime, String groupName, Long minPrice, Long feeRequire, int isLock, int bkFeeId, Date startDate) {
        this.msisdn = msisdn;
        this.groupId = groupId;
        this.type = type;
        this.status = status;
        this.sid = sid;
        this.dwType = dwType;
        this.eotTime = eotTime;
        this.groupName = groupName;
        this.minPrice = minPrice;
        this.feeRequire = feeRequire;
        this.isLock = isLock;
        this.bkFeeId = bkFeeId;
        this.startDate = startDate;
    }

    public DwMsisdn(String msisdn, Short status) {
        this.msisdn = msisdn;
        this.status = status;
    }

    public String getMsisdn() {
        return this.msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public Long getGroupId() {
        return this.groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Short getStatus() {
        return this.status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
        this.str_startDate = Utils.date2str(startDate, "dd/MM/yyyy");
    }

    public String getStr_startDate() {
        return str_startDate;
    }

    public void setStr_startDate(String str_startDate) throws ParseException {
        this.str_startDate = str_startDate;
        this.startDate = Utils.str2date(str_startDate, "dd/MM/yyyy");
    }

    public static String getStatusName(Short status) {
        String result = "";
        if (status != null) {
            if (status == STATUS_ENABLE) {
                result = "Trong kho";
            }
            if (status == STATUS_DISABLE) {
                result = "Khóa";
            }
            if (status == STATUS_AUCTION) {
                result = "Đang đấu giá";
            }
            if (status == STATUS_PENDING) {
                result = "Đang giữ số";
            }
            if (status == STATUS_SOLVED) {
                result = "Đã đấu nối";
            }
        }
        return result;
    }

    public static String getTypeName(String type) {
        String result = "";
        if (type.equals(TYPE_TRATRUOC)) {
            result = "Trả trước";
        }
        if (type.equals(TYPE_TRASAU)) {
            result = "Trả sau";
        }
        return result;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public String getMsisdn_reverse() {
        return msisdn_reverse;
    }

    public void setMsisdn_reverse(String msisdn_reverse) {
        this.msisdn_reverse = msisdn_reverse;
    }

    public Short getDwType() {
        return dwType;
    }

    public void setDwType(Short dwType) {
        this.dwType = dwType;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Long minPrice) {
        this.minPrice = minPrice;
    }

    public Long getFeeRequire() {
        return feeRequire;
    }

    public void setFeeRequire(Long feeRequire) {
        this.feeRequire = feeRequire;
    }

    public int getIsLock() {
        return isLock;
    }

    public void setIsLock(int isLock) {
        this.isLock = isLock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBkFeeId() {
        return bkFeeId;
    }

    public void setBkFeeId(int bkFeeId) {
        this.bkFeeId = bkFeeId;
    }

    public Date getEotTime() {
        return eotTime;
    }

    public void setEotTime(Date eotTime) {
        this.eotTime = eotTime;
    }

}
