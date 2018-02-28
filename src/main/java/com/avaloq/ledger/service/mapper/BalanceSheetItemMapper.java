package com.avaloq.ledger.service.mapper;

import com.avaloq.ledger.domain.*;
import com.avaloq.ledger.service.dto.BalanceSheetItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BalanceSheetItem and its DTO BalanceSheetItemDTO.
 */
@Mapper(componentModel = "spring", uses = {BalanceSheetMapper.class, LedgerAccountMapper.class})
public interface BalanceSheetItemMapper extends EntityMapper<BalanceSheetItemDTO, BalanceSheetItem> {

    @Mapping(source = "balanceSheet.id", target = "balanceSheetId")
    @Mapping(source = "account.id", target = "accountId")
    BalanceSheetItemDTO toDto(BalanceSheetItem balanceSheetItem);

    @Mapping(source = "balanceSheetId", target = "balanceSheet")
    @Mapping(source = "accountId", target = "account")
    BalanceSheetItem toEntity(BalanceSheetItemDTO balanceSheetItemDTO);

    default BalanceSheetItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        BalanceSheetItem balanceSheetItem = new BalanceSheetItem();
        balanceSheetItem.setId(id);
        return balanceSheetItem;
    }
}
