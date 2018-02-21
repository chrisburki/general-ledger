package com.avaloq.ledger.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the BalanceSheet entity.
 */
public class BalanceSheetDTO implements Serializable {

    private Long id;

    private String description;

    @NotNull
    private LocalDate balanceDate;

    @NotNull
    private Double amount;

    @NotNull
    private Double deltaAmountDebit;

    @NotNull
    private Double deltaAmountCredit;

    private String currencyIso;

    private Double amountCurrency;

    private Double deltaAmountDebitCurrency;

    private Double deltaAmountCreditCurrency;

    private Boolean isFinal;

    @NotNull
    private String legalEntityId;

    private Long chartOfAccountsId;

    private Long accountId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(LocalDate balanceDate) {
        this.balanceDate = balanceDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getDeltaAmountDebit() {
        return deltaAmountDebit;
    }

    public void setDeltaAmountDebit(Double deltaAmountDebit) {
        this.deltaAmountDebit = deltaAmountDebit;
    }

    public Double getDeltaAmountCredit() {
        return deltaAmountCredit;
    }

    public void setDeltaAmountCredit(Double deltaAmountCredit) {
        this.deltaAmountCredit = deltaAmountCredit;
    }

    public String getCurrencyIso() {
        return currencyIso;
    }

    public void setCurrencyIso(String currencyIso) {
        this.currencyIso = currencyIso;
    }

    public Double getAmountCurrency() {
        return amountCurrency;
    }

    public void setAmountCurrency(Double amountCurrency) {
        this.amountCurrency = amountCurrency;
    }

    public Double getDeltaAmountDebitCurrency() {
        return deltaAmountDebitCurrency;
    }

    public void setDeltaAmountDebitCurrency(Double deltaAmountDebitCurrency) {
        this.deltaAmountDebitCurrency = deltaAmountDebitCurrency;
    }

    public Double getDeltaAmountCreditCurrency() {
        return deltaAmountCreditCurrency;
    }

    public void setDeltaAmountCreditCurrency(Double deltaAmountCreditCurrency) {
        this.deltaAmountCreditCurrency = deltaAmountCreditCurrency;
    }

    public Boolean isIsFinal() {
        return isFinal;
    }

    public void setIsFinal(Boolean isFinal) {
        this.isFinal = isFinal;
    }

    public String getLegalEntityId() {
        return legalEntityId;
    }

    public void setLegalEntityId(String legalEntityId) {
        this.legalEntityId = legalEntityId;
    }

    public Long getChartOfAccountsId() {
        return chartOfAccountsId;
    }

    public void setChartOfAccountsId(Long chartOfAccountsId) {
        this.chartOfAccountsId = chartOfAccountsId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long ledgerAccountId) {
        this.accountId = ledgerAccountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BalanceSheetDTO balanceSheetDTO = (BalanceSheetDTO) o;
        if(balanceSheetDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), balanceSheetDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BalanceSheetDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", balanceDate='" + getBalanceDate() + "'" +
            ", amount=" + getAmount() +
            ", deltaAmountDebit=" + getDeltaAmountDebit() +
            ", deltaAmountCredit=" + getDeltaAmountCredit() +
            ", currencyIso='" + getCurrencyIso() + "'" +
            ", amountCurrency=" + getAmountCurrency() +
            ", deltaAmountDebitCurrency=" + getDeltaAmountDebitCurrency() +
            ", deltaAmountCreditCurrency=" + getDeltaAmountCreditCurrency() +
            ", isFinal='" + isIsFinal() + "'" +
            ", legalEntityId='" + getLegalEntityId() + "'" +
            "}";
    }
}
