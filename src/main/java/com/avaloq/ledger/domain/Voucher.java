package com.avaloq.ledger.domain;

public class Voucher {

    //  Position
    private String subLedgerCategory;
    private String accountCategory;
    private String financialInstrumentCategory;
    private String currencyIso;
    //  Amount
    private String amountCategory;
    private Double amount;
    private Double amountBaseCurrency;

    public Voucher() {
    }

    public String getSubLedgerCategory() {
        return subLedgerCategory;
    }

    public void setSubLedgerCategory(String subLedgerCategory) {
        this.subLedgerCategory = subLedgerCategory;
    }

    public String getAccountCategory() {
        return accountCategory;
    }

    public void setAccountCategory(String accountCategory) {
        this.accountCategory = accountCategory;
    }

    public String getFinancialInstrumentCategory() {
        return financialInstrumentCategory;
    }

    public void setFinancialInstrumentCategory(String financialInstrumentCategory) {
        this.financialInstrumentCategory = financialInstrumentCategory;
    }

    public String getCurrencyIso() {
        return currencyIso;
    }

    public void setCurrencyIso(String currencyIso) {
        this.currencyIso = currencyIso;
    }

    public String getAmountCategory() {
        return amountCategory;
    }

    public void setAmountCategory(String amountCategory) {
        this.amountCategory = amountCategory;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getAmountBaseCurrency() {
        return amountBaseCurrency;
    }

    public void setAmountBaseCurrency(Double amountBaseCurrency) {
        this.amountBaseCurrency = amountBaseCurrency;
    }

    @Override
    public String toString() {
        return "Voucher{" +
            "subLedgerCategory='" + subLedgerCategory + '\'' +
            ", accountCategory='" + accountCategory + '\'' +
            ", financialInstrumentCategory='" + financialInstrumentCategory + '\'' +
            ", currencyIso='" + currencyIso + '\'' +
            ", amountCategory='" + amountCategory + '\'' +
            ", amount=" + amount +
            ", amountBaseCurrency=" + amountBaseCurrency +
            '}';
    }
}
