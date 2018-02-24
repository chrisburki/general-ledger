package com.avaloq.ledger.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the VoucherValuationType entity.
 */
public class VoucherValuationTypeDTO implements Serializable {

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

        VoucherValuationTypeDTO voucherValuationTypeDTO = (VoucherValuationTypeDTO) o;
        if(voucherValuationTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), voucherValuationTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VoucherValuationTypeDTO{" +
            "id=" + getId() +
            ", category='" + getCategory() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
