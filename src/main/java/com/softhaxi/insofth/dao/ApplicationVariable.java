package com.softhaxi.insofth.dao;

import java.io.Serializable;

/**
 *
 * @author Hutasoit
 */
public class ApplicationVariable implements Serializable {
    private String id;
    private String type;
    private String pid;
    private String name;
    private String value;
    private String remark;

    public ApplicationVariable() {
    }

    public ApplicationVariable(String id, String type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }

    public ApplicationVariable(String id, String type, String pid, String name, String value, String remark) {
        this.id = id;
        this.type = type;
        this.pid = pid;
        this.name = name;
        this.value = value;
        this.remark = remark;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the pid
     */
    public String getPid() {
        return pid;
    }

    /**
     * @param pid the pid to set
     */
    public void setPid(String pid) {
        this.pid = pid;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
