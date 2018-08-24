package com.osp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Admin on 12/20/2017.
 */
@Entity
@Table(name = "SIMA_STOCK_MSISDN")
public class StockMsisdn implements Serializable {

    private static final long serialVersionUID = 2788855918979408924L;

    @Id
    @SequenceGenerator(name = "SIMA_STOCK_MSISDN_SEQ", sequenceName = "SIMA_STOCK_MSISDN_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIMA_STOCK_MSISDN_SEQ")
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "MSISDN", nullable = false)
    private String msisdn;

    @Column(name = "PRICE", nullable = false)
    private Long price;

//    @Column(name = "CUSTOMER_ID",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @Column(name = "CONFIRM_STATUS", nullable = false, length = 1)
    private Short confirmStatus;

    @Column(name = "APPROVE", nullable = false, length = 1)
    private Short approve;

    @Column(name = "STATUS", nullable = false, length = 1)
    private Short status;

    @Column(name = "GROUP_ID", nullable = false, columnDefinition = "int default 0")
    private Long groupId;

    @Column(name = "DESCRIPTION", length = 4000)
    private String description;

    @Column(name = "GEN_DATE")
    private Date genDate;

    @Column(name = "LAST_UPDATED")
    private Date lastUpdate;

    @Column(name = "CONFIRM_EXPIRED")
    private Date confirmExpired;

    @Column(name = "SUFFIX", length = 7)
    private String suffix;

    @Column(name = "TELCO")
    private Byte telco;

    @Column(name = "MSISDN_ALIAS", nullable = false)
    private String msisdnAlias;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Short getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(Short confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public Short getApprove() {
        return approve;
    }

    public void setApprove(Short approve) {
        this.approve = approve;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getGenDate() {
        return genDate;
    }

    public void setGenDate(Date genDate) {
        this.genDate = genDate;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Date getConfirmExpired() {
        return confirmExpired;
    }

    public void setConfirmExpired(Date confirmExpired) {
        this.confirmExpired = confirmExpired;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Byte getTelco() {
        return telco;
    }

    public void setTelco(Byte telco) {
        this.telco = telco;
    }

    public String getMsisdnAlias() {
        return msisdnAlias;
    }

    public void setMsisdnAlias(String msisdnAlias) {
        this.msisdnAlias = msisdnAlias;
    } 

}
