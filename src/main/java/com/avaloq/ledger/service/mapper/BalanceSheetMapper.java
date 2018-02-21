package com.avaloq.ledger.service.mapper;

import com.avaloq.ledger.domain.*;
import com.avaloq.ledger.service.dto.BalanceSheetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BalanceSheet and its DTO BalanceSheetDTO.
 */
@Mapper(componentModel = "spring", uses = {ChartOfAccountsMapper.class, LedgerAccountMapper.class})
public interface BalanceSheetMapper extends EntityMapper<BalanceSheetDTO, BalanceSheet> {

    @Mapping(source = "chartOfAccounts.id", target = "chartOfAccountsId")
    @Mapping(source = "account.id", target = "accountId")
    BalanceSheetDTO toDto(BalanceSheet balanceSheet);

    @Mapping(source = "chartOfAccountsId", target = "chartOfAccounts")
    @Mapping(source = "accountId", target = "account")
    BalanceSheet toEntity(BalanceSheetDTO balanceSheetDTO);

    default BalanceSheet fromId(Long id) {
        if (id == null) {
            return null;
        }
        BalanceSheet balanceSheet = new BalanceSheet();
        balanceSheet.setId(id);
        return balanceSheet;
    }
}
