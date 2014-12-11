package com.softhaxi.insofth.enums;

/**
 *
 * @author Hutasoit
 */
public enum FinanceActivity {
    INCOME, EXPENSE, TRANSFER, BUDGET, RECURRING;
    
    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        switch(this){
            case INCOME: return "FC0001";
            case EXPENSE: return "FC0002";
            case TRANSFER: return "FC0003";
            case BUDGET: return "FC0004";
            case RECURRING: return "FC0005";
            default: return "FC0000";
        }
    }
}
