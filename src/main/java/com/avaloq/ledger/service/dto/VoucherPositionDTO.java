package com.avaloq.ledger.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the VoucherPosition entity.
 */
public class VoucherPositionDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 200)
    private String name;

    @Size(max = 60)
    private String key;

    @NotNull
    private String currencyIso;

    @NotNull
    private String positionId;

    private String positionKeepingId;

    @NotNull
    private String legalEntityId;

    private Long financialInstrumentTypeId;

    private Long accountTypeId;

    private Long subLedgerTypeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCurrencyIso() {
        return currencyIso;
    }

    public void setCurrencyIso(String currencyIso) {
        this.currencyIso = currencyIso;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
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

    public Long getFinancialInstrumentTypeId() {
        return financialInstrumentTypeId;
    }

    public void setFinancialInstrumentTypeId(Long financialInstrumentTypeId) {
        this.financialInstrumentTypeId = financialInstrumentTypeId;
    }

    public Long getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(Long voucherAccountTypeId) {
        this.accountTypeId = voucherAccountTypeId;
    }

    public Long getSubLedgerTypeId() {
        return subLedgerTypeId;
    }

    public void setSubLedgerTypeId(Long subLedgerTypeId) {
        this.subLedgerTypeId = subLedgerTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VoucherPositionDTO voucherPositionDTO = (VoucherPositionDTO) o;
        if(voucherPositionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), voucherPositionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VoucherPositionDTO{" +
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
