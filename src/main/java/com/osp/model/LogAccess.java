package com.osp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Admin on 12/27/2017.
 */
@Entity
@Table(name = "SIMA_LOG_ACCESS")
public class LogAccess implements Serializable {

    private static final long serialVersionUID = 1451508189162183268L;

    @Id
    @SequenceGenerator(name = "SIMA_LOG_ACCESS_SEQ", sequenceName = "SIMA_LOG_ACCESS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIMA_LOG_ACCESS_SEQ")
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "CUSTOMER_ID", nullable = false)
    private Long customerId;

    @Column(name = "MSISDN", length = 22)
    private String msisdn;

    @Column(name = "IP", length = 22)
    private String ip;

    @Column(name = "ACTIONS", length = 100)
    private String actions;

    @Column(name = "GEN_DATE", nullable = false)
    private Date genDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }

    public Date getGenDate() {
        return genDate;
    }

    public void setGenDate(Date genDate) {
        this.genDate = genDate;
    }
}
