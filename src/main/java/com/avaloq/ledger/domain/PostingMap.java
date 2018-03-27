package com.avaloq.ledger.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PostingMap {

    private Double postingAmount;
    private String postingCurrencyIso;
    private String debitLedgerAccount;
    private String creditLedgerAccount;
    private Long status;

    public PostingMap() {
    }

    public Double getPostingAmount() {
        return postingAmount;
    }

    public void setPostingAmount(Double postingAmount) {
        this.postingAmount = postingAmount;
    }

    public String getPostingCurrencyIso() {
        return postingCurrencyIso;
    }

    public void setPostingCurrencyIso(String postingCurrencyIso) {
        this.postingCurrencyIso = postingCurrencyIso;
    }

    public String getDebitLedgerAccount() {
        return debitLedgerAccount;
    }

    public void setDebitLedgerAccount(String debitLedgerAccount) {
        this.debitLedgerAccount = debitLedgerAccount;
    }

    public String getCreditLedgerAccount() {
        return creditLedgerAccount;
    }

    public void setCreditLedgerAccount(String creditLedgerAccount) {
        this.creditLedgerAccount = creditLedgerAccount;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PostingMap{" +
            "postingAmount=" + postingAmount +
            ", postingCurrencyIso='" + postingCurrencyIso + '\'' +
            ", debitLedgerAccount='" + debitLedgerAccount + '\'' +
            ", creditLedgerAccount='" + creditLedgerAccount + '\'' +
            ", status=" + status +
            '}';
    }
}
