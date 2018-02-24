package com.avaloq.ledger.service.mapper;

import com.avaloq.ledger.domain.*;
import com.avaloq.ledger.service.dto.VoucherValuationTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VoucherValuationType and its DTO VoucherValuationTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VoucherValuationTypeMapper extends EntityMapper<VoucherValuationTypeDTO, VoucherValuationType> {



    default VoucherValuationType fromId(Long id) {
        if (id == null) {
            return null;
        }
        VoucherValuationType voucherValuationType = new VoucherValuationType();
        voucherValuationType.setId(id);
        return voucherValuationType;
    }
}
