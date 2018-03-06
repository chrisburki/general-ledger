package com.avaloq.ledger.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.avaloq.ledger.domain.enumeration.BalanceDateType;

/**
 * A BalanceSheet.
 */
@Entity
@Table(name = "balance_sheet",
    indexes = {@Index(name = "idx_chart_of_accounts",  columnList="chart_of_accounts_id", unique = false)})
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
    @Size(max = 60)
    @Column(name = "jhi_key", length = 60, nullable = false)
    private String key;

    @NotNull
    @Column(name = "balance_date", nullable = false)
    private LocalDate balanceDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "balance_date_type", nullable = false)
    private BalanceDateType balanceDateType;

    @NotNull
    @Column(name = "global_sequence_number", nullable = false)
    private Long globalSequenceNumber;

    @NotNull
    @Column(name = "legal_entity_id", nullable = false)
    private String legalEntityId;

    @ManyToOne
    private ChartOfAccounts chartOfAccounts;

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

    public String getKey() {
        return key;
    }

    public BalanceSheet key(String key) {
        this.key = key;
        return this;
    }

    public void setKey(String key) {
        this.key = key;
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

    public BalanceDateType getBalanceDateType() {
        return balanceDateType;
    }

    public BalanceSheet balanceDateType(BalanceDateType balanceDateType) {
        this.balanceDateType = balanceDateType;
        return this;
    }

    public void setBalanceDateType(BalanceDateType balanceDateType) {
        this.balanceDateType = balanceDateType;
    }

    public Long getGlobalSequenceNumber() {
        return globalSequenceNumber;
    }

    public BalanceSheet globalSequenceNumber(Long globalSequenceNumber) {
        this.globalSequenceNumber = globalSequenceNumber;
        return this;
    }

    public void setGlobalSequenceNumber(Long globalSequenceNumber) {
        this.globalSequenceNumber = globalSequenceNumber;
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
            ", key='" + getKey() + "'" +
            ", balanceDate='" + getBalanceDate() + "'" +
            ", balanceDateType='" + getBalanceDateType() + "'" +
            ", globalSequenceNumber=" + getGlobalSequenceNumber() +
            ", legalEntityId='" + getLegalEntityId() + "'" +
            "}";
    }
}
