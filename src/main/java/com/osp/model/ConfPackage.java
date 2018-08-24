package com.osp.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * SPaymentFee generated by hbm2java
 */
@Entity
@Table(name = "SIMA_CONF_PACKAGE")
public class ConfPackage implements java.io.Serializable {

    private Long id;
    private long period;
    private long maxQuantity;
    private Long fee;
    private String packageName;
    private String packageCode;
    private byte status;

    public ConfPackage() {
    }

    @Id
    @SequenceGenerator(name = "SIMA_CONF_PACKAGE_SEQ", sequenceName = "SIMA_CONF_PACKAGE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIMA_CONF_PACKAGE_SEQ")
    @Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "PERIOD", nullable = false, precision = 5, scale = 0)
    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    @Column(name = "MAX_QUANTITY", nullable = false, precision = 10, scale = 0)
    public long getMaxQuantity() {
        return this.maxQuantity;
    }

    public void setMaxQuantity(long maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    @Column(name = "FEE", nullable = false, precision = 22, scale = 0)
    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    @Column(name = "PACKAGE_NAME", length = 200)

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Column(name = "PACKAGE_CODE", length = 50)
    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    @Column(name = "STATUS", nullable = false, precision = 1, scale = 0)
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

}
