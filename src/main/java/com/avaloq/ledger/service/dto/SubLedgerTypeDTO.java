package com.avaloq.ledger.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SubLedgerType entity.
 */
public class SubLedgerTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private String category;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SubLedgerTypeDTO subLedgerTypeDTO = (SubLedgerTypeDTO) o;
        if(subLedgerTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), subLedgerTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SubLedgerTypeDTO{" +
            "id=" + getId() +
            ", category='" + getCategory() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
