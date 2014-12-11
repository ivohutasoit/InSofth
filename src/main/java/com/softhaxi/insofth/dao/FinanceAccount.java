package com.softhaxi.insofth.dao;

/**
 *
 * @author Hutasoit
 */
public class FinanceAccount implements java.io.Serializable {

    private String id;
    private float balance;
    private String currency;
    private FinanceAccountGroup group;
    private float lastBalance;
    private long lastUpdated;
    private int main;
    private String name;
    private String remark;
    private int status;

    public FinanceAccount() {
    }

    public FinanceAccount(String id, float balance, String currency, FinanceAccountGroup group, long lastUpdated, int main, String name, int status) {
        this.id = id;
        this.balance = balance;
        this.currency = currency;
        this.group = group;
        this.lastUpdated = lastUpdated;
        this.main = main;
        this.name = name;
        this.status = status;
    }

    public FinanceAccount(String id, float balance, String currency, FinanceAccountGroup group, Float lastBalance, long lastUpdated, int main, String name, String remark, int status) {
        this.id = id;
        this.balance = balance;
        this.currency = currency;
        this.group = group;
        this.lastBalance = lastBalance;
        this.lastUpdated = lastUpdated;
        this.main = main;
        this.name = name;
        this.remark = remark;
        this.status = status;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getBalance() {
        return this.balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public FinanceAccountGroup getGroup() {
        return this.group;
    }

    public void setGroup(FinanceAccountGroup group) {
        this.group = group;
    }

    public float getLastBalance() {
        return this.lastBalance;
    }

    public void setLastBalance(float lastBalance) {
        this.lastBalance = lastBalance;
    }

    public long getLastUpdated() {
        return this.lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public int getMain() {
        return this.main;
    }

    public void setMain(int main) {
        this.main = main;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return name + " [" + currency + "]";
    }
}
