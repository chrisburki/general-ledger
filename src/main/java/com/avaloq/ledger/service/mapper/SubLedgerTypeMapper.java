package com.avaloq.ledger.service.mapper;

import com.avaloq.ledger.domain.*;
import com.avaloq.ledger.service.dto.SubLedgerTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SubLedgerType and its DTO SubLedgerTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SubLedgerTypeMapper extends EntityMapper<SubLedgerTypeDTO, SubLedgerType> {



    default SubLedgerType fromId(Long id) {
        if (id == null) {
            return null;
        }
        SubLedgerType subLedgerType = new SubLedgerType();
        subLedgerType.setId(id);
        return subLedgerType;
    }
}
