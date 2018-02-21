package com.avaloq.ledger.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.avaloq.ledger.domain.enumeration.LedgerAccountType;

/**
 * A DTO for the LedgerAccount entity.
 */
public class LedgerAccountDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 200)
    private String name;

    @NotNull
    @Size(max = 60)
    private String key;

    @NotNull
    private LedgerAccountType accountType;

    private String orderedBy;

    private Integer level;

    private Boolean isleaf;

    private String balanceAccountId;

    @NotNull
    private String legalEntityId;

    private Long chartOfAccountsId;

    private Long upperAccountId;

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

    public LedgerAccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(LedgerAccountType accountType) {
        this.accountType = accountType;
    }

    public String getOrderedBy() {
        return orderedBy;
    }

    public void setOrderedBy(String orderedBy) {
        this.orderedBy = orderedBy;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Boolean isIsleaf() {
        return isleaf;
    }

    public void setIsleaf(Boolean isleaf) {
        this.isleaf = isleaf;
    }

    public String getBalanceAccountId() {
        return balanceAccountId;
    }

    public void setBalanceAccountId(String balanceAccountId) {
        this.balanceAccountId = balanceAccountId;
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

    public Long getUpperAccountId() {
        return upperAccountId;
    }

    public void setUpperAccountId(Long ledgerAccountId) {
        this.upperAccountId = ledgerAccountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LedgerAccountDTO ledgerAccountDTO = (LedgerAccountDTO) o;
        if(ledgerAccountDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ledgerAccountDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LedgerAccountDTO{" +
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
