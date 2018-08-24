package com.osp.model.view;

/**
 * Created by Admin on 12/21/2017.
 */
public class SearchIndex {
    private String telco;
    private String dauso;
    private String so;
    private Boolean confirmStatus;
    private String birthday;
    private Long from;
    private Long to;
    private Byte sortPrice; 

    public String getTelco() {
        return telco;
    }

    public void setTelco(String telco) {
        this.telco = telco;
    }

    public String getDauso() {
        return dauso;
    }

    public void setDauso(String dauso) {
        this.dauso = dauso;
    }

    public String getSo() {
        return so;
    }

    public void setSo(String so) {
        this.so = so;
    }

    public Boolean getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(Boolean confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public Byte getSortPrice() {
        return sortPrice;
    }

    public void setSortPrice(Byte sortPrice) {
        this.sortPrice = sortPrice;
    }
}
