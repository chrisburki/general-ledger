package com.avaloq.ledger.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.avaloq.ledger.domain.enumeration.AccountingStandard;

/**
 * A ChartOfAccounts.
 */
@Entity
@Table(name = "chart_of_accounts")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ChartOfAccounts implements Serializable {

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
    @Column(name = "accounting_standard", nullable = false)
    private AccountingStandard accountingStandard;

    @NotNull
    @Column(name = "base_currency_iso", nullable = false)
    private String baseCurrencyIso;

    @Column(name = "is_main")
    private Boolean isMain;

    @NotNull
    @Column(name = "legal_entity_id", nullable = false)
    private String legalEntityId;

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

    public ChartOfAccounts name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public ChartOfAccounts key(String key) {
        this.key = key;
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public AccountingStandard getAccountingStandard() {
        return accountingStandard;
    }

    public ChartOfAccounts accountingStandard(AccountingStandard accountingStandard) {
        this.accountingStandard = accountingStandard;
        return this;
    }

    public void setAccountingStandard(AccountingStandard accountingStandard) {
        this.accountingStandard = accountingStandard;
    }

    public String getBaseCurrencyIso() {
        return baseCurrencyIso;
    }

    public ChartOfAccounts baseCurrencyIso(String baseCurrencyIso) {
        this.baseCurrencyIso = baseCurrencyIso;
        return this;
    }

    public void setBaseCurrencyIso(String baseCurrencyIso) {
        this.baseCurrencyIso = baseCurrencyIso;
    }

    public Boolean isIsMain() {
        return isMain;
    }

    public ChartOfAccounts isMain(Boolean isMain) {
        this.isMain = isMain;
        return this;
    }

    public void setIsMain(Boolean isMain) {
        this.isMain = isMain;
    }

    public String getLegalEntityId() {
        return legalEntityId;
    }

    public ChartOfAccounts legalEntityId(String legalEntityId) {
        this.legalEntityId = legalEntityId;
        return this;
    }

    public void setLegalEntityId(String legalEntityId) {
        this.legalEntityId = legalEntityId;
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
        ChartOfAccounts chartOfAccounts = (ChartOfAccounts) o;
        if (chartOfAccounts.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chartOfAccounts.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ChartOfAccounts{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", key='" + getKey() + "'" +
            ", accountingStandard='" + getAccountingStandard() + "'" +
            ", baseCurrencyIso='" + getBaseCurrencyIso() + "'" +
            ", isMain='" + isIsMain() + "'" +
            ", legalEntityId='" + getLegalEntityId() + "'" +
            "}";
    }
}
