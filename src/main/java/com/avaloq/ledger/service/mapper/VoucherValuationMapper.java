package com.avaloq.ledger.service.mapper;

import com.avaloq.ledger.domain.*;
import com.avaloq.ledger.service.dto.VoucherValuationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VoucherValuation and its DTO VoucherValuationDTO.
 */
@Mapper(componentModel = "spring", uses = {JournalPostingMapper.class, VoucherPositionMapper.class})
public interface VoucherValuationMapper extends EntityMapper<VoucherValuationDTO, VoucherValuation> {

    @Mapping(source = "journalPosting.id", target = "journalPostingId")
    @Mapping(source = "position.id", target = "positionId")
    VoucherValuationDTO toDto(VoucherValuation voucherValuation);

    @Mapping(source = "journalPostingId", target = "journalPosting")
    @Mapping(source = "positionId", target = "position")
    VoucherValuation toEntity(VoucherValuationDTO voucherValuationDTO);

    default VoucherValuation fromId(Long id) {
        if (id == null) {
            return null;
        }
        VoucherValuation voucherValuation = new VoucherValuation();
        voucherValuation.setId(id);
        return voucherValuation;
    }
}
