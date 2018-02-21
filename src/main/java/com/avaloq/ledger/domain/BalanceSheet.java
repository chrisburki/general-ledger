package com.avaloq.ledger.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A BalanceSheet.
 */
@Entity
@Table(name = "balance_sheet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BalanceSheet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "balance_date", nullable = false)
    private LocalDate balanceDate;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @NotNull
    @Column(name = "delta_amount_debit", nullable = false)
    private Double deltaAmountDebit;

    @NotNull
    @Column(name = "delta_amount_credit", nullable = false)
    private Double deltaAmountCredit;

    @Column(name = "currency_iso")
    private String currencyIso;

    @Column(name = "amount_currency")
    private Double amountCurrency;

    @Column(name = "delta_amount_debit_currency")
    private Double deltaAmountDebitCurrency;

    @Column(name = "delta_amount_credit_currency")
    private Double deltaAmountCreditCurrency;

    @Column(name = "is_final")
    private Boolean isFinal;

    @NotNull
    @Column(name = "legal_entity_id", nullable = false)
    private String legalEntityId;

    @ManyToOne
    private ChartOfAccounts chartOfAccounts;

    @ManyToOne
    private LedgerAccount account;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public BalanceSheet description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getBalanceDate() {
        return balanceDate;
    }

    public BalanceSheet balanceDate(LocalDate balanceDate) {
        this.balanceDate = balanceDate;
        return this;
    }

    public void setBalanceDate(LocalDate balanceDate) {
        this.balanceDate = balanceDate;
    }

    public Double getAmount() {
        return amount;
    }

    public BalanceSheet amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getDeltaAmountDebit() {
        return deltaAmountDebit;
    }

    public BalanceSheet deltaAmountDebit(Double deltaAmountDebit) {
        this.deltaAmountDebit = deltaAmountDebit;
        return this;
    }

    public void setDeltaAmountDebit(Double deltaAmountDebit) {
        this.deltaAmountDebit = deltaAmountDebit;
    }

    public Double getDeltaAmountCredit() {
        return deltaAmountCredit;
    }

    public BalanceSheet deltaAmountCredit(Double deltaAmountCredit) {
        this.deltaAmountCredit = deltaAmountCredit;
        return this;
    }

    public void setDeltaAmountCredit(Double deltaAmountCredit) {
        this.deltaAmountCredit = deltaAmountCredit;
    }

    public String getCurrencyIso() {
        return currencyIso;
    }

    public BalanceSheet currencyIso(String currencyIso) {
        this.currencyIso = currencyIso;
        return this;
    }

    public void setCurrencyIso(String currencyIso) {
        this.currencyIso = currencyIso;
    }

    public Double getAmountCurrency() {
        return amountCurrency;
    }

    public BalanceSheet amountCurrency(Double amountCurrency) {
        this.amountCurrency = amountCurrency;
        return this;
    }

    public void setAmountCurrency(Double amountCurrency) {
        this.amountCurrency = amountCurrency;
    }

    public Double getDeltaAmountDebitCurrency() {
        return deltaAmountDebitCurrency;
    }

    public BalanceSheet deltaAmountDebitCurrency(Double deltaAmountDebitCurrency) {
        this.deltaAmountDebitCurrency = deltaAmountDebitCurrency;
        return this;
    }

    public void setDeltaAmountDebitCurrency(Double deltaAmountDebitCurrency) {
        this.deltaAmountDebitCurrency = deltaAmountDebitCurrency;
    }

    public Double getDeltaAmountCreditCurrency() {
        return deltaAmountCreditCurrency;
    }

    public BalanceSheet deltaAmountCreditCurrency(Double deltaAmountCreditCurrency) {
        this.deltaAmountCreditCurrency = deltaAmountCreditCurrency;
        return this;
    }

    public void setDeltaAmountCreditCurrency(Double deltaAmountCreditCurrency) {
        this.deltaAmountCreditCurrency = deltaAmountCreditCurrency;
    }

    public Boolean isIsFinal() {
        return isFinal;
    }

    public BalanceSheet isFinal(Boolean isFinal) {
        this.isFinal = isFinal;
        return this;
    }

    public void setIsFinal(Boolean isFinal) {
        this.isFinal = isFinal;
    }

    public String getLegalEntityId() {
        return legalEntityId;
    }

    public BalanceSheet legalEntityId(String legalEntityId) {
        this.legalEntityId = legalEntityId;
        return this;
    }

    public void setLegalEntityId(String legalEntityId) {
        this.legalEntityId = legalEntityId;
    }

    public ChartOfAccounts getChartOfAccounts() {
        return chartOfAccounts;
    }

    public BalanceSheet chartOfAccounts(ChartOfAccounts chartOfAccounts) {
        this.chartOfAccounts = chartOfAccounts;
        return this;
    }

    public void setChartOfAccounts(ChartOfAccounts chartOfAccounts) {
        this.chartOfAccounts = chartOfAccounts;
    }

    public LedgerAccount getAccount() {
        return account;
    }

    public BalanceSheet account(LedgerAccount ledgerAccount) {
        this.account = ledgerAccount;
        return this;
    }

    public void setAccount(LedgerAccount ledgerAccount) {
        this.account = ledgerAccount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BalanceSheet balanceSheet = (BalanceSheet) o;
        if (balanceSheet.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), balanceSheet.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BalanceSheet{" +
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
