package com.avaloq.ledger.service.mapper;

import com.avaloq.ledger.domain.*;
import com.avaloq.ledger.service.dto.ChartOfAccountsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ChartOfAccounts and its DTO ChartOfAccountsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ChartOfAccountsMapper extends EntityMapper<ChartOfAccountsDTO, ChartOfAccounts> {



    default ChartOfAccounts fromId(Long id) {
        if (id == null) {
            return null;
        }
        ChartOfAccounts chartOfAccounts = new ChartOfAccounts();
        chartOfAccounts.setId(id);
        return chartOfAccounts;
    }
}
