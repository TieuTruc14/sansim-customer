package com.osp.common;

public class Condition {

    private String operator;
    private String key;
    private String value;
    private String subSyntax;

    public Condition() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Condition(String operator, String key, String value, String subSyntax) {
        super();
        this.operator = operator;
        this.key = key;
        this.value = value;
        this.subSyntax = subSyntax;
    }

    public String getOperator() {
        return operator;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSubSyntax() {
        return subSyntax;
    }

    public void setSubSyntax(String subSyntax) {
        this.subSyntax = subSyntax;
    }

}
