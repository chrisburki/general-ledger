package com.avaloq.ledger.service.mapper;

import com.avaloq.ledger.domain.*;
import com.avaloq.ledger.service.dto.VoucherAccountTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VoucherAccountType and its DTO VoucherAccountTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VoucherAccountTypeMapper extends EntityMapper<VoucherAccountTypeDTO, VoucherAccountType> {



    default VoucherAccountType fromId(Long id) {
        if (id == null) {
            return null;
        }
        VoucherAccountType voucherAccountType = new VoucherAccountType();
        voucherAccountType.setId(id);
        return voucherAccountType;
    }
}
