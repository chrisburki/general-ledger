package com.avaloq.ledger.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.avaloq.ledger.domain.enumeration.VoucherDateType;

/**
 * A VoucherValuation.
 */
@Entity
@Table(name = "voucher_valuation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VoucherValuation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "currency_iso")
    private String currencyIso;

    @Column(name = "amount_base_currency")
    private Double amountBaseCurrency;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "date_type", nullable = false)
    private VoucherDateType dateType;

    @NotNull
    @Column(name = "global_sequence_number", nullable = false)
    private Long globalSequenceNumber;

    @Column(name = "position_keeping_id")
    private String positionKeepingId;

    @NotNull
    @Column(name = "legal_entity_id", nullable = false)
    private String legalEntityId;

    @ManyToOne
    private JournalPosting journalPosting;

    @ManyToOne
    private VoucherPosition position;

    @ManyToOne
    private VoucherValuationType valuationType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public VoucherValuation amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrencyIso() {
        return currencyIso;
    }

    public VoucherValuation currencyIso(String currencyIso) {
        this.currencyIso = currencyIso;
        return this;
    }

    public void setCurrencyIso(String currencyIso) {
        this.currencyIso = currencyIso;
    }

    public Double getAmountBaseCurrency() {
        return amountBaseCurrency;
    }

    public VoucherValuation amountBaseCurrency(Double amountBaseCurrency) {
        this.amountBaseCurrency = amountBaseCurrency;
        return this;
    }

    public void setAmountBaseCurrency(Double amountBaseCurrency) {
        this.amountBaseCurrency = amountBaseCurrency;
    }

    public VoucherDateType getDateType() {
        return dateType;
    }

    public VoucherValuation dateType(VoucherDateType dateType) {
        this.dateType = dateType;
        return this;
    }

    public void setDateType(VoucherDateType dateType) {
        this.dateType = dateType;
    }

    public Long getGlobalSequenceNumber() {
        return globalSequenceNumber;
    }

    public VoucherValuation globalSequenceNumber(Long globalSequenceNumber) {
        this.globalSequenceNumber = globalSequenceNumber;
        return this;
    }

    public void setGlobalSequenceNumber(Long globalSequenceNumber) {
        this.globalSequenceNumber = globalSequenceNumber;
    }

    public String getPositionKeepingId() {
        return positionKeepingId;
    }

    public VoucherValuation positionKeepingId(String positionKeepingId) {
        this.positionKeepingId = positionKeepingId;
        return this;
    }

    public void setPositionKeepingId(String positionKeepingId) {
        this.positionKeepingId = positionKeepingId;
    }

    public String getLegalEntityId() {
        return legalEntityId;
    }

    public VoucherValuation legalEntityId(String legalEntityId) {
        this.legalEntityId = legalEntityId;
        return this;
    }

    public void setLegalEntityId(String legalEntityId) {
        this.legalEntityId = legalEntityId;
    }

    public JournalPosting getJournalPosting() {
        return journalPosting;
    }

    public VoucherValuation journalPosting(JournalPosting journalPosting) {
        this.journalPosting = journalPosting;
        return this;
    }

    public void setJournalPosting(JournalPosting journalPosting) {
        this.journalPosting = journalPosting;
    }

    public VoucherPosition getPosition() {
        return position;
    }

    public VoucherValuation position(VoucherPosition voucherPosition) {
        this.position = voucherPosition;
        return this;
    }

    public void setPosition(VoucherPosition voucherPosition) {
        this.position = voucherPosition;
    }

    public VoucherValuationType getValuationType() {
        return valuationType;
    }

    public VoucherValuation valuationType(VoucherValuationType voucherValuationType) {
        this.valuationType = voucherValuationType;
        return this;
    }

    public void setValuationType(VoucherValuationType voucherValuationType) {
        this.valuationType = voucherValuationType;
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
        VoucherValuation voucherValuation = (VoucherValuation) o;
        if (voucherValuation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), voucherValuation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VoucherValuation{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", currencyIso='" + getCurrencyIso() + "'" +
            ", amountBaseCurrency=" + getAmountBaseCurrency() +
            ", dateType='" + getDateType() + "'" +
            ", globalSequenceNumber=" + getGlobalSequenceNumber() +
            ", positionKeepingId='" + getPositionKeepingId() + "'" +
            ", legalEntityId='" + getLegalEntityId() + "'" +
            "}";
    }
}
