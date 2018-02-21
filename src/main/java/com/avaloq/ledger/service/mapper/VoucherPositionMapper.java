package com.avaloq.ledger.service.mapper;

import com.avaloq.ledger.domain.*;
import com.avaloq.ledger.service.dto.VoucherPositionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VoucherPosition and its DTO VoucherPositionDTO.
 */
@Mapper(componentModel = "spring", uses = {FinancialInstrumentTypeMapper.class, VoucherAccountTypeMapper.class, SubLedgerTypeMapper.class})
public interface VoucherPositionMapper extends EntityMapper<VoucherPositionDTO, VoucherPosition> {

    @Mapping(source = "financialInstrumentType.id", target = "financialInstrumentTypeId")
    @Mapping(source = "accountType.id", target = "accountTypeId")
    @Mapping(source = "subLedgerType.id", target = "subLedgerTypeId")
    VoucherPositionDTO toDto(VoucherPosition voucherPosition);

    @Mapping(source = "financialInstrumentTypeId", target = "financialInstrumentType")
    @Mapping(source = "accountTypeId", target = "accountType")
    @Mapping(source = "subLedgerTypeId", target = "subLedgerType")
    VoucherPosition toEntity(VoucherPositionDTO voucherPositionDTO);

    default VoucherPosition fromId(Long id) {
        if (id == null) {
            return null;
        }
        VoucherPosition voucherPosition = new VoucherPosition();
        voucherPosition.setId(id);
        return voucherPosition;
    }
}
