package com.avaloq.ledger.service.mapper;

import com.avaloq.ledger.domain.*;
import com.avaloq.ledger.service.dto.FinancialInstrumentTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FinancialInstrumentType and its DTO FinancialInstrumentTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FinancialInstrumentTypeMapper extends EntityMapper<FinancialInstrumentTypeDTO, FinancialInstrumentType> {



    default FinancialInstrumentType fromId(Long id) {
        if (id == null) {
            return null;
        }
        FinancialInstrumentType financialInstrumentType = new FinancialInstrumentType();
        financialInstrumentType.setId(id);
        return financialInstrumentType;
    }
}
