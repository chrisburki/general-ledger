package com.avaloq.ledger.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.avaloq.ledger.domain.enumeration.BalanceDateType;

/**
 * A DTO for the BalanceSheet entity.
 */
public class BalanceSheetDTO implements Serializable {

    private Long id;

    private String description;

    @NotNull
    @Size(max = 60)
    private String key;

    @NotNull
    private LocalDate balanceDate;

    @NotNull
    private BalanceDateType balanceDateType;

    @NotNull
    private Long globalSequenceNumber;

    @NotNull
    private String legalEntityId;

    private Long chartOfAccountsId;

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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public LocalDate getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(LocalDate balanceDate) {
        this.balanceDate = balanceDate;
    }

    public BalanceDateType getBalanceDateType() {
        return balanceDateType;
    }

    public void setBalanceDateType(BalanceDateType balanceDateType) {
        this.balanceDateType = balanceDateType;
    }

    public Long getGlobalSequenceNumber() {
        return globalSequenceNumber;
    }

    public void setGlobalSequenceNumber(Long globalSequenceNumber) {
        this.globalSequenceNumber = globalSequenceNumber;
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
            ", key='" + getKey() + "'" +
            ", balanceDate='" + getBalanceDate() + "'" +
            ", balanceDateType='" + getBalanceDateType() + "'" +
            ", globalSequenceNumber=" + getGlobalSequenceNumber() +
            ", legalEntityId='" + getLegalEntityId() + "'" +
            "}";
    }
}
