package com.osp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Admin on 1/10/2018.
 */
@Entity
@Table(name = "SIMA_FORWARD_LOG")
public class ForwardLog implements Serializable {

    private static final long serialVersionUID = -6607832461929594565L;
    @Id
    @SequenceGenerator(name = "SIMA_FORWARD_LOG_SEQ", sequenceName = "SIMA_FORWARD_LOG_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIMA_FORWARD_LOG_SEQ")
    @Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_SENDER")
    private Customer customerSender;

    @Column(name = "MSISDN", nullable = false)
    private String msisdn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_RECEIVER")
    private Customer customerReceiver;

    @Column(name = "GEN_DATE")
    private Date genDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomerSender() {
        return customerSender;
    }

    public void setCustomerSender(Customer customerSender) {
        this.customerSender = customerSender;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public Customer getCustomerReceiver() {
        return customerReceiver;
    }

    public void setCustomerReceiver(Customer customerReceiver) {
        this.customerReceiver = customerReceiver;
    }

    public Date getGenDate() {
        return genDate;
    }

    public void setGenDate(Date genDate) {
        this.genDate = genDate;
    }

}
