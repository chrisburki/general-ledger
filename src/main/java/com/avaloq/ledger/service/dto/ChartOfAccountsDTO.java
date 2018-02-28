package com.avaloq.ledger.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.avaloq.ledger.domain.enumeration.AccountingStandard;

/**
 * A DTO for the ChartOfAccounts entity.
 */
public class ChartOfAccountsDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 200)
    private String name;

    @NotNull
    @Size(max = 60)
    private String key;

    @NotNull
    private AccountingStandard accountingStandard;

    @NotNull
    private String baseCurrencyIso;

    private Boolean isMain;

    private String legalEntityId;

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

    public AccountingStandard getAccountingStandard() {
        return accountingStandard;
    }

    public void setAccountingStandard(AccountingStandard accountingStandard) {
        this.accountingStandard = accountingStandard;
    }

    public String getBaseCurrencyIso() {
        return baseCurrencyIso;
    }

    public void setBaseCurrencyIso(String baseCurrencyIso) {
        this.baseCurrencyIso = baseCurrencyIso;
    }

    public Boolean isIsMain() {
        return isMain;
    }

    public void setIsMain(Boolean isMain) {
        this.isMain = isMain;
    }

    public String getLegalEntityId() {
        return legalEntityId;
    }

    public void setLegalEntityId(String legalEntityId) {
        this.legalEntityId = legalEntityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ChartOfAccountsDTO chartOfAccountsDTO = (ChartOfAccountsDTO) o;
        if(chartOfAccountsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chartOfAccountsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ChartOfAccountsDTO{" +
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
