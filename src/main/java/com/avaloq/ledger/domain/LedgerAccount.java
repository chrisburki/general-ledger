package com.avaloq.ledger.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.avaloq.ledger.domain.enumeration.LedgerAccountType;

/**
 * A LedgerAccount.
 */
@Entity
@Table(name = "ledger_account",
    indexes = {@Index(name = "idx_la_key_entity",  columnList="jhi_key,legal_entity_id", unique = true)})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LedgerAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @NotNull
    @Size(max = 60)
    @Column(name = "jhi_key", length = 60, nullable = false)
    private String key;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private LedgerAccountType accountType;

    @Column(name = "ordered_by")
    private String orderedBy;

    @Column(name = "jhi_level")
    private Integer level;

    @Column(name = "isleaf")
    private Boolean isleaf;

    @Column(name = "balance_account_id")
    private String balanceAccountId;

    @NotNull
    @Column(name = "legal_entity_id", nullable = false)
    private String legalEntityId;

    @ManyToOne
    private ChartOfAccounts chartOfAccounts;

    @ManyToOne
    private LedgerAccount upperAccount;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public LedgerAccount name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public LedgerAccount key(String key) {
        this.key = key;
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public LedgerAccountType getAccountType() {
        return accountType;
    }

    public LedgerAccount accountType(LedgerAccountType accountType) {
        this.accountType = accountType;
        return this;
    }

    public void setAccountType(LedgerAccountType accountType) {
        this.accountType = accountType;
    }

    public String getOrderedBy() {
        return orderedBy;
    }

    public LedgerAccount orderedBy(String orderedBy) {
        this.orderedBy = orderedBy;
        return this;
    }

    public void setOrderedBy(String orderedBy) {
        this.orderedBy = orderedBy;
    }

    public Integer getLevel() {
        return level;
    }

    public LedgerAccount level(Integer level) {
        this.level = level;
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Boolean isIsleaf() {
        return isleaf;
    }

    public LedgerAccount isleaf(Boolean isleaf) {
        this.isleaf = isleaf;
        return this;
    }

    public void setIsleaf(Boolean isleaf) {
        this.isleaf = isleaf;
    }

    public String getBalanceAccountId() {
        return balanceAccountId;
    }

    public LedgerAccount balanceAccountId(String balanceAccountId) {
        this.balanceAccountId = balanceAccountId;
        return this;
    }

    public void setBalanceAccountId(String balanceAccountId) {
        this.balanceAccountId = balanceAccountId;
    }

    public String getLegalEntityId() {
        return legalEntityId;
    }

    public LedgerAccount legalEntityId(String legalEntityId) {
        this.legalEntityId = legalEntityId;
        return this;
    }

    public void setLegalEntityId(String legalEntityId) {
        this.legalEntityId = legalEntityId;
    }

    public ChartOfAccounts getChartOfAccounts() {
        return chartOfAccounts;
    }

    public LedgerAccount chartOfAccounts(ChartOfAccounts chartOfAccounts) {
        this.chartOfAccounts = chartOfAccounts;
        return this;
    }

    public void setChartOfAccounts(ChartOfAccounts chartOfAccounts) {
        this.chartOfAccounts = chartOfAccounts;
    }

    public LedgerAccount getUpperAccount() {
        return upperAccount;
    }

    public LedgerAccount upperAccount(LedgerAccount ledgerAccount) {
        this.upperAccount = ledgerAccount;
        return this;
    }

    public void setUpperAccount(LedgerAccount ledgerAccount) {
        this.upperAccount = ledgerAccount;
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
        LedgerAccount ledgerAccount = (LedgerAccount) o;
        if (ledgerAccount.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ledgerAccount.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LedgerAccount{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", key='" + getKey() + "'" +
            ", accountType='" + getAccountType() + "'" +
            ", orderedBy='" + getOrderedBy() + "'" +
            ", level=" + getLevel() +
            ", isleaf='" + isIsleaf() + "'" +
            ", balanceAccountId='" + getBalanceAccountId() + "'" +
            ", legalEntityId='" + getLegalEntityId() + "'" +
            "}";
    }
}
