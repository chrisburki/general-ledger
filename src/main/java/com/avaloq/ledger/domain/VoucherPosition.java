package com.avaloq.ledger.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A VoucherPosition.
 */
@Entity
@Table(name = "voucher_position")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VoucherPosition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Size(max = 60)
    @Column(name = "jhi_key", length = 60)
    private String key;

    @NotNull
    @Column(name = "currency_iso", nullable = false)
    private String currencyIso;

    @NotNull
    @Column(name = "position_id", nullable = false)
    private String positionId;

    @Column(name = "position_keeping_id")
    private String positionKeepingId;

    @NotNull
    @Column(name = "legal_entity_id", nullable = false)
    private String legalEntityId;

    @ManyToOne
    private FinancialInstrumentType financialInstrumentType;

    @ManyToOne
    private VoucherAccountType accountType;

    @ManyToOne
    private SubLedgerType subLedgerType;

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

    public VoucherPosition name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public VoucherPosition key(String key) {
        this.key = key;
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCurrencyIso() {
        return currencyIso;
    }

    public VoucherPosition currencyIso(String currencyIso) {
        this.currencyIso = currencyIso;
        return this;
    }

    public void setCurrencyIso(String currencyIso) {
        this.currencyIso = currencyIso;
    }

    public String getPositionId() {
        return positionId;
    }

    public VoucherPosition positionId(String positionId) {
        this.positionId = positionId;
        return this;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getPositionKeepingId() {
        return positionKeepingId;
    }

    public VoucherPosition positionKeepingId(String positionKeepingId) {
        this.positionKeepingId = positionKeepingId;
        return this;
    }

    public void setPositionKeepingId(String positionKeepingId) {
        this.positionKeepingId = positionKeepingId;
    }

    public String getLegalEntityId() {
        return legalEntityId;
    }

    public VoucherPosition legalEntityId(String legalEntityId) {
        this.legalEntityId = legalEntityId;
        return this;
    }

    public void setLegalEntityId(String legalEntityId) {
        this.legalEntityId = legalEntityId;
    }

    public FinancialInstrumentType getFinancialInstrumentType() {
        return financialInstrumentType;
    }

    public VoucherPosition financialInstrumentType(FinancialInstrumentType financialInstrumentType) {
        this.financialInstrumentType = financialInstrumentType;
        return this;
    }

    public void setFinancialInstrumentType(FinancialInstrumentType financialInstrumentType) {
        this.financialInstrumentType = financialInstrumentType;
    }

    public VoucherAccountType getAccountType() {
        return accountType;
    }

    public VoucherPosition accountType(VoucherAccountType voucherAccountType) {
        this.accountType = voucherAccountType;
        return this;
    }

    public void setAccountType(VoucherAccountType voucherAccountType) {
        this.accountType = voucherAccountType;
    }

    public SubLedgerType getSubLedgerType() {
        return subLedgerType;
    }

    public VoucherPosition subLedgerType(SubLedgerType subLedgerType) {
        this.subLedgerType = subLedgerType;
        return this;
    }

    public void setSubLedgerType(SubLedgerType subLedgerType) {
        this.subLedgerType = subLedgerType;
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
        VoucherPosition voucherPosition = (VoucherPosition) o;
        if (voucherPosition.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), voucherPosition.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VoucherPosition{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", key='" + getKey() + "'" +
            ", currencyIso='" + getCurrencyIso() + "'" +
            ", positionId='" + getPositionId() + "'" +
            ", positionKeepingId='" + getPositionKeepingId() + "'" +
            ", legalEntityId='" + getLegalEntityId() + "'" +
            "}";
    }
}
