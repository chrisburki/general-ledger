package com.avaloq.ledger.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.avaloq.ledger.domain.enumeration.VoucherDateType;

/**
 * A DTO for the VoucherValuation entity.
 */
public class VoucherValuationDTO implements Serializable {

    private Long id;

    @NotNull
    private Double amount;

    private String currencyIso;

    private Double amountBaseCurrency;

    @NotNull
    private VoucherDateType dateType;

    @NotNull
    private Long globalSequenceNumber;

    private String positionKeepingId;

    @NotNull
    private String legalEntityId;

    private Long journalPostingId;

    private Long positionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrencyIso() {
        return currencyIso;
    }

    public void setCurrencyIso(String currencyIso) {
        this.currencyIso = currencyIso;
    }

    public Double getAmountBaseCurrency() {
        return amountBaseCurrency;
    }

    public void setAmountBaseCurrency(Double amountBaseCurrency) {
        this.amountBaseCurrency = amountBaseCurrency;
    }

    public VoucherDateType getDateType() {
        return dateType;
    }

    public void setDateType(VoucherDateType dateType) {
        this.dateType = dateType;
    }

    public Long getGlobalSequenceNumber() {
        return globalSequenceNumber;
    }

    public void setGlobalSequenceNumber(Long globalSequenceNumber) {
        this.globalSequenceNumber = globalSequenceNumber;
    }

    public String getPositionKeepingId() {
        return positionKeepingId;
    }

    public void setPositionKeepingId(String positionKeepingId) {
        this.positionKeepingId = positionKeepingId;
    }

    public String getLegalEntityId() {
        return legalEntityId;
    }

    public void setLegalEntityId(String legalEntityId) {
        this.legalEntityId = legalEntityId;
    }

    public Long getJournalPostingId() {
        return journalPostingId;
    }

    public void setJournalPostingId(Long journalPostingId) {
        this.journalPostingId = journalPostingId;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long voucherPositionId) {
        this.positionId = voucherPositionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VoucherValuationDTO voucherValuationDTO = (VoucherValuationDTO) o;
        if(voucherValuationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), voucherValuationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VoucherValuationDTO{" +
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
